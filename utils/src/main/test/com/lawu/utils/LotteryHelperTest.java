package com.lawu.utils;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.lawu.utils.LotteryHelper;

/**
 * @author Leach
 * @date 2018/1/15
 */
public class LotteryHelperTest {

    @Test
    public void draw() {
        Map<Object, Double> probs = new HashMap<>();
        probs.put(1, 10d);
        probs.put(2, 15d);
        probs.put(3, 20d);
        probs.put(4, 25d);
        probs.put(5, 0.1d);


        Map<Object, Integer> result = new HashMap<>();
        for (int i = 0; i < 100000; i++) {
            Object key = LotteryHelper.draw(probs, 1);
            if (result.get(key) == null) {
                result.put(key, 0);
            }
            result.put(key, result.get(key) + 1);
        }
        System.out.println(result.get(1));
        System.out.println(result.get(2));
        System.out.println(result.get(3));
        System.out.println(result.get(4));
        System.out.println(result.get(5));
        System.out.println("谢谢惠顾:" + result.get(null));


        probs.put(6, 100d);
        try {
            LotteryHelper.draw(probs, 1);
            Assert.fail();
        } catch (IllegalArgumentException e) {
        }
    }
}
