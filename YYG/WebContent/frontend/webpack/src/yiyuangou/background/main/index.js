import React from 'react';
import { render } from 'react-dom';
import { Router,hashHistory } from 'react-router';
import injectTapEventPlugin from 'react-tap-event-plugin';
injectTapEventPlugin();
// import 'jquery/dist/jquery.js';
import 'company/yiyuangou/util/Route';
import routes from './Root/indexRoute';

function change(routes){

	if(routes.childs  instanceof Array){
		let array = [];
		for(let i=0;i<routes.childs.length;i++){
			array=array.concat(change(routes.childs[i]));
		}
		return array;
	}
  if(routes.disable){
    return [];
  }else{
    return routes;
  }
	
}
const  rootRoute={
  childRoutes: [ {
    path: '/',
    component: require('./Root'),
    childRoutes: change(routes),
  } ]
}
render((
  <Router
  history={hashHistory}
        routes={rootRoute}
      />),
  document.getElementById('root')
)