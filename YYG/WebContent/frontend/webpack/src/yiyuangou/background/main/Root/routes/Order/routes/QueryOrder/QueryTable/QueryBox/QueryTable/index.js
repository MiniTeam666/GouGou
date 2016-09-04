import React from 'react';
import QueryTr from './QueryTr';
import './index.css';
export default class QueryTable extends React.Component{
	render(){
		var {conditions,others} = this.props;
		var columns=[
			{
				col:'salesdate',
				name:'销售日期',
				type:'date'
			},{
				col:'customname',
				name:'客户名称',
				type:'string'
			},{
				col:'opcode',
				name:'品种',
				type:'list'
			}
		];

		var columnList = columns.map(function(column){
			return (<QueryTr key={column.col} column={column} conditions={conditions} />);
		});

		return (
			<table className="table table-striped table-bordered  table-hover fix-table">
				<thead>
					<tr>
						<th className="fix-th"></th>
						<th className="fix-th">列名</th>
						<th className="fix-th">操作</th>
						<th className="fix-th">值1</th>
						<th className="fix-th">值2</th>
					</tr>
				</thead>
				<tbody>
					{columnList}
				</tbody>
			</table>
		);
	}
};