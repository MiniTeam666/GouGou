import React from 'react';
import './index.css';
import RaisedButton from 'material-ui/RaisedButton';
import ActionSearch from 'material-ui/svg-icons/action/search';
import ActionDeleteForever from 'material-ui/svg-icons/action/delete-forever';
import ActionHighlightOff from 'material-ui/svg-icons/action/highlight-off';
import QueryTable from './QueryTable';
import Event from 'company/util/lib/Event';
import autobind from 'autobind-decorator';
const style = {
  marginLeft: 12,
  marginTop:4,
  // fontSize:18
};
const labelStyle={
	paddingLeft:0,
	// fontSize:15,
}
@autobind
export default class QueryBox extends React.Component{
	constructor(props){
		super(props);
		this.state={
			display:false,
			conditions:{customname:{value:[''],name:"customname",type:"string"},goodsname:{value:[''],name:"goodsname",type:"string"},opcode:{value:[''],type:"string",name:"opcode"},salesdate:{value:['',''],type:"date",check:true,name:"salesdate"}},
		}
	}
	render(){
		return(
		<div style={{display:(this.state.display?"block":"none")}}>
			<div id="query-modal" className=" fix-modal"  >  
	            <div className=" fix-header">  
	                <span>查询界面</span>
	                <button type="button" className="close" onTouchTap={this.close}>×</button>  
	                
	            </div>  
	            <div className=" modal-body fix-body">
	            	<QueryTable conditions={this.state.conditions} />
	            </div> 
	            <div className="modal-footer fix-footer">  
	                 <div className="buttons">
	            	 	<RaisedButton onTouchTap={this.close} label="确定" labelStyle={labelStyle} primary={true} style={style}  icon={<ActionSearch color={'#FFF'} />}/>
						<RaisedButton label="清除" labelStyle={labelStyle} primary={true} style={style}  icon={<ActionDeleteForever color={'#FFF'} />}/>
						<RaisedButton onTouchTap={this.close} label="关闭" labelStyle={labelStyle} primary={true} style={style}  icon={<ActionHighlightOff color={'#FFF'} />}/>
	            	 </div>
	            </div>  
        	</div> 
        	<div className="my-fade">
        	</div> 
        </div>
		);
	}
	close(){
		this.setDisplay(false);
	}
	setDisplay(display){
		this.setState({
			display
		});
	}
	componentDidMount(){
		let event = new Event;
		event.subscribe('Root/routes/QueryOrder/QueryTable/QueryBox/setDisplay',this.setDisplay);
	}
	query(){
		// console.log(this.state.conditions);
		app.controller.resetInstance('any');
		app.controller.setConditions(this.state.conditions);
		app.controller.getInstance().doReload(1);
		app.controller.getInstance().setConditionsCookie();
		app.event.dispatch('selected','');
	}
	clear(){
		var conditions = this.state.conditions;
		for(var prop in conditions){
			var condition = conditions[prop];
			condition.check = false;
		}
		this.setState({conditions});
		app.controller.setConditions(this.state.conditions);
		var checks = ReactDOM.findDOMNode(this).querySelectorAll(':checked');
		for(var i=0,len=checks.length;i<len;i++){
			checks[i].checked=false;
		}

		// console.log(conditions);
	}

};