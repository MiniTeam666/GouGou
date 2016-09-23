import React from 'react';
import ReactDom from 'react-dom';
import RaisedButton from 'material-ui/RaisedButton';
import Popover from 'material-ui/Popover';
import Menu from 'material-ui/Menu';
import MenuItem from 'material-ui/MenuItem';
import Event from 'company/util/lib/Event';
import autobind from'autobind-decorator';
import Pop from './Pop';
import './index.css';
import MapLocalOffer from 'material-ui/svg-icons/maps/local-offer';
const iconStyles = {
  marginRight: 24,
  
};
@autobind
export default class Tag extends React.Component {

  constructor(props) {
    super(props);

    this.state = {
      open: false,
      top:0,
      left:0,
    };
  }

  handleOpen = (event) => {
    // This prevents ghost click.
    event.preventDefault();

    this.setState({
      open: true,
      anchorEl: event.currentTarget,
    });
  };

  handleRequestClose = () => {
    this.setState({
      open: false,
    });
  };
  setPosition(obj){
   
    this.setState({
      top:this.props.tag.top*obj.pixel*obj.width,
      left:this.props.tag.left*obj.width,
    })
  
  }
  componentDidMount(){
    let event = new Event;
    event.subscribe('background/tag/resetPosition',this.setPosition);
  }
  render() {
    return (
      <div style={{position:'absolute',top:this.state.top,left:this.state.left}}>
        <MapLocalOffer className={'imageTagsTag'}
          style={iconStyles} color={'#89c540'} onMouseOver={this.handleOpen} onTouchTap={this.handleOpen} />
        }
        
        <Popover
          open={this.state.open}
          anchorEl={this.state.anchorEl}
          anchorOrigin={{horizontal: 'right', vertical: 'bottom'}}
          targetOrigin={{horizontal: 'left', vertical: 'top'}}
          onRequestClose={this.handleRequestClose}
        >
          <Pop pop={this.props.tag.pop}/>
        </Popover>
      </div>
    );
  }
}