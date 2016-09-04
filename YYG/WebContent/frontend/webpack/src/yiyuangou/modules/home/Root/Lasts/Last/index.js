import React from 'react';
import StyleCss from './index.css';
import Product from './Product';

export default class Last extends React.Component{
	constructor(props){
		super(props);
        this.handleClick = this.handleClick.bind(this);
	    this.more = this.more.bind(this);
    }
    handleClick(evnt){
        event.stopPropagation();
        location.href='#/products/detail/2'
    }
	more(event){
        event.stopPropagation();
        location.href='#/products/'

    }
	render(){
		return (
			<div className="m-home-index-mod m-home-index-newArrivals" onTouchTap={this.handleClick}>
                <div className="m-home-index-mod-hd">
                    <h3>上架新品</h3>
                    <a className="m-home-index-mod-more" onTouchTap={this.more}>更多</a>
                </div>
                <div >
                    <ul className="w-home-goodsList w-home-goodsList-brief">
                        <Product />
                        <Product />
                        <Product />   
                    </ul>
                </div>
            </div>
			
		);
	}
}