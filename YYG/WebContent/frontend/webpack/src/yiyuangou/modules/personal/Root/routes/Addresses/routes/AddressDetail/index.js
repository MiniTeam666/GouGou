import React from 'react';
import StyleCss from './index.css';
import WBarCenter from 'company/yiyuangou/lib/WBarCenter';
import Router from 'company/util/lib/Router';
import autobind from 'autobind-decorator';
@autobind
export default class AddressDetail extends React.Component{
  constructor(props){
  	super(props)
  }

  route(url){
    // let url = this.props.url;
    let router = new Router;
    router.route({url:url});
  }
  cancel(){
    this.route('#/personal/addresses');
  }
  save(){
     this.route('#/personal/addresses');
  }
  render(){
    return(
      <div>
        <div className="m-simpleHeader" >
              <a  data-pro="cancel" className="m-simpleHeader-back" onTouchTap={this.cancel}>
               取消
              </a>
              <a className="m-simpleHeader-ok" onTouchTap={this.save}>
                保存
              </a>
              <h1>修改收货地址</h1>
        </div>
      <div className="m-user" >
          
        <div className="m-user-addrAdd">
          <div className="m-user-bar" >
            <div className="w-inputBar w-bar">
              <div className="w-bar-label">
                收货人
              </div>
              <div className="w-bar-control">
                <input className="w-bar-input" type="text" name="" value="黄俊东" />
              </div>
            </div>
            <div className="w-inputBar w-bar">
              <div className="w-bar-label">
                身份证
              </div>
              <div className="w-bar-control">
                <input className="w-bar-input" type="text" name="" value="44522119930202771X" maxlength="18" />
              </div>
            </div>
            <div className="w-inputBar w-bar" >
              <div className="w-bar-label">
                手机号码
              </div>
              <div className="w-bar-control">
                <input data-pro="input" className="w-bar-input" type="text" name="" value="188*****170" maxlength="11" />
              </div>
            </div>
            <div className="w-inputBar w-bar">
              <div className="w-bar-label">
                邮政编码
              </div>
              <div className="w-bar-control">
                <input className="w-bar-input" type="text" name="" value="515500" maxlength="6"/>
              </div>
            </div>
            <div className="w-selectBar w-bar" >
              <div className="w-bar-label">
                省份
              </div>
              <div className="w-bar-control">
                <select className="w-bar-input" name="">
                  <option value="-1">请选择</option>
                  <option value="110000">北京</option>
                  <option value="120000">天津</option>
                </select>
              </div>
            </div>
            <div className="w-selectBar w-bar" id="pro-view-20">
              <div className="w-bar-label">城市</div>
              <div className="w-bar-control">
                <select className="w-bar-input" name="">
                  <option value="-1">请选择</option>
                  <option value="110100">北京市</option>
                </select>
              </div>
            </div>
            <div className="w-selectBar w-bar" id="pro-view-21">
              <div className="w-bar-label">地区</div>
              <div className="w-bar-control">
                <select className="w-bar-input" name="">
                  <option value="-1">请选择</option>
                </select>
              </div>
            </div>
            <div className="w-inputBar w-bar" id="pro-view-15">
              <div className="w-bar-label">
                详细地址
              </div>
              <div className="w-bar-control">
                <textarea className="w-bar-input" name="">
                  阿斯顿
                </textarea>
              </div>
            </div>
            <div className="w-checkBar w-bar" id="pro-view-16">
              是否设为默认地址
              <div className="w-bar-ext">
                <b data-pro="switcher" className="w-switcher">
                </b>
                <input type="checkbox" />
              </div>
            </div>
            <WBarCenter name={'－ 删除收货地址'} url={'#/show'}/>
          </div>
        </div>
      </div>
      </div>
    )
  }
}  
