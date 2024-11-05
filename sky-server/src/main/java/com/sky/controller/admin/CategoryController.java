package com.sky.controller.admin;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/category")
@Slf4j
@Api(tags="分类相关接口")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    //修改分类
    @PutMapping
    @ApiOperation("修改分类")
    public Result update(@RequestBody CategoryDTO categoryDTO) {
        log.info("修改分类:{}", categoryDTO);
        categoryService.update(categoryDTO);
        return Result.success();
    }


    @GetMapping("/page")
    @ApiOperation("分类分页查询")
public Result<PageResult> page(CategoryPageQueryDTO categoryPageQueryDTO) {
        log.info("分类分页查询:{}", categoryPageQueryDTO);
        PageResult pageResult=categoryService.pageQuery(categoryPageQueryDTO);
        return Result.success(pageResult);

    }


    //启用禁用分类
    @PostMapping("/status/{status}")
    @ApiOperation("启用禁用分类")
    public Result startorStop(@PathVariable Integer status,Long id) {
        log.info("启用禁用分类的id:{}",id);
        categoryService.startOrStop(status,id);
        return Result.success();
    }

    @PostMapping
    @ApiOperation("新增分类")
    public Result<String> save(@RequestBody CategoryDTO categoryDTO) {
        log.info("新增员工:{}", categoryDTO);
        categoryService.save(categoryDTO);
        return Result.success();
    }

    //根据id删除分类(注意:如果该分类关联着dish和setmeal则该分类不能删除)
    @DeleteMapping
    @ApiOperation("删除分类")
    public Result<String> deleteById(Long id) {
        log.info("删除的分类id:{}", id);
        categoryService.deleteById(id);
        return Result.success();
    }


    //根据类型查询分类
    @GetMapping("/list")
    @ApiOperation("根据类型查询分类")
    public Result<List<Category>> list(Integer type) {//因为同一个type类型有多个数据,所以用集合(List<Category>)接收
        log.info("根据类型查询分类,分类的type为:{}", type);
        List<Category> list= categoryService.list(type);
        return Result.success(list);
    }

}
