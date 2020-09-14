import axios from 'axios';

export default class PermissionService {

	getPermissions() {
		return axios.get('assets/layout/data/permission.json').then(res => res.data.data);
    }

    getProductsWithOrdersSmall() {
		return axios.get('assets/layout/data/products-orders-small.json').then(res => res.data.data);
	}
}
