import React from 'react';
import ReactD3 from 'react-d3';
import * as Colors from 'material-ui/lib/styles/colors';
import stylesCSS from './index.css';
// import Pdf from '../NewList/Pdf';
var PieChart = ReactD3.PieChart;
var colors = [
  'rgb(58,168,203)','#2bb2a3','rgb(130,181,37)',
  'rgb(180,147,219)','rgb(240,175,205)',
  'rgb(163,200,255)','rgb(240,220,255)',
];

var getColor = function(d){
  return d;
};

export default class Circle extends React.Component {

  constructor(props) {
    super(props);
    this.handleClick=this.handleClick.bind(this);
  }
  handleClick(obj){
    obj = obj.url;
    if(obj.type==1){
      app.link.launchLinkService( obj.action);
    }else if(obj.type==2){
      app.link.runApp({
        appCode:obj.action
      })
    }else if(obj.type==3){
      // alert(1);
      // var url ='http://10.0.3.185:3000/document.pdf';
      // window.reRender(<Pdf pdf={url}/>);
      location.href=obj.action;
    }else if(obj.type==4){
      
    }else if(obj.type==5){
      app.link.openMsgCenter();
    }
  }
  filter(pieData){

    var newPieData=[];
    this.value = 0;
    for( let i=0;i<pieData.length;i++){
       if(pieData[i]['value']>0){
       
        newPieData.push(pieData[i]);
       }
    }
    // alert(app.link.getUnreadMessageCount);
   
    
    return newPieData;
  }
  changeData(pieData){
    var newPieData=[];
    this.value=0;
    for( let i=0;i<pieData.length;i++){
        this.value += pieData[i]['value'];
        newPieData[i]={};
        newPieData[i]['value']=pieData[i]['value'];
        newPieData[i]['label']=pieData[i]['name']+pieData[i]['value'];
        newPieData[i]['color']=colors[i];
        newPieData[i]['url']=pieData[i];

    }

    if(newPieData.length>1){
      newPieData[newPieData.length-1]['color']='#EEE';
    }

    return newPieData;
  }
  render() {
    let {pieData} = this.props;
    var labelTextFill = '#4285f4';
    if(pieData.length!=0)
    pieData= this.filter(pieData);
    let newPieData = this.changeData(pieData);

    if(newPieData.length==0){
      newPieData=[{value:1,color:'rgb(58,168,203)',url:{},label:''}];
      labelTextFill = "#FFF";
    }

    return (
    	<div className={stylesCSS.center}>
        <div>
      		<PieChart
  			  data={newPieData}
  			  width={340}
  			  height={280}
  			  radius={85}
  			  innerRadius={85*0.7}
  			  sectorBorderColor="white"
          showInnerLabels={false}
          fill={['yellow']}
          labelTextFill={labelTextFill}
          colors={getColor}
          colorAccessor={function(d){return d.color}}
          hoverAnimation={true}
          legend={true}
          handleClick={this.handleClick}
  			/>

        </div>
        <div style={{textAlign:'center',position:'relative',top:'-190px',width:100,height:100,opacity:1}}>
          <h1 style={{marginBottom:'0',marginTop:'15px',fontWeight:'bold',fontSize:'2.5em',color:'#333'}}>{this.value}</h1>
          <h4 style={{marginTop:'0px',color:'#888'}}>待办事项</h4>
        </div>
    	</div>
    );
  }
}

       