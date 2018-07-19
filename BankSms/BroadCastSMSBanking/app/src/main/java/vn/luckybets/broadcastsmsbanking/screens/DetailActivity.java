package vn.luckybets.broadcastsmsbanking.screens;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import vn.luckybets.broadcastsmsbanking.R;

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.txt_id)
    TextView txtId;
    @BindView(R.id.txt_phone)
    TextView txtPhone;
    @BindView(R.id.txt_content)
    TextView txtContent;
    @BindView(R.id.txt_fullContent)
    TextView txtFullContent;
    @BindView(R.id.txt_action)
    TextView txtAction;
    @BindView(R.id.txt_status)
    TextView txtStatus;
    @BindView(R.id.txt_account)
    TextView txtAccount;
    @BindView(R.id.txt_balance)
    TextView txtBalance;
    @BindView(R.id.txt_moneytrans)
    TextView txtMoneytrans;
    @BindView(R.id.txt_time)
    TextView txtTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        long id = intent.getLongExtra("id", 0);
        String phone = intent.getStringExtra("phone");
        int action = intent.getIntExtra("action", -1);
        int status = intent.getIntExtra("status", -1);
        String content = intent.getStringExtra("content");
        String fullContent = intent.getStringExtra("fullcontent");
        String account = intent.getStringExtra("account");
        double balance = intent.getDoubleExtra("balance", 0);
        double moneytrans = intent.getDoubleExtra("moneytrans", 0);
        long time = intent.getLongExtra("time", 0);

        txtAccount.setText("account: "+ account);
        txtAction.setText("action: "+action);
        txtBalance.setText("balance: "+balance);
        txtId.setText("id: "+id);
        txtContent.setText("content: "+content);
        txtFullContent.setText("fullContent: "+fullContent);
        txtMoneytrans.setText("moneytrans: "+moneytrans);
        txtPhone.setText("phone: "+phone);
        txtStatus.setText("status: "+status);
        txtTime.setText("time: "+time);


    }

}
