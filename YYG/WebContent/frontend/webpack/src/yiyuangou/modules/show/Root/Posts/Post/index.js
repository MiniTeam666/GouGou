import React from 'react';
import StyleCss from './index.css';
import Paper from 'material-ui/Paper';

const style = {
   margin:10
};
export default class Post extends React.Component{
  handleClick(){
  }
  render(){
    return(
       <Paper style={style} zDepth={2} className='show'>
        <div className="w-show-shareItem" onClick={this.handleClick} id="pro-view-show-4">
          <p className="title"><a >金币金币如我心意</a></p>
          <p className="author"><a href='#/personal/buyrecords' >夺宝英雄哥</a><span className="time">今天 13:49</span></p>
          <div className="abbr">
              <div className="pic"><a href="#/products/detail/2"><img src="https://onegoods.nosdn.127.net/goods/2375/753bb7b8ab06537a6fb6ea432dac7ea3.jpg" alt="金币金币如我心意" className="" /></a></div>
              <div className="txt"><a >一元夺宝是真正的公平公正公开夺宝平台，中了很多东西，还是那句以娱乐的心态夺宝，切记勿赌博心态要，祝大家中多多宝贝。小金钱</a></div>
          </div>
        </div>
      </Paper>
    )
  }
}  