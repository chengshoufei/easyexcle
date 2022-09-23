package com.example.demo1.mapper;

import com.example.demo1.entity.TArea;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @ClassName AreaMapper
 * @Date 2022/8/17 20:22
 * @Author chengshoufei
 * @Description TODO
 */
@Mapper
public interface AreaMapper {
    List<TArea> getAreas(Long pid);
   TArea getArea(Long pid);
}
