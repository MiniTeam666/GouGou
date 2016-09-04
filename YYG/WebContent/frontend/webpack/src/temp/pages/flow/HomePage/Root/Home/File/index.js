class MyFile{
  constructor(){

  }
  isExit(url){
    return new Promise(function (resolve, reject){
      window.resolveLocalFileSystemURI("file://"+url, 
        function (fileEntry) {
          resolve(true);
        }
        , function(msg){
            resolve(false);
        });
    });
  }
  getAccesToken(){
    return new Promise(function (resolve, reject){
      app.link.getToken(function (result) {
       // app.alert(result.accessToken,null,"获取accesstoken");
       resolve(result.accessToken);
      }) 
    });
  }
  getFileFromRemote(url,filePath){
    var fileTransfer = new FileTransfer();
    var uri = encodeURI(url);
    // alert('AccessToken');
    // alert(AccessToken);
    // filePath ='/sdcard/icon.png';
    // fileTransfer.onprogress = function(progressEvent) {
    //     if (progressEvent.lengthComputable) {
    //       var percent = progressEvent.loaded / progressEvent.total;
    //       // if(percent > 0.9)
    //       alert(percent);
    //     } else {
          
    //     }
    // };
    return new Promise(function (resolve, reject){
      // document.addEventListener('deviceready',function())
      // alert(fileTransfer.download);
      fileTransfer.download(
        uri,
        filePath,
        function(entry) { 
                
          resolve(true);
        },
        function(error) {
          // alert(uri);
          // app.alert(error);
          reject(error);
        }
        
      
       
     );
    });
  }

  async getFilePath(url,fileName,get){
  	
  	
   
    var getSavePath = function(){
       return new Promise(function (resolve, reject){
        app.getAppDirectoryEntry(function(res){
          if(window.devicePlatform=="android"){
            window.savePath = res.sdcard;
          }else if(window.devicePlatform=="iOS"){
            window.savePath = res.documents;
          }
          resolve();
        })
       }
       );
    }
    await getSavePath();
    // if(savePath)window.savePath=savePath;
    let filePath = window.savePath + '/'+fileName;
    let bool = await this.isExit(filePath);

    if(!bool||get){
      // var accessToken = await this.getAccesToken();
      if(window.devicePlatform=="android"){
          await this.getFileFromRemote(url,filePath);
      }else if (window.devicePlatform=="iOS"){
          await this.getFileFromRemote(url,filePath);
      }

    }
    if(window.devicePlatform=="android"){
       return "file://"+filePath;
    }else if (window.devicePlatform=="iOS"){
       return filePath;
    }
    
    
  }

}
window.MyFile = MyFile;
