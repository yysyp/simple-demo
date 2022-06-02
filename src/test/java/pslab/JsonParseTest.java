package pslab;

import com.alibaba.excel.metadata.Sheet;
import com.fasterxml.jackson.core.type.TypeReference;
import com.github.houbb.opencc4j.util.ZhConverterUtil;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.plexus.util.StringUtils;
import ps.demo.dto.AddrInfo;
import ps.demo.util.*;

import java.io.File;
import java.util.*;

@Slf4j
public class JsonParseTest {

    public static final String EMPTY = "海外";
    private static Map<String, List<String>> areaProvice;

    public static void main(String[] args) throws Exception {
        Map<String, Object> provinceCityMap = MyJsonUtil.json2SimpleMap(
                MyReadWriteUtil.readFileContent(new File("meta/provinceAndCity.json")));
        Properties shortNameExt = MyReadWriteUtil.readProperties(MyFileUtil.getFileInHomeDir("companyShortNameExt.properties"));
        Map<String, Object> provinceMap = (Map<String, Object>) provinceCityMap.get("00");
        TypeReference<Map<String, List<String>>> typeReference = new TypeReference<Map<String, List<String>>>() {
        };
        areaProvice = MyJsonUtil.jsonString2Object(
                MyReadWriteUtil.readFileContent(new File("meta/area-province.json")), typeReference);
        log.info("areaProvice={}", areaProvice);

        Properties companyAddrProperties = MyReadWriteUtil.readProperties(MyFileUtil.getFileInHomeDir("company-address.properties"));

        List<Object> shortFullNameExcelSheet = MyExcelUtil.readMoreThan1000RowBySheet(
                MyFileUtil.getFileInHomeDir("2021年和2022年1-5月统计表(1).xlsx").getPath(),
                new Sheet(3));
        Map<String, String> shortFullNameMap = constructShortFullName(shortFullNameExcelSheet);

        List<List<Object>> shortNameFullNameProvinceCityTable = new ArrayList<>();

        List<Object> excelLines = MyExcelUtil.readMoreThan1000RowBySheet(
                MyFileUtil.getFileInHomeDir("2021年和2022年1-5月统计表(1).xlsx").getPath(),
                new Sheet(2)); //sheetNo from 1... NOT 0

        int count = 0;
        for (Object line : excelLines) {
            List<Object> oneLine = (List<Object>) line;
            String shortName = oneLine.get(5) + "";
            if (shortName.trim().equals("客户简称")) {
                continue;
            }
            count++;
            List<Object> shortNameFullNameProvinceCityOneLine = new ArrayList<>();
            shortNameFullNameProvinceCityTable.add(shortNameFullNameProvinceCityOneLine);
            shortNameFullNameProvinceCityOneLine.add(count);
            String fullName = shortFullNameMap.get(shortName);
            shortNameFullNameProvinceCityOneLine.add(shortName);

            if (StringUtils.isBlank(fullName)) {
                if (shortNameExt.containsKey(shortName)) {
                    fullName = shortNameExt.getProperty(shortName);
                } else {
                    fullName = findMostSimilarMatch(shortName, shortFullNameMap);
                }
            }

            shortNameFullNameProvinceCityOneLine.add(fullName == null ? EMPTY : fullName);
            if (StringUtils.isBlank(fullName)) {
                fullName = shortName;
            }
            fullName = fullName.trim();
            if (!StringUtils.isBlank(fullName)) {
                AddrInfo addrInfo = findProvinceAndCityByFullName(provinceCityMap, provinceMap, shortNameFullNameProvinceCityOneLine, fullName);
                String regularName = MyRegexUtil.regularString(fullName);
                if (addrInfo == null && companyAddrProperties.containsKey(regularName)) {
                    fullName = companyAddrProperties.getProperty(regularName);
                    addrInfo = findProvinceAndCityByFullName(provinceCityMap, provinceMap, shortNameFullNameProvinceCityOneLine, fullName);
                    if (addrInfo != null) {
                        shortNameFullNameProvinceCityOneLine.add(addrInfo.getProvince());
                        shortNameFullNameProvinceCityOneLine.add(findAreaByProvinceName(addrInfo.getProvince(), areaProvice));
                    } else {
                        shortNameFullNameProvinceCityOneLine.add(EMPTY);
                        shortNameFullNameProvinceCityOneLine.add(EMPTY);
                    }
                    shortNameFullNameProvinceCityOneLine.add("*");
                } else {
                    if (addrInfo == null) {
                        shortNameFullNameProvinceCityOneLine.add(EMPTY);
                        shortNameFullNameProvinceCityOneLine.add(EMPTY);
                    } else {
                        shortNameFullNameProvinceCityOneLine.add(addrInfo.getProvince());
                        shortNameFullNameProvinceCityOneLine.add(findAreaByProvinceName(addrInfo.getProvince(), areaProvice));
                    }
                }
            }
        }

        log.info("shortNameFullNameProvinceCityTable={}", shortNameFullNameProvinceCityTable);


        MyExcelUtil.writeBySimple(MyFileUtil.getFileTsInHomeDir("shortNameFullNameProvinceCity.xlsx").getPath(),
                shortNameFullNameProvinceCityTable,
                Arrays.asList("Index", "Short Name", "Full Name", "Province", "Area", "City"));

    }

