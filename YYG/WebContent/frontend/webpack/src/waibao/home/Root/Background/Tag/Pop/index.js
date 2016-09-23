import React from 'react';
import './index.css';  
import Event from 'company/util/lib/Event';
export default class Background extends React.Component{
  render(){
  	var {link,url,detail} = this.props.pop;
  	return(
  		<div id="imageTagPopup" className="imageTagPopup hbs"  >
  			<div className="imageTagPopupContainer"  style={{position:'relative',top:0,left:0,opacity:1}}>
	  			<div id="imageTagPopupType">
	  			</div>
	  			<div id="imageTagPopupTitle">
	  				<a className="imageTagPopupProductImage" href={link}>
	  					<img src={url} width="80" height="55" />
	  				</a>
	  				<div className="titleWrap">
	  					<div className="imageTagPopupProductTitle">
	  						<a href={link}>{detail}</a>
	  					</div>
	  					<div className="imageTagPopupBottom">
	  						<a className="colorLink" href={link} style={{lineHeight:'25px'}}>
	  							詳細
	  							<span className="more-icon"></span>
	  						</a>
	  					</div>
	  				</div>
	  				<div style={{clear:'both'}}></div>
	  			</div>
	  			<div id="imageTagPopupBody"></div>
	  		</div>
	  	</div>

  	)
  }
}