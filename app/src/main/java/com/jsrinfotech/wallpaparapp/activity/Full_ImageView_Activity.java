package com.jsrinfotech.wallpaparapp.activity;

import android.app.Dialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.DownloadListener;
import com.bumptech.glide.Glide;
import com.jsrinfotech.wallpaparapp.R;
import com.jsrinfotech.wallpaparapp.globle.CommonUtils;
import com.jsrinfotech.wallpaparapp.globle.Const;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Full_ImageView_Activity extends AppCompatActivity {

    private static final String TAG = "Full_ImageView_Activity";
    @BindView(R.id.img_fullimage_preview)
    ImageView imgFullimagePreview;

    Dialog dialog;
    Intent intent;
    String imgurl;

    ProgressDialog mProgressDialog;
    @BindView(R.id.btn_dowonload)
    ImageView btnDowonload;
    @BindView(R.id.img_fullimage_preview_main)
    ImageView imgFullimagePreviewMain;
    @BindView(R.id.btn_share)
    ImageView btnShare;
    @BindView(R.id.btn_setWallpapar)
    ImageView btnSetWallpapar;
    @BindView(R.id.Llbottom)
    LinearLayout Llbottom;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full__image_view_);
        ButterKnife.bind(this);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        intent = getIntent();
        dialog = CommonUtils.createCustomLoader(Full_ImageView_Activity.this, false);
        imgurl = intent.getStringExtra(Const.IMAGEURL);
        Glide.with(getApplicationContext()).load(imgurl).into(imgFullimagePreview);
        Glide.with(getApplicationContext()).load(imgurl).into(imgFullimagePreviewMain);

    }

    @OnClick(R.id.btn_share)
    public void onViewClicked2() {
        dialog.show();
        ShareImage(imgurl);
    }

    @OnClick(R.id.btn_dowonload)
    public void onViewClicked1() {
        Toast.makeText(this, "Downloading Image", Toast.LENGTH_SHORT).show();
        dialog.show();
        DownloadImage(imgurl);
//        //timestamp
//        DateFormat date1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
//        date1.setTimeZone(TimeZone.getTimeZone("GMT+5:30"));
//        String localTime1 = date1.format(currentLocalTime) + System.nanoTime();
//
//        File myDirectory = new File("/sdcard/Wallpaper");
//        myDirectory.mkdir();
//
//        downloadfile(Full_ImageView_Activity.this, "image" + localTime1, ".jpeg", String.valueOf(myDirectory), imgurl);
        // comman.DownloadImageFromPath(imgurl);
    }


    @OnClick(R.id.btn_setWallpapar)
    public void onViewClicked() {
        dialog.show();
        SetWallpaperImage(imgurl);
    }

    private void downloadfile(Context context, String filename, String fileExtension, String destinationdirectory, String url) {
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);

        Uri uri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalFilesDir(context, destinationdirectory, filename + fileExtension);
        downloadManager.enqueue(request);
    }
    // DownloadImage AsyncTask


    private void DownloadImage(final String url) {

        final String dirPath = "storage/emulated/0/DDS/";
        File StickerAppDirectory = new File("storage/emulated/0/DDS/");
        if (!StickerAppDirectory.exists())
            StickerAppDirectory.mkdir();

        final String fileName = url.replace("/", "_") + ".jpg";

        AndroidNetworking.download(url, dirPath, fileName)
                .build()
                .startDownload(new DownloadListener() {
                    @Override
                    public void onDownloadComplete() {

                        dialog.dismiss();
                        Toast.makeText(Full_ImageView_Activity.this, "DownLoad Complete", Toast.LENGTH_SHORT).show();
//                        Intent share = new Intent(Intent.ACTION_SEND);
//                        share.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(dirPath + fileName)));
//                        share.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=" + getPackageName());
//                        Log.e(TAG, "onDownloadComplete: file path: " + fileName);
//                        share.setType("image/*");
//                    share.putExtra(Intent.EXTRA_STREAM, Uri.parse(intent_url));
//                        startActivity(Intent.createChooser(share, "share_via"));
                    }

                    @Override
                    public void onError(ANError anError) {
                        dialog.dismiss();
                        Toast.makeText(Full_ImageView_Activity.this, "Fail", Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "onError: " + anError.getMessage());
                    }
                });
    }

    private void ShareImage(final String url) {

        final String dirPath = "storage/emulated/0/Wally_Wallpaper/";
        File StickerAppDirectory = new File("storage/emulated/0/Wally_Wallpaper/");
        if (!StickerAppDirectory.exists())
            StickerAppDirectory.mkdir();

        final String fileName = url.replace("/", "_") + ".jpg";

        AndroidNetworking.download(url, dirPath, fileName)
                .build()
                .startDownload(new DownloadListener() {
                    @Override
                    public void onDownloadComplete() {

                        dialog.dismiss();
//                        Toast.makeText(CategoryDetailActivity.this, "DownLoad Complete", Toast.LENGTH_SHORT).show();

                        try {

                            Intent share = new Intent(Intent.ACTION_SEND);
                            share.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(dirPath + fileName)));
                            share.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=" + getPackageName());
                            Log.e(TAG, "onDownloadComplete: file path: " + fileName);
                            share.setType("image/*");
//                    share.putExtra(Intent.EXTRA_STREAM, Uri.parse(intent_url));
                            startActivity(Intent.createChooser(share, "share_via"));

                        } catch (Exception e) {
                            Log.e(TAG, "onDownloadComplete: " + e.getMessage());
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        dialog.dismiss();
                        Toast.makeText(Full_ImageView_Activity.this, "Fail", Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "onError: " + anError.getMessage());
                    }
                });
    }

    private void SetWallpaperImage(final String url) {

        final String dirPath = "storage/emulated/0/Wally_Wallpaper/";
        File StickerAppDirectory = new File("storage/emulated/0/Wally_Wallpaper/");
        if (!StickerAppDirectory.exists())
            StickerAppDirectory.mkdir();

        final String fileName = url.replace("/", "_") + ".jpg";

        AndroidNetworking.download(url, dirPath, fileName)
                .build()
                .startDownload(new DownloadListener() {
                    @Override
                    public void onDownloadComplete() {

//                        Toast.makeText(CategoryDetailActivity.this, "DownLoad Complete", Toast.LENGTH_SHORT).show();
                        setWallpaper(fileName);
                    }

                    @Override
                    public void onError(ANError anError) {
                        dialog.dismiss();
                        Toast.makeText(Full_ImageView_Activity.this, "Fail", Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "onError: " + anError.getMessage());
                    }
                });
    }


    private void setWallpaper(String fileName) {
        final String dirPath = "storage/emulated/0/Wally_Wallpaper/" + fileName;
        File file = new File(dirPath);
        if (file.exists()) {
            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            WallpaperManager manager = WallpaperManager.getInstance(getApplicationContext());
            try {
                manager.setBitmap(bitmap);
                dialog.dismiss();
                Toast.makeText(this, "Wallpaper set!", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                dialog.dismiss();
                Toast.makeText(this, "Error!", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
