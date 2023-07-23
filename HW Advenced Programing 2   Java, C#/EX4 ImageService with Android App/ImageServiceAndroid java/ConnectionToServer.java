package ex4.imageserviceandroid;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;


public class ConnectionToServer {


    public static final String newLine = System.getProperty("line.separator");
    private static final String endSend = "EndSend" + newLine;
    private final String ip = "10.0.2.2";
    private final int port = 8200;
    private Map<String, byte[]> outgoing_images;
    private String TAG = getClass().getSimpleName();
    private Context m_context;

    /**
     * @functionName: ConnectionToServer
     * @description: constructor
     * @param context: the context (the app)
     * @return: null
     */
    public ConnectionToServer(Context context) {
        Log.d(TAG, "###########################   in ConnectionToServer constructor");
        m_context = context;
        sendImages();
    }

    /**
     * @functionName: getImagesFromAndroid
     * @description: reads all the images in the DCIM/Camera folder and saves to map
     * @return: null
     */
    public void getImagesFromAndroid() {
        FileInputStream fis = null;
        this.outgoing_images = new HashMap<>();
        Log.d(TAG, "###########################   getImages");
        // Getting the Camera Folder
        String dcim_camera_dir = Environment.getExternalStorageDirectory().getAbsolutePath()
                + File.separator + Environment.DIRECTORY_DCIM + File.separator + "Camera";
        File camera_dir = new File(dcim_camera_dir);
        if (camera_dir == null) {
            return;
        }
        File[] image_files_from_Android = camera_dir.listFiles();
        if (image_files_from_Android == null) {
            return;
        }
        for (File image_file : image_files_from_Android) {
            try {
                fis = new FileInputStream(image_file);
                Bitmap bitmap = BitmapFactory.decodeStream(fis);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 70, stream);
                this.outgoing_images.put(image_file.getName(), stream.toByteArray());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        Log.d(TAG, "###########################   getImages final outgoing size:" + outgoing_images.size());
    }

    /**
     * @functionName: updateProgressBar
     * @description: updates the ProgressBar based on images sent number
     * @param total_image_num: the number of images to transfer
     * @param curr_image_num: the number of images already transfered
     * @return: null
     */
    public void updateProgressBar(final int total_image_num, final int curr_image_num) {
        Log.d(TAG, "###########################   updateProgressBar2 new iteration: i= " + curr_image_num + "  out of: " + total_image_num);
        new Thread(new Runnable() {
            @Override
            public void run() {
                Intent broadcastIntent = new Intent();
                broadcastIntent.setAction(NotificationUtil.PROGRESSBAR_CHANNEL_ID);
                broadcastIntent.putExtra("TOTAL_IMAGE_NUM", Integer.toString(total_image_num));
                broadcastIntent.putExtra("CURRENT_IMAGE_NUM", Integer.toString(curr_image_num));
                m_context.sendBroadcast(broadcastIntent);
            }
        }).start();
    }
    /**
     * @functionName: sendImages
     * @description: sends the images to service
     * @return: null
     */
    public void sendImages() {
        getImagesFromAndroid();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "###########################   sendCheck");
                InetAddress serverAddress = null;
                Socket socket = null;
                try {
                    serverAddress = InetAddress.getByName(ip);
                    socket = new Socket(serverAddress, port);
                    OutputStream output = socket.getOutputStream();
                    if (!outgoing_images.isEmpty()) {
                        int curr_image_num = 0;
                        final int total_image_num = outgoing_images.size();
                        for (Map.Entry<String, byte[]> entry : outgoing_images.entrySet()) {
                            String pic_name_string = entry.getKey();
                            byte[] pic_byte_arr = entry.getValue();
                            byte[] pic_len = (pic_byte_arr.length + newLine).getBytes();
                            Log.d(TAG, "###########################   sendCheck  sending: pic_len:" + pic_byte_arr.length);
                            output.write(pic_len);
                            byte[] pic_name_byte = (pic_name_string + newLine).getBytes();
                            Log.d(TAG, "###########################   sendCheck  sending: pic_name:" + pic_name_string);
                            output.write(pic_name_byte);
                            Log.d(TAG, "###########################   sendCheck  sending: pic_array");
                            output.write(pic_byte_arr);
                            Log.d(TAG, "###########################   sendCheck  sending: endSend");
                            output.write(endSend.getBytes());
                            curr_image_num++;
                            final int f_curr_image_num = curr_image_num;
                            updateProgressBar(total_image_num, f_curr_image_num);
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                            }
                        }
                    } else {
                        Log.d(TAG, "###########################   sendCheck  nothing sent Reason: no pics");
                    }
                    output.flush();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
