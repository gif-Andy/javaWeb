package travel.dao;

import travel.domain.Category;

import java.util.List;

/**
 * Created by Administrator on 2020/3/12 0012.
 */
public interface CategoryDao {
    public List<Category> findAll();
}
