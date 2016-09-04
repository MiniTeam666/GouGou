import React from 'react';
import Home from './Home';
import MyRawTheme from './MyRawTheme';
import getMuiTheme from 'material-ui/lib/styles/getMuiTheme';
import ThemeDecorator from 'material-ui/lib/styles/theme-decorator';
import autobind from 'autobind-decorator';
import ReactIScroll from 'react-iscroll';
import iScroll from 'iscroll';
import DownRefresh from 'test/lib/DownRefresh';
@ThemeDecorator(getMuiTheme(MyRawTheme))
@autobind
export default class TabsExampleSwipeable extends React.Component {

  constructor(props) {
    super(props);
    this.state={
      y:0
    };
  }
  getDefaultProps() {
    return ({
      options: {
        mouseWheel: true,
        scrollbars: true
      }
    })
  }
  onScrollStart(iScrollInstance) {
    
    
    var yScroll = iScrollInstance.y;
    var directionY = iScrollInstance.directionY;
    if( yScroll==0 && directionY==-1 ){
      window.setTopState(0);
      window.fecthMore();
    }
  }
  onScroll(){
    console.log("onScroll");
  }
 
  
  render() {
    return (
      <div  style={{width:'100%',height:'100%',background:'#FFF'}}>
         <ReactIScroll iScroll={iScroll}
              onScrollStart={this.onScrollStart}
             >
             <div>
                <DownRefresh />
                <Home />
                <div style={{height:500}}>
                </div>
                
              </div>
             
        </ReactIScroll>
        
      </div>
    );
  }
}