    public static String findMostSimilarMatch(String key, Map<String, String> shortFullNameMap) {
        key = ZhConverterUtil.toSimple(key).replaceAll("有限公司", "")
                .replaceAll("分公司", "")
                .replaceAll("科技", "").replaceAll("集团", "")
                .replaceAll("课题组", "");

                
        double score = 0.0d;
        String toKey = null;
        String toValue = null;
        for (String mk : shortFullNameMap.keySet()) {
            mk = ZhConverterUtil.toSimple(mk).replaceAll("有限公司", "")
                    .replaceAll("分公司", "")
                    .replaceAll("科技", "").replaceAll("集团", "")
                    .replaceAll("课题组", "");

            double cs = MyStringUtil.mixSimilarity(key, mk);
            if(key.length() >= 4 && mk.length() >= 4) {
                boolean contains = MyStringUtil.eitherContains(key, mk);
                if (contains) {
                    cs = 0.9d;
                }
            }
            if (cs > score) {
                score = cs;
                toKey = mk;
                toValue = shortFullNameMap.get(mk);
            }
        }
        if (score < 0.8) {
            log.warn("===>>Similarity match [{}] ~TO {} score={} !!!", key, toKey, score);
        } else {
            log.info("===>>Similarity match [{}] ~TO {} score={}", key, toKey, score);
        }
        return toValue;
    }

    private static AddrInfo findProvinceAndCityByFullName(Map<String, Object> provinceCityMap,
                                                          Map<String, Object> provinceMap,
                                                          List<Object> shortNameFullNameProvinceCityOneLine,
                                                          String fullName) {
        AddrInfo addrInfo = findProvinceAndCity(fullName, provinceCityMap);
        if (addrInfo == null) {
            String provinceName = findProvince(fullName, provinceMap);
            if (StringUtils.isNotBlank(provinceName)) {
                return AddrInfo.builder().province(provinceName).build();
            }
        }
        return addrInfo;
    }

    private static AddrInfo findProvinceAndCity(String key, Map<String, Object> map) {
        Map<String, Object> provinceMap = (Map<String, Object>) map.get("00");
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String proviceCode = entry.getKey();
            if (proviceCode.equals("00")) {
                continue;
            }
            Map<String, Object> pMap = (Map<String, Object>) map.get(proviceCode);

            for (Map.Entry<String, Object> cityEntry : pMap.entrySet()) {
                String cityCode = cityEntry.getKey();
                String cityName = (cityEntry.getValue() + "")
                        .replace("市", "")
                        .replace("自治州", "")
                        .replace("地区", "")
                        .replace("区", "")
                        .trim();
                if (key.contains(cityName)) {
                    AddrInfo addrInfo = AddrInfo.builder().province("" + provinceMap.get(proviceCode))
                            .city("" + cityEntry.getValue()).build();
                    return addrInfo;
                }
            }
        }
        return null;
    }

    private static String findProvince(String key, Map<String, Object> provinceMap) {
        for (Map.Entry<String, Object> entry : provinceMap.entrySet()) {
            String provinceCode = entry.getKey();
            String provinceName = (entry.getValue() + "")
                    .replace("省", "")
                    .replace("自治区", "")
                    .replace("特别行政区", "")
                    .trim();
            if (key.contains(provinceName)) {
                return entry.getValue() + "";
            }
        }
        return "";
    }

    public static Map<String, String> constructShortFullName(List<Object> excelLines) {
        Map<String, String> map = new HashMap<>();
        for (Object line : excelLines) {
            List<Object> oneLine = (List<Object>) line;
            map.put(oneLine.get(1) + "", oneLine.get(2) + "");
        }
        return map;
    }

    public static String findAreaByProvinceName(String providerName, Map<String, List<String>> areaProvice) {
        String provinceShort = (providerName + "")
                .replace("省", "")
                .replace("自治区", "")
                .replace("特别行政区", "")
                .trim();
        for (String area : areaProvice.keySet()) {
            List<String> province = areaProvice.get(area);
            for (String city : province) {
                if (city.contains(provinceShort)) {
                    return area;
                }
            }
        }
        return "";
    }

}

