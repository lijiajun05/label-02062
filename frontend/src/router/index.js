import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  {
    path: '/',
    name: 'Home',
    component: () => import('../views/Home.vue')
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import('../views/Login.vue')
  },
  {
    path: '/projects',
    name: 'Projects',
    component: () => import('../views/Projects.vue')
  },
  {
    path: '/project/:id',
    name: 'ProjectDetail',
    component: () => import('../views/ProjectDetail.vue'),
    redirect: to => `/project/${to.params.id}/tasks`,
    children: [
      {
        path: 'tasks',
        name: 'ProjectTasks',
        component: () => import('../views/project/Tasks.vue')
      },
      {
        path: 'kanban',
        name: 'ProjectKanban',
        component: () => import('../views/project/Kanban.vue')
      },
      {
        path: 'gantt',
        name: 'ProjectGantt',
        component: () => import('../views/project/Gantt.vue')
      },
      {
        path: 'prototypes',
        name: 'ProjectPrototypes',
        component: () => import('../views/project/Prototypes.vue')
      },
      {
        path: 'knowledge',
        name: 'ProjectKnowledge',
        component: () => import('../views/project/Knowledge.vue')
      },
      {
        path: 'settings',
        name: 'ProjectSettings',
        component: () => import('../views/project/Settings.vue')
      }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
