package travel.dao;

import travel.domain.Favorite;

/**
 * Created by Administrator on 2020/3/15 0015.
 */
public interface FavoriteDao {
    public Favorite findByRidAndUid(int rid, int uid);

    int finCountByRid(int rid);

    void add(int rid, int uid);
}
