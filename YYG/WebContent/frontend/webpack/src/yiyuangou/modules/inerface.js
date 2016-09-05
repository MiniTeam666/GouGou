#/home 首页接口 /home
{
	autoplays:[
		{
			id:'12323',
			img:'http://res.126.net/p/dbqb/resupload/onlinepath/2016/7/28/0/69e1275c4460f97f2d4b26d716348892.jpg',

		}
	],
	lasts:[
		{
			id:'45456'
			img:'https://onegoods.nosdn.127.net/goods/2377/e59ec63b435a07bddf62856380258eef.jpg',
			name:'DIESEL 迪赛 红色偏光个性精钢石英男表'

		}
	],
	hots:[
		{
			id:'12324',
			img:'http://onegoods.nosdn.127.net/goods/898/d3dc4b84825a35c50e2b5504d2b636cc.png',
			name:'Apple iPhone6s Plus 64g 颜色随机',
			value:17680,
			stock:9786
		}
	]
}

#/products 全部商品接口  /products?category='all'&type='renqi'&direction=1&page=
category:商品分类(例如: 全部分类 手机数码  电脑办公  家用电器 )
type: 商品分类 最新  人气  剩余人次 价值 
direction:剩余人次 价值 那个分类的排序方式 例如 对于剩余人次 1 代表剩余人次最少 0 代表剩余人次最多 
			对于价值  1代表价值最高  0代表价值最低
page:页数

{
	data:[
		{
			id:"123",
			img:"http://onegoods.nosdn.127.net/goods/140/a1945c27c821e85f3b46851467a914c7.png",
			name:"中国黄金 AU9999投资金50g薄片",
			value:17680,
			stock:9786,
			
		}
	],
	has_more:false//是否是末页了
}


#/products/detail 商品内容详情页 /products/detail?id='123123' 
{
	status:0 //商品状态 0代表购买中 1代表开奖中 2代表购买结束
	imgs:[
		{
			img:'http://res.126.net/p/dbqb/resupload/onlinepath/2016/7/28/0/69e1275c4460f97f2d4b26d716348892.jpg'
		}
	],// status=0,1 2 时需要传回
	cnt:13 //商品期数 status=0,1,2 时需要传回
	name:'平安银行 平安金福金条 Au9999 100g' // status=0,1,2 时需要传回
	detail:'以“平安”组成“福”字，寓意平安有福，新颖别致，妙思无穷！' //商品详情介绍 status=0,1,2 时需要传回 
	value:17680,// status=0,1,2 时需要传回
	stock:9786,// status=0,1,2 时需要传回

	time:180000 //status=1 需要传回的字段(ms)
	sys_
	joins:1 //status=2 时需要传回的字段 当前用户参与份数

	lucky_man:{
		id:'1231'， //中奖用户的Id
		name:'给我中个充气娃娃也行啊',
		joins:
		lucky_num:'10000060',
		revealed_time:'2016-08-12 17:23:07',//揭晓时间
	}//status=2 时需要传回的字段 中奖用户的个人信息


}

#/products/records 参与记录 /products/records?id='23423'&page=1 
id:商品ID
page:页数
{
	data:[
		{
			img:'http://faceimg.1yyg.com/UserFace/20160806172150927.jpg',//用户头像
			name:'小明',//用户昵称
			joins:123 ,//用户参与次数
			join_time:'2016-08-10 21:46:04.434', //参与购买时间 注意格式
			id:'234234',用户ID

		}
	],
	has_more:false //是否是末页了
}

#/products/describe 商品图文详情  /products/describe?id='123123'
id:商品ID
{
	data:[
		{
			img:'http://goodsimg.1yyg.com/GoodsInfo/20160616142620619.jpg'
		}
	]
}

#/products/calculate 商品计算详情 返回最后参与的100位用户购买记录 /products/calculate?id='123123'
id:商品ID
{
	data:[
		{
			join_time:'2016-08-10 21:46:04.434',//参与购买时间
			join_man{
				name:'小明',
				id:'34234'
			}
		}
	]
}

获取某个商品幸运者的所有购买号            /personal/getnums?user_id='123'&product_id='23423'
{
	data:[
		'2234243',//购买号
		'1232445',//购买号
	]
}



#/shopping_cart
{
	data:[
		{
			
		}
	]
}
