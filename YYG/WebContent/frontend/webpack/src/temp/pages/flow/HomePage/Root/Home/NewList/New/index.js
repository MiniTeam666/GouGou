import React from 'react';
import Paper from 'material-ui/lib/paper';
import Tag from './Tag';
import autobind from 'autobind-decorator';
const style = {
  width: '90%',
  marginTop:20,
  marginLeft:'5%',
  textAlign: 'center',
  display: 'inline-block',
};
@autobind
export default class New extends React.Component {

  constructor(props) {
    super(props);
    
  }

  handleClick(event){
    // alert(23);
    this.openFile();
  }

  handleLink(event){
    location.href=this.props.newObj.src;
  }

  async openFile(){
    let File = window.MyFile;
    let file = new File();
    window.refresh(true);
    var newObj = this.props.newObj;
    let filePath = await file.getFilePath(
      newObj.src,
      newObj.id,
      newObj.bool
    );
    window.refresh(false);
    app.openFile(filePath);
  }
 
  
  render() {
    
    var {type} = this.props.newObj;
    var hanle ='';
    if(type=='file'){
      hanle = this.handleClick;
    }else{
      hanle = this.handleLink;
    }
    return (
      <Paper style={style} zDepth={2} rounded={false}>
        <div style={{margin:12,textAlign:'left'}}>
          <div style={{textAlign:'left',fontSize:18,color:'#888'}} onTouchTap={hanle}>
             {this.props.newObj.content}
          </div>
        </div>
      </Paper>
    );
    
  }
}