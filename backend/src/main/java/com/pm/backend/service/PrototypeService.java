package com.pm.backend.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pm.backend.entity.Prototype;
import com.pm.backend.mapper.PrototypeMapper;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class PrototypeService extends ServiceImpl<PrototypeMapper, Prototype> {

    private static final String PROTOTYPE_DIR = "/Volumes/lenovo/GSB项目/02062-21/label-02062/prototypes/";

    @Resource
    private PrototypeMapper prototypeMapper;

    @Resource
    private ProjectService projectService;

    /**
     * 上传原型文件
     */
    public Prototype uploadPrototype(Long projectId, String name, String description, MultipartFile file, Long userId) throws IOException {
        if (!projectService.hasProjectPermission(projectId, userId)) {
            return null;
        }

        // 生成唯一文件名
        String originalFilename = file.getOriginalFilename();
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String uniqueFileName = UUID.randomUUID().toString() + fileExtension;

        // 保存文件
        File destFile = new File(PROTOTYPE_DIR + uniqueFileName);
        FileUtils.copyInputStreamToFile(file.getInputStream(), destFile);

        // 保存到数据库
        Prototype prototype = new Prototype();
        prototype.setProjectId(projectId);
        prototype.setName(name);
        prototype.setDescription(description);
        prototype.setFilePath(uniqueFileName);
        prototype.setFileName(originalFilename);
        prototype.setFileSize(file.getSize());
        prototype.setCreateUserId(userId);
        this.save(prototype);

        return prototype;
    }

    /**
     * 获取项目的原型列表
     */
    public List<Prototype> getPrototypesByProject(Long projectId, Long userId) {
        if (!projectService.hasProjectPermission(projectId, userId)) {
            return null;
        }
        LambdaQueryWrapper<Prototype> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Prototype::getProjectId, projectId);
        return prototypeMapper.selectList(wrapper);
    }

    /**
     * 获取原型文件路径
     */
    public String getPrototypeFilePath(Long prototypeId, Long userId) {
        Prototype prototype = this.getById(prototypeId);
        if (prototype == null || !projectService.hasProjectPermission(prototype.getProjectId(), userId)) {
            return null;
        }
        return PROTOTYPE_DIR + prototype.getFilePath();
    }

    /**
     * 删除原型
     */
    public boolean deletePrototype(Long prototypeId, Long userId) {
        Prototype prototype = this.getById(prototypeId);
        if (prototype == null || !projectService.hasProjectPermission(prototype.getProjectId(), userId)) {
            return false;
        }

        // 删除文件
        File file = new File(PROTOTYPE_DIR + prototype.getFilePath());
        if (file.exists()) {
            file.delete();
        }

        // 删除数据库记录
        return this.removeById(prototypeId);
    }
}
