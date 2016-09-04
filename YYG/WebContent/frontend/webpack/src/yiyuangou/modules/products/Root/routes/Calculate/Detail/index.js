import React from 'react';
import StyleCss from '../index.css';


export default class Detail extends React.Component{
  constructor(props){
  	super(props)
  }
  
  render(){
    return(
      <div className="g-formula clearfix">
        <div className="for-con1 z-oval clearfix">
          <em className="orange">10000029</em>
          <i className="colorbbb">最终计算结果</i>
        </div>
        <p></p>
        <div className="for-con2 clearfix">
          <cite>(</cite>
            <span className="z-oval">
              <em className="orange">15413584718</em>
              <i className="colorbbb">时间取值之和</i>
            </span>
          <cite>%</cite>
          <span className="z-oval">
            <em className="orange">70</em>
            <i className="colorbbb">商品总需人次</i>
          </span>
          <cite>)</cite>
          <cite>+</cite>
          <span className="z-oval">
            <em className="orange">10000001</em>
            <i className="colorbbb">固定数值</i>
          </span>
        </div>
        <div className="orange z-and">
          截止该商品最后购买时间【2016-08-18 15:41:41.187】
          <br />网站所有商品的最后100条购买时间取值之和
          <a id="a_showway" onTouchTap={this.props.handleClick} className="orange">
            如何计算?
            <i className="z-set"></i>
          </a>
        </div>
      </div>
    )
  }
}  
