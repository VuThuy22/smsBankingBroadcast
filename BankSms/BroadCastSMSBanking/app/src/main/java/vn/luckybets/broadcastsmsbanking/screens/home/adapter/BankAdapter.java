package vn.luckybets.broadcastsmsbanking.screens.home.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import vn.luckybets.broadcastsmsbanking.model.Bank;
import vn.luckybets.broadcastsmsbanking.R;
import vn.luckybets.broadcastsmsbanking.model.SmsBank;
import vn.luckybets.broadcastsmsbanking.utils.TextUtils;


public class BankAdapter extends RecyclerView.Adapter<BankAdapter.BankHolder> {

    private Context context;
    private List<Bank> list;
    private OnUpdatePhone onUpdatePhone;
    public void setUpdatePhone(OnUpdatePhone onUpdatePhone){
        this.onUpdatePhone=onUpdatePhone;
    }
    public BankAdapter(Context context, List<Bank> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public BankHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BankHolder(LayoutInflater.from(context).inflate(R.layout.item_list_bank, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BankHolder holder, final int position) {
        holder.tvBankName.setText(list.get(position).getName());
        holder.tvTotalBalance.setText(TextUtils.getStringCoin(list.get(position).getBalance()));
        holder.tvTimeUpdate.setText(list.get(position).getPhone());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onUpdatePhone.click(list.get(position));


            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class BankHolder extends RecyclerView.ViewHolder {
        TextView tvBankName, tvTotalBalance, tvTimeUpdate;

        public BankHolder(View itemView) {
            super(itemView);
            tvBankName = itemView.findViewById(R.id.tvBankName);
            tvTotalBalance = itemView.findViewById(R.id.tvTotalBalane);
            tvTimeUpdate = itemView.findViewById(R.id.tvTimeUpdate);
        }
    }
    public  interface OnUpdatePhone{
        void click(Bank bank);
    }
}
