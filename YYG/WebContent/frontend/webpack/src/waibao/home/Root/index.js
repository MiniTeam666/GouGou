import React from 'react';
import Background from './Background';
import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider';
import getMuiTheme from 'material-ui/styles/getMuiTheme';
const muiTheme = getMuiTheme({
 
});
  

export default class Root extends React.Component{
  render(){
    return(
      <MuiThemeProvider muiTheme={muiTheme}>
       <div style={{width:'95%'}}>
          <Background />
          
       </div>
      </MuiThemeProvider>
    )
  }
}  