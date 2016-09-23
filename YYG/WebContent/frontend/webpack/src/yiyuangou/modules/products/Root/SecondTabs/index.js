import React from 'react';
import {Tabs,Tab} from 'material-ui/Tabs';
// import Tab from 'company/yiyuangou/lib/Tab';
import Event from 'company/util/lib/Event';
import Popover from 'material-ui/Popover';

import Menu from 'material-ui/Menu';
import MenuItem from 'material-ui/MenuItem';

import autobind from 'autobind-decorator';

import StyleCss from './index.css';

import {REQUEST_PRODUCTS_LIST_PATH} from 'company/yiyuangou/api/frontend';
import Fetch from 'company/util/lib/Fetch';

import Router from 'company/util/lib/Router';

import RaisedButton from 'material-ui/RaisedButton';

const height = 40;
const TabStyle={
  height:height,
}
@autobind
export default class SecondTabs extends React.Component{
	constructor(props){
		super(props);
		this.state={
			index:1,
			open: false,
			list:[
				{
					name:'手机数码',
					id:1
				},
				{
					name:'精品母婴',
					id:2
				},
				{
					name:'手机数码',
					id:3
				},
				{
					name:'精品母婴',
					id:4
				}
			]
		}
		
	}
	changeTabs(index,event){
		this.setState({
			index:index,
		});
		
	}
	

	handleTouchTap = (index,event) => {
		// alert(1);
	    event.preventDefault();
	    this.setState({
	      open: true,
	      anchorEl: event.currentTarget,
	      // index:0,
	    });
	}

	handleRequestClose = () => {
	    this.setState({
	      open: false,
	    });
	 }

	componentDidMount(){
		let event = new Event;
		window.event1 = event;
		event.subscribe('products_Root_SecondTabs_changeTabs',this.changeTabs);
		// this.getData();
	}
	async getData(){
		
		const url = REQUEST_PRODUCTS_LIST_PATH;
		const fetch = new Fetch();
		let json = await fetch.get(url,REQUEST_PRODUCTS_LIST_PATH,{});
		this.setList(json.data.list);
	}

	setList(list){
		this.setState({
			list,
		})
		
	}
	
	getList(){
		// console.log(this.props);
		var list = this.state.list;
		let list1=[];
		let list2=[];
		let list3=[];
		for(let i=0;i<list.length;i++){
			let j = i%3;
			switch(j){
				case 0:list1.push(list[i]);break;
				case 1:list2.push(list[i]);break;
				case 2:list3.push(list[i]);break;
				default:break;
			}

		}
		var __tabs = this;
		var productList = [];
		let _renderMenu=function(list){
			var menuItems = list.map(function(menuItem){
				var touchTap = function(){
					let router = new Router;
					__tabs.handleRequestClose();
					console.log(__tabs.props.query);
					window.__router = true;
					router.route({
						url:'products',
						query:{
							category:menuItem.id,
							type:1,
							direction:1,
						},
						state:{

						}
					});
				};
				return(
					<MenuItem primaryText={menuItem.name} key={menuItem.id} onTouchTap={touchTap}/> 
				)
			})
			return(
				<div className={StyleCss['inner']}>
				    <Menu>
				         {menuItems}
				    </Menu>
				 </div>
			)
		}
		productList.push(_renderMenu(list1));
		productList.push(_renderMenu(list2));
		productList.push(_renderMenu(list3));
		return productList;
	}

	render(){
		var tabs =[
			{label:"商品分类",data:'',callback:this.handleTouchTap},
			{label:"人气",data:'',callback:this.changeTabs},
			{label:"最新",data:'',callback:this.changeTabs},
			{label:"剩余人次",data:'',callback:this.changeTabs},
			{label:"价值",data:'',callback:this.changeTabs},
		];
		var tabList = tabs.map(function(tab,index){
			var touchTap=function(event){
				tab.callback(index,event);
			}
			return(
				 <Tab
			        label={tab.label}
			        style={TabStyle}
			        tabHeight={height}
			        value={index}
			        onClick={touchTap}
			      />
			)
		});//

		var list = this.getList();

		return(
			<div>
			 
				<Tabs  value={this.state.index}　style={{position:'fixed',top:0,width:'100%',zIndex:99}}>
			     {tabList}
	    	    </Tabs>
	    	   
	    	      <Popover
	    	      	  style={{width:'100%'}}
			          open={this.state.open}
			          anchorEl={this.state.anchorEl}
			          anchorOrigin={{horizontal: 'left', vertical: 'bottom'}}
			          targetOrigin={{horizontal: 'left', vertical: 'top'}}
			          onRequestClose={this.handleRequestClose}
			        >
			        <div className={StyleCss['popover']}>
			          {list}
			         </div>
			      </Popover>
	    	</div>
		)
	}
}