package travel.dao;

import travel.domain.Seller;

/**
 * Created by Administrator on 2020/3/15 0015.
 */
public interface SellerDao {
    Seller findBySid(int sid);
}
