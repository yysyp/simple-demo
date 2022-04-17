

package ps.demo.school.service;

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
import ps.demo.order.repository.NewCartRepository;
import ps.demo.school.dto.StudentDto;
import ps.demo.school.dto.StudentReq;
import ps.demo.school.entity.Student;
import ps.demo.school.repository.StudentDao;
import ps.demo.util.MyBeanUtil;

import javax.persistence.criteria.*;
import lombok.*;
import java.util.*;
import java.math.*;

@Slf4j
@Service
public class StudentServiceImpl {

    @Autowired
    StudentDao studentdao;

    @Transactional
    public StudentDto save(StudentDto studentdto) {
        Student student = new Student();
        MyBeanUtil.copyProperties(studentdto, student);
        Student entity = studentdao.save(student);
        MyBeanUtil.copyProperties(entity, studentdto);
        return studentdto;
    }

    @Transactional(readOnly = true)
    public List<StudentDto> findAll() {
        List<Student> studentList = studentdao.findAll();
        List<StudentDto> studentdtoList = new ArrayList<>();
        for (Student student : studentList) {
            StudentDto studentdto = new StudentDto();
            MyBeanUtil.copyProperties(student, studentdto);
            studentdtoList.add(studentdto);
        }
        return studentdtoList;
    }

    public StudentDto findById(Long id) {
        Optional<Student> studentOptional = studentdao.findById(id);
        StudentDto studentdto = new StudentDto();
        studentOptional.ifPresent(e -> {
            MyBeanUtil.copyProperties(e, studentdto);
        });
        return studentdto;
    }

    @Transactional(readOnly = true)
    public Page<StudentDto> findByPage(Pageable pageable) {
        Page<Student> page = studentdao.findAll(pageable);
        Page<StudentDto> pageDto = page.map((e) -> {
            StudentDto studentdto = new StudentDto();
            MyBeanUtil.copyProperties(e, studentdto);
            return studentdto;
        });
        return pageDto;
    }

    @Transactional(readOnly = true)
    public Page<StudentDto> findByPage(StudentDto studentdto, Pageable pageable) {
        Student student = new Student();
        MyBeanUtil.copyProperties(studentdto, student);
        Specification<Student> spec = new Specification<Student>() {
            @Override
            public Predicate toPredicate(Root<Student> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = null;
                    
                        
                        
                        if (StringUtils.isNotBlank(student.getFirstName())) {
                            predicate = cb.or(cb.like(root.get("firstName"), student.getFirstName()));
                        }
                        
                        
                        
                        
                        
                        
                        if (StringUtils.isNotBlank(student.getLastName())) {
                            predicate = cb.or(predicate, cb.like(root.get("lastName"), student.getLastName()));
                        }
                        
                        
                        
                        
                        
                        
                    
                return predicate;
            }
        };

        Page<Student> page = studentdao.findAll(spec, pageable);
        Page<StudentDto> pageDto = page.map((e) -> {
            StudentDto studentdtoResult = new StudentDto();
            MyBeanUtil.copyProperties(e, studentdtoResult);
            return studentdtoResult;
        });
        return pageDto;
    }

    @Transactional
    public void deleteById(Long id) {
        studentdao.deleteById(id);
    }


}


