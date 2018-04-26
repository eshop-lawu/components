package com.lawu.utils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

import org.junit.Assert;
import org.junit.Test;

import com.lawu.utils.RedPacketStrategy;

/**
 * @author Leach
 * @date 2018/2/26
 */
public class RedPacketStrategyTest {

    private RedPacketStrategy redPacketStrategy = new RedPacketStrategy(
            new BigDecimal(0.2),
            new BigDecimal(1.8),
            3,
            new BigDecimal(10),
            new BigDecimal(20),
            new BigDecimal(0.1),
            new BigDecimal(0.3));


    private CountDownLatch countDownLatch;


    @Test
    public void drawRedPacket() {
        int total = 1;

        int totalAmount = 20;
        BigDecimal totalMoney = new BigDecimal(100);
        countDownLatch = new CountDownLatch(total);
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < total; i++) {
            new Thread(() -> {
                System.out.println();
                Assert.assertEquals(0, draw(new Date().getTime(), totalAmount, totalMoney).compareTo(totalMoney));
                countDownLatch.countDown();
            }).start();
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(System.currentTimeMillis() - startTime);
    }

    private BigDecimal draw(long seed, int totalAmount, BigDecimal totalMoney) {
        int remainAmount = totalAmount;
        BigDecimal remainMoney = new BigDecimal(totalMoney.toString());
        BigDecimal resultTotal = new BigDecimal(0);

        for (int i = 0; i < totalAmount; i++) {
            BigDecimal money = redPacketStrategy.drawRedPacket(totalMoney, totalAmount, seed, 100, remainMoney, remainAmount);
            System.out.printf("%s: %s \n", i, money.toString());
            remainAmount--;
            remainMoney = remainMoney.subtract(money);
            resultTotal = resultTotal.add(money);
        }
        System.out.printf("resultTotal: %s \n", resultTotal);
        return resultTotal;
    }

    /**
     * 大额分配概率测试
     */
    @Test
    public void random() {
        int totalCount = 100;
        Map<Integer, Integer> map1 = new LinkedHashMap<>();
        Map<Integer, Integer> map2 = new LinkedHashMap<>();
        Map<Integer, Integer> map3 = new LinkedHashMap<>();
        for (int i = 0; i < 10000; i++) {
            Random random = new Random(new Date().getTime());
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            setMap(map1, random.nextInt(totalCount));
            setMap(map2, random.nextInt(totalCount));
            setMap(map3, random.nextInt(totalCount));
        }

        for (int i = 0; i < totalCount; i++) {
            System.out.printf("%s: %s %s %s \n", i, map1.get(i), map2.get(i), map3.get(i));
        }
    }

    private void setMap(Map<Integer, Integer> map, int r) {
        Integer count = map.get(r);
        if (count == null) {
            count = 0;
        }
        count++;
        map.put(r, count);
    }

}
