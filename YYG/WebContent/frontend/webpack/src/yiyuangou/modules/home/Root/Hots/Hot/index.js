import React from 'react';
import StyleCss from './index.css';

export default class Hot extends React.Component{
    constructor(props){
        super(props);

    }
     handleClick(evnt){
        event.stopPropagation();
        location.href='#/products/detail/2'
    }
    
    
    render(){
        return (
            <li className="w-goodsList-item" onTouchTap={this.handleClick}>
                <img className="ico ico-label ico-label-goods" src="http://mimg.127.net/p/yymobile/lib/img/common/icon/icon_tens_goods.png" />
                <div className="w-goods w-goods-ing" data-gid="898" data-period="308050659" data-price="7280" data-priceunit="10" data-buyunit="1">
                    <div className="w-goods-pic">
                        <a >
                            <img alt="Apple iPhone6s Plus 64g 颜色随机" src="http://onegoods.nosdn.127.net/goods/898/d3dc4b84825a35c50e2b5504d2b636cc.png" onerror="this.src='http://mimg.127.net/p/yymobile/lib/img/products/square/l.png'" className="" />
                        </a>
                    </div>
                    <div className="w-goods-info">
                        <p className="w-goods-title f-txtabb"><a >Apple iPhone6s Plus 64g 颜色随机</a></p>
                        <div className="w-progressBar">
                            <p className="txt">进度<strong>41%</strong></p>
                            <p className="wrap">
                                <span className="bar" style={{width:'40.8%'}}><i className="color"></i></span>
                            </p>
                        </div>
                    </div>
                    <div className="w-goods-shortFunc">
                        <button className="w-button w-button-round w-button-addToCart"></button>
                    </div>
                </div>
            </li>
        );
    }
}