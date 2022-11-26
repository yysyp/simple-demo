

package ps.demo.newstock.service;

import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Collection;

import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.math.*;

import java.util.stream.Collectors;

import com.google.common.collect.ImmutableMap;
import org.springframework.transaction.annotation.Transactional;
import ps.demo.newstock.constant.StkConstant;
import ps.demo.order.repository.NewCartRepository;
import ps.demo.newstock.dto.NewStockDataDto;
import ps.demo.newstock.dto.NewStockDataReq;
import ps.demo.newstock.entity.NewStockData;
import ps.demo.newstock.repository.NewStockDataDao;
import ps.demo.util.MyBeanUtil;

import javax.persistence.criteria.*;

import lombok.*;

import java.util.*;
import java.math.*;

@Slf4j
@Service
public class NewStockDataServiceImpl {

    @Autowired
    NewStockDataDao newStockDataDao;

    @Transactional
    public NewStockDataDto save(NewStockDataDto newStockDataDto) {
        NewStockData newStockData = new NewStockData();
        MyBeanUtil.copyProperties(newStockDataDto, newStockData);
        NewStockData entity = newStockDataDao.save(newStockData);
        MyBeanUtil.copyProperties(entity, newStockDataDto);
        return newStockDataDto;
    }

//    @Transactional
//    public List<NewStockDataDto> saveAll(Collection<NewStockDataDto> newStockDataDtoList) {
//        if (CollectionUtils.isEmpty(newStockDataDtoList)) {
//            return new ArrayList<>();
//        }
//        List<NewStockDataDto> result = new ArrayList<>();
//        for (NewStockDataDto newStockDataDto : newStockDataDtoList) {
//            result.add(save(newStockDataDto));
//        }
//        return result;
//    }

    public NewStockDataDto findById(Long id) {
        Optional<NewStockData> newStockDataOptional = newStockDataDao.findById(id);
        NewStockDataDto newStockDataDto = new NewStockDataDto();
        newStockDataOptional.ifPresent(e -> {
            MyBeanUtil.copyProperties(e, newStockDataDto);
        });
        return newStockDataDto;
    }

    public List<NewStockDataDto> findByAttribute(String attributeName, Object attribute) {
        Specification<NewStockData> spec = new Specification<NewStockData>() {
            @Override
            public Predicate toPredicate(Root<NewStockData> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.and(cb.equal(root.get(attributeName), attribute));
            }
        };
        List<NewStockData> newStockDataList = newStockDataDao.findAll(spec);
        return MyBeanUtil.copyAndConvertItems(newStockDataList, NewStockDataDto.class);
    }

    //@Transactional(readOnly = true)
    public List<NewStockDataDto> findAll() {
        List<NewStockData> newStockDataList = newStockDataDao.findAll();
        List<NewStockDataDto> newStockDataDtoList = new ArrayList<>();
        for (NewStockData newStockData : newStockDataList) {
            NewStockDataDto newStockDataDto = new NewStockDataDto();
            MyBeanUtil.copyProperties(newStockData, newStockDataDto);
            newStockDataDtoList.add(newStockDataDto);
        }
        return newStockDataDtoList;
    }

    public Page<NewStockDataDto> findInPage(Pageable pageable) {
        Page<NewStockData> page = newStockDataDao.findAll(pageable);
        Page<NewStockDataDto> pageDto = page.map((e) -> {
            NewStockDataDto newStockDataDto = new NewStockDataDto();
            MyBeanUtil.copyProperties(e, newStockDataDto);
            return newStockDataDto;
        });
        return pageDto;
    }

    public List<NewStockDataDto> findByAttributes(NewStockDataDto newStockDataDto, boolean orLike) {
        NewStockData newStockData = new NewStockData();
        MyBeanUtil.copyProperties(newStockDataDto, newStockData);
        Specification<NewStockData> spec = constructSpecification(newStockData, orLike);
        List<NewStockData> newStockDataList = newStockDataDao.findAll(spec);
        return MyBeanUtil.copyAndConvertItems(newStockDataList, NewStockDataDto.class);
    }

