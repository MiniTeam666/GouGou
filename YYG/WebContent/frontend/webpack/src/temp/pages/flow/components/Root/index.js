import React,{Component} from 'react';
import { Provider } from 'react-redux'
import configureStore from '../../store'
import { Router, Route, Link } from 'react-router'
import App from '../App'

const store = configureStore()
window.store = store;
export default class Root extends Component {
  render() {
    return (
    	<Provider store={store}>
    		<Router>
    			<Route path="/" component={App} />
    		</Router>
    	</Provider>
    )
  }
}