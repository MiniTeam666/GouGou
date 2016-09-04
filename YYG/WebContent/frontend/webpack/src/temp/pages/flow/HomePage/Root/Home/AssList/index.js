import React from 'react';
import Ass from './Ass';
import autobind from  'autobind-decorator';
import StyleCSS from './index.css';
import logo from './Ass/wechat.png';
String.prototype.replaceAll = function(s1,s2) { 
    return this.replace(new RegExp(s1,"gm"),s2); 
}
@autobind   
export default class AssList extends React.Component {

  constructor(props) {
    super(props);
    this.state={
      flag:false,
      status:false,
    }
    
  }

  async getLogo(){

      var {assList} = this.props;
      var list = assList.slice(0,12);
      let file = new window.MyFile;
      for(let i=0;i<list.length;i++){
        var ass = list[i];
        var icon = ass.icon.split('||')[0];

        var iconUrl = 'http://app.gzmpc.com/NewMobilePlatform/api/link/file?filepath='+icon;
         // iconUrl = 'https://link.gzmpc.com/uam/themes/metronic/statics/images/icons/flat/64_64/marketmanage.png';
        // var iconUrl = 'http://img3.imgtn.bdimg.com/it/u=807397672,137478881&fm=21&gp=0.jpg';
        let filepath = '';
       
        if(icon=='icons/service/service_default.png'){
          filepath = logo;
        }else{
           
           icon = icon.replaceAll('/','_');
           try{
              filepath = await file.getFilePath(iconUrl,icon,false);
           }catch(err){
              filepath =logo;
           }
           
        }
       
        ass.logo = filepath;
        // break;
      }
     
      this.setState({
        status:true,
      });
    

  }

  getAssList(assList){

    var change=function(start,end){
      let line = [];
      for(let i=start;i<end;i++ ){
        line.push(<Ass ass={assList[i]}/>);
      }
      return(
        <div className={StyleCSS.asslist}>
          {line}
        </div>
      );
    }
//
    let list =[];
    assList = assList.slice(0,12);
    var len = assList.length >4?4:assList.length;
    list.push(change(0,len));
    if(assList.length>4){
      var len2 = assList.length >8?8:assList.length;
      list.push(change(4,len2));
       if(assList.length>8){
          list.push(change(8,assList.length));
        }
    }

    return list;

  }
  

  componentDidMount(){
    window.getLogo = this.getLogo;
  }
  render() {
    var list =[];
    if(this.state.status){
      list = this.getAssList(this.props.assList);

    }
    return (
      <div>
      	{list}
      </div>
    );
  }
}