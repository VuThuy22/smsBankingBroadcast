package vn.luckybets.broadcastsmsbanking.screens.home.mvp;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import vn.luckybets.broadcastsmsbanking.callback.ICallback;
import vn.luckybets.broadcastsmsbanking.common.ErrorUtils;
import vn.luckybets.broadcastsmsbanking.database.BankHelper;
import vn.luckybets.broadcastsmsbanking.model.Bank;
import vn.luckybets.broadcastsmsbanking.model.Error;
import vn.luckybets.broadcastsmsbanking.model.SmsBank;

public class BankModel {
    private Context context;
    private BankHelper helper;

    public BankModel(Context context) {
        this.context = context;
        this.helper = new BankHelper(context);
    }

    public void getListBank(ICallback<List<Bank>> callback) {
        try {
            callback.onSuccess(helper.getAllBank());
        } catch (Exception e) {
            callback.onError(ErrorUtils.getError(ErrorUtils.SYS_ERROR));
            e.printStackTrace();
        }
    }

    public void updateTotalBalance(String phone, double balance, ICallback<Bank> callback) {
        Bank bank = helper.getBankByPhone(phone);
        long id = helper.updateBalance(bank.getId(), balance);
        if (id > -0) {
            callback.onSuccess(helper.getBank(bank.getId()));
        } else {
            callback.onError(ErrorUtils.getError(ErrorUtils.SYS_ERROR));
        }
    }

    public void updateBankPhone(long bankId, String phone, ICallback<List<Bank>> callback){
        long id=helper.updatePhone(bankId,phone);
        if (id > -0) {
            callback.onSuccess(helper.getAllBank());
        } else {
            callback.onError(ErrorUtils.getError(ErrorUtils.SYS_ERROR));
        }
    }

    public void getTotalBank(ICallback<List<Double>> callback){
        try {
            callback.onSuccess(helper.getTotalBank());
        } catch (Exception e) {
            callback.onError(ErrorUtils.getError(ErrorUtils.SYS_ERROR));
            e.printStackTrace();
        }
    }
    public void initDataBank(ICallback<Long> iCallback) {
        if (helper.getCount() == 0) {
            try {
//                helper.addBank(new Bank(0L, "Liên việt PostBank", "LPB", 0.0));
//                helper.addBank(new Bank(0L, "VietinBank", "VietinBank", 0.0));
//                helper.addBank(new Bank(0L, "TPBank", "TPBank", 0.0));
//                helper.addBank(new Bank(0L, "Sacombank", "Sacombank", 0.0));
//                helper.addBank(new Bank(0L, "Vietcombank", "Vietcombank", 0.0));
//                long i = helper.addBank(new Bank(0L, "BIDV", "BIDV", 0.0));

                helper.addBank(new Bank(0L, "Liên việt PostBank", "0", 0.0));
                helper.addBank(new Bank(0L, "VietinBank", "1", 0.0));
                helper.addBank(new Bank(0L, "TPBank", "2", 0.0));
                helper.addBank(new Bank(0L, "Sacombank", "3", 0.0));
                helper.addBank(new Bank(0L, "Vietcombank", "4", 0.0));
                long i = helper.addBank(new Bank(0L, "BIDV", "5", 0.0));
                iCallback.onSuccess(i);
            } catch (Exception e) {
                e.printStackTrace();
                iCallback.onError(ErrorUtils.getError(ErrorUtils.SYS_ERROR));
            }
        } else {
            iCallback.onSuccess(0L);
        }

    }

}
