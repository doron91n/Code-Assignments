package ex4.imageserviceandroid;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

public class WifiReceiver extends BroadcastReceiver {

    private final String TAG = getClass().getSimpleName();
    private Context mContext;
    /**
     * @functionName: onReceive
     * @description: invoked when reciver gets new intent
     * @param context: the context (the app)
     * @param intent: the intent info
     * @return: null
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "@@@@@@@@@@@@@@@@@@@WifiReceiver    onReceive   ");
        mContext = context;
        if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {

            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();

            if (networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_WIFI &&
                    networkInfo.isConnected()) {
                // Wifi is connected
                WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                String ssid = wifiInfo.getSSID();
                Log.e(TAG, " -- Wifi connected --- " + " SSID " + ssid);
                Log.e(TAG, "connecting to server");

                ConnectionToServer connection = new ConnectionToServer(context);
            }
        } else if (intent.getAction().equalsIgnoreCase(WifiManager.WIFI_STATE_CHANGED_ACTION)) {
            int wifiState = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, WifiManager.WIFI_STATE_UNKNOWN);
            if (wifiState == WifiManager.WIFI_STATE_DISABLED) {
                Log.e(TAG, " ----- Wifi  Disconnected ----- ");
            }

        }
    }
}

