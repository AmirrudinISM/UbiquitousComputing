package com.example.academio;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class CustomListAdapter extends BaseAdapter implements ListAdapter {
    private ArrayList<String> credentialsList = new ArrayList<String>();
    private ArrayList<String> gradYearList = new ArrayList<String>();
    private Context context;

    public CustomListAdapter(ArrayList<String> inCredList, ArrayList<String> inYearList, Context context){
        this.credentialsList = inCredList;
        this.gradYearList = inYearList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return credentialsList.size();
    }

    @Override
    public Object getItem(int i) {
        return credentialsList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.singlelistitem, null);
        }
        TextView displayCred= (TextView)view.findViewById(R.id.lblQualification);
        displayCred.setText((i+1)+ ". "+ credentialsList.get(i));
        TextView displayYear= (TextView)view.findViewById(R.id.lblGradYear);
        displayYear.append(gradYearList.get(i));
        return view;
    }
}
