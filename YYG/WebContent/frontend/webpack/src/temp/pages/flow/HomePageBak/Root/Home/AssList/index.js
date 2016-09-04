import React from 'react';
import Ass from './Ass';
import autobind from  'autobind-decorator';
import StyleCSS from './index.css';

@autobind
export default class TabsExampleSwipeable extends React.Component {

  constructor(props) {
    super(props);
    this.state={
      flag:false,
    }
    
  }
  
  getDefaultProps(){
    return{

    };
  }

  
  render() {

    return (
      <div>
      	<div className={StyleCSS.asslist}>
        	 <Ass />
           <Ass />
           <Ass />
           <Ass />
      	</div>
        <div className={StyleCSS.asslist}>
           <Ass />
           <Ass />
           <Ass />
        </div>
      </div>
    );
  }
}