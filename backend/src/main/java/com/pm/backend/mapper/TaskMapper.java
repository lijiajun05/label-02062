package com.pm.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pm.backend.entity.Task;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TaskMapper extends BaseMapper<Task> {

    @Select("SELECT t.* FROM task t WHERE t.project_id = #{projectId} AND t.parent_id = 0 ORDER BY t.order_num ASC")
    List<Task> selectRootTasksByProjectId(@Param("projectId") Long projectId);

    @Select("SELECT t.* FROM task t WHERE t.parent_id = #{parentId} ORDER BY t.order_num ASC")
    List<Task> selectChildTasksByParentId(@Param("parentId") Long parentId);

    @Select("SELECT t.* FROM task t WHERE t.project_id = #{projectId} AND t.stage_id = #{stageId} ORDER BY t.order_num ASC")
    List<Task> selectByProjectIdAndStageId(@Param("projectId") Long projectId, @Param("stageId") Long stageId);
}
