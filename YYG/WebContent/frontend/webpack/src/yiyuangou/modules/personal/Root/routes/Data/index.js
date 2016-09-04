import React from 'react';
import StyleCss from './index.css';
import WBars from 'company/yiyuangou/lib/WBars';
export default class Data extends React.Component{
  constructor(props){
  	super(props)
  }
  
  render(){
    return(
      <div>
        <div className="m-user-info">
          <div className="m-user-bar m-user-bar-l">
              <a className="w-bar m-user-bar-avatar">
                头像
                <span className="w-bar-ext">
                  <span className="m-user-avatar">    
                    <img width="45" height="45" onerror="this.src='http://mimg.127.net/p/yymobile/lib/img/avatar/90.jpeg'" src="http://nos.netease.com/mail-userthumb/740e3f3242c9929af641c16500c34dbb_90.jpeg" />
                  </span>
                </span>
              </a>
              <a href="javascript:void(0);" data-pro="nickname" className="w-bar">
                昵称
                <span className="m-user-link w-bar-ext" style={{whiteSpace: 'nowrap',width: '80%',overflow: 'hidden',textOverflow: 'ellipsis'}}>
                  <span>俊东</span>
                </span>
              </a>
              <a href="javascript:void(0);" data-pro="mobile" className="w-bar">
                手机号码
                <span className="m-user-link w-bar-ext">
                  <span>点击填写</span>
                </span>
              </a>
              <WBars wbars={[{name:'收货地址',url:'#/personal/addresses'}]} />
          </div>
        </div>
      </div>
    )
  }
}  
