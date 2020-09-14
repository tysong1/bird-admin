import axios from 'axios';
import request from "@/utils/request";

export default class MenuService {
	getMenus() {
		return request({
			url: '/menu/list',
			method: 'get',
		})
    }
	saveMenu(data) {
		return request({
			url: '/menu/save',
			method: 'post',
			data
		})
	}
	deleteMenu(data) {
		return request({
			url: '/menu/delete',
			method: 'post',
			data
		})
	}
	deleteMenuBatch(data) {
		return request({
			url: '/menu/deleteBatch',
			method: 'post',
			data
		})
	}
}
