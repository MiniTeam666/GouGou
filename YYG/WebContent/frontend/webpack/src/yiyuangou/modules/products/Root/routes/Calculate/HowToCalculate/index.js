import React from 'react';
import StyleCss from './index.css';

export default class Calculate extends React.Component{
  constructor(props){
  	super(props)
  }
  
  render(){
    return(
      <div >
        <div>1、取该商品最后购买时间前网站所有商品的最后100条购买时间记录；</div>
        <div>2、按时、分、秒、毫秒排列取值之和，除以该商品总参与人次后取余数；</div>
        <div>3、余数加上10000001 即为“幸运云购码”；</div>
        <div>4、余数是指整数除法中被除数未被除尽部分， 如7÷3 = 2 ......1，1就是余数。</div>
      </div>
    )
  }
}  
