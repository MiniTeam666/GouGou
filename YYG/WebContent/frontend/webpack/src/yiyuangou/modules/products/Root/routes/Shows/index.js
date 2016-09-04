import React from 'react';
import StyleCss from './index.css';
import Show from './Show';
export default class Shows extends React.Component{
  constructor(props){
  	super(props)
  }
  
  render(){
    return(
      <div className="show-list">
		  <div className="g-goods-single">
        <ul id="ul_list">  
          <Show />
          <Show />
          <Show />
          <Show />
        </ul>  
      </div>
      </div>
    )
  }
}  
