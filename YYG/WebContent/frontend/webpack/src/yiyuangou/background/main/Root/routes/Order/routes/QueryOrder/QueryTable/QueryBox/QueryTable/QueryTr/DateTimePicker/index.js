import React from 'react';
import ReactDOM from 'react-dom';

import './index.css';
import './index.plugin.js';
import './index.plugin.css';
export default class DateTimePicker extends React.Component{
	render(){
		// console.log(this.props)
		return(
			<div>
				<div className="input-append date form_datetime">
					<input className="fix-input" size="16" type="text"  value={this.props.condition.value[0]} /> 
					<span className="glyphicon glyphicon-calendar" aria-hidden="true"></span><span className="add-on"><i className="icon-th "></i></span>
				</div>  
				
			</div>  
		);
	}//
	componentDidMount(){
		var react = this;
		console.log(ReactDOM.findDOMNode(this).childNodes[0]);
		$(ReactDOM.findDOMNode(this).childNodes[0]).datetimepicker({
			format : 'yyyy-mm-dd',
			minView:'month',
				autoclose : true,
				todayBtn : true,
				/*startDate : date,*/
				minuteStep : 10
		}).on('changeDate', function(ev){
   			var str = ev.date.format('yyyy-MM-dd');
   			react.props.condition.value[0]=str;
		});
		
	}
}