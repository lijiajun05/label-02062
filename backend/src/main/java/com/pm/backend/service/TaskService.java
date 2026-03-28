package com.pm.backend.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pm.backend.entity.Task;
import com.pm.backend.mapper.TaskMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TaskService extends ServiceImpl<TaskMapper, Task> {

    @Resource
    private TaskMapper taskMapper;

    @Resource
    private ProjectService projectService;

    /**
     * 创建任务
     */
    public Task createTask(Task task, Long userId) {
        task.setCreateUserId(userId);
        task.setProgress(0);
        task.setStatus(0); // 待开始
        if (task.getParentId() == null) {
            task.setParentId(0L);
        }
        this.save(task);
        return task;
    }

    /**
     * 获取项目的根任务列表
     */
    public List<Task> getRootTasks(Long projectId, Long userId) {
        if (!projectService.hasProjectPermission(projectId, userId)) {
            return null;
        }
        return taskMapper.selectRootTasksByProjectId(projectId);
    }

    /**
     * 获取任务的子任务列表
     */
    public List<Task> getChildTasks(Long taskId, Long userId) {
        Task task = this.getById(taskId);
        if (task == null || !projectService.hasProjectPermission(task.getProjectId(), userId)) {
            return null;
        }
        return taskMapper.selectChildTasksByParentId(taskId);
    }

    /**
     * 更新任务
     */
    public boolean updateTask(Task task, Long userId) {
        Task existingTask = this.getById(task.getId());
        if (existingTask == null || !projectService.hasProjectPermission(existingTask.getProjectId(), userId)) {
            return false;
        }
        task.setId(existingTask.getId());
        return this.updateById(task);
    }

    /**
     * 删除任务
     */
    public boolean deleteTask(Long taskId, Long userId) {
        Task task = this.getById(taskId);
        if (task == null || !projectService.hasProjectPermission(task.getProjectId(), userId)) {
            return false;
        }
        // 递归删除子任务
        deleteChildTasks(taskId);
        return this.removeById(taskId);
    }

    private void deleteChildTasks(Long parentId) {
        LambdaQueryWrapper<Task> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Task::getParentId, parentId);
        List<Task> childTasks = taskMapper.selectList(wrapper);
        for (Task child : childTasks) {
            deleteChildTasks(child.getId());
            this.removeById(child.getId());
        }
    }

    /**
     * 更新任务进度
     */
    public boolean updateTaskProgress(Long taskId, Integer progress, Long userId) {
        Task task = this.getById(taskId);
        if (task == null || !projectService.hasProjectPermission(task.getProjectId(), userId)) {
            return false;
        }
        task.setProgress(progress);
        // 如果进度为100%，自动标记为已完成
        if (progress >= 100) {
            task.setStatus(2);
        }
        return this.updateById(task);
    }

    /**
     * 获取看板阶段的任务列表
     */
    public List<Task> getTasksByStage(Long projectId, Long stageId, Long userId) {
        if (!projectService.hasProjectPermission(projectId, userId)) {
            return null;
        }
        return taskMapper.selectByProjectIdAndStageId(projectId, stageId);
    }

    /**
     * 移动任务到不同阶段
     */
    public boolean moveTaskToStage(Long taskId, Long stageId, Long userId) {
        Task task = this.getById(taskId);
        if (task == null || !projectService.hasProjectPermission(task.getProjectId(), userId)) {
            return false;
        }
        task.setStageId(stageId);
        return this.updateById(task);
    }
}
