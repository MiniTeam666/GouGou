import React from 'react';
import WBarCenter from 'company/yiyuangou/lib/WBarCenter';
import Address from './Address';
export default class Addresses extends React.Component{
  constructor(props){
  	super(props)
  }
  
  render(){
    return(
      <div>
        {
          this.props.children
          ||
          <div>
             <Address url={'#/personal/addresses/detail/2'}/>
             <Address url={'#/personal/addresses/detail/2'}/>
             <Address url={'#/personal/addresses/detail/2'}/>
             <WBarCenter name={'+新增'} url={'#/personal/addresses/detail/2'}/>
          </div>
        }
      </div>
    )
  }
}  
