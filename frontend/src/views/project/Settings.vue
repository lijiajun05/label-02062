<template>
  <div class="settings-page">
    <div class="page-header">
      <h3>项目设置</h3>
    </div>
    
    <el-card class="settings-card">
      <el-tabs v-model="activeTab">
        <el-tab-pane label="基本信息" name="basic">
          <el-form ref="basicForm" :model="basicForm" label-width="100px">
            <el-form-item label="项目名称" prop="name">
              <el-input v-model="basicForm.name"></el-input>
            </el-form-item>
            <el-form-item label="项目描述" prop="description">
              <el-input type="textarea" v-model="basicForm.description" :rows="4"></el-input>
            </el-form-item>
            <el-form-item label="项目图标" prop="icon">
              <el-upload
                class="avatar-uploader"
                action="#"
                :show-file-list="false"
                :on-success="handleAvatarSuccess"
                :before-upload="beforeAvatarUpload"
              >
                <img v-if="basicForm.icon" :src="basicForm.icon" class="avatar">
                <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
              </el-upload>
            </el-form-item>
            <el-form-item label="可见范围" prop="visibility">
              <el-radio-group v-model="basicForm.visibility">
                <el-radio :label="0">私有 - 只有项目成员可以访问</el-radio>
                <el-radio :label="1">公开 - 所有人可以查看</el-radio>
              </el-radio-group>
            </el-form-item>
            <el-form-item label="项目状态" prop="status">
              <el-radio-group v-model="basicForm.status">
                <el-radio :label="1">进行中</el-radio>
                <el-radio :label="0">已归档</el-radio>
              </el-radio-group>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="saveBasicInfo">保存</el-button>
            </el-form-item>
          </el-form>
        </el-tab-pane>
        
        <el-tab-pane label="成员管理" name="members">
          <div class="member-header">
            <el-button type="primary" @click="showAddMemberDialog = true">添加成员</el-button>
          </div>
          <el-table :data="members" style="width: 100%">
            <el-table-column label="头像" width="80">
              <template #default="scope">
                <el-avatar :size="32">{{ scope.row.name.charAt(0) }}</el-avatar>
              </template>
            </el-table-column>
            <el-table-column prop="name" label="姓名" width="120"></el-table-column>
            <el-table-column prop="email" label="邮箱"></el-table-column>
            <el-table-column prop="role" label="角色" width="120">
              <template #default="scope">
                <el-tag :type="getRoleType(scope.row.role)">{{ getRoleText(scope.row.role) }}</el-tag>
              </template>
            </el-table-column>
            <el-table-column prop="joinTime" label="加入时间" width="180"></el-table-column>
            <el-table-column label="操作" width="150">
              <template #default="scope">
                <el-button type="primary" size="small" @click="editMemberRole(scope.row)">
                  编辑角色
                </el-button>
                <el-button type="danger" size="small" @click="removeMember(scope.row)">
                  移除
                </el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-tab-pane>
        
        <el-tab-pane label="高级设置" name="advanced">
          <el-form ref="advancedForm" :model="advancedForm" label-width="100px">
            <el-form-item label="任务自动完成" prop="autoComplete">
              <el-switch v-model="advancedForm.autoComplete"></el-switch>
              <div class="form-tip">当任务进度达到100%时自动标记为完成</div>
            </el-form-item>
            <el-form-item label="邮件通知" prop="emailNotification">
              <el-switch v-model="advancedForm.emailNotification"></el-switch>
              <div class="form-tip">任务分配、评论等操作发送邮件通知</div>
            </el-form-item>
            <el-form-item label="危险操作">
              <el-button type="danger" @click="archiveProject">归档项目</el-button>
              <el-button type="danger" @click="deleteProject" style="margin-left: 10px;">删除项目</el-button>
              <div class="form-tip">删除项目将删除所有相关数据，且无法恢复</div>
            </el-form-item>
          </el-form>
        </el-tab-pane>
      </el-tabs>
    </el-card>

    <!-- 添加成员对话框 -->
    <el-dialog v-model="showAddMemberDialog" title="添加成员" width="500px">
      <el-form ref="addMemberForm" :model="addMemberForm" label-width="80px">
        <el-form-item label="用户邮箱" prop="email">
          <el-input v-model="addMemberForm.email" placeholder="请输入用户邮箱"></el-input>
        </el-form-item>
        <el-form-item label="角色" prop="role">
          <el-radio-group v-model="addMemberForm.role">
            <el-radio :label="1">管理员</el-radio>
            <el-radio :label="2">成员</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="showAddMemberDialog = false">取消</el-button>
        <el-button type="primary" @click="addMember">添加</el-button>
      </template>
    </el-dialog>

    <!-- 编辑角色对话框 -->
    <el-dialog v-model="showEditRoleDialog" title="编辑角色" width="400px">
      <el-form ref="editRoleForm" :model="editRoleForm" label-width="80px">
        <el-form-item label="角色" prop="role">
          <el-radio-group v-model="editRoleForm.role">
            <el-radio :label="0">拥有者</el-radio>
            <el-radio :label="1">管理员</el-radio>
            <el-radio :label="2">成员</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="showEditRoleDialog = false">取消</el-button>
        <el-button type="primary" @click="saveMemberRole">保存</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: 'Settings',
  data() {
    return {
      activeTab: 'basic',
      showAddMemberDialog: false,
      showEditRoleDialog: false,
      basicForm: {
        name: '电商平台开发',
        description: '开发一个功能完善的电商平台',
        icon: '',
        visibility: 0,
        status: 1
      },
      advancedForm: {
        autoComplete: true,
        emailNotification: true
      },
      addMemberForm: {
        email: '',
        role: 2
      },
      editRoleForm: {
        id: null,
        role: null
      },
      members: [
        { id: 1, name: '张三', email: 'zhangsan@example.com', role: 0, joinTime: '2024-01-01 10:00:00' },
        { id: 2, name: '李四', email: 'lisi@example.com', role: 1, joinTime: '2024-01-02 14:30:00' },
        { id: 3, name: '王五', email: 'wangwu@example.com', role: 2, joinTime: '2024-01-03 09:15:00' },
        { id: 4, name: '赵六', email: 'zhaoliu@example.com', role: 2, joinTime: '2024-01-04 16:45:00' }
      ]
    }
  },
  methods: {
    getRoleType(role) {
      const types = ['success', 'warning', 'info']
      return types[role] || 'info'
    },
    getRoleText(role) {
      const texts = ['拥有者', '管理员', '成员']
      return texts[role] || '成员'
    },
    handleAvatarSuccess(res, file) {
      this.basicForm.icon = URL.createObjectURL(file.raw)
    },
    beforeAvatarUpload(file) {
      const isJPG = file.type === 'image/jpeg' || file.type === 'image/png'
      const isLt2M = file.size / 1024 / 1024 < 2

      if (!isJPG) {
        this.$message.error('头像只能是 JPG/PNG 格式!')
      }
      if (!isLt2M) {
        this.$message.error('头像大小不能超过 2MB!')
      }
      return isJPG && isLt2M
    },
    saveBasicInfo() {
      this.$message.success('保存成功')
    },
    addMember() {
      const newMember = {
        id: this.members.length + 1,
        name: this.addMemberForm.email.split('@')[0],
        email: this.addMemberForm.email,
        role: this.addMemberForm.role,
        joinTime: new Date().toLocaleString()
      }
      this.members.push(newMember)
      this.showAddMemberDialog = false
      this.addMemberForm = { email: '', role: 2 }
      this.$message.success('添加成功')
    },
    editMemberRole(member) {
      this.editRoleForm = { id: member.id, role: member.role }
      this.showEditRoleDialog = true
    },
    saveMemberRole() {
      const member = this.members.find(m => m.id === this.editRoleForm.id)
      if (member) {
        member.role = this.editRoleForm.role
      }
      this.showEditRoleDialog = false
      this.$message.success('保存成功')
    },
    removeMember(member) {
      this.$confirm('确定要移除这个成员吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        const index = this.members.findIndex(m => m.id === member.id)
        if (index !== -1) {
          this.members.splice(index, 1)
          this.$message.success('移除成功')
        }
      })
    },
    archiveProject() {
      this.$confirm('确定要归档这个项目吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.basicForm.status = 0
        this.$message.success('项目已归档')
      })
    },
    deleteProject() {
      this.$confirm('确定要删除这个项目吗？此操作不可恢复！', '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'danger'
      }).then(() => {
        this.$message.success('项目已删除')
        this.$router.push('/projects')
      })
    }
  }
}
</script>

<style scoped>
.page-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.page-header h3 {
  margin: 0;
}

.settings-card {
  padding: 20px;
}

.member-header {
  margin-bottom: 20px;
}

.avatar-uploader {
  width: 120px;
  height: 120px;
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  overflow: hidden;
}

.avatar {
  width: 100%;
  display: block;
}

.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 78px;
  height: 78px;
  line-height: 78px;
  text-align: center;
}

.form-tip {
  color: #909399;
  font-size: 12px;
  margin-top: 5px;
}
</style>
