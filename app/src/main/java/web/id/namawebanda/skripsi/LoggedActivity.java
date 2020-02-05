package web.id.namawebanda.skripsi;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class LoggedActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, RekomendasiFragment.OnDataPass {
    String mtoken,mkelurahan,mkecamatan,mnama,mlatlng,memail,musername;
    PesaingFragment fragobj;
    String lng,lat;

    OptimalisasiFragment fragobj2;
    boolean doubleBackToExitPressedOnce = false;

    private DrawerLayout drawer;
    @Override
    public void onDataPass(String[] data) {

        Bundle bundle = new Bundle();
        bundle.putStringArray("array", data);
// set Fragmentclass Arguments
        fragobj = new PesaingFragment();
        fragobj2 = new OptimalisasiFragment();
        fragobj.setArguments(bundle);
        fragobj2.setArguments(bundle);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged);
        Intent token = getIntent();
        mkelurahan= token.getStringExtra("kelurahan");
        mkecamatan= token.getStringExtra("kecamatan");
        mnama= token.getStringExtra("nama");
        mlatlng = token.getStringExtra("latlng");
        String[] latilong = mlatlng.split(",");
        lat = latilong[0];
        lng = latilong[1];

        memail = token.getStringExtra("email");
        musername = token.getStringExtra("username");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        TextView navNama = (TextView) headerView.findViewById(R.id.nama);
        TextView navAlamat = (TextView) headerView.findViewById(R.id.alamat);
        navNama.setText(mnama);
        navAlamat.setText(mkecamatan+", "+mkelurahan);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(LoggedActivity.this,drawer,toolbar,R.string.buka_navigasi_drawer,R.string.tutup_nafigasi_drawer);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    if (savedInstanceState == null){
        this.setTitle("Rekomendasi Usaha");
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new RekomendasiFragment()).commit();
        navigationView.setCheckedItem(R.id.nav_rekomendasi);


    }
        }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.nav_rekomendasi:
                this.setTitle("Rekomendasi Usaha");
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new RekomendasiFragment()).commit();
                break;
            case R.id.nav_pesaing:
                this.setTitle("Banyak Pesaing");
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragobj).commit();
                break;
            case R.id.nav_penduduk:
                this.setTitle("Data Penduduk");
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new PendudukFragment()).commit();
                break;
            case R.id.nav_optimalisasi:
                this.setTitle("Optimalisasi Sampel Data");
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragobj2).commit();
                break;
            case R.id.nav_gpass:
                this.setTitle("Ganti Password");
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new GpasswordFragment()).commit();
                break;
            case R.id.nav_logout:
                Intent keluar=new Intent(LoggedActivity.this, Login.class);
                startActivity(keluar);
                finish();
                break;
        }

        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (doubleBackToExitPressedOnce) {
                Intent halamanutamaback =new Intent(LoggedActivity.this, MainActivity.class);

                halamanutamaback.putExtra("lat",lat);
                halamanutamaback.putExtra("lng",lng);
                halamanutamaback.putExtra("Token",mtoken);
                halamanutamaback.putExtra("nama",mnama);
                halamanutamaback.putExtra("email",memail);
                halamanutamaback.putExtra("username",musername);
                startActivity(halamanutamaback);
                finish();
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Tekan kembali dua kali memilih lokasi baru", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce=false;
                }
            }, 2000);
        }

    }
}
