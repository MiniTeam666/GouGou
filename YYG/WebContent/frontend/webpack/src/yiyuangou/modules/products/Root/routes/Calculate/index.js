import React from 'react';
import StyleCss from './index.css';
import Detail from './Detail';
import HowToCalculate from './HowToCalculate';
import Alert from 'company/yiyuangou/lib/Alert';
import autobind from 'autobind-decorator';
import Event from 'company/util/lib/Event';
import Table from './Table';
@autobind
export default class Calculate extends React.Component{
  constructor(props){
  	super(props);
    this.state={
      show:false,
    }
  }
  handleClick(){
    let event = new Event;
    event.dispatch('company/yiyuangou/lib/Alert',{});
  }
  
  render(){
    return(
      <div className='calculate'>
		    <Detail handleClick = {this.handleClick}/>
        <Alert Children={HowToCalculate} title={"如何计算"} show={this.state.show}/>
        <Table />
      </div>
    )
  }
}  
