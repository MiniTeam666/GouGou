import React from 'react';
import Tabs from 'company/yiyuangou/lib/Tabs';
import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider';
import getMuiTheme from 'material-ui/styles/getMuiTheme';
import Home from '../../home/Root/Home';
const muiTheme = getMuiTheme({
 
});
  

export default class Root extends React.Component{
  render(){
    return(
      <MuiThemeProvider muiTheme={muiTheme}>
       <div>
          {this.props.children|| <Home />}
         
       </div>
      </MuiThemeProvider>
    )
  }
}  