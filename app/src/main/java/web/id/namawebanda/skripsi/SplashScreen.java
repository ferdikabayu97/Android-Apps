package web.id.namawebanda.skripsi;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashScreen extends AppCompatActivity {
    private int waktu_loading=4000;

    //4000=4 detik

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_splash_screen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

            //setelah loading maka akan langsung berpindah ke home activity
            Intent Login=new Intent(SplashScreen.this, Login.class);
            startActivity(Login);
            finish();

            }
        },waktu_loading);
    }
}
