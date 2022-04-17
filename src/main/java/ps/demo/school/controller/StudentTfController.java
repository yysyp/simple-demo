

package ps.demo.school.controller;

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
import ps.demo.school.dto.StudentDto;
import ps.demo.school.dto.StudentReq;
import ps.demo.school.service.StudentServiceImpl;
import ps.demo.util.MyBeanUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import lombok.*;
import java.util.*;
import java.math.*;

@Slf4j
@RestController
@RequestMapping("/api/school/student")
public class StudentTfController extends MyBaseController {

    @Autowired
    private StudentServiceImpl studentserviceimpl;

    @GetMapping("/form")
    public ModelAndView createForm(Model model) {
        model.addAttribute("studentdto", new StudentDto());
        return new ModelAndView("school/student-form", "studentModel", model);
    }

    @PostMapping("/save")
    public ModelAndView save(StudentReq studentreq) {
        StudentDto studentdto = new StudentDto();
        MyBeanUtil.copyProperties(studentreq, studentdto);
        StudentDto studentResult = studentserviceimpl.save(studentdto);
        return new ModelAndView("redirect:/api/school/student");
    }

    @GetMapping("/list")
    public ModelAndView navigateToQuery() {
        return new ModelAndView("redirect:/api/school/student");
    }

    @GetMapping
    public ModelAndView query(Model model) {
        StudentReq studentreq = new StudentReq();
        model.addAttribute("studentreq", studentreq);
        Pageable pageable = constructPagable(studentreq);
        Page<StudentDto> studentdtoPage = studentserviceimpl.findByPage(pageable);
        MyPageResData<StudentDto> myPageResData = new MyPageResData<>(studentdtoPage,
                studentreq.getCurrent(), studentreq.getSize());
        model.addAttribute("studentreq", studentreq);
        model.addAttribute("page", myPageResData);
        return new ModelAndView("school/student-list", "studentModel", model);
    }

    @GetMapping("/{id}")
    public ModelAndView getById(@PathVariable("id") Long id, Model model) {
        StudentDto studentdto = studentserviceimpl.findById(id);
        model.addAttribute("studentdto", studentdto);
        return new ModelAndView("school/student-view", "studentModel", model);
    }

    @PostMapping("/list")
    public ModelAndView list(Model model, StudentReq studentreq) {
        Pageable pageable = constructPagable(studentreq);
        StudentDto studentdto = new StudentDto();
        String key = studentreq.getKey();
        if (StringUtils.isNotBlank(key)) {
            String percentWrapKey = "%" + key + "%";
            
                
                studentdto.setFirstName(percentWrapKey);
                
                
                studentdto.setLastName(percentWrapKey);
                
                
                
                
            
        }
        //MyBeanUtil.copyProperties(studentreq, studentdto);
        Page<StudentDto> studentdtoPage = studentserviceimpl.findByPage(studentdto, pageable);
        MyPageResData<StudentDto> myPageResData = new MyPageResData<>(studentdtoPage,
                studentreq.getCurrent(), studentreq.getSize());
        model.addAttribute("studentreq", studentreq);
        model.addAttribute("page", myPageResData);
        return new ModelAndView("school/student-list", "studentModel", model);
    }

    @GetMapping("/modify/{id}")
    public ModelAndView modify(@PathVariable("id") Long id, Model model) {
        StudentDto studentdto = studentserviceimpl.findById(id);
        model.addAttribute("studentdto", studentdto);
        return new ModelAndView("school/student-modify", "studentModel", model);
    }

    @PostMapping("/modify")
    public ModelAndView saveOrUpdate(StudentDto studentdto) {
        StudentDto updatedStudentDto = studentserviceimpl.save(studentdto);
        return new ModelAndView("redirect:/api/school/student");
    }

    @GetMapping("/remove/{id}")
    public ModelAndView remove(@PathVariable("id") Long id) {
        studentserviceimpl.deleteById(id);
        return new ModelAndView("redirect:/api/school/student");
    }

}


