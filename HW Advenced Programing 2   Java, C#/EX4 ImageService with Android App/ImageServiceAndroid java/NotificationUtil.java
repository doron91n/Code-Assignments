package ex4.imageserviceandroid;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

public class NotificationUtil extends ContextWrapper {

    public static final String WIFI_CHANNEL_ID = "WIFI_CHANNEL";
    public static final String WIFI_CHANNEL_NAME = "WIFI CHANNEL";
    public static final String PROGRESSBAR_CHANNEL_ID = "PROGRESSBAR_CHANNEL";
    public static final String PROGRESSBAR_CHANNEL_NAME = "PROGRESSBAR CHANNEL";
    private static NotificationUtil util;
    private NotificationManager mManager;
    private Context base_context;

    /**
     * @functionName: NotificationUtil
     * @description: constructor
     * @param base: the context (the app)
     * @return: null
     */
    public NotificationUtil(Context base) {
        super(base);
        base_context = base;
        createChannels();
    }
    /**
     * @functionName: CreateNotificationUtil
     * @description: constructor
     * @param base: the context (the app)
     * @return: a instance of this NotificationUtil
     */
    public static NotificationUtil CreateNotificationUtil(Context base) {
        if (util == null) {
            util = new NotificationUtil(base);
        }
        return util;
    }
    /**
     * @functionName: createChannels
     * @description: createChannels creates the wifi and progresssbar Channels
     * @return: void
     */
    public void createChannels() {
        // create wifi channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel wifi_Channel = new NotificationChannel(WIFI_CHANNEL_ID,
                    WIFI_CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            // Sets whether notifications posted to this channel should display notification lights
            wifi_Channel.enableLights(true);
            // Sets whether notification posted to this channel should vibrate.
            wifi_Channel.enableVibration(true);
            // Sets the notification light color for notifications posted to this channel
            wifi_Channel.setLightColor(Color.GREEN);
            // Sets whether notifications posted to this channel appear on the lockscreen or not
            wifi_Channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
            getManager().createNotificationChannel(wifi_Channel);
            // progressBar channel
            NotificationChannel progressBar_Channel = new NotificationChannel(PROGRESSBAR_CHANNEL_ID,
                    PROGRESSBAR_CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            progressBar_Channel.setDescription("Progress bar for image transfer");
            getManager().createNotificationChannel(progressBar_Channel);
        }

    }
    /**
     * @functionName: getWifiChannelNotification
     * @description: getWifiChannelNotification returns a instance of wifi NotificationCompat.Builder
     * @return: Wifi NotificationCompat.Builder
     */
    public Notification.Builder getWifiChannelNotification(String title, String body) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return new Notification.Builder(getApplicationContext(), WIFI_CHANNEL_ID)
                    .setContentTitle(title)
                    .setContentText(body)
                    .setSmallIcon(android.R.drawable.stat_notify_more)
                    .setAutoCancel(true);
        }
        return null;
    }
    /**
     * @functionName: getProgressBarChannelNotification
     * @description: getProgressBarChannelNotification returns a instance of PROGRESSBAR NotificationCompat.Builder
     * @return: PROGRESSBAR NotificationCompat.Builder
     */
    public NotificationCompat.Builder getProgressBarChannelNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return new NotificationCompat.Builder(getApplicationContext(), PROGRESSBAR_CHANNEL_ID)
                    .setContentTitle("Picture Transfer")
                    .setContentText("Transfer in progress..")
                    .setSmallIcon(android.R.drawable.stat_notify_more)
                    .setAutoCancel(true);
        }
        return null;
    }
    /**
     * @functionName: getManager
     * @description: getManager returns a instance of this NotificationManager
      * @return: mManager: the NotificationManager
     */
    public NotificationManager getManager() {
        if (mManager == null) {
            mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return mManager;
    }
}