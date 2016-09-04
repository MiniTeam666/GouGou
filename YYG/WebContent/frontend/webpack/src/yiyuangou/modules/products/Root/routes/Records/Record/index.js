import React from 'react';
export default class Record extends React.Component{
  constructor(props){
  	super(props)
  }

  handleClick(){
    location.href='#/personal/buyrecords';
  }
  
  render(){
    return(
            	<li buyid="794457901" buynum="786" username="515151515" onTouchTap={this.handleClick}>
            		<i className="fr z-set"></i>
            		<p>
            			<a >
            				<img src="http://faceimg.1yyg.com/UserFace/20160806172150927.jpg" />
            			</a>
            		</p>
            		<dl>
            			<dt>
            				<span className="fl">
            					<a  className="blue">
            						515151515
            					</a>
            				</span>
            				<cite className="fl">
	            				云购了
	            				<b className="orange">
	            					786
	            				</b>
	            				人次
            				</cite>
            			</dt>
            			<dd className="gray9">
            				2016-08-10 21:46:04.434
            			</dd>
            		</dl>
            	</li>
           
    )
  }
}  
