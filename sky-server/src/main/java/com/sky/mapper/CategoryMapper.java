package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CategoryMapper {

    //根据type查询分类
//    @Select("select * from sky_take_out.category where type=#{type}")
    List<Category> list(Integer type);


    //修改分类
    void update(Category category);

    //新增分类
    @Insert("insert into sky_take_out.category(id,type,name,sort,status,create_time,update_time,create_user,update_user) values (#{id},#{type},#{name},#{sort},#{status},#{createTime},#{updateTime},#{createUser},#{updateUser})")
    void insert(Category category);

    //分类分页查询
    Page<Category> pageQuery(CategoryPageQueryDTO categoryPageQueryDTO);

    //根据id删除分类
    @Delete("DELETE from sky_take_out.category where id=#{id}")
    void deleteById(long id);
}
