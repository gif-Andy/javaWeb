package travel.service.impl;

import travel.dao.FavoriteDao;
import travel.dao.impl.FavoriteDaoImpl;
import travel.domain.Favorite;
import travel.service.FavoriteService;

/**
 * Created by Administrator on 2020/3/15 0015.
 */
public class FavoriteServiceImpl implements FavoriteService{
    private FavoriteDao favoriteDao = new FavoriteDaoImpl();

    public boolean isFavorite(String rid, int uid) {
        Favorite favorite = favoriteDao.findByRidAndUid(Integer.parseInt(rid), uid);
        return favorite != null;//如果对象有值，则为true，反之，则为false
    }

    @Override
    public void add(String rid, int uid) {
        favoriteDao.add(Integer.parseInt(rid),uid);
    }
}
