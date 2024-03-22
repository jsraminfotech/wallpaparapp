package com.jsrinfotech.wallpaparapp.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdSettings;
import com.facebook.ads.InterstitialAdListener;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.jsrinfotech.wallpaparapp.R;
import com.jsrinfotech.wallpaparapp.activity.Full_ImageView_Activity;
import com.jsrinfotech.wallpaparapp.globle.Const;
import com.jsrinfotech.wallpaparapp.model.Cat_Images;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Jack sparrow on 25-07-2019.
 */

public class Cat_People_Adapter extends RecyclerView.Adapter<Cat_People_Adapter.Holder> {

    private static final String TAG = "Cat_People_Adapter";
    private ProgressDialog progressDialog;
    private com.google.android.gms.ads.InterstitialAd mInterstitialAd;
    private com.facebook.ads.InterstitialAd fbInterstitial;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private ArrayList<Cat_Images> imagelist = new ArrayList<>();
    private Context context;

    public Cat_People_Adapter(ArrayList<Cat_Images> imagelist, Context context) {
        this.imagelist = imagelist;
        this.context = context;
    }

    @NonNull
    @Override
    public Cat_People_Adapter.Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.raw_cat, viewGroup, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Cat_People_Adapter.Holder holder, final int i) {

        Glide.with(context).load(imagelist.get(i).img.toString()).into(holder.imageView);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(context, Full_ImageView_Activity.class);
//                intent.putExtra(Const.IMAGEURL, imagelist.get(i).img.toString());
//                context.startActivity(intent);
                sharedPreferences = context.getSharedPreferences("MYPREF", Context.MODE_PRIVATE);
                editor = sharedPreferences.edit();
                int count = 0;
                count = sharedPreferences.getInt("AD_VIEW_COUNT", 0);
                Log.d(TAG, "onClick  1 = : " + count);
                if (count == 0) {
                    Log.d(TAG, "onClick  2 = : " + count);
                    count++;
                    editor.putInt("AD_VIEW_COUNT", count).apply();
                    loadInterstitialAd(i);
                } else {
                    Log.d(TAG, "onClick  3 = : " + count);
                    count++;
                    if (count == 4) {
                        Log.d(TAG, "onClick  4 = : " + count);
                        editor.putInt("AD_VIEW_COUNT", 0).apply();
                        count = 0;
                        Log.d(TAG, "onClick  5: " + sharedPreferences.getInt("AD_VIEW_COUNT", 0));
                    }
                    editor.putInt("AD_VIEW_COUNT", count).apply();
//                    Intent intent = new Intent(context, Full_ImageView_Activity.class);
//                    intent.putExtra(Const.IMAGEURL, imagelist.get(i).img.toString());
//                    context.startActivity(intent);
                }
                Intent intent = new Intent(context, Full_ImageView_Activity.class);
                intent.putExtra(Const.IMAGEURL, imagelist.get(i).img.toString());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return imagelist.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public Holder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.img_cat_images);

        }
    }

    private void loadInterstitialAd(final int i) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading Advertisement");
//        progressDialog.show();
        mInterstitialAd = new InterstitialAd(context);
        mInterstitialAd.setAdUnitId(context.getString(R.string.interstitial_ad_id));
        fbInterstitial = new com.facebook.ads.InterstitialAd(context, context.getString(R.string.fb_interstitial_ad_id));
        fbInterstitial.loadAd();
        AdSettings.addTestDevice("328404cebf50ec1fdb05795c0007a8a7");
        fbInterstitial.setAdListener(new InterstitialAdListener() {
            @Override
            public void onInterstitialDisplayed(Ad ad) {
                Log.e(TAG, "onInterstitialDisplayed: fb interstitial displayed");
            }

            @Override
            public void onInterstitialDismissed(Ad ad) {
                Log.e(TAG, "onInterstitialDisplayed: fb interstitial dismissed");
//                progressDialog.dismiss();
//                Intent intent = new Intent(context, Full_ImageView_Activity.class);
//                intent.putExtra(Const.IMAGEURL, imagelist.get(i).img.toString());
//                context.startActivity(intent);
            }

            @Override
            public void onError(Ad ad, AdError adError) {
                Log.e(TAG, "onInterstitialDisplayed: fb interstitial error loading ==> " + adError.getErrorMessage());
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
                mInterstitialAd.show();
            }

            @Override
            public void onAdLoaded(Ad ad) {
                Log.e(TAG, "onInterstitialDisplayed: fb interstitial loaded");
//                progressDialog.dismiss();
                fbInterstitial.show();
            }

            @Override
            public void onAdClicked(Ad ad) {
                Log.e(TAG, "onAdClicked: fb interstial clicked");
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                Log.e(TAG, "onLoggingImpression: fb interstitial logging impression");
            }
        });
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
//                progressDialog.dismiss();
//                Intent intent = new Intent(context, Full_ImageView_Activity.class);
//                intent.putExtra(Const.IMAGEURL, imagelist.get(i).img.toString());
//                context.startActivity(intent);
                Log.e(TAG, "onAdClosed: google interstitial ad failed");
            }

            @Override
            public void onAdClosed() {
                super.onAdClosed();
//                progressDialog.dismiss();
//                Intent intent = new Intent(context, Full_ImageView_Activity.class);
//                intent.putExtra(Const.IMAGEURL, imagelist.get(i).img.toString());
//                context.startActivity(intent);
                Log.e(TAG, "onAdClosed: google interstitial ad closed");
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                if (!fbInterstitial.isAdLoaded() && mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
//                    progressDialog.dismiss();
                }
                Log.e(TAG, "onAdLoaded: google interstital ad loaded");
            }
        });
    }
}
