import React from 'react';

export default class Image extends React.Component{
  constructor(props){
  	super(props)
  }
  
  render(){
    return(
      <p style={{padding:0, margin:0}}>
          <img src="http://goodsimg.1yyg.com/GoodsInfo/20160616142620619.jpg" />
      </p>
    )
  }
}  
