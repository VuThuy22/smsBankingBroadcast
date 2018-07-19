package vn.luckybets.broadcastsmsbanking.reciver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import vn.luckybets.broadcastsmsbanking.database.BankHelper;
import vn.luckybets.broadcastsmsbanking.model.Bank;
import vn.luckybets.broadcastsmsbanking.utils.SmsBankUtils;

public class SmsReciver extends BroadcastReceiver {
    private static final String TAG = "SmsReciver";

    BankHelper helper;

    @Override
    public void onReceive(Context context, Intent intent) {
// Retrieves a map of extended data from the intent.
        helper = new BankHelper(context);
        if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
            final Bundle bundle = intent.getExtras();

            try {

                if (bundle != null) {

                    final Object[] pdusObj = (Object[]) bundle.get("pdus");

                    for (int i = 0; i < pdusObj.length; i++) {

                        SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                        String phoneNumber = currentMessage.getDisplayOriginatingAddress();

                        String senderNum = phoneNumber;
                        String message = currentMessage.getDisplayMessageBody();

                        Log.e("SmsReceiver", "senderNum: " + senderNum + "; message: " + message);
                        checkSms(context, phoneNumber, message);
                    } // end for loop
                } // bundle is null

            } catch (Exception e) {
                e.printStackTrace();
                Log.e("SmsReceiver", "Exception smsReceiver" + e);
            }
        }
    }

    private void checkSms(Context context, String phoneNumber, String message) {
        boolean isBank = helper.isPhoneBank(phoneNumber);
        Log.e(TAG, "isbank = " + isBank);
        if (isBank) {
            Bank bank = helper.getBankByPhone(phoneNumber);
            if (bank!=null)
            Log.e(TAG, bank.toString());
            else Log.e(TAG, "checkSms: bank null" );
            if (bank != null) {
                SmsBankUtils.handingSms(context, message, bank);
            }
        }
    }


}
