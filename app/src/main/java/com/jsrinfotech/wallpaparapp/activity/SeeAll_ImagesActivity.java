package com.jsrinfotech.wallpaparapp.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
//import com.jsrinfotech.wallpaparapp.Adapter.SeeAll_Adapter;
import com.jsrinfotech.wallpaparapp.Adapter.SeeAll_Adapter;
import com.jsrinfotech.wallpaparapp.R;
import com.jsrinfotech.wallpaparapp.globle.Const;
import com.jsrinfotech.wallpaparapp.model.Cat_Images;

import java.util.ArrayList;
import java.util.Collections;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SeeAll_ImagesActivity extends AppCompatActivity {

    @BindView(R.id.Rv_SeeAll)
    RecyclerView RvSeeAll;

    String Catname;
    DatabaseReference reference;
    FirebaseAuth mAuth;
    FirebaseUser user;
    @BindView(R.id.title)
    TextView title;

    private ArrayList<Cat_Images> SeeAllimagelist = new ArrayList<>();

    private static final String TAG = "SeeAll_ImagesActivity";
    SeeAll_Adapter seeAll_adapter;

    GridLayoutManager feature_layoutManager;
    private com.google.android.gms.ads.AdView mAdView;
    private LinearLayout mFbBannerAdContainer;
    private AdView mFbAdview;

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
        setContentView(R.layout.activity_see_all__images);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        Catname = intent.getStringExtra(Const.CATNAME);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        title.setText(Catname);
        //feature
        feature_layoutManager = new GridLayoutManager(this, 3);
//        feature_layoutManager.setReverseLayout(true);
//        feature_layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        RvSeeAll.setLayoutManager(feature_layoutManager);

        Collections.reverse(SeeAllimagelist);
        seeAll_adapter = new SeeAll_Adapter(SeeAllimagelist, SeeAll_ImagesActivity.this);
        RvSeeAll.setAdapter(seeAll_adapter);

        SeeAll();
        mAdView = findViewById(R.id.adView);
        mFbBannerAdContainer = findViewById(R.id.fb_banner_container);
        mFbAdview = new com.facebook.ads.AdView(this, getString(R.string.fb_banner_ad_id), AdSize.BANNER_HEIGHT_50);
        mFbBannerAdContainer.addView(mFbAdview);
        mFbAdview.loadAd();
        mFbAdview.setAdListener(new com.facebook.ads.AdListener() {
            @Override
            public void onError(Ad ad, AdError adError) {
                Log.e(TAG, "onError: fb banner ad error loading");
                mAdView.setVisibility(View.VISIBLE);
                AdRequest adRequest = new AdRequest.Builder().build();
                mAdView.loadAd(adRequest);
            }

            @Override
            public void onAdLoaded(Ad ad) {
                Log.e(TAG, "onAdLoaded: fb bannner ad loaded");
            }

            @Override
            public void onAdClicked(Ad ad) {
                Log.e(TAG, "onAdClicked: fb banner ad clicked");
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                Log.e(TAG, "onLoggingImpression: fb banner ad logging impression");
            }
        });
        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                Log.e(TAG, "onAdLoaded: google banner ad loaded");
            }

            @Override
            public void onAdClosed() {
                super.onAdClosed();
                Log.e(TAG, "onAdClosed: google banner ad closed");
            }

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
                Log.e(TAG, "onAdFailedToLoad: google banner ad failed");
                mAdView.setVisibility(View.GONE);
                mFbAdview.setVisibility(View.GONE);
            }
        });
    }

    private void SeeAll() {

        reference = FirebaseDatabase.getInstance().getReference().child("Images").child(Catname);
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                if (dataSnapshot.exists()) {
                    Cat_Images images = dataSnapshot.getValue(Cat_Images.class);
                    Log.e(TAG, "onChildAdded: img " + images.img);
                    SeeAllimagelist.add(images);
                    seeAll_adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                seeAll_adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
