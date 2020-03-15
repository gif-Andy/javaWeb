package travel.dao.impl;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import travel.dao.RouteDao;
import travel.domain.Route;
import travel.util.JDBCUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2020/3/13 0013.
 */
public class RouteDaoImpl implements RouteDao{
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public int findTotalCount(int cid,String rname) {
        //String sql = "select count(*) from tab_route where cid = ?";
        //定义sql模板
        String sql = "select count(*) from tab_route where 1 = 1";
        StringBuilder stringBuilder = new StringBuilder(sql);
        List params = new ArrayList();//增加条件
        //判断参数是否有值
        if(cid != 0){
            stringBuilder.append(" and cid = ?");
            params.add(cid);
        }if(rname != null && rname.length() > 0){
            stringBuilder.append(" and rname like ?");
            params.add("%"+rname+"%");
        }
        sql = stringBuilder.toString();
        return template.queryForObject(sql,Integer.class,params.toArray());
    }

    @Override
    public List<Route> findByPage(int cid, int start, int pageSize,String rname) {
        //String sql = "select * from tab_route where cid = ? limit ?,?";//limit：从何开始，几条记录
        String sql = "select * from tab_route where 1 = 1";
        StringBuilder stringBuilder = new StringBuilder(sql);
        List params = new ArrayList();//增加条件
        //判断参数是否有值
        if(cid != 0){
            stringBuilder.append(" and cid = ?");
            params.add(cid);
        }if(rname != null && rname.length() > 0){
            stringBuilder.append(" and rname like ?");
            params.add("%"+rname+"%");
        }
        stringBuilder.append(" limit ?,?");
        sql = stringBuilder.toString();
        params.add(start);
        params.add(pageSize);
        return template.query(sql,new BeanPropertyRowMapper<Route>(Route.class),params.toArray());
    }

    @Override
    public Route findOne(int rid) {
        String sql = "select * from tab_route where rid= ?";
        return template.queryForObject(sql,new BeanPropertyRowMapper<Route>(Route.class),rid);
    }
}
