package com.example.healthmanagement;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import android.widget.ImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.content.Intent;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Menu1F#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Menu1F extends Fragment {


    //Declare variables
    ImageView vaccineIcon,nearbyIcon, bmiIcon, bulletinIcon;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Menu1F() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Menu1F.
     */
    // TODO: Rename and change types and number of parameters
    public static Menu1F newInstance(String param1, String param2) {

        //Callout the variable and id
        Menu1F fragment = new Menu1F();
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

        View view =  inflater.inflate(R.layout.fragment_menu1, container, false);

        //Callout variable and id
        vaccineIcon = (ImageView) view.findViewById(R.id.vaccineImage);
        nearbyIcon = (ImageView) view.findViewById(R.id.nearbyImage);
        bmiIcon = (ImageView) view.findViewById(R.id.bmiImage);
        bulletinIcon = (ImageView) view.findViewById(R.id.bulletinImage);

        //Onclick icon to another activity
        vaccineIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),VaccineReserActivity.class);
                startActivity(intent);

            }
        });

        nearbyIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),HealthMaps.class);
                startActivity(intent);

            }
        });

        bmiIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),BMIActitvity.class);
                startActivity(intent);

            }
        });

        bulletinIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),BulletinActivity.class);
                startActivity(intent);

            }
        });

        return view;
    }
}