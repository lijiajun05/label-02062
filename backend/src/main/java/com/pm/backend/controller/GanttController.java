package com.pm.backend.controller;

import com.pm.backend.common.Result;
import com.pm.backend.entity.Project;
import com.pm.backend.entity.Task;
import com.pm.backend.service.ProjectService;
import com.pm.backend.service.TaskService;
import com.pm.backend.vo.GanttTaskVO;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/gantt")
public class GanttController {

    @Resource
    private ProjectService projectService;

    @Resource
    private TaskService taskService;

    /**
     * 获取项目的甘特图数据
     */
    @GetMapping("/project/{projectId}")
    public Result<Map<String, Object>> getProjectGanttData(@PathVariable Long projectId, Long userId) {
        if (!projectService.hasProjectPermission(projectId, userId)) {
            return Result.fail("无权限访问");
        }

        Project project = projectService.getById(projectId);
        List<Task> rootTasks = taskService.getRootTasks(projectId, userId);
        
        Map<String, Object> result = new HashMap<>();
        result.put("project", project);
        result.put("tasks", convertToGanttVO(rootTasks, userId));
        
        return Result.success(result);
    }

    private List<GanttTaskVO> convertToGanttVO(List<Task> tasks, Long userId) {
        List<GanttTaskVO> voList = new ArrayList<>();
        for (Task task : tasks) {
            GanttTaskVO vo = new GanttTaskVO();
            BeanUtils.copyProperties(task, vo);
            vo.setStart(task.getStartDate());
            vo.setEnd(task.getEndDate());
            
            // 获取子任务
            List<Task> childTasks = taskService.getChildTasks(task.getId(), userId);
            if (childTasks != null && !childTasks.isEmpty()) {
                vo.setChildren(convertToGanttVO(childTasks, userId));
            }
            
            voList.add(vo);
        }
        return voList;
    }
}
