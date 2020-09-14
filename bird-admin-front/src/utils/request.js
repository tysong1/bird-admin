import axios from 'axios'
import { Message, MessageBox } from 'element-ui'
import store from '@/store'
import { getToken } from '@/utils/auth'
import net from "@/api/baseUrl"
const service = axios.create({
    baseURL:net.BASE_URL,
    timeout: 3000
})

service.interceptors.request.use(
    config => {
        // if (store.getters.token) {
        //     config.headers['X-Super-Admin-Token'] = getToken()
        // }
        config.headers['X-Super-Admin-Token'] = getToken()
        return config
    },
    error => {
        console.log(error)
        Promise.reject(error)
    }
)
service.interceptors.response.use(
    response => {
        const res = response.data
        if (res.code === 2) {
            MessageBox.alert('系统未登录，请重新登录', '错误', {
                confirmButtonText: '确定',
                type: 'error'
            }).then(() => {
                store.dispatch('FedLogOut').then(() => {
                    location.reload()
                })
            })
            return Promise.reject('error')
        } else if (res.code === 1) {
            MessageBox.alert('系统内部错误，请联系管理员维护', '错误', {
                confirmButtonText: '确定',
                type: 'error'
            })
            return Promise.reject('error')
        } else if (res.code === 3) {
            MessageBox.alert('没有操作权限，请联系管理员授权', '错误', {
                confirmButtonText: '确定',
                type: 'error'
            })
            return Promise.reject('error')
        } else if (res.code !== 0) {
            return Promise.reject(response)
        } else {
            return response
        }
    }, error => {
        console.log('err' + error)// for debug
        Message({
            message: '系统异常',
            type: 'error',
            duration: 5 * 1000
        })
        return Promise.reject(error)
    })

export default service
