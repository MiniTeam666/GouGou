import React from 'react';
import StyleCss from './index.css';
export default class WBar extends React.Component{
  render(){
    return(
    <div>
      <a href="http://m.1.163.com/user/bonus.do?cid=90206390" className="w-bar">
      	  {this.props.wbar.name}
	      <span className="w-bar-ext">
		      <b className="ico-next">
		      </b>
	      </span>
      </a>
     </div>
    )
  }
}  