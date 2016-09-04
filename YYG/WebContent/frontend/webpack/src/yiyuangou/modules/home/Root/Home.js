import React from 'react';
import AutoPlaySwipeViews from './AutoPlaySwipeViews';
import Lasts from './Lasts';
import Hots from './Hots';
import Tabs from 'company/yiyuangou/lib/Tabs';

export default class Home extends React.Component{
  constructor(props){
    super(props);
  }
  componentDidMount(){

  }

  render(){
    return(
       <div>
          <AutoPlaySwipeViews  />
         
          <Lasts />
          <Hots />
          <div style={{height:55}}>
          </div>
          <Tabs index={0}/>
       </div>
    )
  }
}  
