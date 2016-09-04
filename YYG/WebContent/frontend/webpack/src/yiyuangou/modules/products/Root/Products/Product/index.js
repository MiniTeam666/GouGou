import React from 'react';
import Paper from 'material-ui/Paper';
import Divider from 'material-ui/Divider';
import StyleCss from './index.css';
const style = {
  height: 100,
  // width: 100,
  // margin: 20,
  textAlign: 'center',
  // display: 'inline-block',
};
export default class Product extends React.Component{
	getDefaultProps(){
		return{
			test:'pro'
		}
	}
	render(){
		console.log(this.props.test);
		return(
			<div style={{position:'relative'}}>
				<div className="w-product-goods w-product-goods-l w-product-goods-ing">
					<div className="w-product-goods-pic">
						<a href="http://m.1.163.com/detail/140-308042635.html">
							<img src="http://onegoods.nosdn.127.net/goods/140/a1945c27c821e85f3b46851467a914c7.png" onerror="this.src='http://mimg.127.net/p/yymobile/lib/img/products/square/m.png'" className=""/>
						</a>	
					</div>
					<div className="w-product-goods-info">
						<p className="w-product-goods-title f-txtabb f-breakword">
							<a href="http://m.1.163.com/detail/140-308042635.html">
								中国黄金 AU9999投资金50g薄片
							</a>
						</p>
						<div className="w-product-progressBar">
							<p className="wrap">
								<span className="bar" style={{width:'47%'}}>
									<i className="color"></i>
								</span>
							</p>
							<ul className="txt">
								<li className="txt-l">
									<p>价值 : ¥17680 </p>
								</li>
								<li className="txt-r">
									<p>
										剩余
										<b className="txt-blue">9357 </b>
										份
									</p>
								</li>
							</ul>
						</div>
					</div>
					<div className="w-product-goods-shortFunc">
						<button data-pro="addToCart" className="w-product-button w-product-button-round w-product-button-addToCart">
						</button>
					</div>
					<Divider style={{height:1}} />
				</div>
			</div>
		)
	}
}