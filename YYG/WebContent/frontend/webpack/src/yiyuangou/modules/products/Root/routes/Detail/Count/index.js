import React from 'react';
import StyleCss from './index.css';
import Event from 'company/util/lib/Event';
export default class Detail extends React.Component{
  constructor(props){
  	super(props);
    this.state={
      args:{
        minute:0,
        second1:0,
        second2:0,
        ms1:0,
        ms2:0,
      }
    }
    this.timer = this.timer.bind(this);
  }
  timer(num){
    var temp = num ;
    if(num<0){
      let event = new Event ;
      event.dispatch('products/Root/routes/Detail/changeDetialState',{
        content:false,
        count:false,
        finished:true,
      })
      return;
    }
    setTimeout(function(){
      that.timer(temp-10);
    },10);
    // console.log(num);
    num = parseInt(num/10);
    let ms2 = num%10;
    num = parseInt(num/10);
    let ms1 = num%10;
    num = parseInt(num/10);
    let second2 = num%10;
    num = parseInt(num/10);
    let second1 = num%6;
    num = parseInt(num/6);
    let minute =num%10;
    this.setState({
      args:{
        minute:minute,
        second1:second1,
        second2:second2,
        ms1:ms1,
        ms2:ms2,
      }
    });
    var that = this;
   
  }
  componentDidMount(){
    this.timer(5000);
  }
  render(){
    return(
      <div className="count-my">
      <div className="g-Countdown">
                <p className="orange">已满员，揭晓结果即将公布</p>
                <div>
                    <cite>
                        <span>
                          <b>0</b>
                          <b>{this.state.args.minute}</b>
                        </span>
                        <em>:</em>
                        <span>
                          <b>{this.state.args.second1}</b>
                          <b>{this.state.args.second2}</b>
                        </span>
                        <em>:</em>
                        <span>
                          <b>{this.state.args.ms1}</b>
                          <b>{this.state.args.ms2}</b>
                        </span>
                    </cite>
                </div>
       </div>
       </div>
    )
  }
}  
