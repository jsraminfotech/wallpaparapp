package com.jsrinfotech.wallpaparapp;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.jsrinfotech.wallpaparapp.TabSlider.TabpageAdapter;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity {


    private static final int PERMISSION_REQUEST_CODE = 111;
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.Vpager_tabs)
    ViewPager VpagerTabs;

    TabpageAdapter tabdapter;
    boolean connected = false;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    String[] apppermissin = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.SET_WALLPAPER};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        internetconnection();
        checkPermission();

        PackageInfo info;
        try {
            info = getPackageManager().getPackageInfo("com.jsrinfotech.wallpaparapp", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String something = new String(Base64.encode(md.digest(), 0));
                //String something = new String(Base64.encodeBytes(md.digest()));
                Log.e("hash key", something);
            }
        } catch (PackageManager.NameNotFoundException e1) {
            Log.e("name not found", e1.toString());
        } catch (NoSuchAlgorithmException e) {
            Log.e("no such an algorithm", e.toString());
        } catch (Exception e) {
            Log.e("exception", e.toString());
        }

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        tabslider();

//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.addDrawerListener(toggle);
//        toggle.syncState();

//        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);
        if (!connected) {

            Toast.makeText(this, "Please check internet connection", Toast.LENGTH_SHORT).show();

        }

    }

    @Override
    public void onBackPressed() {
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else {
        super.onBackPressed();
//        }
    }


//    @SuppressWarnings("StatementWithEmptyBody")
//    @Override
//    public boolean onNavigationItemSelected(MenuItem item) {
//        // Handle navigation view item clicks here.
//        int id = item.getItemId();
//
//        if (id == R.id.nav_camera) {
//            // Handle the camera action
//        } else if (id == R.id.nav_gallery) {
//
//        } else if (id == R.id.nav_slideshow) {
//
//        } else if (id == R.id.nav_manage) {
//
//        } else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {
//
//        }
//
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        drawer.closeDrawer(GravityCompat.START);
//        return true;
//    }

    private void tabslider() {

        tabdapter = new TabpageAdapter(getSupportFragmentManager());
        VpagerTabs.setAdapter(tabdapter);
        tabs.setupWithViewPager(VpagerTabs);
    }

    private void internetconnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
        } else
            connected = false;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

        if (requestCode == PERMISSION_REQUEST_CODE) {
            HashMap<String, Integer> permissionresult = new HashMap<>();
            int denidecode = 0;

            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_DENIED) {

                    permissionresult.put(permissions[i], grantResults[i]);
                    denidecode++;
                }
            }

            if (denidecode == 0) {

            } else {

                for (Map.Entry<String, Integer> entry : permissionresult.entrySet()) {
                    String pername = entry.getKey();
                    int perresult = entry.getValue();

                    if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, pername)) {

                        showdialog("", "This appliocation need permissions", "Yes, Grant Permission", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                dialog.dismiss();
                                checkPermission();
                            }
                        }, "No Exit app", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                dialog.dismiss();
                                finish();
                            }
                        }, false);

                    } else {

                        showdialog("", "You have denide the some permission, allow all permission  at settings", "Go to settings", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                dialog.dismiss();
                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.fromParts("package", getPackageName(), null));
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();

                            }
                        }, "No Exit app", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                dialog.dismiss();
                                finish();
                            }
                        }, false);
                    }
                }
            }
        }
    }


    public AlertDialog showdialog(String title, String msg, String positivelable, DialogInterface.OnClickListener positiveonclick, String nagativelable, DialogInterface.OnClickListener nagativeonclick, boolean iscancelable) {
        AlertDialog.Builder aBuilder = new AlertDialog.Builder(this);
        aBuilder.setTitle(title);
        aBuilder.setMessage(msg);
        aBuilder.setCancelable(iscancelable);
        aBuilder.setPositiveButton(positivelable, positiveonclick);
        aBuilder.setNegativeButton(nagativelable, nagativeonclick);

        AlertDialog alertDialog = aBuilder.create();
        alertDialog.show();
        return alertDialog;
    }

    private boolean checkPermission() {
        List<String> PermissionList = new ArrayList<>();
        for (String per : apppermissin) {

            if (ContextCompat.checkSelfPermission(this, per) != PackageManager.PERMISSION_GRANTED) {

                PermissionList.add(per);
            }

        }
        if (!PermissionList.isEmpty()) {
            ActivityCompat.requestPermissions(this, PermissionList.toArray(new String[PermissionList.size()]), PERMISSION_REQUEST_CODE);
            return false;
        }
        return true;
    }
}

