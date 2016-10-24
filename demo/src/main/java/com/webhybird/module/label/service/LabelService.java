package com.webhybird.module.label.service;

import com.webhybird.framework.dataquery.QueryParameter;
import com.webhybird.module.label.vo.LabelVO;
import com.webhybird.module.label.entity.Label;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by wangzhongfu on 2015/5/5.
 */
public interface LabelService {

    List<Label> findAllEntity();

    List<Label> findByCreatedBy2();

    List<Label> findByCreatedBy();

    void saveLabel(Label label);

    Label findById(String id);

    List<Label> searchLabel(String word);

    void updateLabel(Label label);

    List<Label> findByCustomMethod(String value,String username);

    Page<Label> findLabelPage(org.springframework.data.domain.Pageable pageable);

    Page<Label> findLabelPage(Label label,Pageable pageable);

    Page<Label> findLabelPage(QueryParameter queryParameter,Pageable pageable);

    Page<Object[]> findAllConstructor(Label label,Pageable pageable);

    List<Object[]> findAll();

    List<Object[]> findAll(Pageable pageable);

    List<LabelVO> findAllLabelVO();

    List<LabelVO> findAllLabelVO2();

    Page<Label> findAllPage(Pageable pageable);

    Page<Label> findAllPage(QueryParameter queryParameter,Pageable pageable);
}
