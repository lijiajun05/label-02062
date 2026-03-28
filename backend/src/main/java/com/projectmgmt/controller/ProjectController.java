package com.projectmgmt.controller;

import com.projectmgmt.common.Result;
import com.projectmgmt.entity.Project;
import com.projectmgmt.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/projects")
@CrossOrigin
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @GetMapping("/{id}")
    public Result<Project> getById(@PathVariable Long id) {
        return Result.success(projectService.getById(id));
    }

    @GetMapping("/user/{userId}")
    public Result<List<Project>> getByUserId(@PathVariable Long userId) {
        return Result.success(projectService.getByUserId(userId));
    }

    @PostMapping
    public Result<Void> create(@RequestBody Project project) {
        projectService.create(project);
        return Result.success("创建成功");
    }

    @PutMapping
    public Result<Void> update(@RequestBody Project project) {
        projectService.update(project);
        return Result.success("更新成功");
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        projectService.delete(id);
        return Result.success("删除成功");
    }
}
