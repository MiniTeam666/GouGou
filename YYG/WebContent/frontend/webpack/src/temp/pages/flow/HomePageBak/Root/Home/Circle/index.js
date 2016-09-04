import React from 'react';
import ReactD3 from 'react-d3';
import stylesCSS from './index.css';
var PieChart = ReactD3.PieChart;

var pieData = [
  {label: '审批 19', value: 19,color:'rgb(14,116,193)',url:'http://www.youku.com/'},
  {label: '运维 5', value: 5,color:'rgb(53,175,26)',url:'http://www.tudou.com/'},
  {label: '公文 2', value: 2,color:'rgb(20,156,155)',url:'http://www.baidu.com/' },
  {label: '邮件 7', value: 7,color:'rgb(125,130,131)',url:'http://www.baidu.com/' },

];
var empty=[];
let chartSeries={
  field:'BMI',
  name:'BMI',
  color:'yellow'
}
var colors = function(d){
  return d;
};
var sort = null; // d3.ascending, d3.descending, func(a,b) { return a - b; }, etc...
export default class TabsExampleSwipeable extends React.Component {

  constructor(props) {
    super(props);
    this.handleClick=this.handleClick.bind(this);
  }
  handleClick(obj){

    location.href=obj.url;
  }
  render() {
    return (
    	<div className={stylesCSS.center}>
        <div>
      		<PieChart
  			  data={pieData}
  			  width={350}
  			  height={250}
  			  radius={90}
  			  innerRadius={90*0.7}
  			  sectorBorderColor="white"
          showInnerLabels={false}
           fill={['yellow']}
          labelTextFill={'#4285f4'}
          colors={colors}
          colorAccessor={function(d){return d.color}}
          hoverAnimation={true}
          legend={true}
          handleClick={this.handleClick}
  			/>

        </div>
        <div style={{textAlign:'center',position:'relative',top:'-175px',width:100,height:100,opacity:1}}>
          <h1 style={{marginBottom:'0',marginTop:'15px',fontWeight:'bold',fontSize:'2.5em'}}>32</h1>
          <h4 style={{marginTop:'0px',color:'#888'}}>待办事项</h4>
        </div>
    	</div>
    );
  }
}

       