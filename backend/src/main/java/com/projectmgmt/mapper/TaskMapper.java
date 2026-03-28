package com.projectmgmt.mapper;

import com.projectmgmt.entity.Task;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface TaskMapper {
    @Select("SELECT * FROM task WHERE id = #{id}")
    Task getById(Long id);

    @Select("SELECT * FROM task WHERE project_id = #{projectId}")
    List<Task> getByProjectId(Long projectId);

    @Insert("INSERT INTO task(project_id, name, description, stage, status, assignee_id, start_time, end_time, progress, parent_id) " +
            "VALUES(#{projectId}, #{name}, #{description}, #{stage}, #{status}, #{assigneeId}, #{startTime}, #{endTime}, #{progress}, #{parentId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Task task);

    @Update("UPDATE task SET name=#{name}, description=#{description}, stage=#{stage}, status=#{status}, " +
            "assignee_id=#{assigneeId}, start_time=#{startTime}, end_time=#{endTime}, progress=#{progress} WHERE id=#{id}")
    int update(Task task);

    @Delete("DELETE FROM task WHERE id=#{id}")
    int deleteById(Long id);
}
