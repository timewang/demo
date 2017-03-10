package com.webhybird.module.label.repositories;

import com.webhybird.framework.dataquery.QueryParameter;
import com.webhybird.module.label.vo.LabelVO;
import com.webhybird.module.label.entity.Label;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by wangzhongfu on 2015/5/10.
 */
public interface LabelRepositoryCustom {

    List<Label> findLableCustom(String value, String username);
    List<LabelVO> findAllLabelVO();

    List<LabelVO> findAllLabelVO2();

    Page<Label> findLabelPage(Pageable pageable);

    Page<Label> findLabelPage(QueryParameter queryParameter, Pageable pageable);
}
