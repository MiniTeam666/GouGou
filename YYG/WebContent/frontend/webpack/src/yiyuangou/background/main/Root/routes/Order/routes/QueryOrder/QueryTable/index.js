import React from 'react';
import RaisedButton from 'material-ui/RaisedButton';
import ActionSearch from 'material-ui/svg-icons/action/search';
import Divider from 'material-ui/Divider';
import Table from './Table';
import QueryBox from './QueryBox';
import Event from 'company/util/lib/Event';
const style = {
  marginLeft: 4,
  marginTop:8,
  // fontSize:18
};
const labelStyle={
	paddingLeft:0,
}
export default class QueryTable extends React.Component{
	constructor(props){
		super(props);
	}

	showQueryModal(){
		let event = new Event;
		event.dispatch('Root/routes/QueryOrder/QueryTable/QueryBox/setDisplay',true);
	}
	render(){
		return(
			<div>
				<RaisedButton onTouchTap={this.showQueryModal} label="查询" labelStyle={labelStyle} primary={true} style={style}  icon={<ActionSearch color={'#FFF'} />}/>
				<RaisedButton label="查询" labelStyle={labelStyle} primary={true} style={style}  icon={<ActionSearch color={'#FFF'} />}/>
				<RaisedButton label="查询" labelStyle={labelStyle}primary={true} style={style}  icon={<ActionSearch color={'#FFF'} />}/>
				<RaisedButton label="查询" labelStyle={labelStyle} primary={true} style={style}  icon={<ActionSearch color={'#FFF'} />}/>

				<Divider style={{marginTop:10,marginBottom:10}}/>
				<Table/>
				<QueryBox />
			</div>
		)
	}

}