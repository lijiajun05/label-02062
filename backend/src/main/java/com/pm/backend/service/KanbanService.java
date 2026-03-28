package com.pm.backend.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pm.backend.entity.KanbanStage;
import com.pm.backend.mapper.KanbanStageMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class KanbanService extends ServiceImpl<KanbanStageMapper, KanbanStage> {

    @Resource
    private KanbanStageMapper kanbanStageMapper;

    @Resource
    private ProjectService projectService;

    /**
     * 创建看板阶段
     */
    public KanbanStage createStage(KanbanStage stage, Long userId) {
        if (!projectService.hasProjectPermission(stage.getProjectId(), userId)) {
            return null;
        }
        // 获取当前项目的最大排序号
        LambdaQueryWrapper<KanbanStage> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(KanbanStage::getProjectId, stage.getProjectId())
                .orderByDesc(KanbanStage::getOrderNum)
                .last("LIMIT 1");
        KanbanStage lastStage = kanbanStageMapper.selectOne(wrapper);
        stage.setOrderNum(lastStage != null ? lastStage.getOrderNum() + 1 : 0);
        this.save(stage);
        return stage;
    }

    /**
     * 获取项目的看板阶段列表
     */
    public List<KanbanStage> getStagesByProject(Long projectId, Long userId) {
        if (!projectService.hasProjectPermission(projectId, userId)) {
            return null;
        }
        return kanbanStageMapper.selectByProjectId(projectId);
    }

    /**
     * 更新看板阶段
     */
    public boolean updateStage(KanbanStage stage, Long userId) {
        KanbanStage existingStage = this.getById(stage.getId());
        if (existingStage == null || !projectService.hasProjectPermission(existingStage.getProjectId(), userId)) {
            return false;
        }
        stage.setId(existingStage.getId());
        return this.updateById(stage);
    }

    /**
     * 删除看板阶段
     */
    public boolean deleteStage(Long stageId, Long userId) {
        KanbanStage stage = this.getById(stageId);
        if (stage == null || !projectService.hasProjectPermission(stage.getProjectId(), userId)) {
            return false;
        }
        return this.removeById(stageId);
    }
}
