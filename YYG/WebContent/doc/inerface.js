#所有接口基本结构
{
	data:{}, //object类型数据 返回数据的存储字段
	status:0, //0代表成功返回数据 非0代表接口返回错误
	errMsg:'超时错误',//接口错误信息
}

#/home 首页接口 /home
{
 data:{
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
 },
 status:0,//状态信息
 errMsg:'超时错误',
}

#/products/category 商品列表 /products/category
{
	data:{
		category:[
			{
				name:'手机数码',
				id:'12',
			}
		]
	},
	status:0,//状态信息
	errMsg:'超时错误',
}

#/products 全部商品接口  /products?category='all'&type='renqi'&direction=1&page=
category:商品分类(例如: 全部分类 手机数码  电脑办公  家用电器 )
type: 商品分类   人气:0  最新:1 剩余人次:2 价值:3 
direction:剩余人次 价值 那个分类的排序方式 例如 对于剩余人次 1 代表剩余人次最少 0 代表剩余人次最多 
			对于价值  1代表价值最高  0代表价值最低
page:页数
{
	data:{
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
	},

	status:0,//状态信息
 	errMsg:'超时错误',
}

#/products/detail 商品内容详情页 /products/detail?id='123123' 
{
	data:{
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
		stock:9786,// status=0,1,2 时需要传回 代表当前该商品剩下的份数
		describe:"<p>sdfsd</p>",//图文详情HTML status=0,1,2 时需要传回


		time:180000 //status=1 需要传回的字段(ms)
		sys_time:18000//status=1 系统时间
		joins:1 //status=2 时需要传回的字段 当前用户参与份数

		lucky_man:{
			id:'1231'， //中奖用户的Id
			name:'给我中个充气娃娃也行啊',
			joins:
			lucky_num:'10000060',
			revealed_time:'2016-08-12 17:23:07',//揭晓时间
		}//status=2 时需要传回的字段 中奖用户的个人信息

		next_product_id:17//status=1,2时需要传回 该商品的下一云购号

	},
	status:0,//状态信息
    errMsg:'超时错误',
}

#/products/records 参与记录&计算详情接口 /products/records?id='23423'&page=1 
id:商品ID
page:页数
{
	data:{
		data:[
			{
				img:'http://faceimg.1yyg.com/UserFace/20160806172150927.jpg',//用户头像
				name:'小明',//用户昵称
				joins:123 ,//用户参与次数
				join_time:'2016-08-10 21:46:04.434', //参与购买时间 注意格式
				id:'234234',//用户ID
				record_id:''

			}
		],
		has_more:false //是否是末页了
	},

	status:0,//状态信息
    errMsg:'超时错误',

}



获取某个商品幸运者的所有购买号            /personal/getnums?user_id='123'&product_id='23423'
{
	data:{
		data:[
			'2234243',//购买号
			'1232445',//购买号
		]
	},
	status:0,//状态信息
 	errMsg:'超时错误',
}

#/show  晒单接口 /show?page=1
#page:0 页码

{
	data:{
		data:[
			{
				show:{
					time:'2016-08-10 21:46:04',
					img:'https://onegoods.nosdn.127.net/goods/2375/753bb7b8ab06537a6fb6ea432dac7ea3.jpg',
					title:'金币金币如我心意',
					id:'123123',//晒单ID
					content:'一元夺宝是真正的公平公正公开夺宝平台，中了很多东西。',//晒单正文

				},
				product_id:'123123',
				lucky_man:{
					id:'1231'， //中奖用户的Id
					name:'给我中个充气娃娃也行啊',
				}

			}
		]
	},
	status:0,//状态信息
 	errMsg:'超时错误',
}



#/shopping_cart 结算页面接口 /shopping_cart
post提交数据 {
			data:[
				{
					id:'123123',//商品ID
					cnt:13,// 购买份数
				}
			]
		 }

返回数据 {
			data:{
				fail_list:[
					{
						id:'123123',//商品ID
						stock:12,//当前商品所剩份数
						next_product_id:'123123'//下一云商品ID
						next_product_stock:568//下一商品所剩份数
						next_num:'1231',//下一云的云号
					}
				],

			},
			status:0,//状态信息
			errMsg:'超时错误'
		}




