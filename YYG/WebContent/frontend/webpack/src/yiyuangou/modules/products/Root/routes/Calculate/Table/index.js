import React from 'react';
import './index.css';
export default class Calculate extends React.Component{
  constructor(props){
  	super(props)
  }
  
  render(){
    return(
      <table className="table" >
        <thead>
          <tr>
            <th>云购日期</th>
            <th>云购时间</th>
            <th>转换数据</th>
             <th>昵称</th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td >2016-08-18</td>
            <td >15:41:41.187</td>
            <td className="orange">154141187</td>
            <td>云个心里想</td>
          </tr>
          <tr>
            <td >2016-08-18</td>
            <td >15:41:41.187</td>
            <td className="orange">154141187</td>
            <td>云个心里想</td>
          </tr>
          <tr>
            <td >2016-08-18</td>
            <td >15:41:41.187</td>
            <td className="orange">154141187</td>
            <td>云个心里想</td>
          </tr>
        </tbody>
      </table>
    )
  }
}  
