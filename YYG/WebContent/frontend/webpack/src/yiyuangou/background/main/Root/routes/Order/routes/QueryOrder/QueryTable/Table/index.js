import React from 'react';
import {Table, TableBody, TableHeader, TableHeaderColumn, TableRow, TableRowColumn} from 'material-ui/Table';

const Style={
  td:{
    textAlign:'center',
    borderLeft:'solid 1px #EEE',

  },
  tr:{
    color:'#00bcd4',
    textAlign:'center',
  }
}
const TableExampleSimple = () => (
  <Table multiSelectable={true} bodyStyle={{height:400,overflow:'scroll'}}  fixedHeader={false} style={{border:'solid 1px #EEE',width:'auto',overflow:'scroll'}} >
    <TableHeader style={{color:'#00bcd4'}} enableSelectAll={true} adjustForCheckbox={true} displaySelectAll={true}>
      <TableRow >
        <TableHeaderColumn style={Style.tr}>ID</TableHeaderColumn>
        <TableHeaderColumn style={Style.tr}>Name</TableHeaderColumn>
        <TableHeaderColumn style={Style.tr}>Status</TableHeaderColumn>
        <TableHeaderColumn style={Style.tr}>ID</TableHeaderColumn>
        <TableHeaderColumn style={Style.tr}>Name</TableHeaderColumn>
        <TableHeaderColumn style={Style.tr}>Status</TableHeaderColumn>
        <TableHeaderColumn style={Style.tr}>ID</TableHeaderColumn>
        <TableHeaderColumn style={Style.tr}>Name</TableHeaderColumn>
        <TableHeaderColumn style={Style.tr}>Status</TableHeaderColumn>
      </TableRow>
    </TableHeader>
    <TableBody>
      <TableRow>
        <TableRowColumn style={Style.td}>1</TableRowColumn>
        <TableRowColumn style={Style.td}>John Smith</TableRowColumn>
        <TableRowColumn style={Style.td}>Employed</TableRowColumn>
        <TableRowColumn style={Style.td}>1</TableRowColumn>
        <TableRowColumn style={Style.td}>John Smith</TableRowColumn>
        <TableRowColumn style={Style.td}>Employed</TableRowColumn>
        <TableRowColumn style={Style.td}>1</TableRowColumn>
        <TableRowColumn style={Style.td}>John Smith</TableRowColumn>
        <TableRowColumn style={Style.td}>Employed</TableRowColumn>

      </TableRow>
       <TableRow>
        <TableRowColumn style={Style.td}>1</TableRowColumn>
        <TableRowColumn style={Style.td}>John Smith</TableRowColumn>
        <TableRowColumn style={Style.td}>Employed</TableRowColumn>
        <TableRowColumn style={Style.td}>1</TableRowColumn>
        <TableRowColumn style={Style.td}>John Smith</TableRowColumn>
        <TableRowColumn style={Style.td}>Employed</TableRowColumn>
        <TableRowColumn style={Style.td}>1</TableRowColumn>
        <TableRowColumn style={Style.td}>John Smith</TableRowColumn>
        <TableRowColumn style={Style.td}>Employed</TableRowColumn>

      </TableRow>
       <TableRow>
        <TableRowColumn style={Style.td}>1</TableRowColumn>
        <TableRowColumn style={Style.td}>John Smith</TableRowColumn>
        <TableRowColumn style={Style.td}>Employed</TableRowColumn>
        <TableRowColumn style={Style.td}>1</TableRowColumn>
        <TableRowColumn style={Style.td}>John Smith</TableRowColumn>
        <TableRowColumn style={Style.td}>Employed</TableRowColumn>
        <TableRowColumn style={Style.td}>1</TableRowColumn>
        <TableRowColumn style={Style.td}>John Smith</TableRowColumn>
        <TableRowColumn style={Style.td}>Employed</TableRowColumn>

      </TableRow>
       <TableRow>
        <TableRowColumn style={Style.td}>1</TableRowColumn>
        <TableRowColumn style={Style.td}>John Smith</TableRowColumn>
        <TableRowColumn style={Style.td}>Employed</TableRowColumn>
        <TableRowColumn style={Style.td}>1</TableRowColumn>
        <TableRowColumn style={Style.td}>John Smith</TableRowColumn>
        <TableRowColumn style={Style.td}>Employed</TableRowColumn>
        <TableRowColumn style={Style.td}>1</TableRowColumn>
        <TableRowColumn style={Style.td}>John Smith</TableRowColumn>
        <TableRowColumn style={Style.td}>Employed</TableRowColumn>

      </TableRow>
       <TableRow>
        <TableRowColumn style={Style.td}>1</TableRowColumn>
        <TableRowColumn style={Style.td}>John Smith</TableRowColumn>
        <TableRowColumn style={Style.td}>Employed</TableRowColumn>
        <TableRowColumn style={Style.td}>1</TableRowColumn>
        <TableRowColumn style={Style.td}>John Smith</TableRowColumn>
        <TableRowColumn style={Style.td}>Employed</TableRowColumn>
        <TableRowColumn style={Style.td}>1</TableRowColumn>
        <TableRowColumn style={Style.td}>John Smith</TableRowColumn>
        <TableRowColumn style={Style.td}>Employed</TableRowColumn>

      </TableRow>
       <TableRow>
        <TableRowColumn style={Style.td}>1</TableRowColumn>
        <TableRowColumn style={Style.td}>John Smith</TableRowColumn>
        <TableRowColumn style={Style.td}>Employed</TableRowColumn>
        <TableRowColumn style={Style.td}>1</TableRowColumn>
        <TableRowColumn style={Style.td}>John Smith</TableRowColumn>
        <TableRowColumn style={Style.td}>Employed</TableRowColumn>
        <TableRowColumn style={Style.td}>1</TableRowColumn>
        <TableRowColumn style={Style.td}>John Smith</TableRowColumn>
        <TableRowColumn style={Style.td}>Employed</TableRowColumn>

      </TableRow>
    </TableBody>
  </Table>
);

export default TableExampleSimple;