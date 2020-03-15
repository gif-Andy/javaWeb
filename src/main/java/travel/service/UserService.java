package travel.service;

import travel.domain.User;

/**
 * Created by Administrator on 2020/3/8 0008.
 */
public interface UserService {

    boolean regist(User user);

    boolean active(String code);

    User login(User user);

}
