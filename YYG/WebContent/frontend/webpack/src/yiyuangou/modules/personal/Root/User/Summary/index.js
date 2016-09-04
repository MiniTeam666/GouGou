import React from 'react';
import StyleCss from './index.css';
import Router from 'company/util/lib/Router';

export default class Summary extends React.Component{
  route(event){
    let url ='#/personal/data/2';
    let router = new Router;
    router.route({url:url});
  }
  render(){
    return(
      <div className="m-user-summary" >
        <img className="bg" src="http://mimg.127.net/p/yymobile/lib/img/user/summary_bg.png" width="100%" />
        <div className="info">
            <div className="m-user-avatar">    
            	<img width="50" height="50" onerror="this.src='http://mimg.127.net/p/yymobile/lib/img/avatar/90.jpeg'" src="http://nos.netease.com/mail-userthumb/740e3f3242c9929af641c16500c34dbb_90.jpeg"/>
			</div>
            <div className="txt">
                <div className="name">
                	刘明
                </div>
                <div className="money">
                	余额：<span className="m-user-coin">0</span>夺宝币
                	<a href="http://1.163.com/cashier/recharge/info.do" className="w-button w-button-s m-user-summary-btn-normal" >
                		充值
                	</a>
                </div>
            </div>
        </div>
        <a onTouchTap={ this.route} className="aside">
            <b className="ico-next"></b>
        </a>
    </div>
    )
  }
}  