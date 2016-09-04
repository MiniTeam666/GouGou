import React from 'react';
import New from './New';
export default class NewList extends React.Component {

  constructor(props) {
    super(props);
    
  }
  
  render() {
    var newList = this.props.news.map(function(newObj){
      return(
        <New newObj={newObj}/>
      )
    })
    return (
      <div style={{paddingBottom:20}}>
      	
        {newList}
      </div>
    );
  }
}