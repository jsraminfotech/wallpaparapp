package com.jsrinfotech.wallpaparapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.jsrinfotech.wallpaparapp.Adapter.Cat_Animal_Adapter;
import com.jsrinfotech.wallpaparapp.Adapter.Cat_Bollywod_Adapter;
import com.jsrinfotech.wallpaparapp.Adapter.Cat_Drawing_Adapter;
import com.jsrinfotech.wallpaparapp.Adapter.Cat_Game_Adapter;
import com.jsrinfotech.wallpaparapp.Adapter.Cat_Love_Adapter;
import com.jsrinfotech.wallpaparapp.Adapter.Cat_Music_Adapter;
import com.jsrinfotech.wallpaparapp.Adapter.Cat_Nature_Adapter;
import com.jsrinfotech.wallpaparapp.Adapter.Cat_People_Adapter;
import com.jsrinfotech.wallpaparapp.Adapter.Cat_Saying_Adapter;
import com.jsrinfotech.wallpaparapp.Adapter.Cat_Sport_Adapter;
import com.jsrinfotech.wallpaparapp.Adapter.Cat_Technology_Adapter;
import com.jsrinfotech.wallpaparapp.Adapter.Cat_car_Adapter;
import com.jsrinfotech.wallpaparapp.R;
import com.jsrinfotech.wallpaparapp.activity.SeeAll_ImagesActivity;
import com.jsrinfotech.wallpaparapp.globle.Const;
import com.jsrinfotech.wallpaparapp.model.Cat_Images;

