package travel.service.impl;

import travel.dao.UserDao;
import travel.dao.impl.UserDaoImpl;
import travel.domain.User;
import travel.service.UserService;
import travel.util.MailUtils;
import travel.util.UuidUtil;

/**
 * Created by Administrator on 2020/3/8 0008.
 */
public class UserServiceImpl implements UserService{
    UserDao userDao = new UserDaoImpl();
    @Override
    /**
     * 注册用户
     * @param user
     * @return
     */
    public boolean regist(User user) {
        //1.根据用户名查询用户对象
        User u = userDao.findByUsername(user.getUsername());
        if(u != null){
            //用户名存在，注册失败
            return false;
        }
        //2.保存用户信息
        //2.1设置激活码，激活码唯一
        user.setCode(UuidUtil.getUuid());
        //2.2设置激活状态
            user.setStatus("N");
        userDao.save(user);
        //3.激活邮件发送
        String content = "<a href='http://localhost:8080/user/cative?code="+user.getCode()+"'>点击激活【旅游网】</a>";
        MailUtils.sendMail(user.getEmail(),content,"激活邮件");
        return true;

    }

    @Override
    public boolean active(String code) {
        //1.根据激活码查询用户对象
        User user = userDao.findByCode(code);
        if(user != null){
        //2.调用dao的修改激活状态的方法
            userDao.updateStatus(user);
            return true;
        }else{
            return false;
        }

    }

    @Override
    public User login(User user) {
        return userDao.findByUsernameAndPassword(user.getUsername(),user.getPassword());
    }

}
