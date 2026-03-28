package com.pm.backend.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pm.backend.entity.KnowledgeBase;
import com.pm.backend.mapper.KnowledgeBaseMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class KnowledgeBaseService extends ServiceImpl<KnowledgeBaseMapper, KnowledgeBase> {

    @Resource
    private KnowledgeBaseMapper knowledgeBaseMapper;

    @Resource
    private ProjectService projectService;

    /**
     * 获取项目知识库列表
     */
    public List<KnowledgeBase> getKnowledgeBasesByProjectId(Long projectId) {
        LambdaQueryWrapper<KnowledgeBase> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(KnowledgeBase::getProjectId, projectId)
                .orderByDesc(KnowledgeBase::getUpdateTime);
        return list(queryWrapper);
    }

    /**
     * 搜索知识库内容
     */
    public List<KnowledgeBase> searchKnowledgeBase(Long projectId, String keyword) {
        LambdaQueryWrapper<KnowledgeBase> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(KnowledgeBase::getProjectId, projectId)
                .and(wrapper -> wrapper.like(KnowledgeBase::getTitle, keyword)
                        .or()
                        .like(KnowledgeBase::getContent, keyword))
                .orderByDesc(KnowledgeBase::getUpdateTime);
        return list(queryWrapper);
    }

    /**
     * 获取分类列表
     */
    public List<String> getCategories(Long projectId) {
        return knowledgeBaseMapper.selectObjs(new LambdaQueryWrapper<KnowledgeBase>()
                .eq(KnowledgeBase::getProjectId, projectId)
                .select(KnowledgeBase::getCategory)
                .groupBy(KnowledgeBase::getCategory)
                .orderByAsc(KnowledgeBase::getCategory));
    }

    /**
     * 校验知识库权限
     */
    public boolean hasKnowledgeBasePermission(Long knowledgeBaseId, Long userId) {
        KnowledgeBase knowledgeBase = getById(knowledgeBaseId);
        if (knowledgeBase == null) {
            return false;
        }
        return projectService.hasProjectPermission(knowledgeBase.getProjectId(), userId);
    }
}
