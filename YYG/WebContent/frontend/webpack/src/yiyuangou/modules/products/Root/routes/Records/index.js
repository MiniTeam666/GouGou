import React from 'react';
import StyleCss from './index.css';
import Record from './Record';
export default class Records extends React.Component{
  constructor(props){
  	super(props)
  }
  
  render(){
    return(
    <div className="records">
  		<div className="buy_records">
              <ul id="divRecordList">
              	<Record />
                <Record />
                  <Record />
                  <Record />
              </ul>
              <div id="divLoading" className="loading clearfix g-acc-bg" style={{display: 'none'}}>
              	<b></b>
              	正在加载
              </div>
          </div>
        </div>
    )
  }
}  
