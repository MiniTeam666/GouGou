import React from 'react';
// import './index.css';
export default class Operation extends React.Component{
	constructor(props) {
		super(props);
		
	}
	render(){
		return(
			<select>
				<option data-dd_id="0">介于</option>
				<option data-dd_id="1">大于</option>
				<option data-dd_id="1">等于</option>
				<option data-dd_id="1">小于</option>
				<option data-dd_id="1">不等于</option>
				<option data-dd_id="1">in</option>
				<option data-dd_id="1">is NULL</option> 
			</select>
		);	
	}

}