import React, {Component} from 'react';
import SwipeableViews from 'react-swipeable-views';
import autoPlay from 'react-swipeable-views/lib/autoPlay';
import Pagination from './pagination/Pagination';
import autobind from 'autobind-decorator';
import MyCard from './MyCard';
import StyleCss from './index.css';
const AutoPlaySwipeableViews = autoPlay(SwipeableViews);

const styles = {
  root: {
    marginTop:10,
    position: 'relative',
    height:250,
    display:'flex',
    flexDirection: 'column',
    alignItems: 'center',
    alignContent:'center',
    justifyContent: 'center',
    width:260,
    margin:'auto',
  },
  slideContainer: {
    height: 250,
  },
  slide: {
    minHeight: 100,
    color: '#fff',
  }
};
@autobind
class MyAutoPlaySwipeableViews extends Component {
  constructor(props){
    super(props);
    this.state = {
      index: 0,
    };
  }
  

  handleChangeIndex(index){
    this.setState({
      index: index,
    });
  }

  render() {
    const {
      index,
    } = this.state;
    let imgList = this.props.imgs.map(function(img,index){
      return(
         <div style={Object.assign({}, styles.slide, styles.slide1)}>
              <MyCard 
                img={{path:img.img}} 
                key={index}
              />
          </div>
        )
    });

   
    return (
      <div style={styles.root}>
        <AutoPlaySwipeableViews
          index={index}
          
          onChangeIndex={this.handleChangeIndex}
          containerStyle={styles.slideContainer}
        >
          {imgList}
         
        </AutoPlaySwipeableViews>
        <Pagination
          dots={this.props.imgs.length}
          index={index}
          onChangeIndex={this.handleChangeIndex}
        />
      </div>
    );
  }
}

export default MyAutoPlaySwipeableViews;