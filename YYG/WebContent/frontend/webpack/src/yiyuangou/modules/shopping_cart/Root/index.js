import React from 'react';
import Rooter from './Root';

import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider';
import getMuiTheme from 'material-ui/styles/getMuiTheme';
const muiTheme = getMuiTheme({
 
});
  

export default class Root extends React.Component{
  render(){
    return(
      <MuiThemeProvider muiTheme={muiTheme}>
       <div>
         
          <Rooter />
          
       </div>
      </MuiThemeProvider>
    )
  }
}  