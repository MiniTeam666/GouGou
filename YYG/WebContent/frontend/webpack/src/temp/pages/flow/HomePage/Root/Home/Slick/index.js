import React from 'react';
import StyleCSS from './index.css';
import CardMedia from 'material-ui/lib/card/card-media';
import CardTitle from 'material-ui/lib/card/card-title';
import Card from 'material-ui/lib/card/card';
import autoPlay from 'react-swipeable-views/lib/autoPlay';
import SwipeableViews from 'react-swipeable-views';

const AutoPlaySwipeableViews = autoPlay(SwipeableViews);

let CardStyle={
  media:{
    height:'100px',
  },
  title:{
    padding:'10px',
  }
}
class MyCard extends React.Component{
   constructor(props) {
    super(props);
    this.redirect = this.redirect.bind(this);
  }
  
  redirect(){
    location.href = this.props.img.link;
  }

  render(){
    var img = this.props.img;
    return(
        <Card onTouchTap={this.redirect} >
        <CardMedia
          style={CardStyle.media}
          overlay={<CardTitle    style={CardStyle.title} subtitle={img.content} />}
        >
             <img src= {img.path}/>

        </CardMedia>
       </Card>
        );
  }
}
export default class Slick extends React.Component {

  constructor(props) {
    super(props);
  }
  
  
  render() {
   
    var imgList = this.props.imgs.map(function(img){
      return (<MyCard img={img}/>)
    });//
    return (
      <AutoPlaySwipeableViews interval={5000}>
       
      {imgList}
        
      </AutoPlaySwipeableViews>
      
    );
  }
}
