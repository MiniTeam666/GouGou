import Event from 'company/util/lib/Event';
import React from 'react';
import Fetch from 'company/util/lib/Fetch';
export default class Network extends React.Component{
	constructor(props){
		super(props);
		this.handleClick = this.handleClick.bind(this);
	}
	async handleClick(){
		let fetch = new Fetch(true);
		try{
			let data = await fetch.jsonp(
				'http://wechat.gzmpc.com/kanban_table/index-test.php',
				'test',
				{action:'getTable'},
				true
				
			);
		}catch(e){
			console.log(e);
		}
		
		
	}
	componentDidMount(){
		var event = new Event;
		event.subscribe('test_Network',this.handleClick);
	}
	render(){
		return (
			<div style={{width:100,height:100,background:'blue'}} onClick ={this.handleClick}>
			  click me
			</div>
		);
	}
}