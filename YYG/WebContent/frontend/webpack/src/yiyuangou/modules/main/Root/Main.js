import React from 'react';
import Tabs from 'company/yiyuangou/lib/Tabs';
import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider';
import getMuiTheme from 'material-ui/styles/getMuiTheme';
import Home from '../../home/Root/Home';
import Loading from './Loading';
import Route from 'company/yiyuangou/util/Route';

const muiTheme = getMuiTheme({
 
});
  

let  Root = React.createClass({
  render:function(){
    return(
      <MuiThemeProvider muiTheme={muiTheme}>
       <div>
          <div>
            <Loading />
          </div>
          <div>
            {this.props.children|| <Home />}
          </div>
       </div>
      </MuiThemeProvider>
    )
  },
  contextTypes: {
    router: React.PropTypes.func.isRequired
  },
  componentDidMount(){
  	let route = new Route(this.context.router);
  	route.init();

  }
} );
export default Root;