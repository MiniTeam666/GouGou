import React,{Component} from 'react';
import {Navbar,Nav,NavDropdown,primary,MenuItem} from 'react-bootstrap'; 
import styles from './index.css';
import NavItem from '../NavItem';
export default class Mynav extends Component{
 constructor(props) {
    super(props);
  }
  
  render(){
    var navs =[
      {href:"#/home",name:"首页"},
      {href:"#/address_list",name:"通讯录"},
      {href:"#/secure_center",name:"安全中心"},
      {href:"#/setting",name:"设置"},
      {href:"#/exit",name:"退出"},
    ];
    var selectedNav = this.props.selectedNav;
    var navList = navs.map(function(nav){
        return(
          <NavItem {...nav} active={nav.href==selectedNav} />
        );
    })
    return (
      <Navbar primary className={styles.nav}>
        <Navbar.Header>
          <Navbar.Brand>
            <a href="#">
             <span className="glyphicon glyphicon-list-alt" aria-hidden="true"></span> 广州医药有限公司
            </a>
          </Navbar.Brand>
          <Navbar.Toggle />
        </Navbar.Header>
        <Navbar.Collapse>
          <Nav pullRight>
            {navList}
          </Nav>
        </Navbar.Collapse>
      </Navbar>
    );
  }
  componentDidMount(){
    location.href = this.props.selectedNav;
  }
  componentDidUpdate( prevProps,  prevState){
    location.href = this.props.selectedNav;
  }
}  
