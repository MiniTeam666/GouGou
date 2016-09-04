import React,{Component} from 'react';
import { connect } from 'react-redux'
import { selectNav,invalidateNav } from '../../store/actions'
import { Router, Route, Link } from 'react-router'
import Container from '../Container';
import Secure from '../Secure';
import GridList from '../GridList';
import Avatar from '../Avatar';
import Tab from '../Tab';
import Nav from '../Nav';
class App extends Component {
  constructor(props) {
    super(props);
  }

  componentDidMount() {
    const { dispatch, selectedNav } = this.props;
    window.dispatch =dispatch;
    dispatch(selectNav(selectedNav));
  }

  componentWillReceiveProps(nextProps) {
    if (nextProps.selectedNav !== this.props.selectedNav) {
     	
    }
  }
  dispatch(){

  }
  render() {
    return (
      <div>
      	<Nav selectedNav={this.props.selectedNav}/>
      	<Router>
	        <Route path="home" component={Container} />
  	  		<Route path="address_list" component={GridList} />
          <Route path="exit" component={Tab}/>
          <Route path="setting" component={Avatar}/>
          <Route path="secure_center" component={Secure}/>
	  	</Router>
      </div>
    )
  } 
}
function mapStateToProps(state) {
  const {queryBox,selectedNav} = state;
  return {
  	queryBox,
  	selectedNav
  }
}

export default connect(mapStateToProps)(App)