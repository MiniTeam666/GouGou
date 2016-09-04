import React from 'react';
import Event from 'company/util/lib/Event';
import { Router, Route,IndexRoute } from 'react-router';
import Home from '../../home/Root/Home';
import Products from '../../products/Root/Root';
import Show from '../../show/Root/Posts';
import Shopping_cart from '../../shopping_cart/Root/Root';
import Personal from '../../personal/Root/User/';
import Main from './Main';

export default class Root extends React.Component{
  constructor(props){
    super(props);
    this.state={
      route:'/home',
    }
  }

  render(){
    
    return(
      <Router>
        <Route path="/" component={Main}>
          <IndexRoute path="home" component={Home} />
          <Route path="home" component={Home} />
          <Route path="products" component={Products} />
          <Route path="show" component={Show} />
          <Route path="shopping_cart" component={Shopping_cart} />
          <Route path="personal" component={Personal} />
        </Route>
      </Router>
    )
  }
}  
 