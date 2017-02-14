package com.example.skilledanswers_d2.wallettransfer.adapters;

import android.content.Context;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.skilledanswers_d2.wallettransfer.Interfaces.PassMerchants;
import com.example.skilledanswers_d2.wallettransfer.R;
import com.example.skilledanswers_d2.wallettransfer.models.MerchantsModel;

import java.util.ArrayList;


public class MerchantsAdptr extends RecyclerView.Adapter<MerchantsAdptr.MerchatsHolder> {

    private Context context=null;
    private ArrayList<MerchantsModel> merchantsModels=null;

    public MerchantsAdptr(Context context, ArrayList<MerchantsModel> merchantsModels) {
        this.context = context;
        this.merchantsModels = merchantsModels;
    }

    @Override
    public MerchatsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.merchants_row, parent, false);
        MerchatsHolder merchatsHolder=new MerchatsHolder(view);
        merchatsHolder.textView.setTag(merchatsHolder);
        merchatsHolder.appCompatRadioButton.setTag(merchatsHolder);
        merchatsHolder.layoutCompat.setTag(merchatsHolder);
        return merchatsHolder;
    }

    @Override
    public void onBindViewHolder(final MerchatsHolder holder,  int position) {
        holder.textView.setText(merchantsModels.get(position).get_name());
        holder.layoutCompat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.appCompatRadioButton.setChecked(true);
                PassMerchants passMerchants= (PassMerchants) context;
                passMerchants.merchantIs(merchantsModels.get(holder.getAdapterPosition()).get_id(),
                        merchantsModels.get(holder.getAdapterPosition()).get_name());
            }
        });

    }

    @Override
    public int getItemCount() {
        return merchantsModels.size();
    }

     class MerchatsHolder extends RecyclerView.ViewHolder
    {
        private AppCompatRadioButton appCompatRadioButton=null;
        private AppCompatTextView textView=null;
        private LinearLayoutCompat layoutCompat=null;
         MerchatsHolder(View itemView) {
            super(itemView);
            this.appCompatRadioButton= (AppCompatRadioButton) itemView.findViewById(R.id.merchant_row_radio_button);
            this.textView= (AppCompatTextView) itemView.findViewById(R.id.merchant_row_name);
            this.layoutCompat= (LinearLayoutCompat) itemView.findViewById(R.id.merchant_row_layout);
        }
    }
}
