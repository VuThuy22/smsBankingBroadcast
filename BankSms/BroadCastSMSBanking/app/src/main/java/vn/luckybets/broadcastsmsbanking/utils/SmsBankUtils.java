package vn.luckybets.broadcastsmsbanking.utils;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.List;

import vn.luckybets.broadcastsmsbanking.common.Cts;
import vn.luckybets.broadcastsmsbanking.database.BankHelper;
import vn.luckybets.broadcastsmsbanking.model.Bank;
import vn.luckybets.broadcastsmsbanking.model.SmsBank;

public class SmsBankUtils {
    private static final String TAG = "SmsBankUtils";

    // helper.addBank(new Bank(0L, "Liên việt PostBank", "LPB", 0.0));
//                helper.addBank(new Bank(0L, "VietinBank", "VietinBank", 0.0));
//                helper.addBank(new Bank(0L, "TPBank", "TPBank", 0.0));
//                helper.addBank(new Bank(0L, "Sacombank", "Sacombank", 0.0));
//                helper.addBank(new Bank(0L, "Vietcombank", "Vietcombank", 0.0));
//    long i = helper.addBank(new Bank(0L, "BIDV", "BIDV", 0.0));
    public static void handingSms(Context context, String message, Bank bank) {

        SmsBank smsBank = null;
        switch (bank.getName()) {
            case Cts.P_LPB:
                smsBank = getSmsLVP(message, bank.getPhone());
                break;
            case Cts.P_VietinBank:
                smsBank = getSmsVietinBank(message, bank.getPhone());
                break;
            case Cts.P_TPBank:
                smsBank = getSmsTPBank(message, bank.getPhone());
                break;
            case Cts.P_Sacombank:
                smsBank = getSmsSacombank(message, bank.getPhone());
                break;
            case Cts.P_Vietcombank:
                smsBank = getSmsVietcomBank(message, bank.getPhone());
                break;
            case Cts.P_BIDV:
                smsBank = getSmsBIDV(message, bank.getPhone());
                break;
        }

        if (smsBank != null) {
            Log.e(TAG, "handingSms: " + smsBank.toString());
            Intent intent = new Intent(Cts.ACTION_SMS);
            intent.putExtra(Cts.OBJEC_SMS, smsBank);
            intent.putExtra(Cts.OBJEC_BANK, bank);
            context.sendBroadcast(intent);
        }
    }


    private static SmsBank getSmsLVP(String message, String phone) {
//      LPB: 06/07/18 17:57 TK 024582970001: -22,000VND. SO DU: 506,045VND. ND: NAP_LUCKYBETS_01648681489
        SmsBank smsBank = new SmsBank();
        smsBank.setFullconte(message);
        smsBank.setPhone(phone);

        try {

            String[] splitString = message.split("\\.");

            double moneyTrans = 0.0;
            String content = "";
            String account = "";
            double totalBalance = 0.0;


            for (int i = 0; i < splitString.length; i++) {
                String string = splitString[i];
                if (string.contains("DU:")) {
                    totalBalance = Double.parseDouble(string.split(":")[1].replace("VND", "").trim().replace(",", "").replaceAll("[^\\d.]", ""));
                }
                if (string.contains("ND:")) {
                    content = string.split(":")[1].trim();
                }
                Log.e(TAG, "getSmsLVP: " + content.toUpperCase());
                if (content.toUpperCase().contains("NAP_LUCKYBETS_")) {
                    account = content.toUpperCase().replace("NAP_LUCKYBETS_", "").trim();

                }

                if (string.contains("TK")) {
                    String[] s1 = (string.split(" "));
                    moneyTrans = Double.parseDouble(s1[s1.length - 1].trim().replace("VND", "").replace(",", "").replaceAll("[^\\d.]", ""));
                    if (s1[s1.length - 1].contains("-")) {
                        moneyTrans = -moneyTrans;
                    }
                }

            }
            if (account.length() > 0 && moneyTrans > 0.0) {
                smsBank.setAction(1);
            } else {
                smsBank.setAction(0);
            }

            smsBank.setFullconte(message);
            smsBank.setContent(content);
            smsBank.setAccount(account);
            smsBank.setBalance(totalBalance);
            smsBank.setMoneytrans(moneyTrans);
            smsBank.setStatus(-2);
            Log.e(TAG, "getSmsLVP: " + smsBank.toString());
            return smsBank;
        } catch (Exception e) {
            e.printStackTrace();
        }


        return null;
    }

