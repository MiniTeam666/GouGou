package com.yyg.utils;

import java.util.Comparator;

import com.yyg.model.Lottery;

public class ProductSortUtils {
	
	public static enum LotterySortType{
		
		Hot(0),Lastest(1),RemainCnt(2),values(3);
		
		private int type;
		
		private LotterySortType(int type){
			this.type = type;
		}
		
		public int getInt(){
			return type;
		}
	}
	
	public static class ProductLastestComparator implements Comparator<Lottery>{

		@Override
		public int compare(Lottery a, Lottery b) {
			int ret = a.createTime > b.createTime ? 1 : -1;
			if(a.createTime == b.createTime)
				ret = 0;
			return ret;
		}

	}
	
	public static class ProductRemainCntComparator implements Comparator<Lottery>{

		@Override
		public int compare(Lottery a, Lottery b) {
			int ret = a.remainCountOfQulification > b.remainCountOfQulification ? 1 : -1;
			if(a.remainCountOfQulification == b.remainCountOfQulification)
				ret = 0;
			return ret;
		}

	}

}
