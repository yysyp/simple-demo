package [(${packageName}+'.'+${moduleName}+'.'+${controllerFolder})];

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;
import ps.demo.common.MyBaseController;
import ps.demo.common.MyPageReq;
import ps.demo.common.MyPageResData;
import [(${packageName}+'.'+${moduleName}+'.'+${dtoFolder})].[(${dtoName})];
import [(${packageName}+'.'+${moduleName}+'.'+${dtoFolder})].[(${reqName})];
import [(${packageName}+'.'+${moduleName}+'.'+${serviceFolder})].[(${serviceName})];
import ps.demo.util.MyBeanUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import lombok.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.math.*;

@Slf4j
@RestController
@RequestMapping("/api/[(${moduleName})]/[(${uriName})]")
public class [(${controllerName})] extends MyBaseController {

    @Autowired
    private [(${serviceName})] [(${serviceKey})];

    @GetMapping("/form")
    public ModelAndView createForm(Model model) {
        model.addAttribute("[(${dtoKey})]", new [(${dtoName})]());
        return new ModelAndView("[(${moduleName})]/[(${uriName})]-form", "[(${entityKey})]Model", model);
    }

    @PostMapping("/save")
    public ModelAndView save([(${reqName})] [(${reqKey})]) {
        [(${dtoName})] [(${dtoKey})] = new [(${dtoName})]();
        MyBeanUtil.copyProperties([(${reqKey})], [(${dtoKey})]);
        initBaseCreateModifyTs([(${dtoKey})]);
        [(${dtoName})] [(${entityKey})]Result = [(${serviceKey})].save([(${dtoKey})]);
        return new ModelAndView("redirect:/api/[(${moduleName})]/[(${uriName})]");
    }

    @GetMapping("/list")
    public ModelAndView navigateToQuery() {
        return new ModelAndView("redirect:/api/[(${moduleName})]/[(${uriName})]");
    }

    @GetMapping
    public ModelAndView query(Model model) {
        [(${reqName})] [(${reqKey})] = new [(${reqName})]();
        model.addAttribute("[(${reqKey})]", [(${reqKey})]);
        Pageable pageable = constructPagable([(${reqKey})]);
        Page<[(${dtoName})]> [(${dtoKey})]Page = [(${serviceKey})].findByPage(pageable);
        MyPageResData<[(${dtoName})]> myPageResData = new MyPageResData<>([(${dtoKey})]Page,
                [(${reqKey})].getCurrent(), [(${reqKey})].getSize());
        model.addAttribute("[(${reqKey})]", [(${reqKey})]);
        model.addAttribute("page", myPageResData);
        return new ModelAndView("[(${moduleName})]/[(${uriName})]-list", "[(${entityKey})]Model", model);
    }

    @GetMapping("/{id}")
    public ModelAndView getById(@PathVariable("id") Long id, Model model) {
        [(${dtoName})] [(${dtoKey})] = [(${serviceKey})].findById(id);
        model.addAttribute("[(${dtoKey})]", [(${dtoKey})]);
        return new ModelAndView("[(${moduleName})]/[(${uriName})]-view", "[(${entityKey})]Model", model);
    }

    @PostMapping("/list")
    public ModelAndView list(Model model, [(${reqName})] [(${reqKey})]) {
        Pageable pageable = constructPagable([(${reqKey})]);
        [(${dtoName})] [(${dtoKey})] = new [(${dtoName})]();
        String key = [(${reqKey})].getKey();
        if (StringUtils.isNotBlank(key)) {
            String percentWrapKey = "%" + key + "%";
            [# th:each="attr,attrStat:${entityAttrs}" ]
                [# th:if="${attr.get('type') eq 'String'}"]
                [(${dtoKey})].set[(${#strings.capitalizeWords(attr.get('name'))})](percentWrapKey);
                [/]
            [/]
        }
        //MyBeanUtil.copyProperties([(${reqKey})], [(${dtoKey})]);
        Page<[(${dtoName})]> [(${dtoKey})]Page = [(${serviceKey})].findByPage([(${dtoKey})], pageable);
        MyPageResData<[(${dtoName})]> myPageResData = new MyPageResData<>([(${dtoKey})]Page,
                [(${reqKey})].getCurrent(), [(${reqKey})].getSize());
        model.addAttribute("[(${reqKey})]", [(${reqKey})]);
        model.addAttribute("page", myPageResData);
        return new ModelAndView("[(${moduleName})]/[(${uriName})]-list", "[(${entityKey})]Model", model);
    }

    @GetMapping("/modify/{id}")
    public ModelAndView modify(@PathVariable("id") Long id, Model model) {
        [(${dtoName})] [(${dtoKey})] = [(${serviceKey})].findById(id);
        model.addAttribute("[(${dtoKey})]", [(${dtoKey})]);
        return new ModelAndView("[(${moduleName})]/[(${uriName})]-modify", "[(${entityKey})]Model", model);
    }

    @PostMapping("/modify")
    public ModelAndView saveOrUpdate([(${dtoName})] [(${dtoKey})]) {
        initBaseCreateModifyTs([(${dtoKey})]);
        [(${dtoName})] updated[(${dtoName})] = [(${serviceKey})].save([(${dtoKey})]);
        return new ModelAndView("redirect:/api/[(${moduleName})]/[(${uriName})]");
    }

    @GetMapping("/remove/{id}")
    public ModelAndView remove(@PathVariable("id") Long id) {
        [(${serviceKey})].deleteById(id);
        return new ModelAndView("redirect:/api/[(${moduleName})]/[(${uriName})]");
    }

}
