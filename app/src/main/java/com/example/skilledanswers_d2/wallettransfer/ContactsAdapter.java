package com.example.skilledanswers_d2.wallettransfer;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder> {
        private ArrayList<String> names;
    private ArrayList<String> phone;

        public ContactsAdapter(ArrayList<String> countries) {
            this.names = countries;
            this.phone=phone;
        }

        @Override
        public ContactsAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.selected_items_contact, viewGroup, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ContactsAdapter.ViewHolder viewHolder, int i) {

            viewHolder.textViewName.setText(names.get(i));
//            viewHolder.textViewPhone.setText(phone.get(i));
        }

        @Override
        public int getItemCount() {
            return names.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder{
            TextView textViewName,textViewPhone;
            public ViewHolder(View view) {
                super(view);

                textViewName=(TextView)view.findViewById(R.id.name);
//                textViewPhone=(TextView)view.findViewById(R.id.phone);
            }
        }

    }
