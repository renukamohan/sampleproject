package com.example.skilledanswers_d2.wallettransfer.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.skilledanswers_d2.wallettransfer.R;
import com.example.skilledanswers_d2.wallettransfer.constant.ALLCONSTANTS;
import com.example.skilledanswers_d2.wallettransfer.constant.CommFunc;
import com.example.skilledanswers_d2.wallettransfer.constant.Connectivity;
import com.example.skilledanswers_d2.wallettransfer.customwidgits.ExpandableLayout;
import com.example.skilledanswers_d2.wallettransfer.models.TransModelOut;
import com.example.skilledanswers_d2.wallettransfer.vollyclasses.CustomJsonReq;
import com.example.skilledanswers_d2.wallettransfer.vollyclasses.SingelTonVollyQueue;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by user on 1/23/2017.
 */

public class TransactionFragOut extends Fragment {
    private RecyclerView recyclerView=null;
    private SwipeRefreshLayout refreshLayout=null;
    private AppCompatTextView textViewForAlert =null;
    private ProgressDialog pDialog = null;
    private int start=0;
    private int length=40;
    private int total_datas=0;
    public ArrayList<TransModelOut> transModelOut =new ArrayList<>();
    private Tadapter adapter=null;
    private Context mContext=null;
    /////////////////////////////////////// VOLLEY TAG
    private String VOLLEY_TAG_TRANS_FRAG_OUT_GET_TRANSACTIONS="VOLLEY_TAG_TRANS_FRAG_OUT_GET_TRANSACTIONS";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_transaction, container, false);
        recyclerView= (RecyclerView) view.findViewById(R.id.transaction_fragment_recycleview_id);
        refreshLayout= (SwipeRefreshLayout) view.findViewById(R.id.order_fragment_swipe_refresh_id);
        textViewForAlert = (AppCompatTextView) view.findViewById(R.id.transaction_textview_cmg_soon);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext=getActivity();
        start=0;length=40;
        transModelOut.clear();
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                if (Connectivity.isConnected(getActivity()))
                {
                    refreshLayout.setRefreshing(true);
                    start=0;length=40;
                    transModelOut.clear();
                    if (adapter!=null)
                    {
                        adapter.notifyDataSetChanged();
                    }
                    getAllPay_OutTransaction(String.valueOf(start), String.valueOf(length), false, false);
                }else {
                   CommFunc.NoInternetPrompt(getActivity());
                }

            }
        });

        if (Connectivity.isConnected(getActivity())) {
            getAllPay_OutTransaction(String.valueOf(start), String.valueOf(length), true, false);
        }else {
            CommFunc.NoInternetPrompt(getActivity());
        }


    }


    private void getAllPay_OutTransaction(String start, String lenght, boolean showProgress, final boolean isLoadMore)
    {
        if (showProgress) {
            showProgressDialog();
        }

        HashMap<String,String> mapgetAllTrans=new HashMap<>();
        mapgetAllTrans.put("action","getWalletOutTransactions");
        SharedPreferences preferences=getActivity().getSharedPreferences(ALLCONSTANTS.SESSION_PREFERENCE_NAME1,MODE_PRIVATE);
        mapgetAllTrans.put("androidToken",preferences.getString(ALLCONSTANTS.SESSION_KEY_TOKEN1,null));
        mapgetAllTrans.put("start",start);
        mapgetAllTrans.put("length",lenght);
        CustomJsonReq jsonReqgetAllTrans=new CustomJsonReq(Request.Method.POST, ALLCONSTANTS.app_post_url,
                mapgetAllTrans, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println("uuuuuuuuuuuuu-------response--------"+response.toString());
                dismissProgressDialog();
                if (refreshLayout.isRefreshing())
                {
                    refreshLayout.setRefreshing(false);
                }
                if (textViewForAlert.getVisibility()==View.VISIBLE)
                {
                    textViewForAlert.setVisibility(View.GONE);

                }
                if (response!=null)
                {
                    try {
                        if (response.getBoolean("status"))
                        {
                            total_datas= Integer.parseInt(response.getString("recordsTotal"));
                            if (total_datas!=0)
                            {
                                if (isLoadMore)
                                {
                                    if (transModelOut.get(transModelOut.size()-1)==null) {
                                        if (adapter != null) {
                                            transModelOut.remove(transModelOut.size() - 1);
                                            adapter.notifyDataSetChanged();
                                        }
                                    }
                                }
                                JSONArray jsonArray=response.getJSONArray("data");
                                for(int i=0;i<jsonArray.length();i++)
                                {

                                    JSONObject object=jsonArray.getJSONObject(i);

                                    transModelOut.add(new TransModelOut(false,object.getString("refid"),
                                            object.getString("merchantName"),
                                            object.getString("amount")
                                            ,object.getString("cashback"),
                                            object.getString("status"),
                                            object.getString("created"),
                                            object.getString("updated")));
                                }
                                if (!isLoadMore) {
                                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(),
                                            LinearLayoutManager.VERTICAL, false);
                                    if (transModelOut.size()<total_datas)
                                    {
                                        transModelOut.add(null);

                                    }

                                    recyclerView.setLayoutManager(layoutManager);
                                    adapter = new Tadapter(getActivity(), transModelOut);
                                    recyclerView.setAdapter(adapter);
                                }else {
                                    if (transModelOut.size()<total_datas)
                                    {
                                        transModelOut.add(null);
                                        adapter.notifyDataSetChanged();

                                    }
                                }
                            }else {
                                textViewForAlert.setVisibility(View.VISIBLE);
                                textViewForAlert.setText("No Out Transactions to show...");
                            }

                        }else {
                            if (response.getString("message")!=null)
                            {
                                CommFunc.commonDialog(mContext,"Error",response.getString("message"));

                            }else {
                                Toast.makeText(mContext, "No internet", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else {
                   CommFunc.NoInternetPrompt(getActivity());
                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dismissProgressDialog();
                if (refreshLayout.isRefreshing())
                {
                    refreshLayout.setRefreshing(false);
                }
                CommFunc.NoInternetPrompt(getActivity());

            }
        });
        jsonReqgetAllTrans.setTag(VOLLEY_TAG_TRANS_FRAG_OUT_GET_TRANSACTIONS);
        SingelTonVollyQueue.getInstance(getActivity()).addToRequestQueue(jsonReqgetAllTrans);

    }


    private void showProgressDialog() {
        if (pDialog == null) {
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Please wait... .");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
        }
        pDialog.show();
    }
    private void dismissProgressDialog() {
        if (pDialog != null && pDialog.isShowing()) {
            pDialog.dismiss();
        }
    }



    @Override
    public void onPause() {
        super.onPause();
        SingelTonVollyQueue.getInstance(getActivity()).getRequestQueue().cancelAll(VOLLEY_TAG_TRANS_FRAG_OUT_GET_TRANSACTIONS);
        dismissProgressDialog();
    }

    private ExpandableLayout.OnExpandListener mOnExpandListener = new ExpandableLayout.OnExpandListener() {

        private boolean isScrollingToBottom = false;

        @Deprecated
        @Override
        public void onToggle(ExpandableLayout view, View child,
                             boolean isExpanded) {
        }

        @Override
        public void onExpandOffset(ExpandableLayout view, View child,
                                   float offset, boolean isExpanding) {
            if (view.getTag() instanceof TransactionFragIn.TadapterHolder) {
                final TransactionFragIn.TadapterHolder holder = (TransactionFragIn.TadapterHolder) view.getTag();
                if (holder.getAdapterPosition() == transModelOut.size() - 1) {
                    if (!isScrollingToBottom) {
                        isScrollingToBottom = true;
                        recyclerView.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                isScrollingToBottom = false;
                                recyclerView.scrollToPosition(holder
                                        .getAdapterPosition());
                            }
                        }, 100);
                    }
                }
            }
        }
    };


    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            TadapterHolder holder = (TadapterHolder) v.getTag();
            boolean result = holder.expandableLayout.toggleExpansion();
            System.out.println("gggggggggggggg-------------"+holder.getAdapterPosition());
            try
            {
                TransModelOut item = transModelOut.get(holder.getAdapterPosition());
                item.setExpand(result != item.isExpand());
                if (item.isExpand())
                {
                    holder.imageViewExpandOrColapseSymbol.setImageResource(R.drawable.circle_minus);
                }else {
                    holder.imageViewExpandOrColapseSymbol.setImageResource(R.drawable.circle_plus);

                }
            }catch (Exception e)
            {
                e.printStackTrace();
            }


        }
    };
    public void loadMoreData()
    {
        System.out.println("sssssssssssss-------------------"+start+"ssssssssssssssssss"+length);

        if (Connectivity.isConnected(getActivity())) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    start = start + 40;
                    length = 40;
                    if (transModelOut.size()<total_datas) {
                        getAllPay_OutTransaction(String.valueOf(start), String.valueOf(length), false, true);
                    }
                }
            },1200);


        }else {
            Toast.makeText(getActivity(), "No internet", Toast.LENGTH_SHORT).show();
            if (transModelOut.get(transModelOut.size()-1)==null)
            {
                if (adapter!=null) {
                    transModelOut.remove(transModelOut.size() - 1);
                    adapter.notifyDataSetChanged();
                }
            }
        }

    }



    public class Tadapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
    {
        private Context context=null;
        private ArrayList<TransModelOut> models =null;
        private int LOADING_VIEW=1;
        private int NON_LOADING_VIEW=0;

        public Tadapter(Context context, ArrayList<TransModelOut> transModels) {
            this.context = context;
            this.models = transModels;
        }
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType==NON_LOADING_VIEW)
            {
                LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View  view = inflater.inflate(R.layout.trans_row_out, parent, false);
                TadapterHolder holder=new TadapterHolder(view);
                holder.expandableLayout.setTag(holder);
                holder.layoutCompatClickLayout.setTag(holder);
                holder.imageViewExpandOrColapseSymbol.setTag(holder);
                holder.imageViewStatusImageiew.setTag(holder);
                holder.textViewRefId.setTag(holder);
                holder.textViewMerchantName.setTag(holder);
                holder.textViewCashBack.setTag(holder);
                holder.textViewAmount.setTag(holder);
                holder.textViewStatus.setTag(holder);
                holder.textViewTimeStamp.setTag(holder);
                holder.textViewHeaderTitle.setTag(holder);
                holder.expandableLayout.setOnExpandListener(mOnExpandListener);
                holder.layoutCompatClickLayout.setOnClickListener(mOnClickListener);

                return holder;
            }else {
                LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View  view = inflater.inflate(R.layout.layout_loading_item, parent, false);
                LoadingViewHolder loadingViewHolder=new LoadingViewHolder(view);
                loadingViewHolder.progressBar.setTag(loadingViewHolder);
                return loadingViewHolder;

            }

        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            if (holder instanceof TadapterHolder) {
                final TadapterHolder holder1 = (TadapterHolder) holder;
                holder1.expandableLayout.setExpanded(models.get(position).isExpand());
                String onlyDate=models.get(position).get_created();
                String s[]=onlyDate.split(" ");
                String s1=null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    s1= Html.fromHtml("&#8377;")+models.get(position).get_amount()+"  "+s[0];
                }else {
                    s1= Html.fromHtml("&#8377;")+models.get(position).get_amount()+"  "+s[0];

                }
                holder1.textViewHeaderTitle.setText(s1);
                holder1.textViewRefId.setText(models.get(position).get_refid());
                holder1.textViewMerchantName.setText(models.get(position).get_merchantName());
                holder1.textViewAmount.setText(models.get(position).get_amount());
                holder1.textViewCashBack.setText(models.get(position).get_cashback());
                holder1.textViewStatus.setText(models.get(position).get_status());
                holder1.textViewTimeStamp.setText(models.get(position).get_created());
                if (models.get(position).get_status().equalsIgnoreCase("paid"))
                {
                    holder1.imageViewStatusImageiew.setImageResource(R.drawable.ic_successnew);
                }else if (models.get(position).get_status().equalsIgnoreCase("FAILED") ||
                        models.get(position).get_status().equalsIgnoreCase("cancelled"))
                {
                    holder1.imageViewStatusImageiew.setImageResource(R.drawable.ic_failnew);
                }else if (models.get(position).get_status().equalsIgnoreCase("waiting_for_payment"))
                {
                    holder1.imageViewStatusImageiew.setImageResource(R.drawable.ic_pendingnew);
                }else {
                    holder1.imageViewStatusImageiew.setImageResource(R.drawable.ic_pendingnew);
                }
                if (models.get(position).isExpand())
                {
                    holder1.imageViewExpandOrColapseSymbol.setImageResource(R.drawable.circle_minus);
                }else {
                    holder1.imageViewExpandOrColapseSymbol.setImageResource(R.drawable.circle_plus);
                }

            }else {
                LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
                loadingViewHolder.progressBar.setIndeterminate(true);
            }


        }


        @Override
        public int getItemCount() {
            return models.size();
        }

        @Override
        public int getItemViewType(int position) {
            if (models.get(position)!=null)
            {
                return NON_LOADING_VIEW;
            }else {
                if (transModelOut.size()<total_datas) {
                    loadMoreData();
                    return LOADING_VIEW;
                }

            }
            return 2;
        }
    }
    static   class TadapterHolder extends RecyclerView.ViewHolder
    {
        ExpandableLayout expandableLayout=null;
        private AppCompatTextView textViewRefId=null;
        private AppCompatTextView textViewMerchantName=null;
        private AppCompatTextView textViewAmount=null;
        private AppCompatTextView textViewCashBack=null;
        private AppCompatTextView textViewStatus=null;
        private AppCompatTextView textViewTimeStamp=null;
        private AppCompatImageView imageViewExpandOrColapseSymbol=null;
        private AppCompatImageView imageViewStatusImageiew=null;
        private AppCompatTextView textViewHeaderTitle=null;
        private LinearLayoutCompat layoutCompatClickLayout=null;
        TadapterHolder(View itemView) {
            super(itemView);
            this.expandableLayout= (ExpandableLayout)
                    itemView.findViewById(R.id.trans_row_out_expand_layout);
            this.textViewHeaderTitle= (AppCompatTextView) itemView.findViewById(R.id.header_title_out);
            this.textViewRefId= (AppCompatTextView) itemView.findViewById(R.id.trans_row_out_child_ref_id);
            this.textViewMerchantName= (AppCompatTextView) itemView.findViewById(R.id.trans_row_out_child_merchant_id);
            this.textViewAmount= (AppCompatTextView) itemView.findViewById(R.id.trans_row_out_child_amount);
            this.textViewCashBack= (AppCompatTextView) itemView.findViewById(R.id.trans_row_out_child_cashback);
            this.textViewStatus= (AppCompatTextView) itemView.findViewById(R.id.trans_row_out_child_status);
            this.textViewTimeStamp= (AppCompatTextView) itemView.findViewById(R.id.trans_row_out_child_time);
            this.imageViewExpandOrColapseSymbol= (AppCompatImageView) itemView.findViewById(R.id.btn_out_expand_toggle);
            this.imageViewStatusImageiew= (AppCompatImageView) itemView.findViewById(R.id.btn_status_out);
            this.layoutCompatClickLayout= (LinearLayoutCompat) itemView.findViewById(R.id.trans_row_out_click_layout);

        }
    }
    static class LoadingViewHolder extends RecyclerView.ViewHolder {
        ProgressBar progressBar;

        LoadingViewHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar1);
        }
    }

}
