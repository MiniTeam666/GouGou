import React from 'react';
import AutoPlaySwipeViews from './AutoPlaySwipeViews';
import Content from './Content';
import WBars from 'company/yiyuangou/lib/WBars'
import Footer from './Footer';
import Count from './Count';
import Event from 'company/util/lib/Event';
import Finished from 'company/yiyuangou/lib/Finished';
import autobind from 'autobind-decorator';
import Fetch from 'company/util/lib/Fetch';
import {REQUEST_PRODUCTS_DETAIL_PATH} from 'company/yiyuangou/api/frontend';
@autobind
export default class Detail extends React.Component{
  constructor(props){
    super(props);
    
    this.state={
        
    }
  }

  componentDidMount(){
    let event = new Event;
    event.subscribe('products/Root/routes/Detail/changeDetialState',this.changeDetialState);
    this.getData();
  }

  async getData(){
    const obj = this.props.location.query;
    const url = REQUEST_PRODUCTS_DETAIL_PATH;
    const fetch = new Fetch();
    let json = await fetch.get(url,REQUEST_PRODUCTS_DETAIL_PATH,obj);
    // json.status=1;
    this.changeDetialState(json);

  }
  changeDetialState(obj){
    console.log(obj);
    // obj.status = 1;
    this.setState({
      ...obj
    })
  }
  _renderBuying(){

    return(
      <div>
         <AutoPlaySwipeViews imgs ={this.state.imgs}/>
         <Content {...this.state}/>
      </div>
    )
  }//

  _renderRevealing(){
    return(
      <div>
        <Count  />
        <AutoPlaySwipeViews imgs ={this.state.imgs}/>
        <Content {...this.state}/>
      </div>
      )
  }//
  _renderFinished(){
    return(
      <div>
        <Finished {...this.state}/>
      </div>
      )
  }//
   
  render(){
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
