import axios from 'axios';
import request from "@/utils/request";
export default class SiderService{
    getMenu(){
        return request({
            url: '/menu/my',
            method: 'get',
        })
    }
}
