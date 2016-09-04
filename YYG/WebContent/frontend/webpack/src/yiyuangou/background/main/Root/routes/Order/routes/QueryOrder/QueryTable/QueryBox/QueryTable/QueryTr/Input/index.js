import React from 'react';
import './index.css';
export default class Input extends React.Component{
	constructor(props) {
		super(props);
		var value  = props.condition.value[0];
    	this.state={
    		value
    	}
	}
	render(){
		return(
			<span>
				<input type="text" className="fix-input input-shadow" value={this.state.value} onChange={this.onChangeCondition}/> 
				<span className="glyphicon glyphicon-edit" aria-hidden="true"></span>
			</span>
		);	
	}

	onChangeCondition(event){
		this.setState({value: event.target.value});
		this.props.condition.value[0]=event.target.value;
	}
	 
}