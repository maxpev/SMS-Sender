package com.maxpevnitskiy.smssender;

import android.content.Context;
import android.telephony.SmsManager;


/**
 * Created by m on 23.02.2017.
 */

public class SMSSender {

    public static void sendSMS(Context context, String phoneNumber) {
        String message = "test";
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, null, null);
    }
}
