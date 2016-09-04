import React from 'react'
import { Router } from 'react-router'

const rootRoute = {
  childRoutes: [ {
    path: '/',
    component: require('./Main'),
    childRoutes: [
      require('../../home/Root/RouteHome'),
      require('../../products/Root/RouteProducts'),
      require('../../show/Root/RouteShow'),
      require('../../shopping_cart/Root/RouteShopping_cart'),
      require('../../personal/Root/RouteUser')
    ]
  } ]
}
export default class Route extends React.Component{
  render(){
    return (
      <Router
        routes={rootRoute}
      />
      )
  }
}