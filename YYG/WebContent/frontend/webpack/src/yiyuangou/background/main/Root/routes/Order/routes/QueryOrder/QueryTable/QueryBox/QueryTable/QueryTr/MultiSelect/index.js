import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import './index.plugin.js';
import './index.plugin.css';
export default class MultiSelect extends React.Component{

	render(){
		return(
			<select className="fix-input  multiselect" multiple="multiple">
			<option data-dd_id="0">未打印</option>
			<option data-dd_id="1">已打印</option> 
			</select>
			)
	}
	componentDidMount(){
		$(ReactDOM.findDOMNode(this)).multiselect({
			buttonTitle: function(options, select) {
				if (options.length === 0) {
					return this.nonSelectedText;
				}
				else {
					var selected = '';
					options.each(function () {
						selected += this.getAttribute('dd_id') + ',';
					});
					return selected.substr(0, selected.length - 2);
				}
			}             
		});
	}
}