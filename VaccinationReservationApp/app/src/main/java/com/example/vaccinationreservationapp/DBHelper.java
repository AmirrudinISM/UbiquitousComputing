package com.example.vaccinationreservationapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {


    public DBHelper(@Nullable Context context) {
        super(context, "Appointment.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("" +
                "CREATE TABLE Appointments(" +
                    "appointmentID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "appointmentDate TEXT, " +
                    "appointmentTime TEXT, " +
                    "facilityName TEXT, " +
                    "vaccineName TEXT, " +
                    "status TEXT, " +
                    "userID TEXT)");

        sqLiteDatabase.execSQL("create Table UserProfile(UID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, name TEXT,email TEXT, dob TEXT, ic TEXT, phone TEXT, password TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Appointments");
        onCreate(sqLiteDatabase);
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
}
