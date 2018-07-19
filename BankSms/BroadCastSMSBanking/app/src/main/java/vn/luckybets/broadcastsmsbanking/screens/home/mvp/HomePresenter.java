package vn.luckybets.broadcastsmsbanking.screens.home.mvp;

import android.content.Context;
import android.util.Log;

import java.util.List;

import vn.luckybets.broadcastsmsbanking.callback.ICallback;
import vn.luckybets.broadcastsmsbanking.model.Bank;
import vn.luckybets.broadcastsmsbanking.model.Error;
import vn.luckybets.broadcastsmsbanking.model.SmsBank;

public class HomePresenter {

    private static final int INIT_DATA = 0;
    public static final int GET_LIST_BANK = 1;
    public static final int GET_LIST_SMS = 3;
    public static final int UPDATE_BALANCE = 2;
    public static final int ADD_SMS_BANK = 4;
    public static final int POST_REQUEST = 5;
    public static final int UPDATE_PHONE = 6;
    public static final int TOTAL_BALANCE = 7;
    public static final int GET_DETAIL_SMS = 8;
    private static final String TAG = "HomePresenter";


    private BankModel bankModel;
    private SmsBankModel smsModel;
    private HomeView view;

    public HomePresenter(Context context, HomeView view) {
        this.view = view;
        this.bankModel = new BankModel(context);
        this.smsModel = new SmsBankModel(context);
    }


    public void getAllListSms() {
        run(GET_LIST_SMS);
    }


    public void getListBank() {
        run(GET_LIST_BANK);
    }

    public void initDataBank() {
        run(INIT_DATA);
    }

    private void run(final int typeRequest, final Object... params) {
        switch (typeRequest) {
            case GET_LIST_BANK:
                bankModel.getListBank(new ICallback<List<Bank>>() {
                    @Override
                    public void onSuccess(List<Bank> result) {
                        view.onLoadSuccess(typeRequest, result);
                    }

                    @Override
                    public void onError(Error e) {
                        view.onError(typeRequest, e);
                    }
                });
                break;
            case INIT_DATA:
                bankModel.initDataBank(new ICallback<Long>() {
                    @Override
                    public void onSuccess(Long result) {
                        view.onLoadSuccess(typeRequest, result);
                        getListBank();
                    }

                    @Override
                    public void onError(Error e) {
                        getListBank();
                        view.onError(typeRequest, e);

                    }
                });
                break;
            case UPDATE_BALANCE:
                bankModel.updateTotalBalance((String) params[0], (double) params[1], new ICallback<Bank>() {
                    @Override
                    public void onSuccess(Bank result) {
                        view.onLoadSuccess(typeRequest, result);
                    }

                    @Override
                    public void onError(Error e) {
                        view.onError(typeRequest, e);
                    }
                });
                break;
            case GET_LIST_SMS:
                smsModel.getAllSms(new ICallback<List<SmsBank>>() {
                    @Override
                    public void onSuccess(List<SmsBank> result) {
                        view.onLoadSuccess(typeRequest, result);
                    }

                    @Override
                    public void onError(Error e) {
                        view.onError(typeRequest, e);
                    }
                });
                break;
            case ADD_SMS_BANK:
                smsModel.addSMS((SmsBank) params[0], new ICallback<SmsBank>() {
                    @Override
                    public void onSuccess(SmsBank result) {
                        updateBankBalance(result.getPhone(), result.getBalance());
                        view.onLoadSuccess(typeRequest, result);
                        sendRequets(result);
                    }

                    @Override
                    public void onError(Error e) {
                        view.onError(typeRequest, e);
                    }
                });
                break;

            case POST_REQUEST:
                final SmsBank sms = (SmsBank) params[0];

                smsModel.postRequest(sms, new ICallback<SmsBank>() {
                    @Override
                    public void onSuccess(SmsBank result) {
                        view.onLoadSuccess(typeRequest, result);
                        Log.e(TAG, "onSuccess: " + result.toString());
                    }

                    @Override
                    public void onError(Error e) {
                        view.onError(typeRequest, e);
                        sms.setStatus(-1);
                        view.onLoadSuccess(typeRequest, sms);
                    }
                });
                break;

            case UPDATE_PHONE:

                bankModel.updateBankPhone((long) params[0], (String) params[1], new ICallback<List<Bank>>() {
                    @Override
                    public void onSuccess(List<Bank> result) {
                        view.onLoadSuccess(typeRequest, result);
                    }

                    @Override
                    public void onError(Error e) {
                        view.onError(typeRequest, e);
                    }
                });
                break;
            case TOTAL_BALANCE:
                bankModel.getTotalBank(new ICallback<List<Double>>() {
                    @Override
                    public void onSuccess(List<Double> result) {
                        view.onLoadSuccess(typeRequest,result);
                    }

                    @Override
                    public void onError(Error e) {
                        view.onError(typeRequest,e);
                    }
                });
                break;
            case GET_DETAIL_SMS:
                final SmsBank smsBank = (SmsBank) params[0];
                smsModel.getDetailSms( smsBank, new ICallback<SmsBank>() {

                    @Override
                    public void onSuccess(SmsBank result) {
                        view.onLoadSuccess(typeRequest,result);
                    }

                    @Override
                    public void onError(Error e) {
                        view.onError(typeRequest,e);
                    }
                });
                break;
                default:
        }
    }

    private void updateBankBalance(String phone, double balance) {
        run(UPDATE_BALANCE, phone, balance);
    }
    public void upadateBankPhone(long id,String phone){
        run(UPDATE_PHONE,id,phone);
    }

    public void addSms(SmsBank smsbank) {
        run(ADD_SMS_BANK, smsbank);


    }

    public void sendRequets(SmsBank smsbank) {
        run(POST_REQUEST, smsbank);
    }
    public void setTotal(){
        run(TOTAL_BALANCE);
    }

    public void getDetailSms(SmsBank smsBank){
        run(GET_DETAIL_SMS);
    }
}




