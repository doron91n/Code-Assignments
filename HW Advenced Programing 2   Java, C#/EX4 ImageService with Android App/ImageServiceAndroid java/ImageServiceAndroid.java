package ex4.imageserviceandroid;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

public class ImageServiceAndroid extends Service {

    private WifiReceiver wifi_receiver;
    /**
     * @functionName: onBind
     * @description: onBind
     * @param intent: the intent
     * @return: null
     */
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    /**
     * @functionName: onCreate
     * @description: onCreate creates the service
      * @return: null
     */
    @Override
    public void onCreate() {
        Log.d("ImageServiceAndroid: ", "###########################   in onCreate");
        super.onCreate();
        Toast.makeText(this, "Service created...", Toast.LENGTH_SHORT).show();
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        wifi_receiver = new WifiReceiver();
        registerReceiver(wifi_receiver, filter);
    }
    /**
     * @functionName: onDestroy
     * @description: onDestroy destroy the service
     * @return: null
     */
    @Override
    public void onDestroy() {
        Log.d("ImageServiceAndroid: ", "###########################   in onDestroy");
        Toast.makeText(this, "Service ending...", Toast.LENGTH_SHORT).show();
        this.unregisterReceiver(wifi_receiver);
        super.onDestroy();
    }
    /**
     * @functionName: onStartCommand
     * @description: onStartCommand starts the service
     * @param intent: the intent
     * @param flags: the flags
     * @param start_id: the start_id
     * @return: START_STICKY - some int
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int start_id) {
        Log.d("ImageServiceAndroid: ", "###########################   in onStartCommand");
        Toast.makeText(this, "Service starting...", Toast.LENGTH_SHORT).show();
        return START_STICKY;
    }
}
