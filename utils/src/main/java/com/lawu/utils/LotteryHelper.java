package com.lawu.utils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author Leach
 * @date 2018/1/15
 */
public class LotteryHelper {

    /**
     *
     * @param probs 各奖品概率，key-奖品标识，value-奖品概率
     * @param precision 精度，概率小数部分最多位数
     * @return 抽到的奖品ID。抽不到则为null
     */
    public static Object draw(Map<Object, Double> probs, int precision) {
        if (probs.size() < 1) {
            return null;
        }

        // 总概率
        int totalProb = 0;
        // 各个奖品概率转为整数
        Map<Object, Integer> intProbs = new HashMap<>();
        for (Map.Entry<Object, Double> prob : probs.entrySet()) {
            // 当前奖品概率，取整
            int currentProb = new BigDecimal(prob.getValue()).multiply(new BigDecimal(10 ^ precision)).intValue();
            intProbs.put(prob.getKey(), currentProb);
            totalProb += currentProb;
        }

        // 最大随机数
        int maxRandomNum = 100 * (10 ^ precision);
        if (totalProb > maxRandomNum) {
            throw new IllegalArgumentException("奖品总概率大于100%");
        }
        Random random = new Random();
        int randomNumber = random.nextInt(maxRandomNum);
        int range = 0;
        for (Map.Entry<Object, Integer> intProb : intProbs.entrySet()) {
            range += intProb.getValue();
            if (randomNumber < range) {
                return intProb.getKey();
            }
        }
        return null;
    }
}
