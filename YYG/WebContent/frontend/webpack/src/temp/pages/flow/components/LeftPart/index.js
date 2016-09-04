import React,{Component} from 'react';
import styles from './index.css';
import Button from 'react-bootstrap/lib/Button';
import ButtonToolbar from 'react-bootstrap/lib/ButtonToolbar';
import AddDept from '../Dialog'
import FlatButton from 'material-ui/lib/flat-button';
import FontIcon from 'material-ui/lib/font-icon';
import ActionAndroid from 'material-ui/lib/svg-icons/action/android';
import TimePicker from 'material-ui/lib/time-picker/time-picker';

const dstyles = {
  exampleImageInput: {
    cursor: 'pointer',
    position: 'absolute',
    top: 0,
    bottom: 0,
    right: 0,
    left: 0,
    width: '100%',
    opacity: 0,
  },
};
const FlatButtonExampleSimple = () => (
  <div>
    <FlatButton label="Default" />
    <FlatButton label="Primary" primary={true} />
    <FlatButton label="Secondary" secondary={true} />
    <FlatButton label="Disabled" disabled={true} />
  </div>
);

const FlatButtonExampleComplex = () => (
  <div>
    <FlatButton label="Choose an Image">
      <input type="file" style={dstyles.exampleImageInput} />
    </FlatButton>

    <FlatButton
      label="Label before"
      labelPosition="before"
      primary={true}
      style={dstyles.button}
      icon={<ActionAndroid />}
    />

    <FlatButton
      label="GitHub Link"
      linkButton={true}
      href="https://github.com/callemall/material-ui"
      secondary={true}
      icon={<FontIcon className="muidocs-icon-custom-github" />}
    />

  </div>
);

export default class LeftPart extends Component{
  
  constructor(props) {
    super(props);
  }

  render(){
    return (
        <div className={styles['left-box']} data-value='123' >
          <div className={styles['panel']+" "+styles['panel-default']+" "+styles['panelBumen']}>
            <div className={styles['panel-heading']}>
              <h3 className={styles['panel-title']}>组织架构</h3>
            </div>
            <div className={styles['dis']}>
              <AddDept name={'添加部门'}/>
              <FlatButtonExampleComplex />
              <FlatButtonExampleSimple />
              <TimePicker hintText="Date Picker" />
            </div>
          </div>
        </div>
    );
  }

  componentDidUpdate( prevProps,  prevState){

  }

}  