package travel.service.impl;

import travel.dao.FavoriteDao;
import travel.dao.RouteDao;
import travel.dao.RouteImgDao;
import travel.dao.SellerDao;
import travel.dao.impl.FavoriteDaoImpl;
import travel.dao.impl.RouteDaoImpl;
import travel.dao.impl.RouteImgDaoImpl;
import travel.dao.impl.SellerDaoImpl;
import travel.domain.*;
import travel.service.RouteService;

import java.util.List;

/**
 * Created by Administrator on 2020/3/13 0013.
 */
public class RouteServiceImpl implements RouteService{
    private RouteDao routeDao = new RouteDaoImpl();
    private RouteImgDao routeImgDao = new RouteImgDaoImpl();
    private SellerDao sellerDao = new SellerDaoImpl();
    private FavoriteDao favoriteDao = new FavoriteDaoImpl();
    @Override
    public PageBean<Route> pageQuery(int cid, int currentPage, int pageSize,String rname) {
        //封装pageBean并返回
        PageBean<Route> pageBean = new PageBean<>();
        //设置当前页码
        pageBean.setCurrentPage(currentPage);
        //设置每页显示条数
        pageBean.setPageSize(pageSize);
        //设置总记录数
        int totalCount = routeDao.findTotalCount(cid,rname);
        pageBean.setTotalCount(totalCount);
        //设置当前页显示的数据集合
        int start = (currentPage-1)*pageSize;//开始的记录数
        List<Route> list = routeDao.findByPage(cid,start,pageSize,rname);
        pageBean.setList(list);
        //设置总页数
        int totalPage = totalCount % pageSize == 0 ? totalCount/pageSize : (totalCount/pageSize) + 1 ;
        pageBean.setTotalPage(totalPage);
        return pageBean;
    }

    @Override
    public Route findOne(String rid) {
        //1.根据id查询route对象信息
        Route route = routeDao.findOne(Integer.parseInt(rid));
        //2.根据route的id查询图片集合信息
        List<RouteImg> routeImgList = routeImgDao.findByRid(Integer.parseInt(rid));
        //2.2将集合设置到route对象
        route.setRouteImgList(routeImgList);
        //3.根据route对象的sid查询商家信息
        Seller seller = sellerDao.findBySid(route.getSid());
        route.setSeller(seller);
        //查询收藏次数
        int count = favoriteDao.finCountByRid(route.getRid());
        route.setCount(count);
        return route;
    }
}
