import React,{Component} from 'react';
import {NavItem} from 'react-bootstrap'; 
import { selectNav,invalidateNav } from '../../store/actions'
import styles from './index.css'
export default class Mynav extends Component{
  constructor(props) {
    super(props);
    this.changeNav= this.changeNav.bind(this);
  }

  changeNav(event){
    var node = this.refs.nav.getDOMNode();
    var value = node.getAttribute('data-value');
    window.dispatch(selectNav(value));
  }

  render(){

    return (
      <NavItem onClick={this.changeNav} ref='nav' href="#" data-value={this.props.href} className={this.props.active?styles.active:''}>
        {this.props.name}
      </NavItem>
    );
  }

 

}  