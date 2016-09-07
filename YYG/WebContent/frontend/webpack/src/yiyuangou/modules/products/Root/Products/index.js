import React from 'react';
import ReactDOM from 'react-dom';
import Product from 'company/yiyuangou/lib/Product';
import {REQUEST_PRODUCTS_PATH} from 'company/yiyuangou/api/frontend';
import Fetch from 'company/util/lib/Fetch';
import autobind from 'autobind-decorator';
var dataCache=[];
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
		this.scrollEnd();
	}

	async getData(page=0){
		const obj = {
			category:-1,
			type:1,
			direction:1,
			page:page,
		};
		const url = REQUEST_PRODUCTS_PATH;
		const fetch = new Fetch();
		let json = await fetch.get(url,REQUEST_PRODUCTS_PATH,obj);
		dataCache = dataCache.concat(json.data);
		this.setProducts(dataCache);
	}
	setProducts(products){
		this.setState({
			products
		});
	}

	scrollEnd(){
		console.log(134346);
		var throttle = function(fn, delay){
			
		 	var timer = null;
		 	return function(){
		 		var context = this, args = arguments;
		 		clearTimeout(timer);
		 		timer = setTimeout(function(){

		 			fn.apply(context, args);
		 		}, delay);
		 	};
		};
		let con = ReactDOM.findDOMNode(this);
		let content = this;
		content.loadingDataFlag = false;
		function getData(){
			var conHeight = con.scrollHeight-con.clientHeight;
			var conScrollTop = con.scrollTop;
			if(conScrollTop/conHeight>0.9 && !content.loadingDataFlag){
				content.getData(1);
				setTimeout(function(){
					content.loadingDataFlag=false;
				},3000);
				content.loadingDataFlag=true;
			}
		} 
		con.onscroll=throttle(getData,100);
	}

	render(){
		var productList = this.state.products.map(function(product){
			return(
				<Product product={product} />
			)
		});
		return(
			<div style={{marginBottom:0,position:'relative',height:600,overflow:'scroll'}}>
				{productList}
			</div>
		)
	}
}