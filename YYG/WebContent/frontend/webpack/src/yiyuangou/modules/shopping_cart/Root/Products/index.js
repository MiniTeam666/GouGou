import React from 'react';
import Product from './Product';
import StyleCss from './index.css';
export default class Products extends React.Component{
  render(){
    return(
      <div className="g-Cart-list marginB">
            <ul id="cartBody">
              <Product />
              <Product />
              <Product />
              <Product />
              <Product />
              <Product />
              <Product />
              <Product />
              <Product />
              <Product />
              <Product />
              <Product />
            </ul>
            <div id="divNone" className="empty" style={{display: 'none'}}>
              <s></s>
              购物车为空
            </div>
            
      </div>
    )
  }
}  