import React from 'react';
import { render } from 'react-dom';
import injectTapEventPlugin from 'react-tap-event-plugin';
injectTapEventPlugin();
// import Route from './Root/Route';
// import { createHistory } from 'history';

import { Router,hashHistory } from 'react-router';

import withExampleBasename from './withExampleBasename'
import  'company/yiyuangou/util/Route';
import MyRouter from 'company/util/lib/Router';
window.Router = MyRouter;
const rootRoute = {
  childRoutes: [ {
    path: '/',
    component: require('./Root/Main'),
    childRoutes: [
      require('../home/Root/RouteHome'),
      require('../products/Root/RouteProducts'),
      require('../show/Root/RouteShow'),
      require('../shopping_cart/Root/RouteShopping_cart'),
      require('../personal/Root/RouteUser'),
    ]
  } ]
}

render((
  <Router
  history={hashHistory}
        routes={rootRoute}
      />),
  document.getElementById('root')
)