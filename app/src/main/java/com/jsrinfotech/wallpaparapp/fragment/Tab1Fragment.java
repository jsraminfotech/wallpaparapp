package com.jsrinfotech.wallpaparapp.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

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
import com.jsrinfotech.wallpaparapp.Adapter.Feature_Adapter;
import com.jsrinfotech.wallpaparapp.R;
import com.jsrinfotech.wallpaparapp.model.Cat_Images;

import java.util.ArrayList;
import java.util.Collections;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class Tab1Fragment extends Fragment {

    DatabaseReference reference;
    FirebaseAuth mAuth;
    FirebaseUser user;
    @BindView(R.id.Rv_feature)
    RecyclerView RvFeature;
    Unbinder unbinder;

    private com.google.android.gms.ads.AdView mAdView;
    private LinearLayout mFbBannerAdContainer;
    private AdView mFbAdview;

    private ArrayList<Cat_Images> featureimagelist = new ArrayList<>();

    private static final String TAG = "Tab1Fragment";
    Feature_Adapter feature_adapter;

    GridLayoutManager feature_layoutManager;

    public Tab1Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tab1, container, false);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        unbinder = ButterKnife.bind(this, view);

        //feature
        feature_layoutManager = new GridLayoutManager(getActivity(), 3);
//        feature_layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        RvFeature.setLayoutManager(feature_layoutManager);
        feature_adapter = new Feature_Adapter(featureimagelist, getActivity());
        RvFeature.setAdapter(feature_adapter);

        feature();

        mAdView = view.findViewById(R.id.adView);
        mFbBannerAdContainer = view.findViewById(R.id.fb_banner_container);
        mFbAdview = new com.facebook.ads.AdView(getActivity(), getString(R.string.fb_banner_ad_id), AdSize.BANNER_HEIGHT_50);
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


        if (isNetworkAvailable(getContext())) {

            ProgressDialog pd = new ProgressDialog(getActivity());
            pd.setMessage("Loading Advertisement..");
            pd.setCancelable(false);
//            pd.show();
        }
        return view;
    }


    public void feature() {

        reference = FirebaseDatabase.getInstance().getReference().child("Feature").child("Images");
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                if (dataSnapshot.exists()) {
                    Collections.reverse(featureimagelist);
                    Cat_Images images = dataSnapshot.getValue(Cat_Images.class);
                    Log.e(TAG, "onChildAdded: img " + images.img);
                    featureimagelist.add(images);
                    feature_adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                feature_adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager
                cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null
                && activeNetwork.isConnected();
    }

}
