package com.example.healthmanagement;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Settings2F#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Settings2F extends Fragment {

    Button logout;
    Switch fpswitch;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Settings2F() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Settings2F.
     */
    // TODO: Rename and change types and number of parameters
    public static Settings2F newInstance(String param1, String param2) {
        Settings2F fragment = new Settings2F();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings2, container, false);

        //define button

        fpswitch=(Switch)view.findViewById(R.id.switchfp) ;
        logout=(Button)view.findViewById(R.id.btnlogout) ;

        //set sharedpreference
        SharedPreferences preferences = this.getActivity().getSharedPreferences("settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        String useFP= this.getActivity().getSharedPreferences("settings", Context.MODE_PRIVATE).getString("fp","");

        //set switch button to checked or not based on fingerprint setting that has been saved by the user
        if(useFP.equals("enable")){
            fpswitch.setChecked(true);
        }else{
            fpswitch.setChecked(false);
        }

        // Inflate the layout for this fragment


        // saved new setting when user switch the button
        if(fpswitch.isChecked()){
            editor.putString("fp", "enable");
            editor.commit();
        }else{
            editor.putString("fp", "disable");
            editor.commit();
        }




        //trigger logout function when user click logout
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //empty the shared preference data
                editor.putString("uid", "" );
                editor.putString("name","" );
                editor.putString("email","" );
                editor.putString("userIC","");
                editor.putString("fp", "disable");
                editor.commit();

                //return to loginActivity
                Intent intent = new Intent(getActivity(),LoginActivity.class);
                startActivity(intent);

            }
        });

        return view;

    }
}