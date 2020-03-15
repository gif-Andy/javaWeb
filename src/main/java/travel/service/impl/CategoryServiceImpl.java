package travel.service.impl;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;
import travel.dao.CategoryDao;
import travel.dao.impl.CategoryDaoImpl;
import travel.domain.Category;
import travel.service.CategoryService;
import travel.util.JedisUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by Administrator on 2020/3/12 0012.
 */
public class CategoryServiceImpl implements CategoryService{
    private CategoryDao categoryDao = new CategoryDaoImpl();
    @Override
    public List<Category> findAll() {
        //1.从redis中查询
        //1.1获取jedis客户端
        Jedis jedis = new Jedis();
        //1.2可使用sortedset排序查询
        //Set<String> categorys =  jedis.zrange("category",0,-1);
        //1.3查询sortedset中的分数(cid)和值(cname)
        Set<Tuple> categorys = jedis.zrangeWithScores("category",0,-1);
        //2.判断查询的集合是否为空
        List<Category> categories = null;
        if(categorys == null || categorys.size() == 0){
            //3.如果为空，需要从数据库查询，再将数据存入redis中
            //3.1 从数据库中查询
            categories = categoryDao.findAll();
            //3.2 将集合数据存储到redis中的category的key
            for (int i = 0; i <categories.size() ; i++) {
                jedis.zadd("category",categories.get(i).getCid(),categories.get(i).getCname());
            }
        }else{
            //4.如果不为空，将set的数据转化为list的数据
            categories = new ArrayList<>();
            for (Tuple tuple:categorys
                 ) {
                Category category = new Category();
                category.setCname(tuple.getElement());
                category.setCid((int) tuple.getScore());
                categories.add(category);
            }
        }
        return categories;
    }
}
