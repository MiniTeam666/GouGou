import React from 'react';
import Drawer from 'material-ui/Drawer';
import AppBar from 'material-ui/AppBar';
import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider';
import getMuiTheme from 'material-ui/styles/getMuiTheme';
import List from './List';
import autobind from 'autobind-decorator';

const muiTheme = getMuiTheme({
 
});

@autobind
export default class DrawerOpenRightExample extends React.Component {

  constructor(props) {
    super(props);
    this.state = {open: false};

  }

  handleToggle = () => this.setState({open: !this.state.open});

  
  render() {
    return (
    <MuiThemeProvider muiTheme={muiTheme}>
      <div>
        <AppBar />
        <Drawer width={256} open={true} >
          <AppBar title="AppBar" />
            <List />
        </Drawer>
        <div style={{paddingLeft:256}}>
           <div style={{margin:20}}>
            {this.props.children||'Introduction'}
           </div>
        </div>
      </div>
   </MuiThemeProvider>

    );
  }
}