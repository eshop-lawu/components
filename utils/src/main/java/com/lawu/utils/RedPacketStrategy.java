package com.lawu.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author Leach
 * @date 2018/2/26
 */
public class RedPacketStrategy {

    /**
     * 最小分配倍数（以余额平均值为准，平均值=剩余金额/剩余数量）
     */
    private BigDecimal minMultiple;
    /**
     * 最大分配倍数（以余额平均值为准，平均值=剩余金额/剩余数量）
     */
    private BigDecimal maxMultiple;

    /**
     * 大额分配最小倍数（以总量平均值为准，平均值=总金额/总数量）
     */
    private BigDecimal bigAmountMinMultiple;

    /**
     * 大额分配最大倍数（以总量平均值为准，平均值=总金额/总数量）
     */
    private BigDecimal bigAmountMaxMultiple;

    /**
     * 大额分配最小百分百
     */
    private BigDecimal bigAmountMinPercent;

    /**
     * 大额分配最大百分百
     */
    private BigDecimal bigAmountMaxPercent;

    /**
     * 大额个数
     */
    private int bigAmountCount;

    public RedPacketStrategy(BigDecimal minMultiple, BigDecimal maxMultiple, int bigAmountCount, BigDecimal bigAmountMinMultiple, BigDecimal bigAmountMaxMultiple, BigDecimal bigAmountMinPercent, BigDecimal bigAmountMaxPercent) {
        this.minMultiple = minMultiple;
        this.maxMultiple = maxMultiple;
        this.bigAmountCount = bigAmountCount;
        this.bigAmountMinMultiple = bigAmountMinMultiple;
        this.bigAmountMaxMultiple = bigAmountMaxMultiple;
        this.bigAmountMinPercent = bigAmountMinPercent;
        this.bigAmountMaxPercent = bigAmountMaxPercent;
    }

    /**
     * 红包金额抽取（微信红包模式+指定数量大额红包）
     * @param totalMoney          总金额
     * @param totalCount          总数量
     * @param bigAmountSeed       大额随机种子，可指定为活动的创建时间
     * @param bigAmountSeedOffset 大额随机种子偏移量，可指定为活动id
     * @param remainMoney         当前剩余金额
     * @param remainCount         当前剩余数量
     */
    public BigDecimal drawRedPacket(BigDecimal totalMoney, int totalCount, long bigAmountSeed, long bigAmountSeedOffset, BigDecimal remainMoney, int remainCount) {


        // 未分配大额数量
        int unAllottedAmount = bigAmountCount;
        // 未分配大额金额
        BigDecimal unAllottedMoney = new BigDecimal(0);

        if (bigAmountCount > 0) {

            /**
             * 大额分配结果不保存到数据库
             * 因此每次分配需要重新计算，固定种子可保证同个活动大额保持一致
             */

            // 当前轮到分配的索引
            int currentIndex = totalCount - remainCount;

            // 大额索引、金额随机数。对于每个活动来说，种子一样，可保证本次活动每次大额随机一致
            Random bigIndexRandom = new Random(bigAmountSeed + bigAmountSeedOffset);
            Random bigMoneyRandom = new Random(bigAmountSeed + bigAmountSeedOffset);

            // 大额红包<索引, 金额>
            Map<Integer, BigDecimal> bigAmountList = new HashMap<>();
            // 总额平均
            BigDecimal totalAvg = totalMoney.divide(new BigDecimal(totalCount), 2, RoundingMode.HALF_UP);
            // 大额最小值
            BigDecimal bigAmountMin = totalAvg.multiply(bigAmountMinMultiple);
            // 大额最大值
            BigDecimal bigAmountMax = totalAvg.multiply(bigAmountMaxMultiple);
            // 大额实际可达到最小值
            BigDecimal bigAmountMinReal = totalMoney.multiply(bigAmountMinPercent).setScale(2, RoundingMode.HALF_UP);
            // 大额实际可达到最大值
            BigDecimal bigAmountMaxReal = totalMoney.multiply(bigAmountMaxPercent).setScale(2, RoundingMode.HALF_UP);

            bigAmountMin = bigAmountMin.compareTo(bigAmountMinReal) >= 0 ? bigAmountMinReal : bigAmountMin;
            bigAmountMax = bigAmountMax.compareTo(bigAmountMaxReal) >= 0 ? bigAmountMaxReal : bigAmountMax;

            for (int i = 0; i < bigAmountCount; ) {
                // 随机大额索引
                int bigAmountIndex = bigIndexRandom.nextInt(totalCount);
                if (bigAmountList.containsKey(bigAmountIndex)) {
                    continue;
                }
                // 随机大额金额
                BigDecimal randomBigMoney = randomBigDecimal(bigMoneyRandom, bigAmountMin, bigAmountMax);
                bigAmountList.put(bigAmountIndex, randomBigMoney);

                //
                unAllottedMoney = unAllottedMoney.add(randomBigMoney);
                if (currentIndex >= bigAmountIndex) {
                    unAllottedAmount--;
                    unAllottedMoney = unAllottedMoney.subtract(randomBigMoney);

                }
                i++;

            }

            // 本次是否抽中大额
            BigDecimal currentBigMoney = bigAmountList.get(currentIndex);
            if (currentBigMoney != null) {
                return currentBigMoney;
            }
        }
        /**
         * 以下为排除大额后的正常分配过程
         * 大部分情况执行该流程
         */
        // 实际剩余的可分配数量，排除未分配大额
        int canAllottedAmount = remainCount - unAllottedAmount;
        // 实际剩余的可分配余额，排除未分配大额
        BigDecimal canAllottedMoney = remainMoney.subtract(unAllottedMoney);

        // 最后一个
        if (canAllottedAmount == 1) {
            return canAllottedMoney;
        }
        // 当前可分配平均值
        BigDecimal avg = canAllottedMoney.divide(new BigDecimal(canAllottedAmount), 2, RoundingMode.HALF_UP);

        Random random = new Random();

        BigDecimal result = randomBigDecimal(random, avg.multiply(minMultiple), avg.multiply(maxMultiple));

        return result.compareTo(BigDecimal.ZERO) > 0 ? result : BigDecimal.ZERO;

    }

    private BigDecimal randomBigDecimal(Random random, BigDecimal min, BigDecimal max) {

        // 随机值
        BigDecimal money = new BigDecimal(random.nextDouble()).multiply(max.subtract(min)).add(min);

        return money.setScale(2, RoundingMode.HALF_UP);
    }
}
