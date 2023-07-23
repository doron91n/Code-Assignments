package fluffybun.game;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // save the phone screen dimensions
        DisplayMetrics screen_matrix = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(screen_matrix);
        Constants.SCREEN_HEIGHT = screen_matrix.heightPixels;
        Constants.SCREEN_WIDTH = screen_matrix.widthPixels;
        setContentView(new GamePanel(this));
    }
}
