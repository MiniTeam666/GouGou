import React from 'react';
import StyleCss from './index.css';
export default class Calculate extends React.Component{
  render(){
    return(
      <div id="mycartpay" className="g-Total-bt g-car-new" >
            <div className="g-auto-next">
                <div className="auto-next-inner">
                    <i id="i_buynext" className="circle checked"></i>
                    <span>如被抢光自动参与最新一云</span>
                    <a href="javascript:;" className="ques-btn">
                      <i className="ques"></i>
                    </a>
                </div>
            </div>
            <dl>
                <dt className="gray6">
                    <p className="money-total">
                      合计
                      <em className="orange">
                        <span>￥</span>
                        2.00
                      </em>
                    </p>
                    <p className="pro-total">
                      共<em>2</em>个商品
                    </p>
                </dt>
                <dd>
                  <a href="javascript:;" id="a_payment" className="orangeBtn w_account">
                    去结算
                  </a>
                </dd>
            </dl>
        </div>
    )
  }
}  