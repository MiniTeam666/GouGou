import React from 'react';
// import CssModules from 'react-css-modules';
import StyleCss from '../index.css';

export default class Product extends React.Component{
	constructor(props){
		super(props);
	}
	
	render(){
		return (
			 <li className="w-home-goodsList-item">
				<div className="w-home-goods w-home-goods-brief">
					<div className="w-home-goods-pic">
			            <a title="DIESEL 迪赛 红色偏光个性精钢石英男表">
						    <img alt="DIESEL 迪赛 红色偏光个性精钢石英男表" src="https://onegoods.nosdn.127.net/goods/2377/e59ec63b435a07bddf62856380258eef.jpg" onerror="this.src='http://mimg.127.net/p/yymobile/lib/img/products/square/s.png'"  />
						</a>
					 </div>
					<p className="w-home-goods-title f-txtabb"><a title="DIESEL 迪赛 红色偏光个性精钢石英男表" >DIESEL 迪赛 红色偏光个性精钢石英男表</a></p>
				</div>							        	    
            </li>     
		);
	}
}