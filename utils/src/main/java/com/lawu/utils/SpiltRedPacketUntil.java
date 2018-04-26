package com.lawu.utils;

import java.text.DecimalFormat;


public class SpiltRedPacketUntil {  
	
	private SpiltRedPacketUntil(){}
	
    //红包最小值  
    private static final double MINMONEY = 0.2;  
    //红包最大值  
    private static final double MAXMONEY = 200;  
    
    /**
	 * 这里为了避免某一个红包占用大量资金，我们需要设定非最后一个红包的最大金额，我们把他设置为红包金额平均值的N倍；
	 */
	private static final double TIMES = 1.8;
    
    private static DecimalFormat format = new DecimalFormat(".##");
    
    
    /**
	 * 拆分红包
	 * 
	 * @param money
	 *            ：红包总金额
	 * @param subMon
	 *            ：剩余金额
	 * @param count
	 *            ：个数
	 * @param getCount
	 *            ：已领个数
	 * @return
	 */
    private static double splitRedPackets(double money ,double subMon , double count,double getCount) {
		
		// 每个红包最大的金额为平均金额的Times 倍
		double max = (double) (money * TIMES / count);
		
		//max = max > MAXMONEY ? MAXMONEY : max;
		// 分配红包
		double one = randomRedPacket(subMon, MINMONEY, max, count - getCount);
			
		return one;
	}
  
  
    /**
	 * 红包 合法性校验
	 * 
	 * @param money
	 * @param count
	 * @return
	 */
	private static boolean isRight(double money, double count) {
		double avg = money / count;
		// 小于最小金额
		if (avg < MINMONEY) {
			return false;
			// 大于最大金额
		} else if (avg > MAXMONEY) {
			return false;
		}
		return true;
	}
  
	/**
	 * 随机分配一个红包
	 * 
	 * @param money
	 * @param minS
	 *            :最小金额
	 * @param maxS
	 *            ：最大金额(每个红包的默认Times倍最大值)
	 * @param count
	 * @return
	 */
	private static double randomRedPacket(double money, double minS, double maxS, double count) {
		// 若是只有一个，直接返回红包
		if (count == 1) {
			return money;
		}
		// 若是最小金额红包 == 最大金额红包， 直接返回最小金额红包
		if (minS == maxS) {
			return minS;
		}
		// 校验 最大值 max 要是比money 金额高的话？ 去 money 金额
		double max = maxS > money ? money : maxS;
		// 随机一个红包 = 随机一个数* (金额-最小)+最小
		double one = ((double)(Math.random() * (max - minS) + minS));
		// 剩下的金额
		double moneyOther = money - one;
		// 校验这种随机方案是否可行，不合法的话，就要重新分配方案
		if (isRight(moneyOther, count - 1)) {
			return one;
		} else {
			// 重新分配
			double avg = moneyOther / (count - 1);
			// 本次红包过大，导致下次的红包过小；如果红包过大，下次就随机一个小值到本次红包金额的一个红包
			if (avg < MINMONEY) {
				// 递归调用，修改红包最大金额
				return randomRedPacket(money, minS, one, count);

			} else if (avg > MAXMONEY) {
				// 递归调用，修改红包最小金额
				return randomRedPacket(money, one, maxS, count);
			}
		}
		return one;
	}  
  
    /** 
     * 分红包 
     * @param totalMoney  总金额
     * @param subMoney  剩余金额
     * @param count 总个数
     * @param getCount 已领个数  
     * @return 
     */  
    public static Double spiltRedPackets(double totalMoney, double subMoney,int count,int getCount) {  
        
    	 double  money = splitRedPackets(totalMoney,subMoney, count,getCount);
		 money= Double.parseDouble(format.format(money));  
        
        return money;  
    }
    
    
 /*public static void main(String[] args) {
		
		double mon=400 , tot =0;
		
		for(double i =0 ; i<141 ;i++){
			
			 double  money =splitRedPackets(400,mon, 141,i);
			 money= Double.parseDouble(format.format(money));  
			 tot=Double.parseDouble(format.format(tot +money));  
			 mon=Double.parseDouble(format.format(mon -money));  
			 System.out.println(money);
		}
		System.out.println("总金额："+tot);
	}*/
} 
