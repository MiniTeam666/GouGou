import React from 'react';
import SwipeableViews from 'react-swipeable-views';
import RaisedButton from 'material-ui/lib/raised-button';
import stylesCSS from './index.css';
import Circle from './Circle';
import AssList from './AssList';
import Slick from './Slick';
import autobind from  'autobind-decorator';
import  './File';
import Refresh from 'test/lib/Refresh';
//import Test from 'test/index.jsx';
import NewList from './NewList';
const styles = {
  headline: {
    fontSize: 24,
    paddingTop: 16,
    marginBottom: 12, 
    fontWeight: 400,
  },
  slide: {
    padding: 10,
  },
};

@autobind
export default class Home extends React.Component {

  constructor(props) {
    super(props);
    this.state={
      assList:[],
      pieData:[],
      reRender:false,
      component:{},
      refresh:false,
      imgs:[],
      imgsStatus:false,
      news:[],
    }
    this.cnt = 0;
    this.initState ={};
  }
  async getImgs(imgs){
    let newImgs = [];
    let file = new window.MyFile;
    for(let i=0;i<imgs.length;i++){
      let img = imgs[i];
      let newImg={};
      let path = await file.getFilePath(img['src'],img['id'],img['bool']);
      newImg['id'] = img.id;
      newImg['link'] = img.link;
      newImg['path'] = path;  
      newImg['content']=img.content;    
      newImgs.push(newImg);
    }
    this.setInitialState({
      imgs:newImgs,
      imgsStatus:true
     
    },'getImgs');
  }
  setInitialState(obj,func){
    for(var prop in obj){
      this.initState[prop] = obj[prop];
    }
    this.init(func);
  }
  init(func){
    switch(func){
      case 'getImgs': this.cnt+=1; break;
      case 'getAssList': this.cnt+=2 ;break;
      case 'setPieData':this.cnt +=4 ;break;
      case 'setNews':this.cnt +=8 ;break;
      default:;
    }
    if(this.cnt==15){
      var p = JSON.stringify(this.initState);
      localStorage.setItem('initState',p);
      this.setState(this.initState);
      window.getLogo();
      window.closeDownRefresh();
      this.cnt=0;
    }
  }
  setReRender(component){
    this.setState({
      component:component,
      reRender:true,
    });
  }
  getAssList(assList){
    this.setInitialState({
        assList:assList,
    },'getAssList');
  }
  setPieData(pieData){
    this.setInitialState({
      pieData:pieData,
    },'setPieData');
  }
  setNews(news){
    this.setInitialState({
      news:news,
    },'setNews');
  }
  refresh(bool){
    this.setState({
      refresh:bool,
    });
  }
fecth(){
    var home = this;
    var url = 'http://app.gzmpc.com/NewMobilePlatform/api/link/main';
    
   app.link.getLoginInfo(function(result){
   
     app.ajax({
            url: url,
            data: {ua:result.loginId},
            async: true,
            contentType:'application/x-www-form-urlencoded',
            timeout: 15000,
            success: function(result) {
              var json = JSON.parse(result.returnValue);
              var data = json.data;
              json = data.msg;
              if(json.length==0)return;
              // home.log(json);
              app.link.getUnreadMessageCount(function(len){
                  
                  var a = json;
                  if(len>0){
                    var obj = {
                      value:len,
                      name:'消息',
                      type:5,
                    };
                    a= a.slice(0,1).concat([obj]).concat(a.slice(1,a.length));
                    // home.log(a);
                    
                  }
                  home.setPieData(a);
                  
              },function(error){
                  home.setPieData(json);
              });
              home.getImgs(data.img);
              home.setNews(data.news);

            },
            fail:function(msg){
              app.alert('请检查你的网络');
              // app.alert(msg);
            }                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
      });
   });
    
  }
  fecthJSON(){
    var home = this;
    var url = 'http://wechat.gzmpc.com/kanban_table/news.config.json';
    app.ajax({
            url: url,
            data: {},
            async: true,
            contentType:'application/x-www-form-urlencoded',
            timeout: 15000,
            success: function(result) {
              // app.alert(result);
              var json = JSON.parse(result.returnValue);
              
            },
            fail:function(msg){
              // app.alert('fail');
              app.alert(msg);
            }                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
      });
  }
  log(log){
    // alert('log start');
    app.ajax({
            url: "http://wechat.gzmpc.com/kanban_table/console.php",
            data: {"log":log},
            async: true,                                  
            timeout: 15000,
            success: function(result) {
              // alert('log end');
            },          
            fail:function(msg){
              // app.alert('fail log');
              app.alert(msg);
            }

      });
  }
  fecthMore(){
     var networkState = navigator.connection.type;
      
     if(networkState == Connection.NONE){
        alert('当前没有网络,请开启网络');
        return;
      }
     this.fecth();
     this.getFavoriteService();
  }
  fecthDataFromRemote(){   
    var home = this;
    // var json=[{"value":1,"action":"bingo-gy-link","linkid":"59134079-d43e-44bc-bc2e-cd02023fc7fe","type":2,"name":"手机审批"},{"value":5,"action":"[OpenUrl]\n url=https:\/\/$${bingo.link_disk_web_ip}:$${bingo.link_disk_web_port}\/disk","linkid":"0c0c6ef12d284244b35093b1f7f03a7b","type":1,"name":"网盘"},{"value":6,"action":"https:\/\/www.gzmpc.com","linkid":"aaaaaa","type":3,"name":"网页测试"},{"value":6,"action":"","linkid":"bbbb","type":4,"name":"空白测试"}];
    // home.setPieData(json);
    app.page.onLoad=function(){
      app.rotation("portrait");
      var networkState = navigator.connection.type;
      
      if(networkState == Connection.NONE){
        var p = JSON.parse(localStorage.getItem('initState'))||{};
       
        home.setState(p);
        setTimeout(function(){
          window.getLogo();
        },0);
        return;
      }else{
         home.fecth();
         home.getFavoriteService();

      }
     
    }
  }

  getFavoriteService(){
    app.link.getFavoriteService=function(callback){
      var params={
        code:"Data",
        key:"GetFavoriteService"
      };
      Cordova.exec(callback,null,"LinkPlugin","launchLinkServiceWithDictionary",[params]);
    };
    var home = this;
    app.link.getFavoriteService(function(data){
     home.getAssList(JSON.parse(data));
    });
  }

  componentDidMount(){

    this.fecthDataFromRemote();
    window.reRender = this.setReRender;
    window.log=this.log;
    window.refresh = this.refresh;
    window.fecthMore = this.fecthMore;
  }
  _render(){
      let p = [];
      if(this.state.imgsStatus){
        p.push(<Slick imgs = {this.state.imgs}/>);
      }
    return (
    
      <div>
        <Refresh display={this.state.refresh}/>
        <Circle pieData={this.state.pieData}/>
        <div style={{marginTop:'-100px'}}>
          <AssList assList={this.state.assList}/>
        </div>
        <div style={{width:'100%','overflow':'hidden'}}>  
          {p}
        </div>
        <NewList news={this.state.news}/>
      </div>
    );
  }//
  _reRender(){
    
    return this.state.component;
    
  }
  render() {
    if(!this.state.reRender){
      return this._render();
    }else{
      return this._reRender();
    }
    
  }
}