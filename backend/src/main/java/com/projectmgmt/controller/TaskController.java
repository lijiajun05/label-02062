package com.projectmgmt.controller;

import com.projectmgmt.common.Result;
import com.projectmgmt.entity.Task;
import com.projectmgmt.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/tasks")
@CrossOrigin
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping("/{id}")
    public Result<Task> getById(@PathVariable Long id) {
        return Result.success(taskService.getById(id));
    }

    @GetMapping("/project/{projectId}")
    public Result<List<Task>> getByProjectId(@PathVariable Long projectId) {
        return Result.success(taskService.getByProjectId(projectId));
    }

    @PostMapping
    public Result<Void> create(@RequestBody Task task) {
        taskService.create(task);
        return Result.success("创建成功");
    }

    @PutMapping
    public Result<Void> update(@RequestBody Task task) {
        taskService.update(task);
        return Result.success("更新成功");
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        taskService.delete(id);
        return Result.success("删除成功");
    }
}
