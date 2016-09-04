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

    position: 'relative',
    height:150,
    display:'flex',
    flexDirection: 'column',
    alignItems: 'center',
    alignContent:'center',
    justifyContent: 'center',

    margin:'auto',


  },
  slideContainer: {
    height: 130,
  },
  slide: {
    minHeight: 100,
    color: '#fff',
  }
};
@autobind
class Demo7 extends Component {
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

    return (
      <div style={styles.root}>
        <AutoPlaySwipeableViews
          index={index}
          onChangeIndex={this.handleChangeIndex}
          containerStyle={styles.slideContainer}
        >
          <div style={Object.assign({}, styles.slide, styles.slide1)}>
              <MyCard 
                img={{link:'#/products/detail/2',path:'http://res.126.net/p/dbqb/resupload/onlinepath/2016/8/3/0/4bf8483e78b077ee55ea1261dcc81852.jpg'}} 
              />
           
          </div>
          <div style={Object.assign({}, styles.slide, styles.slide1)}>
              <MyCard 
                img={{link:'#/products/detail/2',path:'http://res.126.net/p/dbqb/resupload/onlinepath/2016/7/28/0/69e1275c4460f97f2d4b26d716348892.jpg'}} 
              />
           
          </div>
          <div style={Object.assign({}, styles.slide, styles.slide1)}>
              <MyCard 
                img={{link:'#/products/detail/2',path:'http://res.126.net/p/dbqb/resupload/onlinepath/2016/7/29/0/5fe1f6e633c122aa2f53276df6b1d39b.jpg'}} 
              />
           
          </div>
         
        </AutoPlaySwipeableViews>
        <Pagination
          dots={3}
          index={index}
          onChangeIndex={this.handleChangeIndex}
        />
      </div>
    );
  }
}

export default Demo7;