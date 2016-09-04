import React from 'react';
import Dialog from 'material-ui/lib/dialog';
import FlatButton from 'material-ui/lib/flat-button';
import RaisedButton from 'material-ui/lib/raised-button';
import TimePicker from 'material-ui/lib/time-picker/time-picker';
import TextField from 'material-ui/lib/text-field'

export default class DialogExampleDialogDatePicker extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      open: false,
    };
    this.handleOpen= this.handleOpen.bind(this);
    this.handleClose= this.handleClose.bind(this);
  }

  handleOpen(){
    this.setState({open: true});
  };

  handleClose(){
    this.setState({open: false});
  };

  render() {
    const actions = [
      <FlatButton
        label="Ok"
        primary={true}
        keyboardFocused={true}
        onClick={this.handleClose}/>];

    return (
      <div>
        <RaisedButton label={this.props.name} onClick={this.handleOpen} />
        <Dialog title="Dialog With Date Picker" actions={actions} modal={false} open={this.state.open} onRequestClose={this.handleClose}>
          <TimePicker hintText="Date Picker" />
          <TextField hintText="Password Field" floatingLabelText="Password" type="password"/><br/>
          <TextField hintText="Password Field" floatingLabelText="Password" type="password"/><br/>
        </Dialog>
      </div>
    );
  }
}