import React from 'react';
import StyleCss from './index.css';
let height = 13;
const Style={
  main:{
      display:'inline-block',
      borderRadius:height+2,
      background:'pink',
      color:'white',
      marginRight:4,
      marginBottom:4,
      padding:2,
  },
  content:{
     marginLeft:6,
     fontSize:height,
     lineHeight:height+'px',
  },
  close:{
    display:'inline-block',
    marginLeft:6,
    width:height,
    height:height,
    borderRadius:height,
    background:'white',
    fontSize:height,
    color:'pink',
    textAlign:'center',
    lineHeight:height+'px',
  },
  
 
}
export default class Tag extends React.Component {

  constructor(props) {
    super(props);
    
  }
  
  render() {
    return (
      <div style={Style.main}>
        <div className={StyleCss.container}>
         <span style={Style.content}>信息</span>
         <span style={Style.close}>
            x
         </span>
         </div>
      </div>
    );
  }
}