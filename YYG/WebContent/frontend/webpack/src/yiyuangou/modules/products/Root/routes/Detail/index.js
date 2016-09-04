import React from 'react';
import AutoPlaySwipeViews from './AutoPlaySwipeViews';
import Content from './Content';
import WBars from 'company/yiyuangou/lib/WBars'
import Footer from './Footer';
import Count from './Count';
import Event from 'company/util/lib/Event';
import Finished from 'company/yiyuangou/lib/Finished';
import autobind from 'autobind-decorator';
@autobind
export default class Detail extends React.Component{
  constructor(props){
    super(props);
    
    this.state={
      status:this.props.status,
    }
  }

  componentDidMount(){
    let event = new Event;
    event.subscribe('products/Root/routes/Detail/changeDetialState',this.changeDetialState);
    
  }

  componentWillReceiveProps(nextProps){
  	console.log(nextProps.params);
  }
  changeDetialState(obj){
    this.setState({
      status:2
    })
  }
  _renderBuying(){

    return(
      <div>
         <AutoPlaySwipeViews imgs ={this.props.imgs}/>
         <Content {...this.props}/>
      </div>
    )
  }//

  _renderRevealing(){
    return(
      <div>
        <Count />
        <AutoPlaySwipeViews imgs ={this.props.imgs}/>
        <Content />
      </div>
      )
  }//
  _renderFinished(){
    return(
      <div>
        <Finished />
      </div>
      )
  }//
   
  render(){
    // this.setDefaultProps(); 
    console.log(this.props);
    let renderString = [];
    switch(this.state.status){
      case 0: renderString = this._renderBuying();break;
      case 1: renderString = this._renderRevealing();break;
      case 2:renderString = this._renderFinished();break;
      default:break;
    }
    return(
       <div className='detail'>
            {renderString}
            <WBars wbars={[
                {name:'参与记录',url:'#/products/records/2'},
                {name:'商品详情',url:'#/products/describe/2'},
                {name:'商品晒单',url:'#/products/shows/2'},
                {name:'计算详情',url:'#/products/calculate/2'},
              ]}
            />
            <div style={{height:50}}>
            </div>
            <Footer />
       </div>
    )
  }
}  

Detail.defaultProps ={
      status:1, //商品状态 0代表购买中 1代表开奖中 2代表购买结束
      imgs:[
        {
          img:'https://onegoods.nosdn.127.net/goods/1588/d273f8f6f4bcd377239998b78be13fcf.jpg',
        }, {
          img:'https://onegoods.nosdn.127.net/goods/1588/d273f8f6f4bcd377239998b78be13fcf.jpg',
        },
      ],// status=0,1 2 时需要传回
      cnt:13 ,//商品期数 status=0,1,2 时需要传回
      name:'平安银行 平安金福金条 Au9999 100g' ,// status=0,1,2 时需要传回
      detail:'以“平安”组成“福”字，寓意平安有福，新颖别致，妙思无穷！' ,//商品详情介绍 status=0,1,2 时需要传回 
      value:17680,// status=0,1,2 时需要传回
      stock:9786,// status=0,1,2 时需要传回

      time:180000 ,//status=1 需要传回的字段(ms)
      sys_time: 1820000,//status=1 需要传回的字段(ms)
      joins:1, //status=2 时需要传回的字段 当前用户参与份数
      
      describe: "<p>手机上立刻睡觉了</p>",

      lucky_man:{
        id:'1231', //中奖用户的Id
        name:'给我中个充气娃娃也行啊',
        lucky_num:'10000060',
        joins:2,
        revealed_time:'2016-08-12 17:23:07',//揭晓时间
      },//status=2 时需要传回的字段 中奖用户的个人信息
    }
