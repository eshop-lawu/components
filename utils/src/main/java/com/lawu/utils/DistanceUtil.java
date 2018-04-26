package com.lawu.utils;

/**
 * @author meishuquan
 * @date 2017/4/5.
 */
public class DistanceUtil {
	
	/**
	 *  地球半径
	 */
	public static final int RADIUS = 6378137;
	
    private DistanceUtil(){}

    /**
     * 获取两个经纬度之间的距离
     *
     * @param longitude1 开始经度
     * @param latitude1  开始纬度
     * @param longitude2 结束经度
     * @param latitude2  结束纬度
     * @return
     */
    public static int getDistance(Double longitude1, Double latitude1, Double longitude2, Double latitude2) {
        double longDiff; // 保存二个经度的差值
        double latDiff; // 保存二个纬度的差值
        double longDiffSin; // 经度差的sin
        double latDiffSin; // 纬度差的sin
        double distance; // 距离
        latitude1 = latitude1 * Math.PI / 180.0;
        latitude2 = latitude2 * Math.PI / 180.0;
        latDiff = latitude1 - latitude2;
        longDiff = (longitude1 - longitude2) * Math.PI / 180.0;
        longDiffSin = Math.sin(longDiff / 2.0);
        latDiffSin = Math.sin(latDiff / 2.0);
        distance = 2
                * RADIUS
                * Math.asin(Math.sqrt(latDiffSin * latDiffSin
                + Math.cos(latitude1) * Math.cos(latitude2)
                * longDiffSin * longDiffSin));
        return (int) Math.round(distance);
    }
    
}
