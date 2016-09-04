import React from 'react';
import Product from 'company/yiyuangou/lib/Product';
export default class Products extends React.Component{
	componentDidMount(){
		console.log(this.props.params);
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