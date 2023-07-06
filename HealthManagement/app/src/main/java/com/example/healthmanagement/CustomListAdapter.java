package com.example.healthmanagement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

//This class is to acommodate all the necessary information of an appointment
//and display it in a form of a listview
public class CustomListAdapter extends BaseAdapter implements ListAdapter {

    //the items to display in the list view
    private ArrayList<Appointment> appointments;

    //the current activity the listview is being used in
    private Context context;

    //Overridden constructor. Needs to be passed in an arraylist of appointments
    //as well as the the activity context that uses it.
    CustomListAdapter(ArrayList<Appointment> inAppointments, Context context){
        this.appointments = inAppointments;
        this.context = context;
    }

    @Override
    public int getCount() {
        return appointments.size();
    }

    @Override
    public Object getItem(int i) {
        return appointments.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.singleappointment, null);
        }
        //displays appointment ID in textView
        TextView displayID = (TextView)view.findViewById(R.id.appointmentID);
        displayID.setText(Integer.toString(appointments.get(i).getAppointmentID()));

        //displays vaccination center name in textView
        TextView displayLocation= (TextView)view.findViewById(R.id.lblLocation);
        displayLocation.setText(appointments.get(i).getFacilityName());

        //displays appointment date & time  in textView
        TextView displayDateTime= (TextView)view.findViewById(R.id.lblDateTime);
        displayDateTime.setText(appointments.get(i).getAppointmentDate() + ", " + appointments.get(i).getAppointmentTime());

        //displays the status of the appointment; INCOMPLETE, CANCELED, COMPLETE
        TextView displayAppointmentStatus = (TextView) view.findViewById(R.id.lblStatus);
        displayAppointmentStatus.setText("Status: "+ appointments.get(i).getStatus());
        return view;
    }
}
