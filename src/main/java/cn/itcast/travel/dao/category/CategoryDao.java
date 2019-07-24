package cn.itcast.travel.dao.category;

import cn.itcast.travel.domain.Category;

import java.util.List;

public interface CategoryDao {
    List<Category> findAll();
}
