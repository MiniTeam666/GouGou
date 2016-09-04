import React from 'react';
import Hot from './Hot';
import StyleCss from './index.css';
export default class Hots extends React.Component{
	constructor(props){
		super(props);
        // this.handleClick = this.handleClick.bind(this);
        this.more = this.more.bind(this);
    
	}
    more(event){
        event.stopPropagation();
        location.href='#/products/'

    }

	render(){
		return (
			<div className="m-index-mod m-index-popular" >
                <div className="m-index-mod-hd">
                    <h3>今日热门商品</h3>
                    <a className="m-index-mod-more"  onTouchTap={this.more} >更多</a>
                </div>
                <div className="m-index-mod-bd  m-fixed">
                    <ul className="w-goodsList w-goodsList-s m-index-popular-list">
                          <Hot    />
                           <Hot />
                            <Hot  />
                             <Hot  />
                            
                    </ul>
                    <div className="w-more">
                        <a onTouchTap={this.more}>点击查看更多商品</a>
                    </div>
                </div>
            </div>
		);
	}
}