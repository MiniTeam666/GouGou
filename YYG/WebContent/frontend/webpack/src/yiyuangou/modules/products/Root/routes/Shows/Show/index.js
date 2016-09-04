import React from 'react';
export default class Show extends React.Component{
  constructor(props){
  	super(props)
  }
  handleClick(){
    location.href='#/personal/buyrecords';
  }
  
  
  render(){
    return(
          <li postid="285125">
            <p className="fl">
              <img src="http://mimg.1yyg.com/userpost/Small/20160730131101358.jpg" />
            </p>
            <dl>
              <dt>
                <a onTouchTap={this.handleClick} className="blue">
                  天天快乐1295
                </a>
              </dt>
              <dd className="gray6">
                来开奖咯，，，，
              </dd>
              <dd className="gray9">
                我的小米运动手环不见了，想到云购里有，就…
              </dd>
              <dd className="colorbbb">
                07月30日 13:15
              </dd>
            </dl>
          </li>
    )
  }
}  
