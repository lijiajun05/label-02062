package com.pm.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pm.backend.entity.KanbanStage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface KanbanStageMapper extends BaseMapper<KanbanStage> {

    @Select("SELECT * FROM kanban_stage WHERE project_id = #{projectId} ORDER BY order_num ASC")
    List<KanbanStage> selectByProjectId(@Param("projectId") Long projectId);
}