    public static SmsBank getSmsVietinBank(String message, String phone) {
//        VietinBank:09/06/2018 08:48|TK:109002037476|GD:+4,000,000VND|SDC:6,873,566VND|ND:NAP_LUCKYBETS_01648681489


        SmsBank smsBank = new SmsBank();

        try {

            String[] splitString = message.split("\\|");

            double moneyTrans = 0.0;
            String content = "";
            String account = "";
            double totalBalance = 0.0;


            for (int i = 0; i < splitString.length; i++) {
                String string = splitString[i];
                if (string.contains("SDC:")) {
                    totalBalance = Double.parseDouble(string.split(":")[1].replace("VND", "").trim().replace(",", "").replaceAll("[^\\d.]", ""));
                }
                if (string.contains("ND:")) {
                    content = string.split(":")[1].trim();
                }
                Log.e(TAG, "getSmsVietinBank: " + content.toUpperCase());
                if (content.toUpperCase().contains("NAP_LUCKYBETS_")) {
                    account = content.toUpperCase().replace("NAP_LUCKYBETS_", "").trim();
                }

                if (string.contains("GD:")) {
                    String[] s1 = (string.split(":"));
                    moneyTrans = Double.parseDouble(s1[s1.length - 1].trim().replace("VND", "").replace(",", "").replaceAll("[^\\d.]", ""));
                    if (s1[s1.length - 1].contains("-")) {
                        moneyTrans = -moneyTrans;
                    }
                }

            }
            if (account.length() > 0 && moneyTrans > 0.0) {
                smsBank.setAction(1);
            } else {
                smsBank.setAction(0);
            }
            smsBank.setFullconte(message);
            smsBank.setPhone(phone);
            smsBank.setFullconte(message);
            smsBank.setContent(content);
            smsBank.setAccount(account);
            smsBank.setBalance(totalBalance);
            smsBank.setMoneytrans(moneyTrans);
            smsBank.setStatus(-2);
            Log.e(TAG, "getSmsVietinBank: " + smsBank.toString());
            return smsBank;
        } catch (Exception e) {
            e.printStackTrace();
        }


        return null;
    }

