package travel.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import travel.domain.Category;
import travel.service.CategoryService;
import travel.service.impl.CategoryServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2020/3/12 0012.
 */
@WebServlet("/category/*")
public class CategoryServlet extends BaseServlet {
    private CategoryService service = new CategoryServiceImpl();
    public void findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.调用查询所有
        List<Category> categories = service.findAll();
        //2.序列化json返回
        /*ObjectMapper objectMapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        objectMapper.writeValue(response.getOutputStream(),categories);*/
        writeValue(categories,response);
    }
}
