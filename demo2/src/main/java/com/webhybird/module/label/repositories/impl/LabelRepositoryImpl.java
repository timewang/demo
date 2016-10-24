package com.webhybird.module.label.repositories.impl;

import com.webhybird.framework.base.BaseJpaDao;
import com.webhybird.framework.dataquery.QueryParameter;
import com.webhybird.framework.util.QueryUtils;
import com.webhybird.module.label.repositories.LabelRepositoryCustom;
import com.webhybird.module.label.vo.LabelVO;
import com.webhybird.module.label.entity.Label;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by wangzhongfu on 2015/5/10.
 */
@Transactional
public class LabelRepositoryImpl extends BaseJpaDao<Label> implements LabelRepositoryCustom {

    @Override
    public List<Label> findLableCustom(String value, String username) {
        String hql = " from Label label where 1=1 ";
        List<Object> args = new ArrayList<>();
        if(!StringUtils.isEmpty(value)){
            hql += " and label.value like ? ";
            args.add(QueryUtils.getSearchString(value));
        }
        if(!StringUtils.isEmpty(username)){
            hql += " and label.test = ? ";
            args.add(username);
        }
        return this.findListByHql(hql,args);
    }

    @Override
    public List<LabelVO> findAllLabelVO() {
        return this.myJdbcTemplate.queryForBeanList("SELECT * FROM LABEL", LabelVO.class);
    }

    @Override
    public List<LabelVO> findAllLabelVO2() {
        return this.myJdbcTemplate.queryForBeanList("SELECT L.VALUE_,L.USERNAME FROM LABEL L", LabelVO.class);
    }

    @Override
    public Page<Label> findLabelPage(Pageable pageable) {
        String hql = " from Label label order by label.createDate desc";
        return this.findAll(hql,pageable);
    }

    @Override
    public Page<Label> findLabelPage(QueryParameter queryParameter, Pageable pageable) {
        String hql = " from Label label where 1=1 ";
        Map<String,Object> stringObjectMap = queryParameter.getSerachCondition(pageable);
        hql += stringObjectMap.get(QueryParameter.HQL);
        return super.findAll(hql,pageable, (List<Object>) stringObjectMap.get(QueryParameter.VALUES));
    }
}
