import React from 'react';

import CardMedia from 'material-ui/Card/CardMedia';
import CardTitle from 'material-ui/Card/CardTitle';
import Card from 'material-ui/Card/Card';
let CardStyle={
 
}

export default class MyCard extends React.Component{
   constructor(props) {
    super(props);
    this.redirect = this.redirect.bind(this);
  }
  
  redirect(){
    // location.href = ''
  }

  render(){
    var img = this.props.img;
    return(
        <Card onTouchTap={this.redirect} >
        <CardMedia
          style={CardStyle.media}
        >
             <img src= {img.path}/>

        </CardMedia>
       </Card>
        );
  }
}