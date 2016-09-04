import React from 'react';
import {Tabs,Tab} from 'material-ui/Tabs';
// import Tab from 'company/yiyuangou/lib/Tab';
import Event from 'company/util/lib/Event';

const height = 40;
const TabStyle={
  height:height,
}
export default class SecondTabs extends React.Component{
	constructor(props){
		super(props);
		this.state={
			index:1,
		}
		this.changeTabs = this.changeTabs.bind(this);
	}
	changeTabs(index){
		
		this.setState({
			index:index,
		})
	}
	componentDidMount(){
		let event = new Event;
		window.event1 = event;
		event.subscribe('products_Root_SecondTabs_changeTabs',this.changeTabs);
	}

	render(){
		var tabs =[
			{label:"全部",data:'',callback:this.changeTabs},
			{label:"进行中",data:'',callback:this.changeTabs},
			{label:"已揭晓",data:'',callback:this.changeTabs},
			
		];
		var tabList = tabs.map(function(tab,index){
			var touchTap=function(){
				tab.callback(index);
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
		})

		return(
			<div style={{height:height}}>
				<Tabs  value={this.state.index}　style={{position:'fixed',top:0,width:'100%',zIndex:99}}>
			     {tabList}
	    	    </Tabs>
    	    </div>
		)
	}
}