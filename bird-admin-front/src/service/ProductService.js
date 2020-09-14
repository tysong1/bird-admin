import axios from 'axios';

export default class UserService {

    getProductsSmall() {
		return axios.get('assets/layout/data/products-small.json').then(res => res.data.data);
	}

}
