package com.yyg;

import com.yyg.service.Service;
import com.yyg.service.UserService;

public class ServiceManager {
	
	public static final int User_Service = 0;
	
	public static final int Service_Count = User_Service + 1;
	
	private static ServiceManager instance;
	
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
				}
				
				//add to cache
				services[name] = service;
			}
		}
		return service;
	}

}
