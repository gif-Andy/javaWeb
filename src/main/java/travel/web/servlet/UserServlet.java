package travel.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;
import travel.domain.ResultInfo;
import travel.domain.User;
import travel.service.UserService;
import travel.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * Created by Administrator on 2020/3/11 0011.
 */
@WebServlet("/user/*")
public class UserServlet extends BaseServlet {
    private UserService service = new UserServiceImpl();
    /**
     * 注册方法
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void regist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //验证校验
        //获取验证码
        String check = request.getParameter("check");
        //从session中获取验证码
        HttpSession session = request.getSession();
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");//避免验证码复用，移除验证码,保证一个验证码只能使用一次
        //比较
        if(!check .equalsIgnoreCase(checkcode_server) ){//equalsIgnoreCase()不区分大小写
            //验证码填写错误
            ResultInfo info = new ResultInfo();
            info.setFlag(false);
            info.setErrorMsg("验证码错误!");
            //将info对象序列化为json
            /*ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(info);
            //将json错误对象写回客户端
            //设置content-type
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(json);*/
            writeValue(info,response);
            return;
        }
        //1.获取数据
        Map<String,String[]> map = request.getParameterMap();//request.getParameterMap():根据表单中的name值获取value值，不需要参数，返回结果为Map<String,String[]>
        //2.封装对象
        User user = new User();
        try {
            BeanUtils.populate(user,map);//这个方法会遍历map<key, value>中的key，如果bean中有这个属性，就把这个key对应的value值赋给bean的属性。
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        //3.调用service完成注册操作
        //UserService service = new UserServiceImpl();
        boolean flag = service.regist(user);
        ResultInfo info = new ResultInfo();
        //4.响应结果
        if(flag){
            //注册成功
            info.setFlag(true);
        }else {
            //注册失败
            info.setFlag(false);
            info.setErrorMsg("注册失败!");
        }
       /* //将info对象序列化为json
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(info);
        //将json对象写回客户端
        //设置content-type
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);*/
       writeValue(info,response);
    }

    /**
     * 登录方法
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取用户名和密码
        Map<String,String[]> map = request.getParameterMap();
        //封装User对象
        User user = new User();
        try {
            BeanUtils.populate(user,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        //调用Service查询
        //UserService service = new UserServiceImpl();
        User u = service.login(user);
        ResultInfo info = new ResultInfo();
        //判断用户名与密码
        if(u == null){
            //用户名密码错误
            info.setErrorMsg("用户名或密码错误，请重试！");
            info.setFlag(false);
        }
        //判断账号是否激活
        if(u != null && !"Y".equals(u.getStatus())){
            //账号未激活
            info.setFlag(false);
            info.setErrorMsg("账户未激活！");
        }
        //登录成功
        else if(u != null && "Y".equals(u.getStatus())){
            info.setFlag(true);
            request.getSession().setAttribute("user",u);
        }
        //响应数据
       /* ObjectMapper objectMapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        objectMapper.writeValue(response.getOutputStream(),info);*/
       writeValue(info,response);
    }

    /**
     * 查找(单个人)
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //从session中获取登录用户
        User user = (User) request.getSession().getAttribute("user");
        //UserService service = new UserServiceImpl();
        //将user写回客户端
        /*ObjectMapper objectMapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        objectMapper.writeValue(response.getOutputStream(),user);*/
        if(user != null){
            writeValue(user,response);
        }

    }

    /**
     * 退出方法
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void exit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //销毁session
        request.getSession().invalidate();
        //跳转登录页面
        response.sendRedirect(request.getContextPath()+"/login.html");
    }

    /**
     * 激活方法
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void active(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取激活码
        String code = request.getParameter("code");
        if(code != null){
            //2.调用Service完成激活
            //UserService service = new UserServiceImpl();
            boolean flag = service.active(code);
            //判断标记
            String msg = null;
            if(flag){
                //激活成功
                msg = "激活成功，请<a href = 'login.html'>登录</a>";
            }else {
                //激活失败
                msg = "激活失败，请联系管理员！";
            }
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(msg);
        }
    }
}
