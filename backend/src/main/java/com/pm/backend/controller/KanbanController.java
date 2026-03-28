package com.pm.backend.controller;

import com.pm.backend.common.Result;
import com.pm.backend.entity.KanbanStage;
import com.pm.backend.service.KanbanService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api/kanban")
public class KanbanController {

    @Resource
    private KanbanService kanbanService;

    /**
     * 创建看板阶段
     */
    @PostMapping("/stage")
    public Result<KanbanStage> createStage(@RequestBody KanbanStage stage, @RequestParam Long userId) {
        KanbanStage createdStage = kanbanService.createStage(stage, userId);
        if (createdStage == null) {
            return Result.fail("无权限创建");
        }
        return Result.success(createdStage);
    }

    /**
     * 获取项目的看板阶段列表
     */
    @GetMapping("/project/{projectId}/stages")
    public Result<List<KanbanStage>> getStagesByProject(@PathVariable Long projectId, @RequestParam Long userId) {
        List<KanbanStage> stages = kanbanService.getStagesByProject(projectId, userId);
        if (stages == null) {
            return Result.fail("无权限访问");
        }
        return Result.success(stages);
    }

    /**
     * 更新看板阶段
     */
    @PutMapping("/stage/{id}")
    public Result<Void> updateStage(@PathVariable Long id, @RequestBody KanbanStage stage, @RequestParam Long userId) {
        stage.setId(id);
        boolean success = kanbanService.updateStage(stage, userId);
        if (!success) {
            return Result.fail("无权限修改或阶段不存在");
        }
        return Result.success();
    }

    /**
     * 删除看板阶段
     */
    @DeleteMapping("/stage/{id}")
    public Result<Void> deleteStage(@PathVariable Long id, @RequestParam Long userId) {
        boolean success = kanbanService.deleteStage(id, userId);
        if (!success) {
            return Result.fail("无权限删除或阶段不存在");
        }
        return Result.success();
    }
}
