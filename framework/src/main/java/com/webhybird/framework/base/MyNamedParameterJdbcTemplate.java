package com.webhybird.framework.base;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by wangzhongfu on 2015/12/25.
 */
public class MyNamedParameterJdbcTemplate extends NamedParameterJdbcTemplate {


    public MyNamedParameterJdbcTemplate(DataSource dataSource) {
        super(dataSource);
    }

    /**
     * 继承自 dbutils 的 BeanProcessor 修改 以增加注解的支持
     */
    private final BasicRowProcessor convert = new BasicRowProcessor();

    public <T> T queryForBean(String sql, final Class<T> beanType) {
        return query(sql, new ResultSetExtractor<T>() {

            @Override
            public T extractData(ResultSet rs) throws SQLException,
                    DataAccessException {
                return rs.next() ? convert.toBean(rs, beanType) : null;
            }
        });
    }

    public <T> T queryForBean(String sql, final Class<T> beanType,
                              Map<String,Object> args) {
        return query(sql, args, new ResultSetExtractor<T>() {

            @Override
            public T extractData(ResultSet rs) throws SQLException,
                    DataAccessException {
                return rs.next() ? convert.toBean(rs, beanType) : null;
            }
        });
    }

    public <T> List<T> queryForBeanList(String sql, final Class<T> beanType) {
        return query(sql, new RowMapper<T>() {

            @Override
            public T mapRow(ResultSet rs, int rowNum) throws SQLException {
                return convert.toBean(rs, beanType);
            }
        });
    }

    public <T> List<T> queryForBeanList(String sql, final Class<T> beanType,
                                        Map<String,Object> args) {
        return query(sql, args, new RowMapper<T>() {

            @Override
            public T mapRow(ResultSet rs, int rowNum) throws SQLException {
                return convert.toBean(rs, beanType);
            }
        });
    }

}
