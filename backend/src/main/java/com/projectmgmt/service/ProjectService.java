package com.projectmgmt.service;

import com.projectmgmt.entity.Project;
import com.projectmgmt.mapper.ProjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProjectService {

    @Autowired
    private ProjectMapper projectMapper;

    public Project getById(Long id) {
        return projectMapper.getById(id);
    }

    public List<Project> getByUserId(Long userId) {
        return projectMapper.getByUserId(userId);
    }

    public int create(Project project) {
        return projectMapper.insert(project);
    }

    public int update(Project project) {
        return projectMapper.update(project);
    }

    public int delete(Long id) {
        return projectMapper.deleteById(id);
    }
}
