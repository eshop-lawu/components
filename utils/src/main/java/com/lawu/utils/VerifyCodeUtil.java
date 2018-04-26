package com.lawu.utils;

import java.awt.*;
import java.util.Random;

/**
 * @author meishuquan
 * @date 2017/3/28.
 */
public class VerifyCodeUtil {

    // 验证码图片的宽度。
    private static final int WIDTH = 120;
    // 验证码图片的高度。
    private static final int HEIGHT = 40;
    // 验证码字符个数
    private static final int CODE_COUNT = 4;
    // 字体高度
    private static final int FONT_HEIGHT = HEIGHT - 2;
    private static final int X = WIDTH / (CODE_COUNT + 1);
    private static final int CODE_Y = HEIGHT - 4;
    private static final char[] codeSequence = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r', 's','t','u','v','w','x','y','z',
            'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z',};

    private VerifyCodeUtil() {
    }

    public static String getVerifyCode(Graphics2D g) {
        // 将图像填充为白色
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        // 创建字体，字体的大小应该根据图片的高度来定。
        Font font = new Font("Fixedsys", Font.PLAIN, FONT_HEIGHT);
        // 设置字体。
        g.setFont(font);
        // 画边框。
        g.setColor(Color.white);
        g.drawRect(0, 0, WIDTH - 1, HEIGHT - 1);
        // 随机产生160条干扰线，使图象中的认证码不易被其它程序探测到。
        g.setColor(Color.BLACK);
        // 创建一个随机数生成器类
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            int x = random.nextInt(WIDTH);
            int y = random.nextInt(HEIGHT);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            g.drawLine(x, y, x + xl, y + yl);
        }
        // randomCode用于保存随机产生的验证码，以便用户登录后进行验证。
        StringBuilder randomCode = new StringBuilder();
        int red, green, blue;
        // 随机产生codeCount数字的验证码。
        for (int i = 0; i < CODE_COUNT; i++) {
            // 得到随机产生的验证码数字。
            String strRand = String.valueOf(codeSequence[random.nextInt(62)]);
            // 产生随机的颜色分量来构造颜色值，这样输出的每位数字的颜色值都将不同。
            red = random.nextInt(255);
            green = random.nextInt(255);
            blue = random.nextInt(255);
            // 用随机产生的颜色将验证码绘制到图像中。
            g.setColor(new Color(red, green, blue));
            g.drawString(strRand, (i + 1) * X, CODE_Y);
            // 将产生的四个随机数组合在一起。
            randomCode.append(strRand);
        }
        return randomCode.toString();
    }
}