    public Page<NewStockDataDto> findByAttributesInPage(NewStockDataDto newStockDataDto, boolean orLike, Pageable pageable) {
        NewStockData newStockData = new NewStockData();
        MyBeanUtil.copyProperties(newStockDataDto, newStockData);
        Specification<NewStockData> spec = constructSpecification(newStockData, orLike);

        Page<NewStockData> page = newStockDataDao.findAll(spec, pageable);
        Page<NewStockDataDto> pageDto = page.map((e) -> {
            NewStockDataDto newStockDataDtoResult = new NewStockDataDto();
            MyBeanUtil.copyProperties(e, newStockDataDtoResult);
            return newStockDataDtoResult;
        });
        return pageDto;
    }

    private Specification<NewStockData> constructSpecification(NewStockData newStockData, boolean orLike) {
        Specification<NewStockData> spec = new Specification<NewStockData>() {
            @Override
            public Predicate toPredicate(Root<NewStockData> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = null;
                if (orLike) {
                
                    predicate = orLike(predicate, cb, root,"companyCode", newStockData.getCompanyCode());
                    predicate = orEqual(predicate, cb, root,"periodYear", newStockData.getPeriodYear());
                    predicate = orEqual(predicate, cb, root,"periodMonth", newStockData.getPeriodMonth());
                    predicate = orLike(predicate, cb, root,"companyName", newStockData.getCompanyName());
                    predicate = orLike(predicate, cb, root,"rawPeriod", newStockData.getRawPeriod());
                    predicate = orLike(predicate, cb, root,"kemuType", newStockData.getKemuType());
                    predicate = orLike(predicate, cb, root,"rawKemu", newStockData.getRawKemu());
                    predicate = orLike(predicate, cb, root,"kemuEn", newStockData.getKemuEn());
                    predicate = orLike(predicate, cb, root,"kemu", newStockData.getKemu());
                    predicate = orLike(predicate, cb, root,"rawKemuValue", newStockData.getRawKemuValue());
                    predicate = orEqual(predicate, cb, root,"kemuValue", newStockData.getKemuValue());
                    predicate = orEqual(predicate, cb, root,"yoy", newStockData.getYoy());
                    predicate = orEqual(predicate, cb, root,"pctInAssetOrRevenue", newStockData.getPctInAssetOrRevenue());
                    predicate = orEqual(predicate, cb, root,"coreProfitOnAssetEffect", newStockData.getCoreProfitOnAssetEffect());
                    predicate = orEqual(predicate, cb, root,"flag", newStockData.getFlag());
                    predicate = orLike(predicate, cb, root,"fileName", newStockData.getFileName());
                    predicate = orLike(predicate, cb, root,"comments", newStockData.getComments());

                } else {
                
                    predicate = andEqual(predicate, cb, root, "companyCode", newStockData.getCompanyCode());
                    predicate = andEqual(predicate, cb, root, "periodYear", newStockData.getPeriodYear());
                    predicate = andEqual(predicate, cb, root, "periodMonth", newStockData.getPeriodMonth());
                    predicate = andEqual(predicate, cb, root, "companyName", newStockData.getCompanyName());
                    predicate = andEqual(predicate, cb, root, "rawPeriod", newStockData.getRawPeriod());
                    predicate = andEqual(predicate, cb, root, "kemuType", newStockData.getKemuType());
                    predicate = andEqual(predicate, cb, root, "rawKemu", newStockData.getRawKemu());
                    predicate = andEqual(predicate, cb, root, "kemuEn", newStockData.getKemuEn());
                    predicate = andEqual(predicate, cb, root, "kemu", newStockData.getKemu());
                    predicate = andEqual(predicate, cb, root, "rawKemuValue", newStockData.getRawKemuValue());
                    predicate = andEqual(predicate, cb, root, "kemuValue", newStockData.getKemuValue());
                    predicate = andEqual(predicate, cb, root, "yoy", newStockData.getYoy());
                    predicate = andEqual(predicate, cb, root, "pctInAssetOrRevenue", newStockData.getPctInAssetOrRevenue());
                    predicate = andEqual(predicate, cb, root, "coreProfitOnAssetEffect", newStockData.getCoreProfitOnAssetEffect());
                    predicate = andEqual(predicate, cb, root, "flag", newStockData.getFlag());
                    predicate = andEqual(predicate, cb, root, "fileName", newStockData.getFileName());
                    predicate = andEqual(predicate, cb, root, "comments", newStockData.getComments());
                
                }

                return predicate;
            }
        };
        return spec;
    }

