package com.webhybird.module.label.service.impl;

import com.webhybird.framework.dataquery.QueryParameter;
import com.webhybird.framework.dataquery.SpecificationImpl;
import com.webhybird.framework.util.RandomUtils;
import com.webhybird.framework.util.QueryUtils;
import com.webhybird.module.label.repositories.LabelRepository;
import com.webhybird.module.label.entity.Label;
import com.webhybird.module.label.repositories.UserRepository;
import com.webhybird.module.label.service.LabelService;
import com.webhybird.module.label.vo.LabelVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by wangzhongfu on 2015/5/5.
 */
@Service
public class LabelServiceImpl implements LabelService {

    @Autowired
    private LabelRepository labelRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Label> findAllEntity() {
        return this.labelRepository.findAll();
    }

    @Override
    public List<Label> findByCreatedBy2() {
        return this.labelRepository.findByUserId("1");
    }

    @Override
    public List<Label> findByCreatedBy() {
        return this.labelRepository.findByCreatedBy_id("1");
    }

    @Override
    public void saveLabel(Label label) {
        label.setId(RandomUtils.uuid2());
        label.setCreateDate(new Date());
        /*User user = new User();
        user.setUsername("123456789");
        this.userRepository.save(user);*/
        this.labelRepository.save(label);
    }

    @Override
    public Label findById(String id) {
        return this.labelRepository.findOne(id);
    }

    @Override
    public List<Label> searchLabel(String word) {
        return this.labelRepository.searchLabel(QueryUtils.getSearchString(word));
    }

    @Override
    public void updateLabel(Label label) {
        label.setCreateDate(new Date());
        //this.labelRepository.m(label);
    }

    @Override
    public List<Label> findByCustomMethod(String value, String username) {
        return this.labelRepository.findLableCustom(value, username);
    }

    @Override
    public Page<Label> findLabelPage(Pageable pageable){
        return this.labelRepository.findLabelPage(pageable);
    }

    @Override
    public Page<Label> findLabelPage(Label label, Pageable pageable) {
        return null;
    }

    @Override
    public Page<Label> findLabelPage(QueryParameter queryParameter, Pageable pageable) {
        Specification<Label> labelSpecification = new SpecificationImpl<>(queryParameter.getParameters(),queryParameter.getType());
        return null;
    }

    @Override
    public Page<Object[]> findAllConstructor(Label label, Pageable pageable) {
        return this.labelRepository.findAllConstructor(this.getLabelAndSpecification(label), pageable);
    }

    @Override
    public List<Object[]> findAll() {
        return this.labelRepository.findLabels();
    }

    @Override
    public List<Object[]> findAll(Pageable pageable) {
        return this.labelRepository.findLabels(pageable);
    }

    @Override
    public List<LabelVO> findAllLabelVO() {
        return this.labelRepository.findAllLabelVO();
    }

    @Override
    public List<LabelVO> findAllLabelVO2() {
        return this.labelRepository.findAllLabelVO2();
    }

    @Override
    public Page<Label> findAllPage(Pageable pageable) {
        return this.labelRepository.findLabelPage(pageable);
    }

    @Override
    public Page<Label> findAllPage(QueryParameter queryParameter, Pageable pageable) {
        return this.labelRepository.findLabelPage(queryParameter,pageable);
    }

    public Specification<Label> getLabelAndSpecification(final Label label){
        return new Specification<Label>(){
            //Cr eat es t he sear ch cr i t er i a
            @Override
            public Predicate toPredicate( Root<Label> root ,
                                          CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb)  {
                //String likePattern = getLikePattern( searchTerm ) ;
                Predicate[] predicates = buildAndPredicate(root,cb,label);
                return cb.and(predicates);
            }
            private String getLikePattern( final  String searchTerm )  {
                return searchTerm.toLowerCase( )  + "% ";
            }
        } ;
    }

    private Predicate[] buildAndPredicate(Root<Label> root, CriteriaBuilder cb,Label label){
        List<Predicate> pageableList = new ArrayList<>();
        if(label != null){
            if(!StringUtils.isEmpty(label.getValue())){
                pageableList.add(cb.like(cb.lower(root.<String>get("value")), QueryUtils.getSearchString(label.getValue())));
            }
            /*if(!StringUtils.isEmpty(label.getUsername())){
                pageableList.add(cb.equal(cb.lower(root.<String>get("username")), label.getUsername()));
            }*/
        }
       /* Predicate[] predicates = new Predicate[pageableList.size()];
        for(int i = 0 ; i< pageableList.size() ; i++){
            predicates[i] = pageableList.get(i);
        }*/
        return pageableList.toArray(new Predicate[pageableList.size()]);
    }
}
