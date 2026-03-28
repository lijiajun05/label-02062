package com.pm.backend.controller;

import com.pm.backend.common.Result;
import com.pm.backend.entity.Project;
import com.pm.backend.entity.ProjectMember;
import com.pm.backend.service.ProjectService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api/project")
public class ProjectController {

    @Resource
    private ProjectService projectService;

    /**
     * 创建项目
     */
    @PostMapping
    public Result<Project> createProject(@RequestBody Project project, @RequestParam Long userId) {
        Project createdProject = projectService.createProject(project, userId);
        return Result.success(createdProject);
    }

    /**
     * 获取用户的项目列表
     */
    @GetMapping("/user/{userId}")
    public Result<List<Project>> getUserProjects(@PathVariable Long userId) {
        List<Project> projects = projectService.getUserProjects(userId);
        return Result.success(projects);
    }

    /**
     * 获取项目详情
     */
    @GetMapping("/{id}")
    public Result<Project> getProjectById(@PathVariable Long id, @RequestParam Long userId) {
        if (!projectService.hasProjectPermission(id, userId)) {
            return Result.fail("无权限访问该项目");
        }
        Project project = projectService.getById(id);
        return Result.success(project);
    }

    /**
     * 更新项目
     */
    @PutMapping("/{id}")
    public Result<Void> updateProject(@PathVariable Long id, @RequestBody Project project, @RequestParam Long userId) {
        if (!projectService.hasProjectPermission(id, userId)) {
            return Result.fail("无权限修改该项目");
        }
        project.setId(id);
        projectService.updateById(project);
        return Result.success();
    }

    /**
     * 删除项目
     */
    @DeleteMapping("/{id}")
    public Result<Void> deleteProject(@PathVariable Long id, @RequestParam Long userId) {
        if (!projectService.hasProjectPermission(id, userId)) {
            return Result.fail("无权限删除该项目");
        }
        projectService.removeById(id);
        return Result.success();
    }

    /**
     * 添加项目成员
     */
    @PostMapping("/{projectId}/member")
    public Result<Void> addProjectMember(@PathVariable Long projectId, @RequestBody ProjectMember member, @RequestParam Long userId) {
        if (!projectService.hasProjectPermission(projectId, userId)) {
            return Result.fail("无权限添加成员");
        }
        member.setProjectId(projectId);
        projectService.addProjectMember(member);
        return Result.success();
    }

    /**
     * 获取项目成员列表
     */
    @GetMapping("/{projectId}/members")
    public Result<List<ProjectMember>> getProjectMembers(@PathVariable Long projectId, @RequestParam Long userId) {
        if (!projectService.hasProjectPermission(projectId, userId)) {
            return Result.fail("无权限查看成员");
        }
        List<ProjectMember> members = projectService.getProjectMembers(projectId);
        return Result.success(members);
    }

    /**
     * 删除项目成员
     */
    @DeleteMapping("/{projectId}/member/{memberId}")
    public Result<Void> removeProjectMember(@PathVariable Long projectId, @PathVariable Long memberId, @RequestParam Long userId) {
        if (!projectService.hasProjectPermission(projectId, userId)) {
            return Result.fail("无权限删除成员");
        }
        projectService.removeProjectMember(projectId, memberId);
        return Result.success();
    }
}
