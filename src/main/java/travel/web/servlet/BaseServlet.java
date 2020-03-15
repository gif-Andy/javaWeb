package travel.web.servlet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


/**
 * Created by Administrator on 2020/3/11 0011.
 */

public class BaseServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //完成方法分发
        //1.获取请求路径
        String uri = request.getRequestURI();   //返回：/user/xx
        //2.获取方法名称
        String methodName = uri.substring(uri.lastIndexOf("/")+1);
        //3.获取方法对象Method
        try {
            //忽略访问修饰符，获取方法
            Method method = this.getClass().getMethod(methodName,HttpServletRequest.class,HttpServletResponse.class);
            //4.执行方法
            //暴力反射
            //  method.setAccessible(true);
            method.invoke(this,request,response);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将传入的对象序列化为json，并写回客户端
     * @param obj
     * @param response
     */
    public void writeValue(Object obj,HttpServletResponse response) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        objectMapper.writeValue(response.getOutputStream(),obj);
    }
    public String writeValue(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }
}
