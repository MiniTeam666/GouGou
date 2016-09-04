import React from 'react';
import WBar from './WBar';
export default class WBars extends React.Component{
  render(){
    return(
      <div className='WBars' style={{marginBottom:10,marginTop:10,borderTop: '1px solid #d5d5d5'
      }}>
      	<WBar wbar={{name:'我的红包'}}/>
        <WBar wbar={{name:'夺宝记录'}}/>
        <WBar wbar={{name:'幸运记录'}}/>
      </div>
    )
  }
}  