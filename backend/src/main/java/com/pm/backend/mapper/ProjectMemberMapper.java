package com.pm.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pm.backend.entity.ProjectMember;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ProjectMemberMapper extends BaseMapper<ProjectMember> {

    @Select("SELECT pm.*, u.username, u.real_name, u.avatar FROM project_member pm JOIN user u ON pm.user_id = u.id WHERE pm.project_id = #{projectId}")
    List<ProjectMember> selectByProjectIdWithUserInfo(@Param("projectId") Long projectId);
}