import java.util.ArrayList;
import java.util.Collections;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class Tab2Fragment extends Fragment {

    DatabaseReference reference;
    FirebaseAuth mAuth;
    FirebaseUser user;
    @BindView(R.id.Rv_animal)
    RecyclerView RvAnimal;
    @BindView(R.id.Rv_bollywod)
    RecyclerView RvBollywod;
    @BindView(R.id.Rv_car)
    RecyclerView RvCar;
    @BindView(R.id.Rv_drawing)
    RecyclerView RvDrawing;
    @BindView(R.id.Rv_game)
    RecyclerView RvGame;
    @BindView(R.id.Rv_love)
    RecyclerView RvLove;
    @BindView(R.id.Rv_music)
    RecyclerView RvMusic;
    @BindView(R.id.Rv_nature)
    RecyclerView RvNature;
    @BindView(R.id.Rv_people)
    RecyclerView RvPeople;
    @BindView(R.id.Rv_saying)
    RecyclerView RvSaying;
    @BindView(R.id.Rv_sport)
    RecyclerView RvSport;
    @BindView(R.id.Rv_technology)
    RecyclerView RvTechnology;
    Unbinder unbinder;
    @BindView(R.id.tv_animal)
    TextView tvAnimal;
    @BindView(R.id.tv_bollywod)
    TextView tvBollywod;
    @BindView(R.id.tv_car)
    TextView tvCar;
    @BindView(R.id.tv_drawing)
    TextView tvDrawing;
    @BindView(R.id.tv_game)
    TextView tvGame;
    @BindView(R.id.tv_love)
    TextView tvLove;
    @BindView(R.id.tv_music)
    TextView tvMusic;
    @BindView(R.id.tv_nature)
    TextView tvNature;
    @BindView(R.id.tv_people)
    TextView tvPeople;
    @BindView(R.id.tv_saying)
    TextView tvSaying;
    @BindView(R.id.tv_sport)
    TextView tvSport;
    @BindView(R.id.tv_technology)
    TextView tvTechnology;

    private ArrayList<Cat_Images> animalimagelist = new ArrayList<>();
    private ArrayList<Cat_Images> bollywodlist = new ArrayList<>();
    private ArrayList<Cat_Images> carlist = new ArrayList<>();
    private ArrayList<Cat_Images> drawinglist = new ArrayList<>();
    private ArrayList<Cat_Images> gamelist = new ArrayList<>();
    private ArrayList<Cat_Images> lovelist = new ArrayList<>();
    private ArrayList<Cat_Images> peoplelist = new ArrayList<>();
    private ArrayList<Cat_Images> musiclist = new ArrayList<>();
    private ArrayList<Cat_Images> naturelist = new ArrayList<>();
    private ArrayList<Cat_Images> sayinglist = new ArrayList<>();
    private ArrayList<Cat_Images> sportlist = new ArrayList<>();
    private ArrayList<Cat_Images> technologylist = new ArrayList<>();


    Cat_Animal_Adapter animal_adapter;
    Cat_Bollywod_Adapter bollywod_adapter;
    Cat_car_Adapter cat_car_adapter;
    Cat_Drawing_Adapter drawing_adapter;
    Cat_Game_Adapter game_adapter;
    Cat_Love_Adapter love_adapter;
    Cat_People_Adapter people_adapter;
    Cat_Music_Adapter music_adapter;
    Cat_Nature_Adapter nature_adapter;
    Cat_Saying_Adapter saying_adapter;
    Cat_Sport_Adapter sport_adapter;
    Cat_Technology_Adapter technology_adapter;

    private static final String TAG = "Tab2Fragment";

    LinearLayoutManager animal_layoutManager,
            bollywod_LayoutManager,
            car_LayoutManager,
            drawing_layoutManager,
            game_LayoutManager,
            love_LayoutManager,
            people_LayoutManager,
            music_LayoutManager,
            nature_LayoutManager,
            saying_LayoutManager,
            sport_LayoutManager,
            technology_LayoutManager;

    private com.google.android.gms.ads.AdView mAdView;
    private LinearLayout mFbBannerAdContainer;
    private AdView mFbAdview;

    public Tab2Fragment() {
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
        View view = inflater.inflate(R.layout.fragment_tab2, container, false);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        unbinder = ButterKnife.bind(this, view);


        //animal
        animal_layoutManager = new LinearLayoutManager(getActivity());
        animal_layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        RvAnimal.setLayoutManager(animal_layoutManager);
        animal_adapter = new Cat_Animal_Adapter(animalimagelist, getActivity());
        RvAnimal.setAdapter(animal_adapter);

        //bollywod
        bollywod_LayoutManager = new LinearLayoutManager(getActivity());
        bollywod_LayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        RvBollywod.setLayoutManager(bollywod_LayoutManager);
        bollywod_adapter = new Cat_Bollywod_Adapter(bollywodlist, getActivity());
        RvBollywod.setAdapter(bollywod_adapter);

        //car and vehical
        car_LayoutManager = new LinearLayoutManager(getActivity());
        car_LayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        RvCar.setLayoutManager(car_LayoutManager);
        cat_car_adapter = new Cat_car_Adapter(carlist, getActivity());
        RvCar.setAdapter(cat_car_adapter);

        //drawing
        drawing_layoutManager = new LinearLayoutManager(getActivity());
        drawing_layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        RvDrawing.setLayoutManager(drawing_layoutManager);
        drawing_adapter = new Cat_Drawing_Adapter(drawinglist, getActivity());
        RvDrawing.setAdapter(drawing_adapter);

        //Game
        game_LayoutManager = new LinearLayoutManager(getActivity());
        game_LayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        RvGame.setLayoutManager(game_LayoutManager);
        game_adapter = new Cat_Game_Adapter(gamelist, getActivity());
        RvGame.setAdapter(game_adapter);

        //love
        love_LayoutManager = new LinearLayoutManager(getActivity());
        love_LayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        RvLove.setLayoutManager(love_LayoutManager);
        love_adapter = new Cat_Love_Adapter(lovelist, getActivity());
        RvLove.setAdapter(love_adapter);

        //people
        people_LayoutManager = new LinearLayoutManager(getActivity());
        people_LayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        RvPeople.setLayoutManager(people_LayoutManager);
        people_adapter = new Cat_People_Adapter(peoplelist, getActivity());
        RvPeople.setAdapter(people_adapter);

        //music
        music_LayoutManager = new LinearLayoutManager(getActivity());
        music_LayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        RvMusic.setLayoutManager(music_LayoutManager);
        music_adapter = new Cat_Music_Adapter(musiclist, getActivity());
        RvMusic.setAdapter(music_adapter);

        //nature
        nature_LayoutManager = new LinearLayoutManager(getActivity());
        nature_LayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        RvNature.setLayoutManager(nature_LayoutManager);
        nature_adapter = new Cat_Nature_Adapter(naturelist, getActivity());
        RvNature.setAdapter(nature_adapter);

        //saying
        saying_LayoutManager = new LinearLayoutManager(getActivity());
        saying_LayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        RvSaying.setLayoutManager(saying_LayoutManager);
        saying_adapter = new Cat_Saying_Adapter(sayinglist, getActivity());
        RvSaying.setAdapter(saying_adapter);

        //sport
        sport_LayoutManager = new LinearLayoutManager(getActivity());
        sport_LayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        RvSport.setLayoutManager(sport_LayoutManager);
        sport_adapter = new Cat_Sport_Adapter(sportlist, getActivity());
        RvSport.setAdapter(sport_adapter);

        //technology
        technology_LayoutManager = new LinearLayoutManager(getActivity());
        technology_LayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        RvTechnology.setLayoutManager(technology_LayoutManager);
        technology_adapter = new Cat_Technology_Adapter(technologylist, getActivity());
        RvTechnology.setAdapter(technology_adapter);


        animal();
        bollywod();
        car_vehical();
        drawing();
        game();
        love();
        people();
        music();
        nature();
        saying();
        sport();
        technology();

        mAdView = view.findViewById(R.id.adView);
        mFbBannerAdContainer = view.findViewById(R.id.fb_banner_container);
        mFbAdview = new com.facebook.ads.AdView(getContext(), getString(R.string.fb_banner_ad_id), AdSize.BANNER_HEIGHT_50);
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


        return view;
    }

    public void animal() {

        reference = FirebaseDatabase.getInstance().getReference().child("Images").child("Animal");
        Query query = reference.limitToLast(5);
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                if (dataSnapshot.exists()) {
                    Collections.reverse(animalimagelist);
                    Cat_Images images = dataSnapshot.getValue(Cat_Images.class);
                    Log.e(TAG, "onChildAdded: img " + images.img);
                    animalimagelist.add(images);
                    animal_adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void bollywod() {

        reference = FirebaseDatabase.getInstance().getReference().child("Images").child("Bollywod");
        Query query = reference.limitToLast(5);
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                if (dataSnapshot.exists()) {
                    Collections.reverse(bollywodlist);
                    Cat_Images images = dataSnapshot.getValue(Cat_Images.class);
                    Log.e(TAG, "onChildAdded: img " + images.img);
                    bollywodlist.add(images);
                    bollywod_adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

                final String pos = dataSnapshot.getKey();
                DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference().child("Images").child("Bollywod");
                reference1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            bollywodlist.clear();
                            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                Cat_Images users = ds.getValue(Cat_Images.class);
                                if (users.id == pos) {
                                    bollywodlist.remove(users);
                                    bollywod_adapter.notifyDataSetChanged();
                                } else {
                                    bollywodlist.add(users);
                                    bollywod_adapter.notifyDataSetChanged();
                                }
                                Log.e(TAG, "onDataChange: jacksparoow ");
                                //  requestList.add(users);

                                // dialog.dismiss();
                            }

                        } else {
                            Log.e(TAG, "onDataChange: clear all ");
                            bollywodlist.clear();
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void car_vehical() {

        reference = FirebaseDatabase.getInstance().getReference().child("Images").child("Cars and Vehical");
        Query query = reference.limitToLast(5);
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                if (dataSnapshot.exists()) {
                    Collections.reverse(carlist);
                    Cat_Images images = dataSnapshot.getValue(Cat_Images.class);
                    Log.e(TAG, "onChildAdded: img " + images.img);
                    carlist.add(images);
                    cat_car_adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void drawing() {

        reference = FirebaseDatabase.getInstance().getReference().child("Images").child("Drawings");
        Query query = reference.limitToLast(5);
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                if (dataSnapshot.exists()) {
                    Collections.reverse(drawinglist);
                    Cat_Images images = dataSnapshot.getValue(Cat_Images.class);
                    Log.e(TAG, "onChildAdded: img " + images.img);
                    drawinglist.add(images);
                    drawing_adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void game() {

        reference = FirebaseDatabase.getInstance().getReference().child("Images").child("Game");
        Query query = reference.limitToLast(5);
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                if (dataSnapshot.exists()) {
                    Collections.reverse(gamelist);
                    Cat_Images images = dataSnapshot.getValue(Cat_Images.class);
                    Log.e(TAG, "onChildAdded: img " + images.img);
                    gamelist.add(images);
                    game_adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void love() {

        reference = FirebaseDatabase.getInstance().getReference().child("Images").child("Love");
        Query query = reference.limitToLast(5);
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                if (dataSnapshot.exists()) {
                    Collections.reverse(lovelist);
                    Cat_Images images = dataSnapshot.getValue(Cat_Images.class);
                    Log.e(TAG, "onChildAdded: img " + images.img);
                    lovelist.add(images);
                    love_adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void people() {

        reference = FirebaseDatabase.getInstance().getReference().child("Images").child("People");
        Query query = reference.limitToLast(5);
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                if (dataSnapshot.exists()) {
                    Collections.reverse(peoplelist);
                    Cat_Images images = dataSnapshot.getValue(Cat_Images.class);
                    Log.e(TAG, "onChildAdded: img " + images.img);
                    peoplelist.add(images);
                    people_adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void music() {

        reference = FirebaseDatabase.getInstance().getReference().child("Images").child("Music");
        Query query = reference.limitToLast(5);
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                if (dataSnapshot.exists()) {
                    Collections.reverse(musiclist);
                    Cat_Images images = dataSnapshot.getValue(Cat_Images.class);
                    Log.e(TAG, "onChildAdded: img " + images.img);
                    musiclist.add(images);
                    music_adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void nature() {

        reference = FirebaseDatabase.getInstance().getReference().child("Images").child("Nature");
        Query query = reference.limitToLast(5);
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                if (dataSnapshot.exists()) {
                    Collections.reverse(naturelist);
                    Cat_Images images = dataSnapshot.getValue(Cat_Images.class);
                    Log.e(TAG, "onChildAdded: img " + images.img);
                    naturelist.add(images);
                    nature_adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void saying() {

        reference = FirebaseDatabase.getInstance().getReference().child("Images").child("Saying");
        Query query = reference.limitToLast(5);
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                if (dataSnapshot.exists()) {
                    Collections.reverse(sayinglist);
                    Cat_Images images = dataSnapshot.getValue(Cat_Images.class);
                    Log.e(TAG, "onChildAdded: img " + images.img);
                    sayinglist.add(images);
                    saying_adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void sport() {

        reference = FirebaseDatabase.getInstance().getReference().child("Images").child("Sport");
        Query query = reference.limitToLast(5);
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                if (dataSnapshot.exists()) {
                    Collections.reverse(sportlist);
                    Cat_Images images = dataSnapshot.getValue(Cat_Images.class);
                    Log.e(TAG, "onChildAdded: img " + images.img);
                    sportlist.add(images);
                    sport_adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void technology() {

        reference = FirebaseDatabase.getInstance().getReference().child("Images").child("Technology");
        Query query = reference.limitToLast(5);
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                if (dataSnapshot.exists()) {
                    Collections.reverse(technologylist);
                    Cat_Images images = dataSnapshot.getValue(Cat_Images.class);
                    Log.e(TAG, "onChildAdded: img " + images.img);
                    technologylist.add(images);
                    technology_adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

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

    @OnClick({R.id.tv_animal, R.id.tv_bollywod, R.id.tv_car, R.id.tv_drawing, R.id.tv_game, R.id.tv_love, R.id.tv_music, R.id.tv_nature, R.id.tv_people, R.id.tv_saying, R.id.tv_sport, R.id.tv_technology})
    public void onViewClicked(View view) {

        Intent intent;
        intent = new Intent(getContext(), SeeAll_ImagesActivity.class);
        switch (view.getId()) {
            case R.id.tv_animal:
                intent.putExtra(Const.CATNAME, "Animal");
                break;
            case R.id.tv_bollywod:
                intent.putExtra(Const.CATNAME, "Bollywod");
                break;
            case R.id.tv_car:
                intent.putExtra(Const.CATNAME, "Cars and Vehical");
                break;
            case R.id.tv_drawing:
                intent.putExtra(Const.CATNAME, "Drawings");
                break;
            case R.id.tv_game:
                intent.putExtra(Const.CATNAME, "Game");
                break;
            case R.id.tv_love:
                intent.putExtra(Const.CATNAME, "Love");
                break;
            case R.id.tv_music:
                intent.putExtra(Const.CATNAME, "Music");
                break;
            case R.id.tv_nature:
                intent.putExtra(Const.CATNAME, "Nature");
                break;
            case R.id.tv_people:
                intent.putExtra(Const.CATNAME, "People");
                break;
            case R.id.tv_saying:
                intent.putExtra(Const.CATNAME, "Saying");
                break;
            case R.id.tv_sport:
                intent.putExtra(Const.CATNAME, "Sport");
                break;
            case R.id.tv_technology:
                intent.putExtra(Const.CATNAME, "Technology");
                break;
        }
        startActivity(intent);
    }
}

