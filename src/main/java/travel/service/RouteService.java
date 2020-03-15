package travel.service;

import travel.domain.PageBean;
import travel.domain.Route;

/**
 * Created by Administrator on 2020/3/13 0013.
 */
public interface RouteService {

    PageBean<Route> pageQuery(int cid, int currentPage, int pageSize,String rname);

    Route findOne(String rid);
}
