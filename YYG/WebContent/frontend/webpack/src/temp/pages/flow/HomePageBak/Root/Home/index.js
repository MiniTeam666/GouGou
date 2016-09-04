import React from 'react';
import SwipeableViews from 'react-swipeable-views';
import RaisedButton from 'material-ui/lib/raised-button';
import stylesCSS from './index.css';
import Circle from './Circle';
import AssList from './AssList';
import Slick from './Slick';
import autobind from  'autobind-decorator';
const styles = {
  headline: {
    fontSize: 24,
    paddingTop: 16,
    marginBottom: 12,
    fontWeight: 400,
  },
  slide: {
    padding: 10,

  },
};

@autobind
export default class TabsExampleSwipeable extends React.Component {

  constructor(props) {
    super(props);
    
  }
 
  
  render() {
    return (
    	<div>
        <Circle />
        <div style={{marginTop:'-100px'}}>
          <AssList />
        </div>	
        <div style={{width:'100%',overflow:'hidden'}} onClick={this.change}>
          <Slick  />
        </div>
    	</div>
    );
  }
}