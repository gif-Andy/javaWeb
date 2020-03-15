package travel.dao;

import travel.domain.RouteImg;

import java.util.List;

/**
 * Created by Administrator on 2020/3/15 0015.
 */
public interface RouteImgDao {
    List<RouteImg> findByRid(int rid);
}
