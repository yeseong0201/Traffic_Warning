package com.example.ys020.sms_sendreceive;

import android.content.BroadcastReceiver;

public class SMSBroadcast extends BroadcastReceiver{
    @Override
    public void onReceive(Context mContext, Intent intent) {
        String action =  intent.getAction();

        if("android.provider.Telephony.SMS_RECEIVED".equals(action)){
            Bundle bundle = intent.getExtras();
            Object messages[] = (Object[])bundle.get("pdus");
            SmsMessage smsMessage[] = new SmsMessage[messages.length];

            for(int i = 0; i < messages.length; i++) {
                smsMessage[i] = SmsMessage.createFromPdu((byte[]) messages[i]);
            }

            Date curDate = new Date(smsMessage[0].getTimestampMillis());
            SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyy년 MM월 HH시 mm분 ss초 ", Locale.KOREA);

            String originDate = mDateFormat.format(curDate);
            String origNumber = smsMessage[0].getOriginatingAddress();
            String Message = smsMessage[0].getMessageBody().toString();

            Intent showSMSIntent = new Intent(mContext, ShowSMSActivity.class);
            showSMSIntent.putExtra("originNum", origNumber);
            showSMSIntent.putExtra("smsDate", originDate);
            showSMSIntent.putExtra("originText", Message);
            showSMSIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            mContext.startActivity(showSMSIntent);
        }
    }


    출처: http://jwprogramming.tistory.com/166?category=680197 [개발자를 꿈꾸는 프로그래머]
}
