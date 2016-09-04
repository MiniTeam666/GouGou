import React from 'react';
import StyleCss from './index.css';
import Image from './Image';

export default class Describe extends React.Component{
  constructor(props){
  	super(props)
  }
  
  render(){
    return(
      <div className='transform'>
		  <div style={{width:'100%',margin:'0px auto', padding:0, textAlign:'center'}}>
        <Image />
        <Image />
        <Image />
      </div>
      </div>
    )
  }
}  
