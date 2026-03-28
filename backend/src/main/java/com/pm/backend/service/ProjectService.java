package com.pm.backend.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pm.backend.entity.Project;
import com.pm.backend.entity.ProjectMember;
import com.pm.backend.mapper.ProjectMapper;
import com.pm.backend.mapper.ProjectMemberMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ProjectService extends ServiceImpl<ProjectMapper, Project> {

    @Resource
    private ProjectMemberMapper projectMemberMapper;

    @Resource
    private ProjectMapper projectMapper;

    /**
     * 创建项目
     */
    @Transactional(rollbackFor = Exception.class)
    public Project createProject(Project project, Long userId) {
        project.setCreateUserId(userId);
        project.setStatus(1); // 进行中
        project.setVisibility(0); // 私有
        this.save(project);

        // 添加创建者为项目拥有者
        ProjectMember member = new ProjectMember();
        member.setProjectId(project.getId());
        member.setUserId(userId);
        member.setRole(0); // 拥有者
        projectMemberMapper.insert(member);

        return project;
    }

    /**
     * 获取用户的项目列表
     */
    public List<Project> getUserProjects(Long userId) {
        return projectMapper.selectByUserId(userId);
    }

    /**
     * 检查用户是否有权限访问项目
     */
    public boolean hasProjectPermission(Long projectId, Long userId) {
        LambdaQueryWrapper<ProjectMember> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ProjectMember::getProjectId, projectId)
                .eq(ProjectMember::getUserId, userId);
        return projectMemberMapper.selectCount(wrapper) > 0;
    }

    /**
     * 添加项目成员
     */
    public void addProjectMember(ProjectMember member) {
        projectMemberMapper.insert(member);
    }

    /**
     * 获取项目成员列表
     */
    public List<ProjectMember> getProjectMembers(Long projectId) {
        return projectMemberMapper.selectByProjectIdWithUserInfo(projectId);
    }

    /**
     * 删除项目成员
     */
    public void removeProjectMember(Long projectId, Long userId) {
        LambdaQueryWrapper<ProjectMember> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ProjectMember::getProjectId, projectId)
                .eq(ProjectMember::getUserId, userId);
        projectMemberMapper.delete(wrapper);
    }
}
