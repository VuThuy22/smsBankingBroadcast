package vn.luckybets.broadcastsmsbanking.screens.home;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import vn.luckybets.broadcastsmsbanking.R;
import vn.luckybets.broadcastsmsbanking.common.Cts;
import vn.luckybets.broadcastsmsbanking.model.Bank;
import vn.luckybets.broadcastsmsbanking.model.Error;
import vn.luckybets.broadcastsmsbanking.model.SmsBank;
import vn.luckybets.broadcastsmsbanking.screens.home.adapter.BankAdapter;
import vn.luckybets.broadcastsmsbanking.screens.home.adapter.SmsAdapter;
import vn.luckybets.broadcastsmsbanking.screens.DetailActivity;
import vn.luckybets.broadcastsmsbanking.screens.home.mvp.HomePresenter;
import vn.luckybets.broadcastsmsbanking.screens.home.mvp.HomeView;

public class MainActivity extends AppCompatActivity implements HomeView {


    private static final String TAG = "MainActivity";
    int MY_PERMISSIONS_REQUEST_READ_BROADCAST_SMS = 1;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tvTotalBalance)
    TextView tvTotalBalance;
    @BindView(R.id.rcBank)
    RecyclerView rcBank;
    @BindView(R.id.rcSms)
    RecyclerView rcSms;
    @BindView(R.id.rootlayout)
    CoordinatorLayout rootlayout;

    private HomePresenter presenter;
    private List<Bank> banks = new ArrayList<>();
    private List<SmsBank> smsBanks = new ArrayList<>();

    private BankAdapter adapterBank;
    private SmsAdapter adapterSms;

    private BroadcastReceiver receiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initVal();
        initUI();
        initData();
        presenter.setTotal();
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.RECEIVE_SMS},
                MY_PERMISSIONS_REQUEST_READ_BROADCAST_SMS);
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                if (intent.getAction().equalsIgnoreCase(Cts.ACTION_SMS)) {
                    Bank bank = (Bank) intent.getSerializableExtra(Cts.OBJEC_BANK);
                    SmsBank smsbank = (SmsBank) intent.getSerializableExtra(Cts.OBJEC_SMS);
                    presenter.addSms(smsbank);
                    presenter.setTotal();
                }
            }
        };
        registerReceiver(receiver, new IntentFilter(Cts.ACTION_SMS));
    }

    private void initUI() {
        rcBank.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rcBank.setAdapter(adapterBank);
        rcSms.setLayoutManager(new LinearLayoutManager(this));
        rcSms.setAdapter(adapterSms);
        rcSms.setNestedScrollingEnabled(false);

        adapterSms.setOnReloadClick(new SmsAdapter.OnReloadClick() {
            @Override
            public void click(SmsBank smsBank) {
                presenter.sendRequets(smsBank);
            }
        });
        adapterBank.setUpdatePhone(new BankAdapter.OnUpdatePhone() {
            @Override
            public void click(Bank bank) {
                UpdatePhone(bank);
            }


        });
        adapterSms.setOnShowDetail(new SmsAdapter.onShowDetail() {
            @Override
            public void onClick(SmsBank smsBank) {
                presenter.getDetailSms(smsBank);

            }
        });
    }

    public void UpdatePhone(final Bank bank) {
        LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
        View dialogView = inflater.inflate(R.layout.dialog_update_phone, null);
        final EditText edt_phone = (EditText) dialogView
                .findViewById(R.id.edt_phone);
        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setView(dialogView);
        builder.setTitle("Bạn có muốn sửa số điện thoại của ngân hàng " + bank.getName() + "?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                presenter.upadateBankPhone(bank.getId(), edt_phone.getText().toString());

            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }

    private void initVal() {
        adapterBank = new BankAdapter(this, banks);
        adapterSms = new SmsAdapter(this, smsBanks);
        presenter = new HomePresenter(this, this);
    }

    private void initData() {
        presenter.initDataBank();
        presenter.getAllListSms();
    }

    @Override
    public void onLoadding(int typeRequest) {

    }

    @Override
    public void onLoadSuccess(int typeRequest, Object obj) {
        switch (typeRequest) {
            case HomePresenter.GET_LIST_BANK:
                banks.addAll((List<Bank>) obj);
                adapterBank.notifyDataSetChanged();
                break;
            case HomePresenter.GET_LIST_SMS:
                smsBanks.addAll((List<SmsBank>) obj);
                adapterSms.notifyDataSetChanged();
                break;
            case HomePresenter.UPDATE_BALANCE:
                Bank newBank = (Bank) obj;
                for (int i = 0; i < banks.size(); i++) {
                    if (banks.get(i).getId().equals(newBank.getId())) {
                        banks.get(i).setBalance(newBank.getBalance());
                        banks.get(i).setTimeUpdate(newBank.getTimeUpdate());
                        adapterBank.notifyItemChanged(i);
                        break;
                    }
                }
                break;
            case HomePresenter.ADD_SMS_BANK:
                SmsBank smsBank = (SmsBank) obj;
                smsBanks.add(0, smsBank);
                adapterSms.notifyItemInserted(0);
                adapterSms.notifyItemRangeChanged(1, smsBanks.size());
                break;
            case HomePresenter.POST_REQUEST:
                try {
                    SmsBank newSms = (SmsBank) obj;
                    for (int i = 0; i < smsBanks.size(); i++) {
                        if (smsBanks.get(i).getId() == newSms.getId()) {
                            smsBanks.get(i).setStatus(newSms.getStatus());
                            adapterSms.notifyItemChanged(i);
                            break;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case HomePresenter.UPDATE_PHONE:
                banks.clear();
                banks.addAll((List<Bank>) obj);
                adapterBank.notifyDataSetChanged();
                break;

            case HomePresenter.TOTAL_BALANCE:
                List<Double> listTotal = new ArrayList<>();
                listTotal.addAll((List<Double>) obj);
                double total = 0.0;
                for (int i = 0; i < listTotal.size(); i++) {
                    total = total + listTotal.get(i);
                }
                DecimalFormat decimalFormat = new DecimalFormat("#,##0");
                tvTotalBalance.setText(decimalFormat.format(total) + " đ");
                break;
            case HomePresenter.GET_DETAIL_SMS:
                SmsBank smsBank1 = (SmsBank) obj;
                Intent intent=new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("id",smsBank1.getId());
                intent.putExtra("conten",smsBank1.getContent());
                intent.putExtra("phone",smsBank1.getPhone());
                intent.putExtra("action",smsBank1.getAction());
                intent.putExtra("status",smsBank1.getStatus());
                intent.putExtra("fullcontent",smsBank1.getFullconte());
                intent.putExtra("account",smsBank1.getAccount());
                intent.putExtra("balance",smsBank1.getBalance());
                intent.putExtra("moneytrans",smsBank1.getMoneytrans());
                intent.putExtra("time",smsBank1.getTime());
                startActivity(intent);
            default:
        }
    }

    @Override
    public void onError(int typeRequest, Error e) {
        Log.e(TAG, "onError: " + typeRequest + " " + e.toString());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }
}
