package com.pm.backend.controller;

import com.pm.backend.common.Result;
import com.pm.backend.entity.KnowledgeBase;
import com.pm.backend.service.KnowledgeBaseService;
import com.pm.backend.service.ProjectService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api/knowledge-base")
public class KnowledgeBaseController {

    @Resource
    private KnowledgeBaseService knowledgeBaseService;

    @Resource
    private ProjectService projectService;

    /**
     * 获取项目知识库列表
     */
    @GetMapping("/project/{projectId}")
    public Result getKnowledgeBasesByProjectId(@PathVariable Long projectId, @RequestParam(required = false) String keyword) {
        // 校验项目权限
        if (!projectService.hasProjectPermission(projectId, 1L)) { // 暂时用1L，实际应该从登录用户获取
            return Result.fail("无访问权限");
        }

        List<KnowledgeBase> knowledgeBases;
        if (keyword != null && !keyword.isEmpty()) {
            knowledgeBases = knowledgeBaseService.searchKnowledgeBase(projectId, keyword);
        } else {
            knowledgeBases = knowledgeBaseService.getKnowledgeBasesByProjectId(projectId);
        }
        return Result.success(knowledgeBases);
    }

    /**
     * 获取知识库详情
     */
    @GetMapping("/{id}")
    public Result getKnowledgeBaseById(@PathVariable Long id) {
        // 校验权限
        if (!knowledgeBaseService.hasKnowledgeBasePermission(id, 1L)) {
            return Result.fail("无访问权限");
        }

        KnowledgeBase knowledgeBase = knowledgeBaseService.getById(id);
        if (knowledgeBase == null) {
            return Result.fail("知识库不存在");
        }
        return Result.success(knowledgeBase);
    }

    /**
     * 创建知识库
     */
    @PostMapping
    public Result createKnowledgeBase(@RequestBody KnowledgeBase knowledgeBase) {
        // 校验项目权限
        if (!projectService.hasProjectPermission(knowledgeBase.getProjectId(), 1L)) {
            return Result.fail("无权限");
        }

        knowledgeBase.setCreateUserId(1L); // 暂时用1L，实际应该从登录用户获取
        knowledgeBaseService.save(knowledgeBase);
        return Result.success(knowledgeBase);
    }

    /**
     * 更新知识库
     */
    @PutMapping("/{id}")
    public Result updateKnowledgeBase(@PathVariable Long id, @RequestBody KnowledgeBase knowledgeBase) {
        // 校验权限
        if (!knowledgeBaseService.hasKnowledgeBasePermission(id, 1L)) {
            return Result.fail("无权限");
        }

        knowledgeBase.setId(id);
        knowledgeBaseService.updateById(knowledgeBase);
        return Result.success(knowledgeBase);
    }

    /**
     * 删除知识库
     */
    @DeleteMapping("/{id}")
    public Result deleteKnowledgeBase(@PathVariable Long id) {
        // 校验权限
        if (!knowledgeBaseService.hasKnowledgeBasePermission(id, 1L)) {
            return Result.fail("无权限");
        }

        knowledgeBaseService.removeById(id);
        return Result.success();
    }

    /**
     * 获取项目知识库分类列表
     */
    @GetMapping("/project/{projectId}/categories")
    public Result getCategories(@PathVariable Long projectId) {
        // 校验项目权限
        if (!projectService.hasProjectPermission(projectId, 1L)) {
            return Result.fail("无访问权限");
        }

        List<String> categories = knowledgeBaseService.getCategories(projectId);
        return Result.success(categories);
    }
}
