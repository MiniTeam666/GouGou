import React from 'react';
import StyleCss from './index.css';
export default class Products extends React.Component{
  render(){
    return(
      	<li>
              <a className="fl u-Cart-img" href="#/products/detail/2">
                <img src="http://mimg.1yyg.com/GoodsPic/pic-200-200/20160225145319600.jpg" border="0" alt=""/>
              </a>
              <div className="u-Cart-r">
                <a href="#/products/detail/2" className="gray6">
                  (已更新至第38219云)平安银行 招财进宝猴年生肖金条 Au9999 10g
                </a>
                <span className="gray9">
                  <em>剩余3372人次</em>
                </span>
                <div className="num-opt">
                  <em className="num-mius dis">
                    <i></i>
                  </em>
                  <input id="txtNum7705286" name="num" maxlength="6" type="text" value="1" codeid="7705286" />
                  <em className="num-add">
                      <i></i>
                  </em>
                </div>
                <a href="javascript:;" name="delLink" cid="7705286" isover="0" className="z-del">
                  <s></s>
                </a>
              </div>
              
        </li>
    )
  }
}  