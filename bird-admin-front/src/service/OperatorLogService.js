import axios from 'axios';
import request from "@/utils/request";

export default class OperatorLogService {
	list() {
		return request({
			url: '/operatorLog/all',
			method: 'get',
		})
    }

}
