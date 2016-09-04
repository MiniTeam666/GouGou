import React from 'react';
import SecondTabs from './SecondTabs';
import OnSale from './OnSale';
import Finished from './Finished';
export default class BuyRecords extends React.Component{
  constructor(props){
  	super(props)
  }
  
  render(){

    return(
      <div>
        <SecondTabs />
        <OnSale />
        <OnSale />
        <Finished />
        <Finished />
        
      </div>
    )
  }
}  
