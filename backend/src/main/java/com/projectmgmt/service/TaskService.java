package com.projectmgmt.service;

import com.projectmgmt.entity.Task;
import com.projectmgmt.mapper.TaskMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskMapper taskMapper;

    public Task getById(Long id) {
        return taskMapper.getById(id);
    }

    public List<Task> getByProjectId(Long projectId) {
        return taskMapper.getByProjectId(projectId);
    }

    public int create(Task task) {
        return taskMapper.insert(task);
    }

    public int update(Task task) {
        return taskMapper.update(task);
    }

    public int delete(Long id) {
        return taskMapper.deleteById(id);
    }
}
