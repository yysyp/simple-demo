

package ps.demo.mock.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.ui.Model;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;
import ps.demo.common.MyBaseController;
import ps.demo.common.MyPageReq;
import ps.demo.common.MyPageResData;
import ps.demo.mock.dto.MyMockDto;
import ps.demo.mock.dto.MyMockReq;
import ps.demo.mock.service.MyMockServiceImpl;
import ps.demo.util.MyBeanUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import lombok.*;
import ps.demo.util.MyJsonUtil;
import ps.demo.util.MyRegexUtil;
import ps.demo.util.MyThymeleafUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.math.*;

@Slf4j
@RestController
@RequestMapping("/mock")
public class MyMockTfController extends MyBaseController {

    @Autowired
    private MyMockServiceImpl mymockserviceimpl;

    @RequestMapping("/api/**")
    public ResponseEntity<String> mockUriTest(HttpServletRequest request) {
        String pattern = (String)
                request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
        String searchTerm = new AntPathMatcher().extractPathWithinPattern(pattern,
                request.getServletPath());

        String method = request.getMethod();

        List<MyMockDto> myMockDtos = mymockserviceimpl.findAll();
        MyMockDto myMockDto = null;
        for (MyMockDto mockDto : myMockDtos) {
            String uri = mockDto.getUri() + "";
            if (mockDto.getMethod().trim().equalsIgnoreCase(method)
            && (uri.equals(searchTerm) || (mockDto.getRegexMatch() &&
                    MyRegexUtil.isMatche(searchTerm, uri)))) {
                myMockDto = mockDto;
                break;
            }
        }

        HttpHeaders headers = new HttpHeaders();
        if (myMockDto == null) {
            headers.setContentType(MediaType.TEXT_PLAIN);
            return new ResponseEntity("Not Found!", headers, HttpStatus.OK);
        }
        Map<String, Object> mockHeaders = MyJsonUtil.json2SimpleMap(myMockDto.getHeaders());
        Map<String, Object> ctx = new HashMap<>();
        ctx.put("request", request);
        for (Map.Entry<String, Object> entry : mockHeaders.entrySet()) {
            String key = entry.getKey() + "";
            String value = MyThymeleafUtil.processText(entry.getValue() + "", ctx);
            headers.add(key, value);
        }
        if (!headers.containsKey("Content-Type")) {
            headers.setContentType(MediaType.APPLICATION_JSON);
        }
        String body = MyThymeleafUtil.processText(myMockDto.getBody(), ctx);
        ResponseEntity<String> responseEntity = new ResponseEntity<String>(body,
                headers, HttpStatus.valueOf(myMockDto.getStatus()));

        return responseEntity;
    }

    @GetMapping("/form")
    public ModelAndView createForm(Model model) {
        model.addAttribute("mymockdto", new MyMockDto());
        return new ModelAndView("mock/my-mock-form", "mymockModel", model);
    }

    @PostMapping("/save")
    public ModelAndView save(MyMockReq mymockreq) {
        MyMockDto mymockdto = new MyMockDto();
        MyBeanUtil.copyProperties(mymockreq, mymockdto);
        initBaseCreateModifyTs(mymockdto);
        MyMockDto mymockResult = mymockserviceimpl.save(mymockdto);
        return new ModelAndView("redirect:/mock");
    }

    @GetMapping("/list")
    public ModelAndView navigateToQuery() {
        return new ModelAndView("redirect:/mock");
    }

    @GetMapping
    public ModelAndView query(Model model) {
        MyMockReq mymockreq = new MyMockReq();
        model.addAttribute("mymockreq", mymockreq);
        Pageable pageable = constructPagable(mymockreq);
        Page<MyMockDto> mymockdtoPage = mymockserviceimpl.findByPage(pageable);
        MyPageResData<MyMockDto> myPageResData = new MyPageResData<>(mymockdtoPage,
                mymockreq.getCurrent(), mymockreq.getSize());
        model.addAttribute("mymockreq", mymockreq);
        model.addAttribute("page", myPageResData);
        return new ModelAndView("mock/my-mock-list", "mymockModel", model);
    }

    @GetMapping("/{id}")
    public ModelAndView getById(@PathVariable("id") Long id, Model model) {
        MyMockDto mymockdto = mymockserviceimpl.findById(id);
        model.addAttribute("mymockdto", mymockdto);
        return new ModelAndView("mock/my-mock-view", "mymockModel", model);
    }

    @PostMapping("/list")
    public ModelAndView list(Model model, MyMockReq mymockreq) {
        Pageable pageable = constructPagable(mymockreq);
        MyMockDto mymockdto = new MyMockDto();
        String key = mymockreq.getKey();
        if (StringUtils.isNotBlank(key)) {
            String percentWrapKey = "%" + key + "%";
            
                
                mymockdto.setUri(percentWrapKey);
                
                
                
                mymockdto.setMethod(percentWrapKey);
                
                
                
                mymockdto.setHeaders(percentWrapKey);
                
                
                mymockdto.setBody(percentWrapKey);
                
            
        }
        //MyBeanUtil.copyProperties(mymockreq, mymockdto);
        Page<MyMockDto> mymockdtoPage = mymockserviceimpl.findByPage(mymockdto, pageable);
        MyPageResData<MyMockDto> myPageResData = new MyPageResData<>(mymockdtoPage,
                mymockreq.getCurrent(), mymockreq.getSize());
        model.addAttribute("mymockreq", mymockreq);
        model.addAttribute("page", myPageResData);
        return new ModelAndView("mock/my-mock-list", "mymockModel", model);
    }

    @GetMapping("/modify/{id}")
    public ModelAndView modify(@PathVariable("id") Long id, Model model) {
        MyMockDto mymockdto = mymockserviceimpl.findById(id);
        model.addAttribute("mymockdto", mymockdto);
        return new ModelAndView("mock/my-mock-modify", "mymockModel", model);
    }

    @PostMapping("/modify")
    public ModelAndView saveOrUpdate(MyMockDto mymockdto) {
        initBaseCreateModifyTs(mymockdto);
        MyMockDto updatedMyMockDto = mymockserviceimpl.save(mymockdto);
        return new ModelAndView("redirect:/mock");
    }

    @GetMapping("/remove/{id}")
    public ModelAndView remove(@PathVariable("id") Long id) {
        mymockserviceimpl.deleteById(id);
        return new ModelAndView("redirect:/mock");
    }

}


