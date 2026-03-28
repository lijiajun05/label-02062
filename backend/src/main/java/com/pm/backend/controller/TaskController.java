package com.pm.backend.controller;

import com.pm.backend.common.Result;
import com.pm.backend.entity.Task;
import com.pm.backend.service.TaskService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api/task")
public class TaskController {

    @Resource
    private TaskService taskService;

    /**
     * 创建任务
     */
    @PostMapping
    public Result<Task> createTask(@RequestBody Task task, @RequestParam Long userId) {
        Task createdTask = taskService.createTask(task, userId);
        return Result.success(createdTask);
    }

    /**
     * 获取项目的根任务列表
     */
    @GetMapping("/project/{projectId}/root")
    public Result<List<Task>> getRootTasks(@PathVariable Long projectId, @RequestParam Long userId) {
        List<Task> tasks = taskService.getRootTasks(projectId, userId);
        if (tasks == null) {
            return Result.fail("无权限访问");
        }
        return Result.success(tasks);
    }

    /**
     * 获取任务的子任务列表
     */
    @GetMapping("/{taskId}/children")
    public Result<List<Task>> getChildTasks(@PathVariable Long taskId, @RequestParam Long userId) {
        List<Task> tasks = taskService.getChildTasks(taskId, userId);
        if (tasks == null) {
            return Result.fail("无权限访问");
        }
        return Result.success(tasks);
    }

    /**
     * 获取任务详情
     */
    @GetMapping("/{id}")
    public Result<Task> getTaskById(@PathVariable Long id, @RequestParam Long userId) {
        Task task = taskService.getById(id);
        if (task == null) {
            return Result.fail("任务不存在");
        }
        // 这里可以添加权限检查
        return Result.success(task);
    }

    /**
     * 更新任务
     */
    @PutMapping("/{id}")
    public Result<Void> updateTask(@PathVariable Long id, @RequestBody Task task, @RequestParam Long userId) {
        task.setId(id);
        boolean success = taskService.updateTask(task, userId);
        if (!success) {
            return Result.fail("无权限修改或任务不存在");
        }
        return Result.success();
    }

    /**
     * 删除任务
     */
    @DeleteMapping("/{id}")
    public Result<Void> deleteTask(@PathVariable Long id, @RequestParam Long userId) {
        boolean success = taskService.deleteTask(id, userId);
        if (!success) {
            return Result.fail("无权限删除或任务不存在");
        }
        return Result.success();
    }

    /**
     * 更新任务进度
     */
    @PutMapping("/{id}/progress")
    public Result<Void> updateTaskProgress(@PathVariable Long id, @RequestParam Integer progress, @RequestParam Long userId) {
        boolean success = taskService.updateTaskProgress(id, progress, userId);
        if (!success) {
            return Result.fail("无权限修改或任务不存在");
        }
        return Result.success();
    }

    /**
     * 获取看板阶段的任务列表
     */
    @GetMapping("/project/{projectId}/stage/{stageId}")
    public Result<List<Task>> getTasksByStage(@PathVariable Long projectId, @PathVariable Long stageId, @RequestParam Long userId) {
        List<Task> tasks = taskService.getTasksByStage(projectId, stageId, userId);
        if (tasks == null) {
            return Result.fail("无权限访问");
        }
        return Result.success(tasks);
    }

    /**
     * 移动任务到不同阶段
     */
    @PutMapping("/{id}/stage/{stageId}")
    public Result<Void> moveTaskToStage(@PathVariable Long id, @PathVariable Long stageId, @RequestParam Long userId) {
        boolean success = taskService.moveTaskToStage(id, stageId, userId);
        if (!success) {
            return Result.fail("无权限移动或任务不存在");
        }
        return Result.success();
    }
}
