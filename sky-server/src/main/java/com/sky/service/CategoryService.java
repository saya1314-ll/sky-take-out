package com.sky.service;

import com.github.pagehelper.Page;
import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;

import java.util.List;

public interface CategoryService {
//    根据类型查询分类
    List<Category> list(Integer type);
    //修改分类(根据id)
    void update(CategoryDTO categoryDTO);
    //添加分类
    void save(CategoryDTO categoryDTO);
    //启用禁用分类
    void startOrStop( Integer status,Long id);
    //根据id删除分类
    void deleteById(long id);

    //分类分页查询
    PageResult pageQuery(CategoryPageQueryDTO categoryPageQueryDTO);


}

