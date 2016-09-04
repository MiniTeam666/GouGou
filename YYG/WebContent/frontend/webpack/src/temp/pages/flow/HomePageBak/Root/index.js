import React from 'react';
import Home from './Home';
import MyRawTheme from './MyRawTheme';
import getMuiTheme from 'material-ui/lib/styles/getMuiTheme';
import ThemeDecorator from 'material-ui/lib/styles/theme-decorator';
import autobind from 'autobind-decorator';
@ThemeDecorator(getMuiTheme(MyRawTheme))
export default class TabsExampleSwipeable extends React.Component {

  constructor(props) {
    super(props);
    
  }
  
  render() {
    return (
      <div  style={{width:'100%',height:'100%',background:'#FFF'}}>
        <Home />
      </div>
    );
  }
}
