import React from 'react';
import AppBar from 'material-ui/lib/app-bar';
import IconButton from 'material-ui/lib/icon-button';
import NavigationClose from 'material-ui/lib/svg-icons/navigation/close';
import IconMenu from 'material-ui/lib/menus/icon-menu';
import FlatButton from 'material-ui/lib/flat-button';
import MoreVertIcon from 'material-ui/lib/svg-icons/navigation/more-vert';
import MenuItem from 'material-ui/lib/menus/menu-item';
import Avatar from './Avatar';
import Nav from './Nav';
import Badge from './Badge';
import List from "./List";
import ListIcon from "./ListIcon";
import Switches from "./Switches";
function handleTouchTap() {
  alert('onTouchTap triggered on the title component');
}

const styles = {
  title: {
    cursor: 'pointer',
  },
};


export default class Root extends React.Component {
  render() {
    return (
      <div >
         <AppBar
          title="Title"
          iconClassNameRight="muidocs-icon-navigation-expand-more"
        />
        <div style={{height:'350px',overflow:'scroll'}}>
          <Avatar />
        </div>
        <div>
          <Nav />
        </div>
        <div>
          <Badge />
        </div>
        <List />
        <ListIcon />
      </div>

    )
  }
}