    private Predicate andEqual(Predicate predicate, CriteriaBuilder cb, Root<NewStockData> root, String attributeName, Object attributeValue) {
        if (null == attributeValue) {
            return predicate;
        }
        if (null == predicate) {
            return cb.or(cb.equal(root.get(attributeName), attributeValue));
        } else {
            return cb.or(predicate, cb.equal(root.get(attributeName), attributeValue));
        }
    }
    private Predicate orEqual(Predicate predicate, CriteriaBuilder cb, Root<NewStockData> root, String attributeName, Object attributeValue) {
        if (null == attributeValue) {
            return predicate;
        }
        if (null == predicate) {
            return cb.or(cb.equal(root.get(attributeName), attributeValue));
        } else {
            return cb.or(predicate, cb.equal(root.get(attributeName), attributeValue));
        }
    }
    private Predicate orLike(Predicate predicate, CriteriaBuilder cb, Root<NewStockData> root, String attributeName, String attributeValue) {
        if (null == attributeValue) {
            return predicate;
        }
        if (null == predicate) {
            return cb.or(cb.like(root.get(attributeName), attributeValue));
        } else {
            return cb.or(predicate, cb.like(root.get(attributeName), attributeValue));
        }
    }

    @Transactional
    public void deleteById(Long id) {
        newStockDataDao.deleteById(id);
    }

    @Transactional
    public void deleteAll(Collection<Long> idList) {
        if (CollectionUtils.isEmpty(idList)) {
            return;
        }
        for (Long id : idList) {
            deleteById(id);
        }
    }


    @Transactional
    public NewStockData save(NewStockData newStockData) {

        NewStockData entity = newStockDataDao.save(newStockData);

        return entity;
    }

    public List<NewStockData> findByCompanyCodePeriodKemu(String companyCode, Integer year, Integer month, String kemu) {
        List<NewStockData> list = newStockDataDao.findByCompanyCodeAndPeriodYearAndPeriodMonthAndKemu(companyCode, year, month, kemu);
        return list;
    }

    public List<NewStockData> findByCompanyCodePeriod(String companyCode, Integer year, Integer month) {
        List<NewStockData> list = newStockDataDao.findByCompanyCodeAndPeriodYearAndPeriodMonthOrderByIdAsc(companyCode, year, month);
        return list;
    }

    public List<NewStockData> findByCompanyCodePeriodWithNullYoy(String companyCode, Integer year, Integer month) {
        List<NewStockData> list = newStockDataDao.findByCompanyCodeAndPeriodYearAndPeriodMonthAndYoy(companyCode, year, month, null);
        return list;
    }


    public List<NewStockData> findByCompanyCodeAndPeriodMonthAndKemuOrderByPeriodYearAsc(String companyCode, Integer month, String kemu) {
        List<NewStockData> list = newStockDataDao.findByCompanyCodeAndPeriodMonthAndKemuOrderByPeriodYearAsc(companyCode, month, kemu);
        return list;
    }

    @Transactional
    public List<NewStockData> saveAll(Collection<NewStockData> newStockDataList) {
        if (CollectionUtils.isEmpty(newStockDataList)) {
            return new ArrayList<>();
        }
        return newStockDataDao.saveAll(newStockDataList);
    }

    public boolean existsByAllKemuType(String companyCode, Integer year, Integer month) {
        return newStockDataDao.existsByCompanyCodeAndPeriodYearAndPeriodMonthAndKemuType(
                companyCode,
                year,
                month,
                StkConstant.debt
        ) && newStockDataDao.existsByCompanyCodeAndPeriodYearAndPeriodMonthAndKemuType(
                companyCode,
                year,
                month,
                StkConstant.benefit
        ) && newStockDataDao.existsByCompanyCodeAndPeriodYearAndPeriodMonthAndKemuType(
                companyCode,
                year,
                month,
                StkConstant.cash
        );
    }

    @Transactional
    public void deleteByCompanyCode(String companyCode) {
        newStockDataDao.deleteByCompanyCode(companyCode);
    }

}


