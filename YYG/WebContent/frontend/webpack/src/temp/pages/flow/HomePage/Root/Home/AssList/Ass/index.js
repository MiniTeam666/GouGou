import React from 'react';
import Badge from 'material-ui/lib/badge';
import StyleCSS from './index.css';
import Avatar from 'material-ui/lib/avatar';
export default class Ass extends React.Component{
	
	constructor(props){
		super(props);
		this.state ={
			logo:'',
		}
		this.handleRedirect = this.handleRedirect.bind(this);
	}
	handleRedirect(){
		app.link.launchLinkService(this.props.ass.actionParams);
	}

	render(){
		// alert(this.props.ass.logo);
		return (
		  <div className={StyleCSS.icon} >
		  	<div className={StyleCSS.text}>
			    <Avatar style={{
				    	borderRadius:0,
				    	border:0,
				    	margin:'auto',
				    	marginBottom:10,
				    	width:50,
				    	height:50,
			    	}} 
				    src={this.props.ass.logo} 
				    onTouchTap={this.handleRedirect}
				 />
				 <div style={{fontSize:12,textAlign:'center',color:'#888'}}>
				 	{this.props.ass.name}
				 </div>
			 </div>
		    {/*
		    	<Badge
			      badgeContent={0}
			      primary={true}
			      onTouchTap={this.handleRedirect}
			      >
			       <Avatar style={{borderRadius:0,marginTop:'-12.5px',marginRight:'-12.5px',border:0}} 
			       src={iconUrl} />
			    </Badge>
		    */}
		    
		  </div>
		);
	}
} 

