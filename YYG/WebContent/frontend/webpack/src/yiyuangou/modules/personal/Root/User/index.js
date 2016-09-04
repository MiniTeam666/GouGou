import React from 'react';
import Summary from './Summary';
import WBars from 'company/yiyuangou/lib/WBars';
import WBarCenter from 'company/yiyuangou/lib/WBarCenter';
import Tabs from 'company/yiyuangou/lib/Tabs';

export default class User extends React.Component{
  render(){
    return(
      <div className='user'>
       { this.props.children||(<div><Summary />
        <WBars 
           wbars={[
              {name:'夺宝记录',url:'#/personal/buyrecords'},
              {name:'幸运记录',url:'#/personal/buyrecords'},
              {name:'我的晒单',url:'#/personal/buyrecords'},
            ]}
        />
      
        <WBarCenter name={'退出登录'} url={'/show'}/>
        <div style={{height:55}}>
        </div>
        <Tabs index={4}/></div>)}
      </div>
    )
  }
}  