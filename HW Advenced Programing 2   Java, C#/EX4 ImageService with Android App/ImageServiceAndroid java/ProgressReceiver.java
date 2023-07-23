package ex4.imageserviceandroid;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class ProgressReceiver extends BroadcastReceiver {

    private static ProgressReceiver reciver;
    private final String TAG = getClass().getSimpleName();
    private Context mContext = null;
    private NotificationUtil notificationUtil;

    /**
     * @functionName: CreateProgressReceiver
     * @description: constructor
     * @return: a instance of this ProgressReceiver
     */
    public static ProgressReceiver CreateProgressReceiver() {
        if (reciver == null) {
            reciver = new ProgressReceiver();
        }
        return reciver;
    }
    /**
     * @functionName: onReceive
     * @description: invoked when reciver gets new intent
     * @param context: the context (the app)
     * @param intent: the intent info
     * @return: null
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "@@@@@@@@@@@@@@@@@@@   PROGRESSReceiver    onReceive() intent: " + intent.getAction() + "total img num:" + intent.getExtras().toString());
        Bundle extras = intent.getExtras();
        int total_num = 0;
        int current_num = 0;
        if (extras != null) {
            total_num = Integer.parseInt(extras.getString("TOTAL_IMAGE_NUM"));
            current_num = Integer.parseInt(extras.getString("CURRENT_IMAGE_NUM"));
            Log.d(TAG, "@@@@@@@@@@@@@@@@@@@   PROGRESSReceiver    onReceive() total img num:" + total_num + " current:" + current_num);
        }
        final int f_total_num = total_num;
        final int f_current_num = current_num;
        mContext = context;
        notificationUtil = NotificationUtil.CreateNotificationUtil(mContext);
        final NotificationManager notificationManager = notificationUtil.getManager();
        final NotificationCompat.Builder builder = notificationUtil.getProgressBarChannelNotification();
        final int notify_id = 1;
        Log.d(TAG, "@@@@@@@@@@@@@@@@@@@   PROGRESSReceiver    onReceive()  doing the bar now");
        if (f_total_num == f_current_num) {
            builder.setContentText("Image Transfer complete").setProgress(0, 0, false);
        } else {
            builder.setProgress(f_total_num, f_current_num, false);
        }
        notificationManager.notify(notify_id, builder.build());
    }

}

