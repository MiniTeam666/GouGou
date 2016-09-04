import React from 'react';
import StyleCss from './index.css';
export default class Content extends React.Component{
  constructor(props){
  	super(props)
  }
  
  render(){
    return(
       <div className="pro_info">
            <h2 className="gray6">
                <span className="purchase-icon">限购</span>
                (第13云)
                平安银行 平安金福金条 Au9999 100g<span>以“平安”组成“福”字，寓意平安有福，新颖别致，妙思无穷！</span>
            </h2>
           <div className="purchase-txt gray9 clearfix">
                价值：￥33888.00<span>限购5人次</span>
            </div>
            <div className="clearfix">
              <div className="g-goods-share fr"><a id="btnShare" href="javascript:;"><p className="z-set"></p>分享</a></div>
                <div className="gRate">
                    <div className="Progress-bar"><p className="u-progress" title="已完成92%">
                    <span className="pgbar" style={{width:'92%'}}>
                      <span className="pging"></span>
                    </span></p>
                    <ul className="Pro-bar-li">
                      <li className="P-bar01">
                        <em>31192</em>
                        已参与
                      </li>
                      <li className="P-bar02">
                        <em>33888</em>总需人次
                      </li>
                      <li className="P-bar03">
                        <em>2696</em>剩余
                      </li>
                    </ul>
                </div>
              </div>
            </div>
      </div>
    )
  }
}  
