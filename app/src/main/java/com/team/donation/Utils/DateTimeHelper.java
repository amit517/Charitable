package com.team.donation.Utils;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;



public class DateTimeHelper {


    public static String getDate(){

        return new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
    }


    public static String getTime(){

        return new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
    }


}
