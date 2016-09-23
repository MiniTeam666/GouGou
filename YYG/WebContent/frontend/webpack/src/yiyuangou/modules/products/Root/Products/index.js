import React from 'react';
import ReactDOM from 'react-dom';
import Product from 'company/yiyuangou/lib/Product';
import {REQUEST_PRODUCTS_PATH} from 'company/yiyuangou/api/frontend';
import Fetch from 'company/util/lib/Fetch';
import autobind from 'autobind-decorator';
import Location from 'company/util/lib/Location';
var dataCache=[];
@autobind
export default class Products extends React.Component{
	constructor(props){
		super(props);
		this.state={
			products:[],

		}
		this.page=0;
	}

	componentDidMount(){
		// alert('mount');
		this.getData();
		this.scrollEnd();
	}

	async getData(page=0){
		var type = Location.getItem('type');
		var category = Location.getItem('category');
		var direction = Location.getItem('direction');
		const obj = {
			category:-1,
			type:category=='undefined'?1:category,
			direction:1,
			page:page,
		};
		const url = REQUEST_PRODUCTS_PATH;
		const fetch = new Fetch(false,true);
		let has_more = fetch.has_more(url,obj);
		if(page!=0&&!has_more){
			alert('没有更多了');
			return;
		}
		let json = await fetch.get(url,REQUEST_PRODUCTS_PATH,obj);
		// dataCache = dataCache.concat(json.data);
		// console.log(dataCache);
		this.setProducts(json);
	}
	setProducts(products){
		this.setState({
			products
		});
	}

	scrollEnd(){
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
				content.getData(++content.page);
				setTimeout(function(){
					content.loadingDataFlag=false;
				},3000);
				content.loadingDataFlag=true;
			}
		} 
		con.onscroll=throttle(getData,100);
	}

	render(){
		if(window.__router){
			var that = this;
			this.page=0;
			setTimeout(function(){that.getData()},0);
			window.__router=false;
		}
		// 
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