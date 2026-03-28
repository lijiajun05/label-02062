import axios from 'axios'

const api = axios.create({
  baseURL: '/api',
  timeout: 10000,
  headers: {
    'Content-Type': 'application/json'
  }
})

// 请求拦截器
api.interceptors.request.use(
  config => {
    // 可以在这里添加token等认证信息
    return config
  },
  error => {
    return Promise.reject(error)
  }
)

// 响应拦截器
api.interceptors.response.use(
  response => {
    return response.data
  },
  error => {
    console.error('API Error:', error)
    return Promise.reject(error)
  }
)

// 项目相关API
export const projectApi = {
  // 获取项目列表
  getProjects: () => api.get('/projects'),
  // 创建项目
  createProject: (data) => api.post('/projects', data),
  // 获取项目详情
  getProject: (id) => api.get(`/projects/${id}`),
  // 更新项目
  updateProject: (id, data) => api.put(`/projects/${id}`, data),
  // 删除项目
  deleteProject: (id) => api.delete(`/projects/${id}`)
}

// 任务相关API
export const taskApi = {
  // 获取项目任务列表
  getTasks: (projectId) => api.get(`/projects/${projectId}/tasks`),
  // 创建任务
  createTask: (projectId, data) => api.post(`/projects/${projectId}/tasks`, data),
  // 更新任务
  updateTask: (id, data) => api.put(`/tasks/${id}`, data),
  // 删除任务
  deleteTask: (id) => api.delete(`/tasks/${id}`)
}

// 看板相关API
export const kanbanApi = {
  // 获取项目看板阶段
  getKanbanStages: (projectId) => api.get(`/projects/${projectId}/kanban`),
  // 创建阶段
  createStage: (projectId, data) => api.post(`/projects/${projectId}/kanban`, data),
  // 更新阶段
  updateStage: (id, data) => api.put(`/kanban/stages/${id}`, data),
  // 删除阶段
  deleteStage: (id) => api.delete(`/kanban/stages/${id}`)
}

// 原型相关API
export const prototypeApi = {
  // 获取项目原型列表
  getPrototypes: (projectId) => api.get(`/projects/${projectId}/prototypes`),
  // 上传原型
  uploadPrototype: (projectId, formData) => api.post(`/projects/${projectId}/prototypes`, formData, {
    headers: { 'Content-Type': 'multipart/form-data' }
  }),
  // 删除原型
  deletePrototype: (id) => api.delete(`/prototypes/${id}`)
}

// 知识库相关API
export const knowledgeApi = {
  // 获取项目知识库列表
  getKnowledgeBases: (projectId) => api.get(`/projects/${projectId}/knowledge`),
  // 创建知识库
  createKnowledgeBase: (projectId, data) => api.post(`/projects/${projectId}/knowledge`, data),
  // 获取知识库详情
  getKnowledgeBase: (id) => api.get(`/knowledge/${id}`),
  // 更新知识库
  updateKnowledgeBase: (id, data) => api.put(`/knowledge/${id}`, data),
  // 删除知识库
  deleteKnowledgeBase: (id) => api.delete(`/knowledge/${id}`)
}

// 甘特图相关API
export const ganttApi = {
  // 获取项目甘特图数据
  getGanttData: (projectId) => api.get(`/projects/${projectId}/gantt`)
}

// 成员相关API
export const memberApi = {
  // 获取项目成员列表
  getMembers: (projectId) => api.get(`/projects/${projectId}/members`),
  // 添加成员
  addMember: (projectId, data) => api.post(`/projects/${projectId}/members`, data),
  // 更新成员角色
  updateMemberRole: (projectId, memberId, data) => api.put(`/projects/${projectId}/members/${memberId}`, data),
  // 移除成员
  removeMember: (projectId, memberId) => api.delete(`/projects/${projectId}/members/${memberId}`)
}

export default api
