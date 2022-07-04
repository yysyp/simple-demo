package ps.demo.memreview.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import ps.demo.common.MyBaseController;
import ps.demo.common.MyPageReq;
import ps.demo.common.MyPageResData;
import ps.demo.memreview.config.NoteReviewSettings;
import ps.demo.memreview.dto.NoteCardDto;
import ps.demo.memreview.dto.NoteCardReq;
import ps.demo.memreview.service.NoteCardServiceImpl;
import ps.demo.util.MyTimeUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/memreview/note-card/review")
public class NoteCardReviewController extends MyBaseController {

    @Autowired
    private NoteCardServiceImpl noteCardServiceImpl;

    @Autowired
    NoteReviewSettings noteReviewSettings;


    @GetMapping
    public ModelAndView query(Model model) {
        NoteCardReq noteCardReq = new NoteCardReq();
        noteCardReq.setSize(noteReviewSettings.getPageSize());
        MyPageReq.OrderBy orderBy = new MyPageReq.OrderBy();
        orderBy.setKey("nextReviewDate");
        orderBy.setAsc(true);
        noteCardReq.getOrderBys().add(orderBy);
        model.addAttribute("noteCardReq", noteCardReq);
        Pageable pageable = constructPagable(noteCardReq);

        Page<NoteCardDto> noteCardDtoPage = noteCardServiceImpl.findInPageNotDeleted(pageable);
        MyPageResData<NoteCardDto> myPageResData = new MyPageResData<>(noteCardDtoPage,
                noteCardReq.getCurrent(), noteCardReq.getSize());
        model.addAttribute("noteCardReq", noteCardReq);
        model.addAttribute("page", myPageResData);
        model.addAttribute("noteReviewSettings", noteReviewSettings);
        return new ModelAndView("memreview/review", "noteCardModel", model);
    }

    @PostMapping("/pass")
    public ModelAndView pass(Model model, HttpServletRequest request) {
        //model.addAttribute("noteCardDto", new NoteCardDto());
        Long id = Long.parseLong(request.getParameter("id"));
        NoteCardDto noteCardDto = noteCardServiceImpl.findById(id);
        if (noteCardDto.getReviewCount() == null) {
            noteCardDto.setReviewCount(0);
        }
        if (noteCardDto.getNextReviewDate() == null) {
            noteCardDto.setNextReviewDate(new Date());
        }

        Integer curReviewCount = noteCardDto.getReviewCount();
        Integer size = noteReviewSettings.getReviewAtMinutes().size();
        Integer gapMinutes = noteReviewSettings.getReviewAtMinutes().get(curReviewCount);
        Date now = new Date();
        Date nextReview = MyTimeUtil.addMinutes(noteCardDto.getNextReviewDate(), gapMinutes);
        noteCardDto.setNextReviewDate(nextReview);
        noteCardDto.setReviewCount(curReviewCount+1);
        if (noteCardDto.getReviewCount() >= size) {
            noteCardDto.setIsLogicalDeleted(true);
        }
        noteCardServiceImpl.save(noteCardDto);

        return query(model);
    }

    @PostMapping("/ok")
    public ModelAndView ok(Model model, HttpServletRequest request) {
        Long id = Long.parseLong(request.getParameter("id"));
        NoteCardDto noteCardDto = noteCardServiceImpl.findById(id);
        if (noteCardDto.getReviewCount() == null) {
            noteCardDto.setReviewCount(0);
        }
        if (noteCardDto.getNextReviewDate() == null) {
            noteCardDto.setNextReviewDate(new Date());
        }

        Integer curReviewCount = noteCardDto.getReviewCount();
        Integer size = noteReviewSettings.getReviewAtMinutes().size();
        Integer gapMinutes = noteReviewSettings.getReviewAtMinutes().get(curReviewCount);
        Double d = gapMinutes * noteReviewSettings.getOkMinus();
        gapMinutes = d.intValue();
        Date now = new Date();
        Date nextReview = MyTimeUtil.addMinutes(noteCardDto.getNextReviewDate(), gapMinutes);
        noteCardDto.setNextReviewDate(nextReview);

        noteCardServiceImpl.save(noteCardDto);
        return query(model);
    }

    @PostMapping("/fail")
    public ModelAndView fail(Model model, HttpServletRequest request) {
        Long id = Long.parseLong(request.getParameter("id"));
        NoteCardDto noteCardDto = noteCardServiceImpl.findById(id);
        if (noteCardDto.getReviewCount() == null) {
            noteCardDto.setReviewCount(0);
        }
        if (noteCardDto.getNextReviewDate() == null) {
            noteCardDto.setNextReviewDate(new Date());
        }

        Integer reviewCount = 1;
        noteCardDto.setReviewCount(reviewCount);
        Integer size = noteReviewSettings.getReviewAtMinutes().size();
        Integer gapMinutes = noteReviewSettings.getReviewAtMinutes().get(reviewCount);
        Date now = new Date();
        Date nextReview = MyTimeUtil.addMinutes(noteCardDto.getNextReviewDate(), gapMinutes);
        noteCardDto.setNextReviewDate(nextReview);

        noteCardServiceImpl.save(noteCardDto);
        return query(model);
    }


}
