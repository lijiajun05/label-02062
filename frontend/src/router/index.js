import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  {
    path: '/login',
    name: 'Login',
    component: () => import('@/views/Login.vue')
  },
  {
    path: '/',
    component: () => import('@/views/Layout.vue'),
    redirect: '/dashboard',
    children: [
      {
        path: 'dashboard',
        name: 'Dashboard',
        component: () => import('@/views/Dashboard.vue')
      },
      {
        path: 'projects',
        name: 'ProjectList',
        component: () => import('@/views/projects/ProjectList.vue')
      },
      {
        path: 'projects/:id',
        name: 'ProjectDetail',
        component: () => import('@/views/projects/ProjectDetail.vue')
      },
      {
        path: 'projects/:id/tasks',
        name: 'TaskManagement',
        component: () => import('@/views/tasks/TaskManagement.vue')
      },
      {
        path: 'projects/:id/kanban',
        name: 'KanbanBoard',
        component: () => import('@/views/tasks/KanbanBoard.vue')
      },
      {
        path: 'projects/:id/gantt',
        name: 'GanttChart',
        component: () => import('@/views/tasks/GanttChart.vue')
      },
      {
        path: 'projects/:id/prototypes',
        name: 'PrototypeManagement',
        component: () => import('@/views/prototypes/PrototypeManagement.vue')
      },
      {
        path: 'projects/:id/knowledge',
        name: 'KnowledgeManagement',
        component: () => import('@/views/knowledge/KnowledgeManagement.vue')
      },
      {
        path: 'projects/:id/settings',
        name: 'ProjectSettings',
        component: () => import('@/views/projects/ProjectSettings.vue')
      }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('token')
  if (to.path !== '/login' && !token) {
    next('/login')
  } else if (to.path === '/login' && token) {
    next('/')
  } else {
    next()
  }
})

export default router
