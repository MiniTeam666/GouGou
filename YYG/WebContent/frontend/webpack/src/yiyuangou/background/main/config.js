import routes from './Root/route_config';
function getRoutes(routes,path){

	if(routes  instanceof Array){
		let array = [];
		for(let i=0;i<routes.length;i++){
			array=array.concat(change(routes[i]));
		}
		return array;
	}
	if(!path){
		return routes;
	}else{
		return routes.path;
	}
	
}

module.exports={
	routes:
	tree:[]
}