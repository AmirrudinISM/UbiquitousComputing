package com.example.partikhmal;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBController extends SQLiteOpenHelper {

    public DBController(Context context) {

        super(context, "HealthDB.db", null, 2);

    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("" +
                "CREATE TABLE Appointments(" +
                "appointmentID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "appointmentDate TEXT, " +
                "appointmentTime TEXT, " +
                "facilityName TEXT, " +
                "vaccineName TEXT, " +
                "status TEXT, " +
                "userID TEXT)");
        DB.execSQL("create Table UserProfile(UID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, name TEXT,email TEXT, dob TEXT, ic TEXT, phone TEXT, password TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int i1) {

        DB.execSQL("drop Table if exists UserProfile");
        onCreate(DB);
    }


    public Long createUser(String name, String email, String dob, String ic, String phone, String password){


        Log.d("name=", name);
        Log.d("emailString=", email);
        Log.d("dobString=", dob);
        Log.d("icString=", ic);
        Log.d("phoneString=", phone);
        Log.d("pass1String=", password);

        SQLiteDatabase database= this.getWritableDatabase();

        Cursor cursor = database.rawQuery("Select * from UserProfile where email=?", new String[]{email});
        long result;

        if(cursor.getCount()==0){


            ContentValues contentValues= new ContentValues();

            contentValues.put("name", name);
            contentValues.put("email", email);
            contentValues.put("dob", dob);
            contentValues.put("ic", ic);
            contentValues.put("phone", phone);
            contentValues.put("password", password);

            result= database.insert("UserProfile",null,contentValues);

        }else{

            result= -2;

        }
        return  result;

    }




    public Cursor findUser (String email)
    {
        SQLiteDatabase database= this.getWritableDatabase();
        Cursor cursor = database.rawQuery("Select * from UserProfile where email=?", new String[]{email});
        return cursor;
    }

    public void insertAppointmentData(String inDate, String inTime, String inFacility, int inUserID){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("appointmentDate", inDate);
        values.put("appointmentTime", inTime);
        values.put("facilityName", inFacility);
        values.put("vaccineName", "");
        values.put("status", "INCOMPLETE");
        values.put("userID", inUserID);

        db.insert("Appointments",null,values);
        db.close();
    }

    public ArrayList<Appointment> getAllAppointments(int inUserID){
        ArrayList<Appointment> appointments = new ArrayList<Appointment>();
        SQLiteDatabase db = getReadableDatabase();
        Log.d("userID", String.valueOf(inUserID));
        String query = "SELECT * FROM Appointments WHERE userID = ?";
        Cursor cursor = db.rawQuery(query, new String[]{Integer.toString(inUserID)});

        try{
            while (cursor.moveToNext()){
                Appointment temp = new Appointment();
                temp.setAppointmentID(cursor.getInt(cursor.getColumnIndexOrThrow("appointmentID")));
                temp.setAppointmentDate(cursor.getString(cursor.getColumnIndexOrThrow("appointmentDate")));
                temp.setAppointmentTime(cursor.getString(cursor.getColumnIndexOrThrow("appointmentTime")));
                temp.setFacilityName(cursor.getString(cursor.getColumnIndexOrThrow("facilityName")));
                temp.setVaccineName(cursor.getString(cursor.getColumnIndexOrThrow("vaccineName")));
                temp.setStatus(cursor.getString(cursor.getColumnIndexOrThrow("status")));
                temp.setUserID(cursor.getInt(cursor.getColumnIndexOrThrow("userID")));
                appointments.add(temp);
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        finally {
            if(cursor != null){
                cursor.close();
            }
        }
        return  appointments;
    }

    public Cursor getAppointment(int appointmentID){
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT facilityName, appointmentDate, appointmentTime, vaccineName FROM Appointments WHERE appointmentID = ?";
        Cursor cursor = db.rawQuery(query, new String[]{Integer.toString(appointmentID)});
        return cursor;
    }

    public void updateAppointment(int appointmentID, String inVaccine, String inStatus){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("vaccineName", inVaccine);
        values.put("status", inStatus);

        db.update("Appointments", values, "appointmentID=?", new String[]{String.valueOf(appointmentID)});
        db.close();

    }

    public int getNumberOfVaccinations(int userID){
        SQLiteDatabase db = getReadableDatabase();
        //String query = "SELECT COUNT(status) FROM Appointments WHERE appointmentID = ? AND status = ?";
        int count = (int) DatabaseUtils.queryNumEntries(db,"Appointments", "userID = ? AND status = ?",new String[]{Integer.toString(userID),"COMPLETE"});

        //db.rawQuery(query, new String[]{Integer.toString(userID),"COMPLETE"});
        return count;
    }
}
