package com.pm.backend.controller;

import com.pm.backend.common.Result;
import com.pm.backend.entity.Prototype;
import com.pm.backend.service.PrototypeService;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/prototype")
public class PrototypeController {

    @Resource
    private PrototypeService prototypeService;

    /**
     * 上传原型文件
     */
    @PostMapping("/upload")
    public Result<Prototype> uploadPrototype(
            @RequestParam Long projectId,
            @RequestParam String name,
            @RequestParam(required = false) String description,
            @RequestParam("file") MultipartFile file,
            @RequestParam Long userId) {
        try {
            Prototype prototype = prototypeService.uploadPrototype(projectId, name, description, file, userId);
            if (prototype == null) {
                return Result.fail("无权限上传");
            }
            return Result.success(prototype);
        } catch (IOException e) {
            return Result.fail("上传失败: " + e.getMessage());
        }
    }

    /**
     * 获取项目的原型列表
     */
    @GetMapping("/project/{projectId}")
    public Result<List<Prototype>> getPrototypesByProject(@PathVariable Long projectId, @RequestParam Long userId) {
        List<Prototype> prototypes = prototypeService.getPrototypesByProject(projectId, userId);
        if (prototypes == null) {
            return Result.fail("无权限访问");
        }
        return Result.success(prototypes);
    }

    /**
     * 在线查看原型
     */
    @GetMapping("/view/{id}")
    public ResponseEntity<Resource> viewPrototype(@PathVariable Long id, @RequestParam Long userId) {
        String filePath = prototypeService.getPrototypeFilePath(id, userId);
        if (filePath == null) {
            return ResponseEntity.notFound().build();
        }

        File file = new File(filePath);
        if (!file.exists()) {
            return ResponseEntity.notFound().build();
        }

        Resource resource = new FileSystemResource(file);
        
        HttpHeaders headers = new HttpHeaders();
        if (filePath.endsWith(".html")) {
            headers.setContentType(MediaType.TEXT_HTML);
        } else {
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        }
        
        return ResponseEntity.ok()
                .headers(headers)
                .body(resource);
    }

    /**
     * 删除原型
     */
    @DeleteMapping("/{id}")
    public Result<Void> deletePrototype(@PathVariable Long id, @RequestParam Long userId) {
        boolean success = prototypeService.deletePrototype(id, userId);
        if (!success) {
            return Result.fail("无权限删除或原型不存在");
        }
        return Result.success();
    }
}
