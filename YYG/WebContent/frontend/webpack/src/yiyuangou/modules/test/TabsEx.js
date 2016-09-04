import React from 'react';

import {cyan500} from 'material-ui/styles/colors';
import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider';
import getMuiTheme from 'material-ui/styles/getMuiTheme';
import Tabs from 'company/yiyuangou/lib/Tabs';
import DatePicker from 'material-ui/DatePicker';


const muiTheme = getMuiTheme({
  palette: {
    textColor: cyan500,
  },
  appBar: {
    height: 50,
  },
 
});


const TabsExampleIconText = () => (
   <MuiThemeProvider muiTheme={muiTheme}>
   <div>
    <Tabs />
    </div>
   </MuiThemeProvider>
);

export default TabsExampleIconText;