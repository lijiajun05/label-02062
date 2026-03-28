<template>
  <div class="prototypes-page">
    <div class="page-header">
      <h3>原型管理</h3>
      <el-button type="primary" @click="showUploadDialog = true">上传原型</el-button>
    </div>
    
    <el-card class="prototypes-card">
      <el-table :data="prototypes" style="width: 100%">
        <el-table-column prop="name" label="原型名称" width="200"></el-table-column>
        <el-table-column prop="description" label="描述"></el-table-column>
        <el-table-column prop="fileSize" label="文件大小" width="120">
          <template #default="scope">
            {{ formatFileSize(scope.row.fileSize) }}
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="上传时间" width="180"></el-table-column>
        <el-table-column label="操作" width="200">
          <template #default="scope">
            <el-button type="primary" size="small" @click="previewPrototype(scope.row)">
              预览
            </el-button>
            <el-button type="danger" size="small" @click="deletePrototype(scope.row)">
              删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <!-- 上传原型对话框 -->
    <el-dialog v-model="showUploadDialog" title="上传原型" width="500px">
      <el-form ref="uploadForm" :model="uploadForm" label-width="80px">
        <el-form-item label="原型名称" prop="name">
          <el-input v-model="uploadForm.name" placeholder="请输入原型名称"></el-input>
        </el-form-item>
        <el-form-item label="描述" prop="description">
          <el-input type="textarea" v-model="uploadForm.description" placeholder="请输入原型描述"></el-input>
        </el-form-item>
        <el-form-item label="文件" prop="file">
          <el-upload
            ref="upload"
            action="#"
            :auto-upload="false"
            :on-change="handleFileChange"
            accept=".html,.zip"
          >
            <el-button type="primary">选择文件</el-button>
            <template #tip>
              <div class="el-upload__tip">支持HTML和ZIP格式，单个文件不超过10MB</div>
            </template>
          </el-upload>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="showUploadDialog = false">取消</el-button>
        <el-button type="primary" @click="uploadPrototype">上传</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: 'Prototypes',
  data() {
    return {
      showUploadDialog: false,
      uploadForm: {
        name: '',
        description: '',
        file: null
      },
      prototypes: [
        {
          id: 1,
          name: '用户端原型',
          description: '电商平台用户端页面原型',
          fileSize: 1024000,
          createTime: '2024-01-15 10:30:00'
        },
        {
          id: 2,
          name: '管理端原型',
          description: '电商平台管理端页面原型',
          fileSize: 512000,
          createTime: '2024-01-16 14:20:00'
        }
      ]
    }
  },
  methods: {
    formatFileSize(size) {
      if (size < 1024) {
        return size + ' B'
      } else if (size < 1024 * 1024) {
        return (size / 1024).toFixed(2) + ' KB'
      } else {
        return (size / (1024 * 1024)).toFixed(2) + ' MB'
      }
    },
    handleFileChange(file) {
      this.uploadForm.file = file.raw
    },
    previewPrototype(prototype) {
      console.log('预览原型:', prototype)
    },
    deletePrototype(prototype) {
      this.$confirm('确定要删除这个原型吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        const index = this.prototypes.findIndex(p => p.id === prototype.id)
        if (index !== -1) {
          this.prototypes.splice(index, 1)
          this.$message.success('删除成功')
        }
      })
    },
    uploadPrototype() {
      // 模拟上传
      const newPrototype = {
        id: this.prototypes.length + 1,
        name: this.uploadForm.name,
        description: this.uploadForm.description,
        fileSize: this.uploadForm.file ? this.uploadForm.file.size : 0,
        createTime: new Date().toLocaleString()
      }
      this.prototypes.push(newPrototype)
      this.showUploadDialog = false
      this.uploadForm = { name: '', description: '', file: null }
      this.$message.success('上传成功')
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

.prototypes-card {
  padding: 20px;
}
</style>
