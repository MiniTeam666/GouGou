package com.yyg;

import com.yyg.service.OrderService;
import com.yyg.service.ProductService;
import com.yyg.service.Service;
import com.yyg.service.UserService;

public class ServiceManager {
	
	public static final int User_Service = 0;
	
	public static final int Product_Service = User_Service + 1;

	public static final int Order_Service = Product_Service + 1;
	
	public static final int Service_Count = Order_Service + 1;


	private volatile static ServiceManager instance;
	
	private Service[] services;
	
	private Object[] serviceLocks;
	
	private ServiceManager(){
		services = new Service[Service_Count];
		serviceLocks = new Object[Service_Count];
		for(int i = 0; i < Service_Count; i++){
			serviceLocks[i] = new Object();
		}
	}
	
	public static ServiceManager getInstance(){
		if(instance == null){
			instance = new ServiceManager();
		}
		return instance;
	}
	
	public Service getService(int name){
		Service service = services[name];
		if(service == null){
			//lock
			synchronized(serviceLocks[name]){
				
				//double check
				service = services[name];
				if(service != null)
					return service;
				
				//create
				switch(name){
					case User_Service:
						service = new UserService();
						break;
					case Product_Service:
						service = new ProductService();
						break;
					case Order_Service:
						service = new OrderService();
						break;
					default:
						break;
				}
				
				//add to cache
				services[name] = service;
			}
		}
		return service;
	}

}
