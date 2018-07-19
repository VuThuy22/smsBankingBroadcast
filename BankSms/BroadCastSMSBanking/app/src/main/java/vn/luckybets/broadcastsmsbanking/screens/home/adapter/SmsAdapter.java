package vn.luckybets.broadcastsmsbanking.screens.home.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import vn.luckybets.broadcastsmsbanking.R;
import vn.luckybets.broadcastsmsbanking.model.SmsBank;
import vn.luckybets.broadcastsmsbanking.utils.TextUtils;

public class SmsAdapter extends RecyclerView.Adapter<SmsAdapter.SmsHolder> {

    private Context context;
    private List<SmsBank> list;
    private OnReloadClick onReloadClick;
    private onShowDetail onShowDetail;

    public void setOnReloadClick(OnReloadClick onReloadClick) {
        this.onReloadClick = onReloadClick;
    }
    public void setOnShowDetail(onShowDetail onShowDetail){
        this.onShowDetail=onShowDetail;
    }

    public SmsAdapter(Context context, List<SmsBank> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public SmsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SmsHolder(LayoutInflater.from(context).inflate(R.layout.item_sms_bank, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SmsHolder holder, final int position) {
        holder.tvBankName.setText(list.get(position).getPhone());
        holder.tvMoneyTrans.setText(TextUtils.getStringCoin(list.get(position).getMoneytrans()));
        holder.tvTime.setText("" + list.get(position).getTime());

        holder.prLoadding.setVisibility(list.get(position).getStatus() == -2 ? View.VISIBLE : View.GONE);
        holder.btnReload.setVisibility((list.get(position).getStatus() != -2 && list.get(position).getStatus() != 0) ? View.VISIBLE : View.GONE);

        if (list.get(position).getMoneytrans() > 0) {
            holder.imgTodayStt.setImageResource(R.mipmap.ic_dot_blue);
            holder.tvMoneyTrans.setTextColor(context.getResources().getColor(R.color.colorPrimary));
        } else {
            holder.imgTodayStt.setImageResource(R.mipmap.ic_dot_organe);
            holder.tvMoneyTrans.setTextColor(context.getResources().getColor(R.color.organe));
        }

        holder.icAction.setVisibility(list.get(position).getAction() == 1 ? View.VISIBLE : View.GONE);

        holder.btnReload.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    list.get(position).setStatus(-2);
                                                    if (onReloadClick != null) onReloadClick.click(list.get(position));
                                                    notifyItemChanged(position);
                                                }
                                            }
        );
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onShowDetail.onClick(list.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class SmsHolder extends RecyclerView.ViewHolder {
        TextView tvTime, tvMoneyTrans, tvBankName, btnReload;
        View viewStt;
        ImageView imgTodayStt, icAction;
        ProgressBar prLoadding;

        public SmsHolder(View itemView) {
            super(itemView);
            imgTodayStt = itemView.findViewById(R.id.imgTodayStt);
            viewStt = itemView.findViewById(R.id.viewStt);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvMoneyTrans = itemView.findViewById(R.id.tvMoneyTrans);
            tvBankName = itemView.findViewById(R.id.tvBankName);
            prLoadding = itemView.findViewById(R.id.prLoadding);
            btnReload = itemView.findViewById(R.id.btnReload);
            icAction = itemView.findViewById(R.id.icAction);
        }
    }

    public interface OnReloadClick {
        void click(SmsBank smsBank);
    }
    public interface onShowDetail{
        void onClick(SmsBank smsBank);
    }
}
