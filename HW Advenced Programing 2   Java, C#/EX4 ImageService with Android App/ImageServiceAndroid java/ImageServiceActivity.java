package ex4.imageserviceandroid;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

public class ImageServiceActivity extends AppCompatActivity {

    private final int REQUEST_PERMISSION = 101;
    private final String TAG = getClass().getSimpleName();
    private Context context;
    private NotificationUtil notificationUtils;
    private ProgressReceiver progressBar_reciver;
    /**
     * @functionName: onCreate
     * @description: onCreate creates the app
     * @param savedInstanceState: the context (the app) last saved state
     * @return: null
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context = this;
        Log.d(TAG, "###########################   in onCreate ");

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSION);
        } else {
            Log.d(TAG, "###########################   in onCreate READ_EXTERNAL_STORAGE PERMISSION failed");
        }
        setContentView(R.layout.activity_image_service);
        notificationUtils = NotificationUtil.CreateNotificationUtil(this);
        registerProgressBarReciver();
        super.onCreate(savedInstanceState);
    }
    /**
     * @functionName: registerProgressBarReciver
     * @description: registers the ProgressBar Reciver
      * @return: null
     */
    public void registerProgressBarReciver() {
        IntentFilter filter = new IntentFilter(notificationUtils.PROGRESSBAR_CHANNEL_ID);
        progressBar_reciver = ProgressReceiver.CreateProgressReceiver();
        registerReceiver(progressBar_reciver, filter);
    }
    /**
     * @functionName: onPause
     * @description: onPause pauses the app
     * @return: null
     */
    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "###########################   in onPause ");
        if (progressBar_reciver != null) {
            Log.d(TAG, "###########################   in onPause  unregister progressBar reciver ");
            unregisterReceiver(progressBar_reciver);
        }
    }
    /**
     * @functionName: onResume
     * @description: onResume resums the app
\     * @return: null
     */
    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "###########################   in onResume  register progressBar reciver ");
        registerProgressBarReciver();
    }
    /**
     * @functionName: startService
     * @description: starts the imageService
     * @param view: the view (the app)
     * @return: null
     */
    public void startService(View view) {
        Intent intent = new Intent(this, ImageServiceAndroid.class);
        Log.d("ImageServiceActivity: ", "###########################   in startService");
        registerProgressBarReciver();
        startService(intent);
    }
    /**
     * @functionName: stopService
     * @description: stops the imageService
     * @param view: the view (the app)
     * @return: null
     */
    public void stopService(View view) {
        Intent intent = new Intent(this, ImageServiceAndroid.class);
        Log.d("ImageServiceActivity: ", "###########################   in stopService");
        unregisterReceiver(progressBar_reciver);
        stopService(intent);
    }
    /**
     * @functionName: onRequestPermissionsResult
     * @description: request needed permissions if not granted already
     * @param requestCode: the request Code
     * @param permissions: the permissions
     * @param grantResults: the grant Results
     * @return: null
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        int storagePermission = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (storagePermission == PackageManager.PERMISSION_GRANTED) {
            finish();
            startActivity(getIntent());
        } else {
            finish();
        }
    }

}
