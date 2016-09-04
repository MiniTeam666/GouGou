import React from 'react';
import Slider from 'react-slick';
import StyleCSS from './index.css';
import CardMedia from 'material-ui/lib/card/card-media';
import CardTitle from 'material-ui/lib/card/card-title';
import Card from 'material-ui/lib/card/card';

let CardStyle={
  media:{
    height:'100px',
  },
  title:{
    padding:'10px',
  }
}
export default class Slick extends React.Component {

  constructor(props) {
    super(props);
    
  }
  
  render() {
    var settings = {
      dots: true,
      infinite: true,
      speed: 500,
      slidesToShow: 1,
      slidesToScroll: 1
    };
    return (
      <Slider {...settings} >
       <Card >
        <CardMedia
          style={CardStyle.media}
          overlay={<CardTitle    style={CardStyle.title} subtitle="广药最新动态" />}
        >
          <img src="http://lorempixel.com/600/337/nature/" />
        </CardMedia>
       </Card>
       <Card>
        <CardMedia
          style={CardStyle.media}
          overlay={<CardTitle style={CardStyle.title}  subtitle="新一届员工培训" />}
        >
          <img src="http://lorempixel.com/600/337/nature/" />
        </CardMedia>
       </Card>
        
      </Slider>
    );
  }
}
