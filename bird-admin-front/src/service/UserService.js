import axios from 'axios';
import request from "@/utils/request";

export default class UserService {
	getUsers() {
		return request({
			url: '/user/all',
			method: 'get',
		})
    }
	saveUser(data) {
		return request({
			url: '/user/save',
			method: 'post',
			data
		})
	}
	deleteUser(data) {
		return request({
			url: '/user/delete',
			method: 'post',
			data
		})
	}
	deleteUserBatch(data) {
		return request({
			url: '/user/deleteBatch',
			method: 'post',
			data
		})
	}
}
