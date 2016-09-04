import React from 'react';
import Products from './Products';
import SecondTabs from './SecondTabs';
import Tabs from 'company/yiyuangou/lib/Tabs';

export default class Root extends React.Component{
  render(){
    return(
       <div className='products'>
          {this.props.children||
          	<div>
          	 <SecondTabs />
           <div style={{height:40}}>

           </div>
          
           <Products />
           <div style={{height:55}}>
           </div>
           <Tabs index={1}/></div>
          }
           
       </div>
    )
  }
}  
