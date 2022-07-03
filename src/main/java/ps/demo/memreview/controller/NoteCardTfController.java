

package ps.demo.memreview.controller;

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
import ps.demo.memreview.dto.NoteCardDto;
import ps.demo.memreview.dto.NoteCardReq;
import ps.demo.memreview.service.NoteCardServiceImpl;
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
@RequestMapping("/api/memreview/note-card")
public class NoteCardTfController extends MyBaseController {

    @Autowired
    private NoteCardServiceImpl noteCardServiceImpl;

    @GetMapping("/form")
    public ModelAndView createForm(Model model) {
        model.addAttribute("noteCardDto", new NoteCardDto());
        return new ModelAndView("memreview/note-card-form", "noteCardModel", model);
    }

    @PostMapping("/save")
    public ModelAndView save(NoteCardReq noteCardReq, HttpServletRequest request) {
        NoteCardDto noteCardDto = new NoteCardDto();
        
        noteCardDto.setIsPic(null != request.getParameter("isPic"));

        MyBeanUtil.copyProperties(noteCardReq, noteCardDto);
        initBaseCreateModifyTs(noteCardDto);
        NoteCardDto noteCardResult = noteCardServiceImpl.save(noteCardDto);
        return new ModelAndView("redirect:/api/memreview/note-card");
    }

    @GetMapping("/list")
    public ModelAndView navigateToQuery() {
        return new ModelAndView("redirect:/api/memreview/note-card");
    }

    @GetMapping
    public ModelAndView query(Model model) {
        NoteCardReq noteCardReq = new NoteCardReq();
        model.addAttribute("noteCardReq", noteCardReq);
        Pageable pageable = constructPagable(noteCardReq);
        Page<NoteCardDto> noteCardDtoPage = noteCardServiceImpl.findInPage(pageable);
        MyPageResData<NoteCardDto> myPageResData = new MyPageResData<>(noteCardDtoPage,
                noteCardReq.getCurrent(), noteCardReq.getSize());
        model.addAttribute("noteCardReq", noteCardReq);
        model.addAttribute("page", myPageResData);
        return new ModelAndView("memreview/note-card-list", "noteCardModel", model);
    }

    @GetMapping("/{id}")
    public ModelAndView getById(@PathVariable("id") Long id, Model model) {
        NoteCardDto noteCardDto = noteCardServiceImpl.findById(id);
        model.addAttribute("noteCardDto", noteCardDto);
        return new ModelAndView("memreview/note-card-view", "noteCardModel", model);
    }

    @PostMapping("/list")
    public ModelAndView list(Model model, NoteCardReq noteCardReq) {
        Pageable pageable = constructPagable(noteCardReq);
        NoteCardDto noteCardDto = new NoteCardDto();
        String key = noteCardReq.getKey();
        if (StringUtils.isNotBlank(key)) {
            String percentWrapKey = "%" + key + "%";
            
            noteCardDto.setFile(percentWrapKey);
            noteCardDto.setQuestion(percentWrapKey);
            noteCardDto.setPic(percentWrapKey);
            noteCardDto.setAnswer(percentWrapKey);
            noteCardDto.setComments(percentWrapKey);

        }
        //MyBeanUtil.copyProperties(noteCardReq, noteCardDto);
        Page<NoteCardDto> noteCardDtoPage = noteCardServiceImpl.findByAttributesInPage(noteCardDto, true, pageable);
        MyPageResData<NoteCardDto> myPageResData = new MyPageResData<>(noteCardDtoPage,
                noteCardReq.getCurrent(), noteCardReq.getSize());
        model.addAttribute("noteCardReq", noteCardReq);
        model.addAttribute("page", myPageResData);
        return new ModelAndView("memreview/note-card-list", "noteCardModel", model);
    }

    @GetMapping("/modify/{id}")
    public ModelAndView modify(@PathVariable("id") Long id, Model model) {
        NoteCardDto noteCardDto = noteCardServiceImpl.findById(id);
        model.addAttribute("noteCardDto", noteCardDto);
        return new ModelAndView("memreview/note-card-modify", "noteCardModel", model);
    }

    @PostMapping("/modify")
    public ModelAndView saveOrUpdate(NoteCardDto noteCardDto, HttpServletRequest request) {
        initBaseCreateModifyTs(noteCardDto);
        
        noteCardDto.setIsPic(null != request.getParameter("isPic"));

        NoteCardDto updatedNoteCardDto = noteCardServiceImpl.save(noteCardDto);
        return new ModelAndView("redirect:/api/memreview/note-card");
    }

    @GetMapping("/remove/{id}")
    public ModelAndView remove(@PathVariable("id") Long id) {
        noteCardServiceImpl.deleteById(id);
        return new ModelAndView("redirect:/api/memreview/note-card");
    }

}


