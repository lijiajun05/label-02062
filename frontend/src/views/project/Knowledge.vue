<template>
  <div class="knowledge-page">
    <div class="page-header">
      <h3>知识库</h3>
      <el-button type="primary" @click="showCreateDialog = true">创建文档</el-button>
    </div>
    
    <el-card class="knowledge-card">
      <div class="search-bar">
        <el-input 
          v-model="searchKeyword" 
          placeholder="搜索文档" 
          style="width: 300px;"
          clearable
          @input="searchKnowledge"
        >
          <template #prefix>
            <el-icon><Search /></el-icon>
          </template>
        </el-input>
        <el-select v-model="selectedCategory" placeholder="选择分类" style="width: 150px; margin-left: 10px;" clearable>
          <el-option label="全部" value=""></el-option>
          <el-option v-for="category in categories" :key="category" :label="category" :value="category"></el-option>
        </el-select>
      </div>
      
      <el-row :gutter="20" style="margin-top: 20px;">
        <el-col :span="6" v-for="doc in filteredKnowledge" :key="doc.id">
          <el-card class="knowledge-item" @click.native="viewKnowledge(doc)">
            <div class="knowledge-header">
              <el-icon size="32" color="#409EFF"><Document /></el-icon>
              <span class="category-tag">{{ doc.category }}</span>
            </div>
            <h4 class="knowledge-title">{{ doc.title }}</h4>
            <p class="knowledge-desc">{{ doc.content.substring(0, 100) }}...</p>
            <div class="knowledge-footer">
              <span class="create-time">{{ doc.createTime }}</span>
              <el-button type="text" size="small" @click.stop="editKnowledge(doc)">编辑</el-button>
              <el-button type="text" size="small" @click.stop="deleteKnowledge(doc)">删除</el-button>
            </div>
          </el-card>
        </el-col>
      </el-row>
    </el-card>

    <!-- 创建/编辑文档对话框 -->
    <el-dialog v-model="showCreateDialog" :title="isEdit ? '编辑文档' : '创建文档'" width="700px">
      <el-form ref="knowledgeForm" :model="knowledgeForm" label-width="80px">
        <el-form-item label="标题" prop="title">
          <el-input v-model="knowledgeForm.title" placeholder="请输入文档标题"></el-input>
        </el-form-item>
        <el-form-item label="分类" prop="category">
          <el-input v-model="knowledgeForm.category" placeholder="请输入分类"></el-input>
        </el-form-item>
        <el-form-item label="内容" prop="content">
          <el-input type="textarea" v-model="knowledgeForm.content" placeholder="请输入文档内容" :rows="10"></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="showCreateDialog = false">取消</el-button>
        <el-button type="primary" @click="saveKnowledge">保存</el-button>
      </template>
    </el-dialog>

    <!-- 查看文档对话框 -->
    <el-dialog v-model="showViewDialog" title="查看文档" width="700px">
      <div class="view-content">
        <h3>{{ currentKnowledge.title }}</h3>
        <div class="view-meta">
          <el-tag size="small">{{ currentKnowledge.category }}</el-tag>
          <span>{{ currentKnowledge.createTime }}</span>
        </div>
        <div class="view-text">{{ currentKnowledge.content }}</div>
      </div>
      <template #footer>
        <el-button @click="showViewDialog = false">关闭</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: 'Knowledge',
  data() {
    return {
      showCreateDialog: false,
      showViewDialog: false,
      isEdit: false,
      searchKeyword: '',
      selectedCategory: '',
      currentKnowledge: {},
      knowledgeForm: {
        id: null,
        title: '',
        category: '',
        content: ''
      },
      categories: ['需求文档', '技术文档', '设计文档', '会议记录'],
      knowledgeList: [
        {
          id: 1,
          title: '项目需求分析文档',
          category: '需求文档',
          content: '本项目旨在开发一个功能完善的电商平台，主要功能包括用户管理、商品管理、订单管理、支付管理等模块。用户可以通过平台浏览商品、添加购物车、下单购买等操作。',
          createTime: '2024-01-10 10:30:00'
        },
        {
          id: 2,
          title: '数据库设计文档',
          category: '技术文档',
          content: '数据库采用MySQL 8.0版本，主要包括用户表、商品表、订单表、支付表等。用户表存储用户的基本信息，商品表存储商品的详细信息。',
          createTime: '2024-01-12 14:20:00'
        },
        {
          id: 3,
          title: 'UI设计规范',
          category: '设计文档',
          content: 'UI设计采用简洁、美观的风格，主色调为蓝色，辅助色为白色和灰色。页面布局采用响应式设计，适配不同屏幕尺寸。',
          createTime: '2024-01-15 09:15:00'
        },
        {
          id: 4,
          title: '项目启动会议记录',
          category: '会议记录',
          content: '2024年1月1日召开项目启动会议，参会人员包括项目经理、开发人员、测试人员等。会议确定了项目的目标、范围、时间安排等。',
          createTime: '2024-01-01 11:00:00'
        }
      ]
    }
  },
  computed: {
    filteredKnowledge() {
      let filtered = this.knowledgeList
      
      if (this.searchKeyword) {
        filtered = filtered.filter(doc => 
          doc.title.includes(this.searchKeyword) || 
          doc.content.includes(this.searchKeyword)
        )
      }
      
      if (this.selectedCategory) {
        filtered = filtered.filter(doc => doc.category === this.selectedCategory)
      }
      
      return filtered
    }
  },
  methods: {
    searchKnowledge() {
      // 搜索逻辑已在computed中实现
    },
    viewKnowledge(doc) {
      this.currentKnowledge = doc
      this.showViewDialog = true
    },
    editKnowledge(doc) {
      this.isEdit = true
      this.knowledgeForm = { ...doc }
      this.showCreateDialog = true
    },
    deleteKnowledge(doc) {
      this.$confirm('确定要删除这个文档吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        const index = this.knowledgeList.findIndex(k => k.id === doc.id)
        if (index !== -1) {
          this.knowledgeList.splice(index, 1)
          this.$message.success('删除成功')
        }
      })
    },
    saveKnowledge() {
      if (this.isEdit) {
        // 编辑模式
        const index = this.knowledgeList.findIndex(k => k.id === this.knowledgeForm.id)
        if (index !== -1) {
          this.knowledgeList[index] = { ...this.knowledgeForm }
        }
      } else {
        // 创建模式
        const newKnowledge = {
          id: this.knowledgeList.length + 1,
          title: this.knowledgeForm.title,
          category: this.knowledgeForm.category,
          content: this.knowledgeForm.content,
          createTime: new Date().toLocaleString()
        }
        this.knowledgeList.push(newKnowledge)
      }
      
      this.showCreateDialog = false
      this.knowledgeForm = { id: null, title: '', category: '', content: '' }
      this.isEdit = false
      this.$message.success('保存成功')
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

.knowledge-card {
  padding: 20px;
}

.search-bar {
  display: flex;
  align-items: center;
}

.knowledge-item {
  margin-bottom: 20px;
  cursor: pointer;
}

.knowledge-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 10px;
}

.category-tag {
  background: #ecf5ff;
  color: #409EFF;
  padding: 2px 8px;
  border-radius: 4px;
  font-size: 12px;
}

.knowledge-title {
  margin: 0 0 10px 0;
  font-size: 16px;
}

.knowledge-desc {
  color: #606266;
  font-size: 14px;
  margin-bottom: 15px;
}

.knowledge-footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding-top: 10px;
  border-top: 1px solid #ebeef5;
}

.create-time {
  color: #909399;
  font-size: 12px;
}

.view-content {
  padding: 20px;
}

.view-content h3 {
  margin: 0 0 15px 0;
}

.view-meta {
  margin-bottom: 20px;
}

.view-meta span {
  margin-left: 10px;
  color: #909399;
  font-size: 12px;
}

.view-text {
  line-height: 1.6;
  color: #303133;
}
</style>
