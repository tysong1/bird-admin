import { loginByUsername, logout, getUserInfo } from '@/api/login'
import { getToken, setToken, removeToken } from '@/utils/auth'
const user={
    state:{
        name: null,
        password: null,
        icon: null,
        token: null,
        roles: [],
        perms: [],
    },
    mutations:{
        SET_NAME: (state,name)=>{
            state.name=name
        },
        SET_ICON:(state,icon)=>{
            state.icon=icon
        },
        SET_TOKEN: (state, token) => {
            state.token = token
        },
        SET_ROLES: (state, roles) => {
            state.roles = roles
        },
        SET_PERMS: (state, perms) => {
            state.perms = perms
        },
    },
    actions:{
        LoginByUsername({commit},userInfo){
            const username = userInfo.username.trim()
            return new Promise((resolve, reject) => {
                loginByUsername(username, userInfo.password).then(response => {
                    const token = response.data.data
                    commit('SET_TOKEN', token)
                    setToken(token)
                    resolve()
                }).catch(error => {
                    reject(error)
                })
            })
        },
        GetUserInfo({ commit, state }) {
            return new Promise((resolve, reject) => {
                getUserInfo(state.token).then(response => {
                    const data = response.data.data
                    if (data.perms && data.perms.length > 0) { // 验证返回的perms是否是一个非空数组
                        commit('SET_PERMS', data.perms)
                    } else {
                        reject('getInfo: perms must be a non-null array !')
                    }

                    commit('SET_ROLES', data.roles)
                    commit('SET_NAME', data.name)
                    commit('SET_ICON', data.icon)
                    resolve(response)
                }).catch(error => {
                    reject(error)
                })
            })
        },
        LogOut({ commit, state }) {
            return new Promise((resolve, reject) => {
                logout(state.token).then(() => {
                    commit('SET_TOKEN', '')
                    commit('SET_ROLES', [])
                    commit('SET_PERMS', [])
                    removeToken()
                    resolve()
                }).catch(error => {
                    reject(error)
                })
            })
        },

        // 前端 登出
        FedLogOut({ commit }) {
            return new Promise(resolve => {
                commit('SET_TOKEN', '')
                removeToken()
                resolve()
            })
        },
    }
}
export default user
