package com.lambdaschool.cars;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CarsLog implements Serializable
{
    private final String msg;
    private final String brand;
    private final String formattedDate;

    public CarsLog(String msg, String brand)
    {
        this.msg = msg;
        this.brand = brand;
        Date date = new Date();
        String strDateFormat = "yyyy-MM-dd hh:mm:ss a";
        DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
        formattedDate = dateFormat.format(date);

    }
}
