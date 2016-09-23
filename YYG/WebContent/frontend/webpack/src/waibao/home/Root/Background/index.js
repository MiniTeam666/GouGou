import React from 'react';
import './index.css';  
import Tag from './Tag';
import Event from 'company/util/lib/Event';
export default class Background extends React.Component{
  render(){
    return(
       <div id="home_logo" >
		<Tag tag={{
			top:0.7,
			left:0.12,
			pop:{
				url:'http://192.168.1.101/demo/image/cache/catalog/demo/product/product_9/product9_1-200x200.jpg',
				detail:'玻璃茶几',
				link:'http://192.168.1.101/demo/index.php?route=product/product&product_id=43'
			},
		}}/>
		<Tag tag={{
			top:0.13,
			left:0.735,
			pop:{
				url:'http://192.168.1.101/demo/image/cache/catalog/demo/product/product_1/product1_1-228x228.jpg',
				detail:'玻璃茶几',
				link:'http://192.168.1.101/demo/index.php?route=product/product&product_id=42'
			},
		}}/>
		<Tag tag={{
			top:0.13,
			left:0.67,
			pop:{
				url:'http://192.168.1.101/demo/image/cache/catalog/demo/product/product_1/product1_1-228x228.jpg',
				detail:'玻璃茶几',
				link:'http://192.168.1.101/demo/index.php?route=product/product&product_id=42'
			},
		}}/>
	   </div>
    )
  }//

  componentDidMount(){
  	this.init();
  }
   logo_redirect(url){
		location.href = url;
   }
   init(){
	   	$(document).ready(function(){
			window.onresize = function(){  
	            var width = $('#root').width();
				var pixel = 678/998;
				$('#home_logo').width(width);
				$('#home_logo').height(width*pixel);
				let event = new Event ;
				event.dispatch('background/tag/resetPosition',{width:width,pixel:pixel});
	        }  
	        $(window).resize();
		})
   }
	
	imageTagOnMouseOver(){

	}
	imageTagOnMouseOut(){
		
	}
}  