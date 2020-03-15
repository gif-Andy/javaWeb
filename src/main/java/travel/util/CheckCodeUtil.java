package travel.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import travel.domain.ResultInfo;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by Administrator on 2020/3/9 0009.
 */
public class CheckCodeUtil {
    private ResultInfo info = new ResultInfo();
    public ResultInfo checkCode(String check, HttpSession session) throws Exception {
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");//避免验证码复用，移除验证码,保证一个验证码只能使用一次
        //比较
        if(!check .equalsIgnoreCase(checkcode_server) ){//equalsIgnoreCase()不区分大小写
            //验证码填写错误
            info.setFlag(false);
            info.setErrorMsg("验证码错误!");
        }
        return info;
    }

}

