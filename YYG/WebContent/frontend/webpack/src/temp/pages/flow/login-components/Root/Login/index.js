import React from 'react';
import Tabs from 'material-ui/lib/tabs/tabs';
import Tab from 'material-ui/lib/tabs/tab';
import Paper from 'material-ui/lib/paper';
import SwipeableViews from 'react-swipeable-views';
import TextField from 'material-ui/lib/text-field';
import RaisedButton from 'material-ui/lib/raised-button';
import QRCode from 'qrcode.react';
import Dialog from 'material-ui/lib/dialog';
import stylesCSS from './index.css'
import autobind from  'autobind-decorator';
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
const iconStyles = {
  marginRight: 24,
};
let isPC =function(){    
     let userAgentInfo = navigator.userAgent;  
     // alert(userAgentInfo)
     let Agents = new Array("Android", "iPhone", "SymbianOS", "Windows Phone", "iPad", "iPod");    
     let flag = true;    
     for (let v = 0; v < Agents.length; v++) {    
         if (userAgentInfo.indexOf(Agents[v]) > 0) { flag = false; break; }    
     }    
     return flag;    
  } ();
  // alert(isPC());
@autobind
export default class TabsExampleSwipeable extends React.Component {

  constructor(props) {
    super(props);
    var margin = {
       marginTop:'0px',
       marginRight:'0px',
    };
    if(isPC){
      margin={
        marginTop:'150px',
        marginRight:'260px',
      }
    }
    this.state = {
      slideIndex: 0,
      QRCode:'',
      change:true,
      open:false,
      marginLeft:isPC?'260px':0,
      width : isPC?400:'100%',
      top:isPC?'50%':0,
      fix:isPC?50:0,
    };

    // this.handleChange = this.handleChange.bind(this);
    // this.changeQRCode = this.changeQRCode.bind(this);
    // this.handleOpen = this.handleOpen.bind(this);
    // this.handleClose = this.handleClose.bind(this);
    // this.getActions = this.getActions.bind(this);
  }
  getActions(){
    var that = this ;
    return (
      <div>
       <RaisedButton  label="确定" primary={true} style={{margin:12}}  onClick={that.handleClose}/>
      </div>
    );
  }
  
  handleChange (value){
    
    this.setState({
      slideIndex: value,

    });
    if(this.state.change){
      this.setState({
        change:false
      });
      this.changeQRCode();
    }

  }

  handleOpen () {
    // this.setState({open: true});
    document.getElementsByClassName('fm1-code')[0].submit();
  }

  handleClose(){
    this.setState({open: false});
  }

  changeQRCode(){
     this.setState({
      QRCode:Math.random()*1000000
    });
  }
  render() {
    return (

      <Paper style={{height:400,width:this.state.width,position:'relative',top:this.state.top,margin:'auto',background:'#FFF',marginLeft:this.state.marginLeft}} zDepth={5} >
        <Tabs
          onChange={this.handleChange}
          value={this.state.slideIndex}
        >
          <Tab  label="帐号登录" value={0}  />
         {/* <Tab  label="扫码登录" value={1} />*/}
        </Tabs>
        <SwipeableViews
          index={this.state.slideIndex}
          onChangeIndex={this.handleChange}
        >
          
          <div style={styles.slide}>
           <div className={stylesCSS.center}>
             <form id="fm1" className="fm1-code" action="/cas-server/login" method="post">
                <div style={{margin:'auto',marginTop:20}}>
                <TextField
                  floatingLabelText="请输入你的帐号"
                  floatingLabelStyle={{color:'#555'}}
                  floatingLabelFixed={true}
                  style={{width:280}}
                  name="username"
                  id="username"
                /><br />
                <TextField
                  floatingLabelText="请输入你的密码"
                   floatingLabelStyle={{color:'#555'}}
                  floatingLabelFixed={true}
                  type="password"
                  name="password"
                  id="password"
                  style={{width:280}}
                /><br />
                <RaisedButton type=""label="登录" primary={true} style={{width:280,marginTop:50}} onClick={this.handleOpen}/>
                <Dialog
                  actions={this.getActions()}
                  title="错误提示"
                  titleStyle={{color:'rgba(0,0,0,0.5)'}}
                  modal={false}
                  bodyStyle={{color:'rgba(0,0,0,0.6)'}}
                  open={this.state.open}
                  onRequestClose={this.handleClose}
                >
                  格式错误,请输入你的帐号或者密码
                </Dialog>
                </div>  
                <div id="hidden-code"></div>
             </form>
           </div>

          </div>
          {/*<div style={styles.slide}>
            <div className={stylesCSS.center}>
              <div style={{margin:'auto',color: '#888',marginTop:30}}>
                请使用LINK手机客户端扫描登录
              </div>
              <div style={{margin:'auto',marginTop:30}}>
                 <QRCode value={this.state.QRCode+''}/>
              </div>
              <div style={{margin:'auto',marginTop:30}}>
                <a  style={{color:'rgba(0, 0, 0, 0.5)'}} onClick ={this.changeQRCode}>
                 刷新二维码
                </a>
              </div>
            </div>
          </div>
        */}
        </SwipeableViews>
      </Paper>

    );
  }
}