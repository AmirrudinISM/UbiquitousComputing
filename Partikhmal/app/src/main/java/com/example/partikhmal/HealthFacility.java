package com.example.partikhmal;

import java.util.ArrayList;

public class HealthFacility {
    private String facilityName;
    private String facilityAddress;
    private String type;
    private double latitude;
    private double longitude;

    public HealthFacility() {
    }

    public HealthFacility(String facilityName, String facilityAddress, String type, double latitude, double longitude) {
        this.facilityName = facilityName;
        this.facilityAddress = facilityAddress;
        this.type = type;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getFacilityName() {
        return facilityName;
    }

    public void setFacilityName(String facilityName) {
        this.facilityName = facilityName;
    }

    public String getFacilityAddress() {
        return facilityAddress;
    }

    public void setFacilityAddress(String facilityAddress) {
        this.facilityAddress = facilityAddress;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public ArrayList<HealthFacility> getHealthFacilities(){
        ArrayList<HealthFacility> res = new ArrayList<>();

        res.add(new HealthFacility("Bangsar Medical Centre","9, Jalan Bangsar, Bangsar, 59000 Kuala Lumpur, Wilayah Persekutuan Kuala Lumpur","PRIVATE",3.13014580382958,101.680363868578));
        res.add(new HealthFacility("Tanglin Health Clinic","Jalan Cenderasari, Tasik Perdana, 50480 Kuala Lumpur, Wilayah Persekutuan Kuala Lumpur","PUBLIC",3.1448998315424,101.69093004734817));
        res.add(new HealthFacility("University of Malaya Medical Centre","Jln Profesor Diraja Ungku Aziz, 59100 Kuala Lumpur, Selangor","PRIVATE",3.11599620500033,101.65282081904515));
        res.add(new HealthFacility("Beacon Hospital Sdn Bhd","No. 1, Jalan 215, Off, Jalan Templer, Section 51, 46050 Petaling Jaya, Selangor","PRIVATE",3.09508427307627,101.645625553934));
        res.add(new HealthFacility("KPJ Damansara Specialist Hospital","119, Jalan SS 20/10, Damansara Utama, 47400 Petaling Jaya, Selangor","PRIVATE",3.13785633986825,101.627486374609));
        res.add(new HealthFacility("Columbia Asia Hospital - Petaling Jaya","Lot 69, Jalan 13/6, Seksyen 13, 46200 Petaling Jaya, Selangor","PRIVATE",3.11876255352813,101.637577749515));
        res.add(new HealthFacility("Assunta Hospital PJ","Jalan Templer, Pjs 4, 46990 Petaling Jaya, Selangor","PRIVATE",3.09366137853255,101.645778788318));
        res.add(new HealthFacility("Ampang Hospital","Hospital Ampang, Jalan Mewah Utara, Taman Pandan Mewah, 68000 Ampang Jaya, Selangor","PUBLIC",3.12825173523524,101.763995476149));
        res.add(new HealthFacility("KPJ Tawakkal KL Specialist Hospital","1, Jalan Pahang Barat, Pekeliling, 53000 Kuala Lumpur, Wilayah Persekutuan Kuala Lumpur","PRIVATE",3.17713933867371,101.698630684574));
        res.add(new HealthFacility("Rawang Health Clinic","Jalan Rawang Perdana, Taman Rawang Perdana, 48000 Rawang, Selangor","PUBLIC",3.31482322743885,101.5958694116));
        res.add(new HealthFacility("Bentong Hospital","Jalan Padang, 28700 Bentong, Pahang","PUBLIC",3.52532746889601,101.905456997694));
        res.add(new HealthFacility("Tuanku Ja'afar Hospital, Seremban","Jalan Rasah, Bukit Rasah, 70300 Seremban, Negeri Sembilan","PUBLIC",2.71005176700032,101.944969485897));
        res.add(new HealthFacility("Dong Health Clinic","Ulu Dong Raub","PUBLIC",3.90096408379437,101.895483551227));
        res.add(new HealthFacility("Raub Hospital","Jalan Tengku Abdul Samad, Kampung Baru Sungai Lui, 27600 Raub, Pahang","PUBLIC",3.80992539222182,101.851424725602));
        res.add(new HealthFacility("Klinik Ragavan","1, Jalan Dato Abdullah, 27600 Raub, Pahang","PRIVATE",3.79415518903666,101.855074838982));
        res.add(new HealthFacility("Teluk Intan Hospital","Jalan Changkat Jong, 36000 Teluk Intan, Perak","PUBLIC",4.0046759617028,101.040302897747));

        return res;
    }
}
