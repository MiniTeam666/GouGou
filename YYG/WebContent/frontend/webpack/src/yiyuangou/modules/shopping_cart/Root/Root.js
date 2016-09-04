import React from 'react';
import Products from './Products';
import Calculate from './Calculate';
import Tabs from 'company/yiyuangou/lib/Tabs';

  

export default class Root extends React.Component{
  render(){
    return(
       <div className='shopping_cart'>
            {this.props.children||
             ( <div>
                <Products />
                <Calculate />
                <div style={{height:55}}>
                </div>
                <Tabs index={3}/>
               </div>
              )
            }
       </div>
    )
  }
}  