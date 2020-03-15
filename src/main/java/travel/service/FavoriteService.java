package travel.service;

import travel.domain.Favorite;

/**
 * Created by Administrator on 2020/3/15 0015.
 */
public interface FavoriteService {
    /*boolean isFavorite(String rid, int uid);*/
    public boolean isFavorite(String rid, int uid);

    void add(String rid, int uid);
}
