import React from 'react';

import {cyan500} from 'material-ui/styles/colors';
import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider';
import getMuiTheme from 'material-ui/styles/getMuiTheme';
import Root from './Root';
const muiTheme = getMuiTheme({
  palette: {
    textColor: 'blue',
  }
 
});


export default class Network extends React.Component{
  render(){
    return(
      <MuiThemeProvider muiTheme={muiTheme}>
       <div>
        <Root />
        
       </div>
      </MuiThemeProvider>
    )
  }
}  
 