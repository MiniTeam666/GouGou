package com.yyg.utils;

import java.util.Comparator;

import com.yyg.AppConstant;
import com.yyg.model.Lottery;
import com.yyg.model.Product;

public class ProductSortUtils {
	
	public static enum LotterySortType{
		
		Hot(0),Lastest(1),RemainCnt(2),Values(3);
		
		private int type;
		
		private LotterySortType(int type){
			this.type = type;
		}
		
		public int getInt(){
			return type;
		}
		
		public static LotterySortType valueOf(int type){
			switch(type){
			case 0 :
				return Hot;
			case 1 :
				return Lastest;
			case 2 :
				return RemainCnt;
			case 3 :
				return Values;
			default :
				return null; 
			}
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

	public static class ProductValueComparator implements Comparator<Lottery>{

		@Override
		public int compare(Lottery o1, Lottery o2) {
			return o1.product.price >= o2.product.price ? 1 : -1;
		}
	}

	public static class ProductHotComparator implements Comparator<Lottery>{

		@Override
		public int compare(Lottery o1, Lottery o2) {
			String[] records1 = o1.buyRecord.split(AppConstant.PRODUCT_LOTTERY_BUY_RECORD_SPLIT_CHAR);
			String[] records2 = o2.buyRecord.split(AppConstant.PRODUCT_LOTTERY_BUY_RECORD_SPLIT_CHAR);
			int o1Value = getRecordValue(records1);
			int o2Value = getRecordValue(records2);
			return o1Value >= o2Value ? 1 : -1;
		}

		public int getRecordValue(String[] record){
			long now = System.currentTimeMillis();
			int ret = 0;

			if(record == null || record.length <= 0)
				return ret;

			for(int i = 0 ; i < record.length; i++){
				String[] kv = record[i].split(AppConstant.PRODUCT_LOTTERY_RECORD_SPLIT_CHAR);
				long buyTime = Long.valueOf(kv[0]);
				int time = Integer.valueOf(kv[1]);
				if(now - buyTime > AppConstant.DEFAULT_PRODUCT_HOT_CYCLE)
					break;
				ret += time;
			}
			return ret;
		}
	}

}
