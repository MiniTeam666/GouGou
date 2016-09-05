import React from 'react';
import Product from 'company/yiyuangou/lib/Product';
import {REQUEST_PRODUCTS_PATH} from 'company/yiyuangou/api/frontend';
import Fetch from 'company/util/lib/Fetch';
import autobind from 'autobind-decorator';
export default class Products extends React.Component{
	componentDidMount(){
		this.getData();
	}
	async getData(){
		const obj = {
			category:-1,
			type:1,
			direction:1,
			page:0,
		}
		const url = REQUEST_PRODUCTS_PATH;
		const fetch = new Fetch();
		let data = await fetch.get(url,REQUEST_PRODUCTS_PATH,obj);
		console.log(data);
	}

	render(){
		
		return(
			<div>
				<Product />
				<Product />
				<Product />
				<Product />
				<Product />
				<Product />
				<Product />
				<Product />
				
			</div>
		)
	}
}