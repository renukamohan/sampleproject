package com.example.skilledanswers_d2.wallettransfer;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.skilledanswers_d2.wallettransfer.flowactivities.LoadOrPay;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ListViewForContact extends BaseActivity implements AdapterView.OnItemClickListener {

    List<String> name1 = new ArrayList<String>();
    List<String> phno1 = new ArrayList<String>();
    TextView textView;
    MyAdapter ma ;
    Button select;
    String hindi,kannada,telugu,tamil,english;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RelativeLayout relativeLayout=(RelativeLayout)findViewById(R.id.content_base);
        getLayoutInflater().inflate(R.layout.display,relativeLayout);
        //
        textView=(TextView)findViewById(R.id.pick);
        toggle.setDrawerIndicatorEnabled(false);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performBack();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getAllContacts(getContentResolver());
        ListView lv= (ListView) findViewById(R.id.lv);
        ma = new MyAdapter();
        lv.setAdapter(ma);
        lv.setOnItemClickListener(this);
        lv.setItemsCanFocus(false);
        lv.setTextFilterEnabled(true);
        // adding
        select = (Button) findViewById(R.id.button_select);
        select.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v) {

                StringBuilder checkedcontacts= new StringBuilder();
                StringBuilder checkedphone= new StringBuilder();
                System.out.println(".............."+ma.mCheckStates.size());
                for(int i = 0; i < name1.size(); i++)

                {
                    if(ma.mCheckStates.get(i)==true)
                    {
                        checkedcontacts.append(name1.get(i).toString());
                        checkedcontacts.append("\n");
                        checkedphone.append(phno1.get(i).toString());
                        checkedphone.append("\n");
                        String[] myArray = checkedcontacts.toString().split(" ");
                        String[] phone = checkedphone.toString().split(" ");
                        Log.e("djkdd",""+ Arrays.toString(phone));
                        Intent intent=new Intent(getApplicationContext(),MiniPercentage.class);
                        intent.putExtra("name",Arrays.toString(myArray));
                        intent.putExtra("phone",Arrays.toString(phone));
                        startActivity(intent);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);

                    }
                    else
                    {
                       Log.e("","Not Checked......"+name1.get(i).toString());
                    }

                }

                Toast.makeText(getApplicationContext(), checkedcontacts,Toast.LENGTH_SHORT).show();
            }
        });
        SharedPreferences pref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        english = pref.getString("english","");
        hindi = pref.getString("hindi","");
        kannada = pref.getString("kannada","");
        tamil = pref.getString("tamil","");
        telugu = pref.getString("telugu","");



        if(english.equalsIgnoreCase("1"))
        {
            Log.e("english",""+english);
            updateViews("en");
        }
        else if(hindi.equalsIgnoreCase("2"))
        {
            Log.e("hindi",""+hindi);
            updateViews("hi");

        }
        else if(kannada.equalsIgnoreCase("3"))
        {
            Log.e("kannada",""+kannada);
            updateViews("kn");
        }
        else if(tamil.equalsIgnoreCase("4"))
        {
            Log.e("tamil",""+tamil);
            updateViews("ta");

        }
        else if(telugu.equalsIgnoreCase("5"))
        {
            Log.e("telugu",""+telugu);

            updateViews("te");

        }


    }
    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        // TODO Auto-generated method stub
        ma.toggle(arg2);
    }

    public  void getAllContacts(ContentResolver cr) {
//        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null,
//                null, null,  "upper("+ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + ") ASC");
        Cursor phones = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null,null, null);
        while (phones.moveToNext())
        {
            String name=phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            System.out.println(".................."+phoneNumber);
            name1.add(name);
            phno1.add(phoneNumber);
        }

        phones.close();
        sortList(name1);
    }

    private static void sortList(List<String> aItems){
        Collections.sort(aItems, String.CASE_INSENSITIVE_ORDER);
    }

    class MyAdapter extends BaseAdapter implements CompoundButton.OnCheckedChangeListener
    {  private SparseBooleanArray mCheckStates;
        LayoutInflater mInflater;
        TextView tv1,tv;
        CheckBox cb;
        MyAdapter()
        {
            mCheckStates = new SparseBooleanArray(name1.size());
            mInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return name1.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub

            return 0;
        }



        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            View vi=convertView;
            if(convertView==null)
                vi = mInflater.inflate(R.layout.row, null);
            TextView tv= (TextView) vi.findViewById(R.id.contact_name);
            tv1= (TextView) vi.findViewById(R.id.phone_number);
            cb = (CheckBox) vi.findViewById(R.id.checkBox_id);
            tv.setText("Name :"+ name1.get(position));
            tv1.setText("Phone No :"+ phno1.get(position));
            cb.setTag(position);
            cb.setChecked(mCheckStates.get(position, false));
            cb.setOnCheckedChangeListener(this);

            return vi;
        }
        public boolean isChecked(int position) {

            return mCheckStates.get(position, false);
        }

        public void setChecked(int position, boolean isChecked) {
            mCheckStates.put(position, isChecked);
            Log.e("hello...........","hhhjklhlkj");
            notifyDataSetChanged();
        }

        public void toggle(int position) {
            setChecked(position, !isChecked(position));
        }
        @Override
        public void onCheckedChanged(CompoundButton buttonView,
                                     boolean isChecked) {
            // TODO Auto-generated method stub

            mCheckStates.put((Integer) buttonView.getTag(), isChecked);
        }
    }
    @Override
    public void onBackPressed() {
        performBack();
    }
    private void performBack()
    {
        if (isTaskRoot())
        {
            Intent intentLoadCash=new Intent(ListViewForContact.this, LoadOrPay.class);
            intentLoadCash.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intentLoadCash.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intentLoadCash.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentLoadCash);
        }else {
            finish();
        }
    }
    private void updateViews(String languageCode) {
        Context context = LocaleHelper.setLocale(this, languageCode);
        Resources resources = context.getResources();
        textView.setText(resources.getString(R.string.pick));
        select.setText(resources.getString(R.string.select));


    }
}
