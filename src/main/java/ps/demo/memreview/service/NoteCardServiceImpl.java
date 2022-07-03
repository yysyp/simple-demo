

package ps.demo.memreview.service;

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
import ps.demo.memreview.dto.NoteCardDto;
import ps.demo.memreview.dto.NoteCardReq;
import ps.demo.memreview.entity.NoteCard;
import ps.demo.memreview.repository.NoteCardDao;
import ps.demo.util.MyBeanUtil;

import javax.persistence.criteria.*;

import lombok.*;

import java.util.*;
import java.math.*;

@Slf4j
@Service
public class NoteCardServiceImpl {

    @Autowired
    NoteCardDao noteCardDao;

    @Transactional
    public NoteCardDto save(NoteCardDto noteCardDto) {
        NoteCard noteCard = new NoteCard();
        MyBeanUtil.copyProperties(noteCardDto, noteCard);
        NoteCard entity = noteCardDao.save(noteCard);
        MyBeanUtil.copyProperties(entity, noteCardDto);
        return noteCardDto;
    }

    @Transactional
    public List<NoteCardDto> saveAll(Collection<NoteCardDto> noteCardDtoList) {
        if (CollectionUtils.isEmpty(noteCardDtoList)) {
            return new ArrayList<>();
        }
        List<NoteCardDto> result = new ArrayList<>();
        for (NoteCardDto noteCardDto : noteCardDtoList) {
            result.add(save(noteCardDto));
        }
        return result;
    }

    public NoteCardDto findById(Long id) {
        Optional<NoteCard> noteCardOptional = noteCardDao.findById(id);
        NoteCardDto noteCardDto = new NoteCardDto();
        noteCardOptional.ifPresent(e -> {
            MyBeanUtil.copyProperties(e, noteCardDto);
        });
        return noteCardDto;
    }

    public List<NoteCardDto> findByAttribute(String attributeName, Object attribute) {
        Specification<NoteCard> spec = new Specification<NoteCard>() {
            @Override
            public Predicate toPredicate(Root<NoteCard> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.and(cb.equal(root.get(attributeName), attribute));
            }
        };
        List<NoteCard> noteCardList = noteCardDao.findAll(spec);
        return MyBeanUtil.copyAndConvertItems(noteCardList, NoteCardDto.class);
    }

    //@Transactional(readOnly = true)
    public List<NoteCardDto> findAll() {
        List<NoteCard> noteCardList = noteCardDao.findAll();
        List<NoteCardDto> noteCardDtoList = new ArrayList<>();
        for (NoteCard noteCard : noteCardList) {
            NoteCardDto noteCardDto = new NoteCardDto();
            MyBeanUtil.copyProperties(noteCard, noteCardDto);
            noteCardDtoList.add(noteCardDto);
        }
        return noteCardDtoList;
    }

    public Page<NoteCardDto> findInPage(Pageable pageable) {
        Page<NoteCard> page = noteCardDao.findAll(pageable);
        Page<NoteCardDto> pageDto = page.map((e) -> {
            NoteCardDto noteCardDto = new NoteCardDto();
            MyBeanUtil.copyProperties(e, noteCardDto);
            return noteCardDto;
        });
        return pageDto;
    }

    public List<NoteCardDto> findByAttributes(NoteCardDto noteCardDto, boolean orLike) {
        NoteCard noteCard = new NoteCard();
        MyBeanUtil.copyProperties(noteCardDto, noteCard);
        Specification<NoteCard> spec = constructSpecification(noteCard, orLike);
        List<NoteCard> noteCardList = noteCardDao.findAll(spec);
        return MyBeanUtil.copyAndConvertItems(noteCardList, NoteCardDto.class);
    }

    public Page<NoteCardDto> findByAttributesInPage(NoteCardDto noteCardDto, boolean orLike, Pageable pageable) {
        NoteCard noteCard = new NoteCard();
        MyBeanUtil.copyProperties(noteCardDto, noteCard);
        Specification<NoteCard> spec = constructSpecification(noteCard, orLike);

        Page<NoteCard> page = noteCardDao.findAll(spec, pageable);
        Page<NoteCardDto> pageDto = page.map((e) -> {
            NoteCardDto noteCardDtoResult = new NoteCardDto();
            MyBeanUtil.copyProperties(e, noteCardDtoResult);
            return noteCardDtoResult;
        });
        return pageDto;
    }

    private Specification<NoteCard> constructSpecification(NoteCard noteCard, boolean orLike) {
        Specification<NoteCard> spec = new Specification<NoteCard>() {
            @Override
            public Predicate toPredicate(Root<NoteCard> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = null;
                if (orLike) {
                
                    predicate = orLike(predicate, cb, root,"file", noteCard.getFile());
                    predicate = orLike(predicate, cb, root,"question", noteCard.getQuestion());
                    predicate = orLike(predicate, cb, root,"pic", noteCard.getPic());
                    predicate = orLike(predicate, cb, root,"answer", noteCard.getAnswer());
                    predicate = orEqual(predicate, cb, root,"isPic", noteCard.getIsPic());
                    predicate = orEqual(predicate, cb, root,"reviewCount", noteCard.getReviewCount());
                    predicate = orEqual(predicate, cb, root,"nextReviewDate", noteCard.getNextReviewDate());
                    predicate = orLike(predicate, cb, root,"comments", noteCard.getComments());

                } else {
                
                    predicate = andEqual(predicate, cb, root, "file", noteCard.getFile());
                    predicate = andEqual(predicate, cb, root, "question", noteCard.getQuestion());
                    predicate = andEqual(predicate, cb, root, "pic", noteCard.getPic());
                    predicate = andEqual(predicate, cb, root, "answer", noteCard.getAnswer());
                    predicate = andEqual(predicate, cb, root, "isPic", noteCard.getIsPic());
                    predicate = andEqual(predicate, cb, root, "reviewCount", noteCard.getReviewCount());
                    predicate = andEqual(predicate, cb, root, "nextReviewDate", noteCard.getNextReviewDate());
                    predicate = andEqual(predicate, cb, root, "comments", noteCard.getComments());
                
                }

                return predicate;
            }
        };
        return spec;
    }

    private Predicate andEqual(Predicate predicate, CriteriaBuilder cb, Root<NoteCard> root, String attributeName, Object attributeValue) {
        if (null == attributeValue) {
            return predicate;
        }
        if (null == predicate) {
            return cb.or(cb.equal(root.get(attributeName), attributeValue));
        } else {
            return cb.or(predicate, cb.equal(root.get(attributeName), attributeValue));
        }
    }
    private Predicate orEqual(Predicate predicate, CriteriaBuilder cb, Root<NoteCard> root, String attributeName, Object attributeValue) {
        if (null == attributeValue) {
            return predicate;
        }
        if (null == predicate) {
            return cb.or(cb.equal(root.get(attributeName), attributeValue));
        } else {
            return cb.or(predicate, cb.equal(root.get(attributeName), attributeValue));
        }
    }
    private Predicate orLike(Predicate predicate, CriteriaBuilder cb, Root<NoteCard> root, String attributeName, String attributeValue) {
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
        noteCardDao.deleteById(id);
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

}