    public static SmsBank getSmsBIDV(String message, String phone) {
//        TK 11120034147016 tai BIDV -3,000,000VND vao 15:03 13/07/2018. So du: 79,000,000VND. ND: NAP_LUCKYBETS_01648681489
//
        SmsBank smsBank = new SmsBank();

        try {

            String[] splitString = message.split("\\.");

            double moneyTrans = 0.0;
            String content = "";
            String account = "";
            double totalBalance = 0.0;


            for (int i = 0; i < splitString.length; i++) {
                String string = splitString[i];
                if (string.contains("du")) {
                    totalBalance = Double.parseDouble(string.split("u")[1].replace("VND", "").trim().replace(",", "").replaceAll("[^\\d.]", ""));
                }
                if (string.contains("ND:")) {
                    content = string.split(":")[1].trim();
                }
                if (content.toUpperCase().contains("NAP_LUCKYBETS_")) {
                    account = content.toUpperCase().replace("NAP_LUCKYBETS_", "").trim();

                }

                if (string.contains("TK")) {
                    String[] s1 = (string.split(" "));
                    moneyTrans = Double.parseDouble(s1[s1.length - 4].trim().replace("VND", "").replace(",", "").replaceAll("[^\\d.]", ""));
                    if (s1[s1.length - 4].contains("-")) {
                        moneyTrans = -moneyTrans;
                    }
                }

            }
            if (account.length() > 0 && moneyTrans > 0.0) {
                smsBank.setAction(1);
            } else {
                smsBank.setAction(0);
            }
            smsBank.setFullconte(message);
            smsBank.setPhone(phone);
            smsBank.setFullconte(message);
            smsBank.setContent(content);
            smsBank.setAccount(account);
            smsBank.setBalance(totalBalance);
            smsBank.setMoneytrans(moneyTrans);
            smsBank.setStatus(-2);
            Log.e(TAG, "getSmsBIDV: " + smsBank.toString());
            return smsBank;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static SmsBank getSmsVietcomBank(String message, String phone) {
        //So du TK VCB 11120034147016 thay doi +9,000,000 VND. So du 9,000,000 VND. Ref NAP_LUCKYBETS_01648681489
        SmsBank smsBank = new SmsBank();

        try {

            String[] splitString = message.split("\\.");

            double moneyTrans = 0.0;

            String content = "";
            String account = "";
            double totalBalance = 0.0;


            for (int i = 0; i < splitString.length; i++) {
                String string = splitString[i];
                if (string.contains("du")) {
                    totalBalance = Double.parseDouble(string.split("u")[1].replace("VND", "").trim().replace(",", "").replaceAll("[^\\d.]", ""));
                }
                if (string.contains("ND:")) {
                    content = string.split(":")[1].trim();
                }
                if (content.toUpperCase().contains("NAP_LUCKYBETS_")) {
                    account = content.toUpperCase().replace("NAP_LUCKYBETS_", "").trim();

                }

                if (string.contains("So du TK")) {
                    String[] s1 = (string.split(" "));
                    moneyTrans = Double.parseDouble(s1[s1.length - 2].trim().replace("VND", "").replace(",", "").replaceAll("[^\\d.]", ""));
                    if (s1[s1.length - 2].contains("-")) {
                        moneyTrans = -moneyTrans;
                    }
                }

            }
            if (account.length() > 0 && moneyTrans > 0.0) {
                smsBank.setAction(1);
            } else {
                smsBank.setAction(0);
            }
            smsBank.setFullconte(message);
            smsBank.setPhone(phone);
            smsBank.setFullconte(message);
            smsBank.setContent(content);
            smsBank.setAccount(account);
            smsBank.setBalance(totalBalance);
            smsBank.setMoneytrans(moneyTrans);
            smsBank.setStatus(-2);
            Log.e(TAG, "getSmsVCB: " + smsBank.toString());
            return smsBank;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static SmsBank getSmsTPBank(String message, String phone) {
        // (TPBank): 16/07/18;13:16 TK: xxxx5316901 PS:+1.000.000VND SD: 1.779.234VND SD KHA DUNG: 1.779.234VND ND: NAP_LUCKYBETS_01648681489
        SmsBank smsBank = new SmsBank();

        try {

            String[] splitString = message.split(" ");

            double moneyTrans = 0.0;

            String content = "";
            String account = "";
            double totalBalance = 0.0;


            for (int i = 0; i < splitString.length; i++) {
                String string = splitString[i];
                if (string.contains("SD:")) {
                    string = splitString[i + 1];
                    totalBalance = Double.parseDouble(string.replace("VND", "").trim().replace(".", "").replaceAll("[^\\d.]", ""));
                }
                if (string.contains("ND:")) {
                    string = splitString[i + 1];
                    content = string.trim();
                }
                if (content.toUpperCase().contains("NAP_LUCKYBETS_")) {
                    account = content.toUpperCase().replace("NAP_LUCKYBETS_", "").trim();

                }

                if (string.contains("PS:")) {
                    moneyTrans = Double.parseDouble(string.split(":")[1].replace("VND", "").trim().replace(".", "").replaceAll("[^\\d.]", ""));
                    if (string.split(":")[1].contains("-")) {
                        moneyTrans = -moneyTrans;
                    }
                }

            }
            if (account.length() > 0 && moneyTrans > 0.0) {
                smsBank.setAction(1);
            } else {
                smsBank.setAction(0);
            }
            smsBank.setFullconte(message);
            smsBank.setPhone(phone);
            smsBank.setFullconte(message);
            smsBank.setContent(content);
            smsBank.setAccount(account);
            smsBank.setBalance(totalBalance);
            smsBank.setMoneytrans(moneyTrans);
            smsBank.setStatus(-2);
            Log.e(TAG, "getSmsTPBank: " + smsBank.toString());
            return smsBank;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private static SmsBank getSmsSacombank(String message, String phone) {
//Sacombank  11/07/2018 11:36 gui  3,484,000  VND  vao TK  020039754224  NAP_LUCKYBETS_01648681489 - So du kha dung:  19,915,373  VND

        SmsBank smsBank = new SmsBank();

        try {

            String[] splitString = message.replace("  ", " ").split(" ");

            double moneyTrans = 0.0;

            String content = "";
            String account = "";
            double totalBalance = 0.0;


            for (int i = 0; i < splitString.length; i++) {
                String string = splitString[i];
                if (string.contains("dung:")) {
                    string = splitString[i + 1];
                    totalBalance = Double.parseDouble(string.replace("VND", "").trim().replace(",", "").replaceAll("[^\\d.]", ""));
                }
                if (string.contains("TK")) {
                    string = splitString[i + 2];
                    content = string.trim();
                }
                if (content.toUpperCase().contains("NAP_LUCKYBETS_")) {
                    account = content.toUpperCase().replace("NAP_LUCKYBETS_", "").trim();
                }

                if (string.contains("gui")) {
                    string = splitString[i + 1];
                    moneyTrans = Double.parseDouble(string.replace("VND", "").trim().replace(",", "").replaceAll("[^\\d.]", ""));
                }
                if (string.contains("rut")) {
                    string = splitString[i + 1];
                    moneyTrans = Double.parseDouble(string.replace("VND", "").trim().replace(",", "").replaceAll("[^\\d.]", ""));
                    moneyTrans = -moneyTrans;
                }

            }
            if (account.length() > 0 && moneyTrans > 0.0) {
                smsBank.setAction(1);
            } else {
                smsBank.setAction(0);
            }
            smsBank.setFullconte(message);
            smsBank.setPhone(phone);
            smsBank.setFullconte(message);
            smsBank.setContent(content);
            smsBank.setAccount(account);
            smsBank.setBalance(totalBalance);
            smsBank.setMoneytrans(moneyTrans);
            smsBank.setStatus(-2);
            Log.e(TAG, "getSmsTPBank: " + smsBank.toString());
            return smsBank;
        } catch (Exception e) {
            e.printStackTrace();
        }


        return null;
    }

}
