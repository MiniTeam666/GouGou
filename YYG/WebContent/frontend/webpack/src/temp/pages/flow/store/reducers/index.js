import { combineReducers } from 'redux'
import {
 SELECT_NAV,INVALIDATE_NAV
} from '../actions'

function selectedNav(state = '#/home', action) {
  switch (action.type) {
    case SELECT_NAV:
      return action.nav
    default:
      return state
  }
}
function queryBox(state=[1,2,3],action){
  
  switch(action.type){
    case 'query':state.push(5);
    default:
    return state;
  }
}

const rootReducer = combineReducers({
  selectedNav,
  queryBox
  
})

export default rootReducer