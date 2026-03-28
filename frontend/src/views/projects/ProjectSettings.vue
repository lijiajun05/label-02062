<template>
  <div class="project-settings">
    <el-tabs>
      <el-tab-pane label="基本信息" name="basic">
        <el-card>
          <el-form :model="basicForm" label-width="100px" style="max-width: 600px">
            <el-form-item label="项目名称">
              <el-input v-model="basicForm.name" />
            </el-form-item>
            <el-form-item label="项目描述">
              <el-input v-model="basicForm.description" type="textarea" :rows="4" />
            </el-form-item>
            <el-form-item label="可见性">
              <el-radio-group v-model="basicForm.visibility">
                <el-radio :label="0">
                  <span>私有 - 只有项目成员可以访问</span>
                </el-radio>
                <el-radio :label="1">
                  <span>公开 - 所有人可见</span>
                </el-radio>
              </el-radio-group>
            </el-form-item>
            <el-form-item label="状态">
              <el-radio-group v-model="basicForm.status">
                <el-radio :label="1">进行中</el-radio>
                <el-radio :label="0">已归档</el-radio>
              </el-radio-group>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="saveBasicInfo">保存</el-button>
            </el-form-item>
          </el-form>
        </el-card>
      </el-tab-pane>
      
      <el-tab-pane label="成员管理" name="members">
        <el-card>
          <template #header>
            <div style="display: flex; justify-content: space-between; align-items: center">
              <span>项目成员</span>
              <el-button type="primary" size="small" @click="showAddMemberDialog = true">添加成员</el-button>
            </div>
          </template>
          <el-table :data="members" style="width: 100%">
            <el-table-column prop="realName" label="姓名" width="120" />
            <el-table-column prop="username" label="用户名" width="120" />
            <el-table-column prop="email" label="邮箱" />
            <el-table-column prop="role" label="角色" width="120">
              <template #default="scope">
                <el-tag :type="scope.row.role === 'admin' ? 'danger' : 'success'" size="small">
                  {{ scope.row.role === 'admin' ? '管理员' : '成员' }}
                </el-tag>
              </template>
            </el-table-column>
            <el-table-column label="操作" width="150">
              <template #default="scope">
                <el-button link type="primary" size="small" @click="changeRole(scope.row)">切换角色</el-button>
                <el-button link type="danger" size="small" @click="removeMember(scope.row.id)">移除</el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-card>
      </el-tab-pane>
    </el-tabs>

    <el-dialog v-model="showAddMemberDialog" title="添加成员" width="500px">
      <el-form :model="addMemberForm" label-width="80px">
        <el-form-item label="选择用户">
          <el-select v-model="addMemberForm.userId" placeholder="请选择用户" style="width: 100%">
            <el-option v-for="user in availableUsers" :key="user.id" :label="user.realName" :value="user.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="角色">
          <el-radio-group v-model="addMemberForm.role">
            <el-radio label="member">成员</el-radio>
            <el-radio label="admin">管理员</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="showAddMemberDialog = false">取消</el-button>
        <el-button type="primary" @click="addMember">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'

const route = useRoute()
const showAddMemberDialog = ref(false)

const basicForm = reactive({
  name: '',
  description: '',
  visibility: 0,
  status: 1
})

const members = ref([])
const availableUsers = ref([])

const addMemberForm = reactive({
  userId: null,
  role: 'member'
})

const saveBasicInfo = () => {
  ElMessage.success('保存成功')
}

const changeRole = (member) => {
  member.role = member.role === 'admin' ? 'member' : 'admin'
  ElMessage.success('角色已更新')
}

const removeMember = (id) => {
  ElMessageBox.confirm('确定要移除这个成员吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    members.value = members.value.filter(m => m.id !== id)
    ElMessage.success('已移除')
  }).catch(() => {})
}

const addMember = () => {
  if (!addMemberForm.userId) {
    ElMessage.error('请选择用户')
    return
  }
  const user = availableUsers.value.find(u => u.id === addMemberForm.userId)
  if (user) {
    members.value.push({
      ...user,
      role: addMemberForm.role
    })
    showAddMemberDialog.value = false
    addMemberForm.userId = null
    addMemberForm.role = 'member'
    ElMessage.success('添加成功')
  }
}

onMounted(() => {
  basicForm.name = '电商平台开发'
  basicForm.description = '开发一个完整的电商平台，包含用户、商品、订单、支付等模块'
  basicForm.visibility = 0
  basicForm.status = 1

  members.value = [
    { id: 1, realName: '张三', username: 'zhangsan', email: 'zhangsan@example.com', role: 'admin' },
    { id: 2, realName: '李四', username: 'lisi', email: 'lisi@example.com', role: 'member' },
    { id: 3, realName: '王五', username: 'wangwu', email: 'wangwu@example.com', role: 'member' }
  ]

  availableUsers.value = [
    { id: 4, realName: '赵六', username: 'zhaoliu', email: 'zhaoliu@example.com' },
    { id: 5, realName: '钱七', username: 'qianqi', email: 'qianqi@example.com' }
  ]
})
</script>

<style scoped>
.project-settings {
  padding: 20px;
}
</style>
