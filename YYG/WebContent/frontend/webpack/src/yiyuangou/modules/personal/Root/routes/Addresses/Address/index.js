import React from 'react';
import StyleCss from './index.css';
import Router from 'company/util/lib/Router';
const styles={
  default:{
    // backgroundColor: '#F7F7F7',
  },
}
export default class Address extends React.Component{
  constructor(props){
  	super(props);
    this.route = this.route.bind(this);
  }
  route(event){
    let url = this.props.url;
    let router = new Router;
    router.route({url:url});
  }
  
  render(){
    return(
      <div className={StyleCss["item"]} style={styles.default} onTouchTap={ this.route}>
          <div className={StyleCss["name"]}>史蒂夫</div>
          <div className={StyleCss["mobile"]}>188*****170</div>
          <div className={StyleCss["detail"]}>
            <span className={StyleCss["m-user-link"]}>[默认]</span>
            北京北京市西城区史蒂夫
          </div>
      </div>
    )
  }
}  
