package com.newsman.newsman.Auxiliary;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Me on 1/10/2019.
 */

public class DateGetter {

    public static Date getDate(String date)throws ParseException
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

        Date properDate = sdf.parse(date);
        return properDate;
    }
}
