package com.yyg;

import com.yyg.service.Manager;
import com.yyg.service.UserService;

public class ServiceManager {
	
	public static final int USER_MANAGER = 0;
	
	public static final int MANAGER_COUNT = USER_MANAGER + 1;
	
	private static ServiceManager instance;
	
	private Manager[] managers;
	
	private Object[] mgsLocks;
	
	private ServiceManager(){
		managers = new Manager[MANAGER_COUNT];
		mgsLocks = new Object[MANAGER_COUNT];
		for(int i = 0; i < MANAGER_COUNT; i++){
			mgsLocks[i] = new Object();
		}
	}
	
	public static ServiceManager getInstance(){
		if(instance == null){
			instance = new ServiceManager();
		}
		return instance;
	}
	
	public Manager getManager(int name){
		Manager manager = managers[name];
		if(manager == null){
			//lock
			synchronized(mgsLocks[name]){
				
				//double check
				manager = managers[name];
				if(manager != null)
					return manager;
				
				//create
				switch(name){
					case USER_MANAGER:
						manager = new UserService();
				}
				
				//add to cache
				managers[name] = manager;
			}
		}
		return manager;
	}

}
