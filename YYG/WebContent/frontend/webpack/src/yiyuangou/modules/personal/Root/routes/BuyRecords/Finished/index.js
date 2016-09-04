import React from 'react';
import StyleCss from './index.css';
export default class Finished extends React.Component{
  constructor(props){
  	super(props)
  }
  
  render(){
    return(
    <div className ="finished">
      <div className="tab_item" id="199689975">
        <div className="prize_icon_container" >
          <img src="http://i.duobao999.com/goods_1603031055452803x.jpg"/>
        </div>
        <div className="prize_content_container">
          <label className="color_black" id="title">锐澳鸡尾酒（包装随机）</label>
          <div style={{overflow:'hidden', marginTop:'0.2rem'}}>
            <div style={{float:'left',width:'70%',lineHeight:'1.7rem'}}>
              <label>总需：<label id="price">115</label>人次</label><br />
              <label>商品期数：<label id="peroid">2081206207</label></label><br />
              <label>本期参与：<label className="color_red" id="count">1</label>人次</label>
            </div>
            <div style={{float:'right', width:'30%',textAlign:'right', marginTop:'1.2rem'}}>
              <a className="color_blue click_for_nums" >查看号码</a>
            </div>
          </div>
        </div>

        <div style={{backgroundColor:'#f0f0f0', overflow:'hidden', lineHeight:'1.7rem', padding:'0.5rem', marginTop:'0.2rem',fontSize:'10px'}}>
          <label>获得者：<label className="color_black" >给我中个冲气娃娃也好啊！</label></label><br />
          <label>本期参与：<label className="color_red">1</label><label className="color_black">人次</label><br />
          <label>幸运号码：<label className="color_red" >10000060</label></label><br />
          <label>揭晓时间：<label className="color_black" >2016-08-12 17:23:07</label></label>
        </label></div>
      </div>
    </div>
    )
  }
}  
