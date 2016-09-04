import React from 'react';
import Post from './Post';
import Tabs from 'company/yiyuangou/lib/Tabs';

export default class Posts extends React.Component{
  render(){
    return(
      <div>
        <Post />
        <Post />
        <Post />
        <Post />
        <div style={{height:55}}>
        </div>
        <Tabs index={2}/>
      </div>
    )
  }
}  