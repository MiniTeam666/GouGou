import React from 'react';
import User from './User';
import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider';
import getMuiTheme from 'material-ui/styles/getMuiTheme';
const muiTheme = getMuiTheme({
 
});
  

export default class Root extends React.Component{
  render(){
    return(
      <MuiThemeProvider muiTheme={muiTheme}>
       <div>
         <User />
          
       </div>
      </MuiThemeProvider>
    )
  }
}  