package vn.luckybets.broadcastsmsbanking.utils;

import java.text.DecimalFormat;

import vn.luckybets.broadcastsmsbanking.R;

public class TextUtils {


    public static String getStringCoin(double currentMoney) {
        DecimalFormat formatter = new DecimalFormat("#,###.##");


        return formatter.format(currentMoney) + " đ";
    }

    public static String getStringCoin(int currentMoney)

    {
        DecimalFormat formatter = new DecimalFormat("#,###.##");


        return formatter.format(currentMoney) + " đ";
    }

    public static String getStringCoin(long currentMoney)

    {
        DecimalFormat formatter = new DecimalFormat("#,###.##");


        return formatter.format(currentMoney) + " đ";
    }

}
