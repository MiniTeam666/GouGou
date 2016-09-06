import React from 'react';
import StyleCss from './index.css';
export default class Content extends React.Component{
  constructor(props){
  	super(props)
  }
  
  render(){
    let {
      detail,
      stock,
      cnt,
      status,
      describe,
      name,
      value
    } = this.props;
    return(
       <div className="pro_info">
            <h2 className="gray6">
                {/*<span className="purchase-icon">限购</span>*/}
                (第{cnt}云)
                {name}<span>{detail}</span>
            </h2>
           <div className="purchase-txt gray9 clearfix">
                价值：￥{value}{/*<span>限购5人次</span>*/}
            </div>
            <div className="clearfix">
              <div className="g-goods-share fr"><a id="btnShare" href="javascript:;"><p className="z-set"></p>分享</a></div>
                <div className="gRate">
                    <div className="Progress-bar"><p className="u-progress" title={"已完成"+((value-stock)/value)*100+"%"}>
                    <span className="pgbar" style={{width:((value-stock)/value)*100+'%'}}>
                      <span className="pging"></span>
                    </span></p>
                    <ul className="Pro-bar-li">
                      <li className="P-bar01">
                        <em>{value-stock}</em>
                        已参与
                      </li>
                      <li className="P-bar02">
                        <em>{value}</em>总需人次
                      </li>
                      <li className="P-bar03">
                        <em>{stock}</em>剩余
                      </li>
                    </ul>
                </div>
              </div>
            </div>
      </div>
    )
  }
}  
