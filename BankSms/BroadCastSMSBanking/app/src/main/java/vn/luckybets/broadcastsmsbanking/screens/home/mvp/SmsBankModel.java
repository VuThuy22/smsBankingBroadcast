package vn.luckybets.broadcastsmsbanking.screens.home.mvp;

import android.content.Context;
import android.content.Intent;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import vn.luckybets.broadcastsmsbanking.callback.ICallback;
import vn.luckybets.broadcastsmsbanking.common.ErrorUtils;
import vn.luckybets.broadcastsmsbanking.database.BankHelper;
import vn.luckybets.broadcastsmsbanking.database.SmsHelper;
import vn.luckybets.broadcastsmsbanking.model.Error;
import vn.luckybets.broadcastsmsbanking.model.SmsBank;

public class SmsBankModel {
    private ICallback callBack;
    private Context context;
    private SmsHelper helper;
    private BankHelper bankHelper;

    public SmsBankModel(Context context) {
        this.context = context;
        this.helper = new SmsHelper(context);
        this.bankHelper = new BankHelper(context);
    }

    public void getAllSms(ICallback<List<SmsBank>> callback) {
        try {
            callback.onSuccess(helper.getAllSmsBank());
        } catch (Exception e) {
            e.printStackTrace();
            callback.onError(ErrorUtils.getError(ErrorUtils.SYS_ERROR));
        }
    }


    public void addSMS(SmsBank smsBank, ICallback<SmsBank> callback) {
        try {
            long id = helper.addSMSBank(smsBank);

            callback.onSuccess(helper.getSmsBank(id));
        } catch (Exception e) {
            e.printStackTrace();
            callback.onError(ErrorUtils.getError(ErrorUtils.SYS_ERROR));
        }
    }
    public void getDetailSms(SmsBank smsBank, ICallback<SmsBank> callback ){
        try{
            callback.onSuccess(helper.getSmsBank(smsBank.getId()));
        }catch (Exception e){
            e.printStackTrace();
            callback.onError(ErrorUtils.getError(ErrorUtils.SYS_ERROR));
        }
    }
    public static final String URL = "http://101.99.23.175:5566";
    String url = URL + "/api/vietlott/admin/change_balance";

    public void postRequest(final SmsBank sms, final ICallback<SmsBank> callback) {
        JSONObject request = new JSONObject();
        helper.updateStatusSMSBank(sms.getId(),-2);
        try {
            request.put("requestId", (int) System.currentTimeMillis());
            request.put("bankId", bankHelper.getBankByPhone(sms.getPhone()).getId());
            request.put("bankName", bankHelper.getBankByPhone(sms.getPhone()).getName());
            request.put("requestType", (sms.getMoneytrans() > 0) ? 1 : 0);
            request.put("money", sms.getMoneytrans());
            request.put("content", sms.getContent());
            request.put("account", sms.getAccount());
            request.put("fullContent", sms.getFullconte());
            request.put("action", sms.getAction());

        } catch (Exception e) {
            e.printStackTrace();
        }
        JsonObjectRequest postJson = new JsonObjectRequest(Request.Method.POST, url, request,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            int status = response.getInt("result");
                            sms.setStatus(status);

                            helper.updateStatusSMSBank(sms.getId(), status);
                            callback.onSuccess(sms);
                        } catch (JSONException e) {
                            sms.setStatus(-1);
                            e.printStackTrace();
                        }
                    }

                    ;
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                helper.updateStatusSMSBank(sms.getId(), -1);
                callback.onError(ErrorUtils.getError(ErrorUtils.SYS_ERROR));
            }
        });
        Volley.newRequestQueue(context.getApplicationContext()).add(postJson);
    }
}
