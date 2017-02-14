package com.example.skilledanswers_d2.wallettransfer;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MyRecyclerView extends RecyclerView
        .Adapter<MyRecyclerView
        .DataObjectHolder> {
    private static String LOG_TAG = "MyRecyclerViewAdapter";
    public static int totalvalue ;

    private ArrayList<DataObject> mDataset;
    private static MyClickListener myClickListener;
    Context context;
    private List<DataObject> _retData;
    int sum;
    static String s;
    ArrayList<Integer> list;

    public static class DataObjectHolder extends RecyclerView.ViewHolder
             {
        TextView label;
        AppCompatEditText appCompatEditText;


        public DataObjectHolder(View itemView) {
            super(itemView);
            label = (TextView) itemView.findViewById(R.id.name);
            appCompatEditText=(AppCompatEditText)itemView.findViewById(R.id.percentage);

//            itemView.setOnClickListener(this);
        }

        /*@Override
        public void onClick(View v) {
            myClickListener.onItemClick(getAdapterPosition(), v);
        }*/
    }

    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public MyRecyclerView(ArrayList<DataObject> myDataset) {
        mDataset = myDataset;


    }

    public MyRecyclerView()
    {

    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.selected_items_contact, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }


    @Override
    public void onBindViewHolder(final DataObjectHolder holder, final int position) {
        holder.label.setText(mDataset.get(position).getmText1());
        holder.appCompatEditText.setText(String.valueOf(mDataset.get(position).getmText2()));

        holder.appCompatEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                try {
                    int j;
                   list= new ArrayList<Integer>(); // define this is as globally

                    Integer integer = Integer.valueOf(holder.appCompatEditText.getText().toString());
                    list.add(integer);
                    for ( j = 0; j < list.size(); j++) {

                        sum += list.get(j);
                        Log.e("sum---------", "" + sum);
                    }
                    Log.e("valuearray---------", "" + sum);
                    if (sum == 100) {
                        totalvalue = sum;
                    }
                    else {
                        totalvalue =0;
                        if(list.size()>0)
                        {
                            list.clear();
                        }
                    }


                } catch (NumberFormatException e) {

                }
            }
        });

    }



    public void addItem(DataObject dataObj, int index) {
        mDataset.add(index, dataObj);
        notifyItemInserted(index);
    }
    public void deleteItem(int index) {
        if (mDataset.size() > index) {
            mDataset.remove(index);
            notifyDataSetChanged();

        }
    }


    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }


}