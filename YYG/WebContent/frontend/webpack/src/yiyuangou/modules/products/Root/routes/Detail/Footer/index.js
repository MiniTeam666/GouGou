import React from 'react';
import StyleCss from './index.css';
export default class Detail extends React.Component{
  constructor(props){
  	super(props)
  }
  
  render(){
    return(
      <div className="pro_foot">
          <a   id="a_sc" className="z-set z-foot-fans fl"></a>
          <a  href="#/shopping_cart" id="btnCart"><i className="fr"></i></a>
          <div className="btn">
            <ul>
              <li>
                <a href="#/shopping_cart" className="orangeBtn">立即1元云购</a>
              </li> 
              <li>
                <a  className="blueBtn">加入购物车</a>
              </li>
            </ul>
          </div>
      </div>
    )
  }
}  
