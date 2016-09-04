import React from 'react';
import Input from './Input';
import DateTimePicker from './DateTimePicker' ;
import MultiSelect from './MultiSelect';
import './bootstrap/index.css';
import './bootstrap';
import './index.css';
import Operation from './Operation';

export default class QueryTr extends React.Component{
	render(){
		var {column,conditions}=this.props;
		var {col,type,name}=column;
		var ele ="";
		if(type==="string"){
			ele = (<Input condition={conditions[col]}/>);
		}else if(type==="date"){
			ele =(<DateTimePicker condition={conditions[col]} />);
		}else if(type==="list"){
			ele =(<MultiSelect condition={conditions[col]} />)
		}
		return(
			<tr>
				<td><input ref="checkBox" type="checkbox" onChange={this.onChangeCheckBox}  /></td>
				<td name={col}>{name}</td>
				<td>
					<Operation />
				</td>
				<td>
					{ele}
				</td>
				<td>
					{ele}
				</td>
			</tr>
		);
	}//
	componentDidMount(){
		var {column,conditions}=this.props;
		var {col,type,name}=column;
		var check = conditions[col].check;
		this.refs.checkBox.checked=check;
	}

	onChangeCheckBox(event){
		var {column,conditions}=this.props;
		var {col,type,name}=column;
		this.props.conditions[col].check=event.target.checked;
	}

};