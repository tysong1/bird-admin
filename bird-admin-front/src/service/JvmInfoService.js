import axios from 'axios';
import request from "@/utils/request";

export default class JvmInfoService {


	getJvm() {
		return request({
			url: '/monitor',
			method: 'get',
		})
    }
}
