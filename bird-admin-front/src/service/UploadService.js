import axios from 'axios';
import request from "@/utils/request";
export default class UploadService{
    upload(data){
        return request({
            url: '/file/upload',
            method: 'post',
            data
        })
    }
}
