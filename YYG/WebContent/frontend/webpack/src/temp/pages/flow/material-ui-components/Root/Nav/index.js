import React from 'react';
import LeftNav from 'material-ui/lib/left-nav';
import AppBar from 'material-ui/lib/app-bar';
import RaisedButton from 'material-ui/lib/raised-button';
import MenuItem from 'material-ui/lib/menus/menu-item';
import ListIcon from "../ListIcon";

export default class LeftNavOpenRightExample extends React.Component {

  constructor(props) {
    super(props);
    this.state = {open: false};
    this.handleToggle = this.handleToggle.bind(this);
  }

  handleToggle(){
   this.setState({open: !this.state.open});
 }
   handleClose(event){
    event.stopPropagation();
   }
  render() {
    return (
      <div onClick= {this.handleToggle}>
        <RaisedButton
          style={{marginLeft:100}}
          label="Toggle LeftNav"
          onClick={this.handleToggle} />
        <LeftNav onClick={this.handleClose} 
          docked={false}
          width={300}
          open={this.state.open}
          onRequestChange={(open) => this.setState({open})} >
          <AppBar title="AppBar" />
           <ListIcon />
        </LeftNav>
      </div>
    );
  }
}