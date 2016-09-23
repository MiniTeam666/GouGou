import React from 'react';
import Event  from 'company/util/lib/Event';
import './index.css';
var Loading = React.createClass({
	render:function(){
		return (
			<div className="loading-modal" style={{zIndex:9999999,display:this.state.loading?'block':'none'}}>
				<span className="icon" ></span>
			</div>
		);
	},
	componentDidMount:function(){
		let event = new Event();
		event.subscribe('main/Root/Loading/setLoading',this.setLoading);
	},
	getInitialState:function(){
		return {loading:false};
	},
	setLoading:function(bool){
		this.setState({
			loading:bool,
		})
	}
});
export default Loading;