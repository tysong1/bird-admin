import axios from 'axios';
import request from "@/utils/request";

export default class RoleService {

	getRoles() {
		return request({
			url: '/role/all',
			method: 'get',
		})
    }

	saveRole(data) {
		return request({
			url: '/role/save',
			method: 'post',
			data
		})
	}
	deleteRole(data) {
		return request({
			url: '/role/delete',
			method: 'post',
			data
		})
	}
	deleteRoleBatch(data) {
		return request({
			url: '/role/deleteBatch',
			method: 'post',
			data
		})
	}
}
