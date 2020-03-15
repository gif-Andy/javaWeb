package travel.dao;

import travel.domain.Route;

import java.util.List;

/**
 * Created by Administrator on 2020/3/13 0013.
 */
public interface RouteDao {
    /**
     * 根据cid查询总记录数
     */
    public int findTotalCount( int cid,String rname);
    /**
     * 根据cid，start，pageSize查询当前页的数据集合
     */
    public List<Route> findByPage(int cid,int start,int pageSize,String rname);

    public Route findOne(int rid);

}
