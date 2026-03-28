package com.pm.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pm.backend.entity.Project;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ProjectMapper extends BaseMapper<Project> {

    @Select("SELECT p.* FROM project p JOIN project_member pm ON p.id = pm.project_id WHERE pm.user_id = #{userId}")
    List<Project> selectByUserId(@Param("userId") Long userId);
}
