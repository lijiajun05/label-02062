package com.projectmgmt.mapper;

import com.projectmgmt.entity.Project;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface ProjectMapper {
    @Select("SELECT * FROM project WHERE id = #{id}")
    Project getById(Long id);

    @Select("SELECT * FROM project WHERE creator_id = #{userId} OR id IN (SELECT project_id FROM project_member WHERE user_id = #{userId})")
    List<Project> getByUserId(Long userId);

    @Insert("INSERT INTO project(name, description, creator_id, visibility, status) VALUES(#{name}, #{description}, #{creatorId}, #{visibility}, #{status})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Project project);

    @Update("UPDATE project SET name=#{name}, description=#{description}, visibility=#{visibility}, status=#{status} WHERE id=#{id}")
    int update(Project project);

    @Delete("DELETE FROM project WHERE id=#{id}")
    int deleteById(Long id);
}
