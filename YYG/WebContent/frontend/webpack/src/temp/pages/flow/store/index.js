import { createStore, applyMiddleware } from 'redux'
import thunkMiddleware from 'redux-thunk'
import createLogger from 'redux-logger'
import rootReducer from './reducers/'

const loggerMiddleware = createLogger()
const myMiddleWare1=function(_ref) {
  var dispatch = _ref.dispatch;
  var getState = _ref.getState;
  return function (next) {
  	 console.log('myMiddleWare1next');
  	 console.log(next);
    return function (action) {
     console.log('myMiddleWare1action');
     console.log(action);
      return typeof action === 'function' ? action(dispatch, getState) : next(action);
    };
  };
}

const myMiddleWare2=function(_ref) {
  var dispatch = _ref.dispatch;
  var getState = _ref.getState;
  return function (next) {
    return function (action) {
    	 var promise = new Promise(function(resolve,reject){
    	 	 window.setTimeout(function () {
                // 我们满足（fullfil）了这个promise!
               	action.getName = 123;
               	console.log(action);
                resolve(action);
            }, 1000);
    	 });
    	  return promise.then(function(action){
    	  		return typeof action === 'function' ? action(dispatch, getState) : next(action);
    	  }).catch(function(err){
    	  	console.log(err);
    	  });
      
    };
  };
}
// ,
//   loggerMiddleware,
//    myMiddleWare1,
//    myMiddleWare2
const createStoreWithMiddleware = applyMiddleware(
  thunkMiddleware
)(createStore)

export default function configureStore(initialState) {
  return createStoreWithMiddleware(rootReducer, initialState)
}