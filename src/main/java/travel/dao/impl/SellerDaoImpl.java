package travel.dao.impl;

import com.sun.org.apache.xalan.internal.xsltc.compiler.Template;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import travel.dao.SellerDao;
import travel.domain.Seller;
import travel.util.JDBCUtils;

/**
 * Created by Administrator on 2020/3/15 0015.
 */
public class SellerDaoImpl implements SellerDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public Seller findBySid(int sid) {
        String sql = "select * from tab_seller where sid = ?";
        return template.queryForObject(sql,new BeanPropertyRowMapper<Seller>(Seller.class),sid);
    }
}
