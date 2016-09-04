import React from 'react';
import autobind from 'autobind-decorator';
import {List} from 'material-ui/List';
import ListItem from 'company/widgets/lib/ListItem';
import routes from '../indexRoute.js';
@autobind
export default class MyList extends React.Component{
	constructor(props){
		super(props);
	}
	_renderListItem(routes){

    let nestedItems = [];

    if(routes.childs  instanceof Array){
      for(let i=0;i<routes.childs.length;i++){
        nestedItems.push(this._renderListItem(routes.childs[i]));
      }

    }

    return (
      <ListItem primaryText={routes.name} 
                key={Math.random()}
                disabled={routes.disabled}
                nestedItems={nestedItems}
                routes={routes}
      />
    );
  }

  shouldComponentUpdate(){
		return false;
   }

  render(){
    let that = this
    let list = routes.childs.map(function(route){
      return that._renderListItem(route);
    })
    return(
        <List>
            {list}
        </List>
      )
  }//

}