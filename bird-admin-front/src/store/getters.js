const getters = {
  token: state => state.user.token,
  icon: state => state.user.icon,
  name: state => state.user.name,
  status: state => state.user.status,
  roles: state => state.user.roles,
  perms: state => state.user.perms,
  permission_routers: state => state.permission.routers,
  addRouters: state => state.permission.addRouters
}
export default getters
