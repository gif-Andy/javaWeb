package travel.web.servlet;

import travel.domain.Favorite;
import travel.domain.PageBean;
import travel.domain.Route;
import travel.domain.User;
import travel.service.FavoriteService;
import travel.service.RouteService;
import travel.service.impl.FavoriteServiceImpl;
import travel.service.impl.RouteServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Administrator on 2020/3/13 0013.
 */
@WebServlet("/route/*")
public class RouteServlet extends BaseServlet {
    private RouteService routeService = new RouteServiceImpl();
    private FavoriteService favoriteService = new FavoriteServiceImpl();
    /**
     * 分页查询
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void pageQuery(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.1接收参数
        String currentPageStr = request.getParameter("currentPage");//当前页码
        String pageSizeStr = request.getParameter("pageSize");//每页显示的条数
        String cidStr = request.getParameter("cid");//类别id
        //接受搜索线路名称
        String rname = request.getParameter("rname");
        //rname = new String(rname.getBytes("iso-8859-1"),"utf-8");

        int cid = 0;
        //1.2处理参数
        if(cidStr != null && cidStr.length() > 0 && !"null".equals(cidStr)){
            cid = Integer.parseInt(cidStr);
        }
        int currentPage = 0;//如果没有传递当前页码值，默认传递第一页
        if(currentPageStr != null && currentPageStr.length()> 0){
            currentPage = Integer.parseInt(currentPageStr);
        }else{
            currentPage = 1;
        }
        int pageSize = 0;//如果不传递，默认每页显示5条参数
        if(pageSizeStr != null && pageSizeStr.length()> 0){
            pageSize = Integer.parseInt(pageSizeStr);
        }else{
            pageSize = 5;
        }
        //调用Service查询pageBean对象
        PageBean pageBean = routeService.pageQuery(cid,currentPage,pageSize,rname);
        //将pageBean对象序列化为json，并返回前端页面
        writeValue(pageBean,response);
    }

    /**
     * 根据id查询旅游线路的详细信息
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.接收id
        String rid = request.getParameter("rid");
        //2.调用service查询route对象
        Route route = routeService.findOne(rid);
        //转化为json写会客户端
        writeValue(route,response);
    }

    /**
     * 判断当前用户是否收藏线路
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void isFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1. 获取线路id
        String rid = request.getParameter("rid");

        //2. 获取当前登录的用户 user
        User user = (User) request.getSession().getAttribute("user");
        int uid;//用户id
        if(user == null){
            //用户尚未登录
            uid = 0;
        }else{
            //用户已经登录
            uid = user.getUid();
        }
        //3. 调用FavoriteService查询是否收藏
        boolean flag = favoriteService.isFavorite(rid, uid);

        //4. 写回客户端
        writeValue(flag,response);
    }

    /**
     * 添加收藏
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void addFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取线路rid
        String rid = request.getParameter("rid");
        //获取当前用户的登录状态
        User user = (User) request.getSession().getAttribute("user");
        int uid;//用户id
        if(user == null){
            //用户尚未登录
           return;
        }else{
            //用户已经登录
            uid = user.getUid();
        }
        //调用service进行添加工作
        favoriteService.add(rid,uid);
    }
}
