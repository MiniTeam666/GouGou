import React from 'react';
import Product from 'company/yiyuangou/lib/Product';
import {REQUEST_PRODUCTS_PATH} from 'company/yiyuangou/api/frontend';
import Fetch from 'company/util/lib/Fetch';
import autobind from 'autobind-decorator';

@autobind
export default class Products extends React.Component{
	constructor(props){
		super(props);
		this.state={
			products:[],
		}
	}

	componentDidMount(){
		this.getData();
	}

	async getData(){
		const obj = {
			category:-1,
			type:1,
			direction:1,
			page:0,
		};
		const url = REQUEST_PRODUCTS_PATH;
		const fetch = new Fetch();
		let json = await fetch.get(url,REQUEST_PRODUCTS_PATH,obj);
		this.setProducts(json.data);
	}
	setProducts(products){
		this.setState({
			products
		});
	}

	render(){
		var productList = this.state.products.map(function(product){
			return(
				<Product product={product} />
			)
		});
		return(
			<div>
				{productList}
			</div>
		)
	}
}