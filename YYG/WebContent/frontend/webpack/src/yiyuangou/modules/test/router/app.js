import React from 'react'
import { render } from 'react-dom'
import { Router, browserHistory } from 'react-router'


const rootRoute = {
  childRoutes: [ {
    path: '/',
    component: require('../../main/Root/Main.js'),
    childRoutes: [
      require('../../home/Root/RouteHome'),
      require('../../products/Root/RouteProducts'),
      require('../../show/Root/RouteShow'),
      require('../../shopping_cart/Root/RouteShopping_cart'),
      require('../../personal/Root/RouteUser')
    ]
  } ]
}

render((
  <Router
    routes={rootRoute}
  />
), document.getElementById('root'))


