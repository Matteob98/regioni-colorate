package com.italianappsw.regionicolorate;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CountryMapFragment extends Fragment {

    private static final int DB_REFERENCE_COUNT = 6;
    private int actualReferenceCount = 0; //Referenze che sono già state caricate

    private WebView mWebView;
    private ProgressBar mProgressBar;
    private View view;
    private DatabaseReference
            regionDbRef,
            commercialActivityDbRef,
            sportiveActivityDbRef,
            eventDbRef,
            instructionDbRef,
            displacementsDbRef;


    private ArrayList<Region> regions;

    public CountryMapFragment() {
        // Required empty public constructor
    }

    public static CountryMapFragment newInstance(String param1, String param2) {
        CountryMapFragment fragment = new CountryMapFragment();
        return fragment;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_country_map, container, false);
        this.regions = new ArrayList<>();

        webViewSettings();
        progressBarSettings();
        getDataFromBE();

        return view;
    }



    private void progressBarSettings() {
        mProgressBar = view.findViewById(R.id.circularProgressBarCountryMap);
        mProgressBar.setVisibility(View.VISIBLE);
    }

    private void progressBarHandler() {
        if(actualReferenceCount == DB_REFERENCE_COUNT) {
            mProgressBar.setVisibility(View.INVISIBLE);
            mWebView.setVisibility(View.VISIBLE);
        }
    }

    @SuppressLint({"ClickableViewAccessibility", "SetJavaScriptEnabled"})
    private void webViewSettings() {
        mWebView = (WebView)view.findViewById(R.id.regionsWebView);
        mWebView.setVisibility(View.INVISIBLE);
        mWebView.setPadding(0, 0, 0, 0);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return (event.getAction() == MotionEvent.ACTION_MOVE);
            }
        });
    }

    private void loadWebView() {
        // this function is used to access javascript from html page
        mWebView.addJavascriptInterface(new JavaScriptInterface(
                        getContext(),
                        getActivity(),
                        mWebView,
                        regions),
                "AndroidNativeCode");
        // load file from assets folder
        mWebView.loadUrl("file:///android_asset/map.html");
    }


    /**
     * Prende da Firebase i dati relativi ai colori delle regioni e li salva
     * all'interno dell'array regions
     */
    private void getDataFromBE() {

        regionDbRef = FirebaseDatabase.getInstance().getReference("regioni");

        regionDbRef.addListenerForSingleValueEvent(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot){
                regions = new ArrayList<>();
                for (DataSnapshot productSnapshot: dataSnapshot.getChildren()) {
                    Region region = productSnapshot.getValue(Region.class);
                    regions.add(region);
                }
                loadWebView();
                actualReferenceCount++;
                progressBarHandler();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError){
            }
        });

        commercialActivityDbRef = FirebaseDatabase.getInstance().getReference("rules/attivita_commerciali");
        commercialActivityDbRef.addListenerForSingleValueEvent(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot){
                WhiteRules.eraseCommercial_activities();
                YellowRules.eraseCommercial_activities();
                OrangeRules.eraseCommercial_activities();
                RedRules.eraseCommercial_activities();
                for (DataSnapshot productSnapshot: dataSnapshot.getChildren()) {
                    WhiteRules.addCommercial_activity(
                            productSnapshot.getKey(),
                            productSnapshot.child("white").getValue(int.class));
                    YellowRules.addCommercial_activity(
                            productSnapshot.getKey(),
                            productSnapshot.child("yellow").getValue(int.class));
                    OrangeRules.addCommercial_activity(
                            productSnapshot.getKey(),
                            productSnapshot.child("orange").getValue(int.class));
                    RedRules.addCommercial_activity(
                            productSnapshot.getKey(),
                            productSnapshot.child("red").getValue(int.class));
                }
                actualReferenceCount++;
                progressBarHandler();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError){
            }
        });

        sportiveActivityDbRef = FirebaseDatabase.getInstance().getReference("rules/attivita_sportiva");
        sportiveActivityDbRef.addListenerForSingleValueEvent(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot){
                WhiteRules.eraseSportive_activities();
                YellowRules.eraseSportive_activities();
                OrangeRules.eraseSportive_activities();
                RedRules.eraseSportive_activities();
                for (DataSnapshot productSnapshot: dataSnapshot.getChildren()) {
                    WhiteRules.addSportive_activity(
                            productSnapshot.getKey(),
                            productSnapshot.child("white").getValue(int.class));
                    YellowRules.addSportive_activity(
                            productSnapshot.getKey(),
                            productSnapshot.child("yellow").getValue(int.class));
                    OrangeRules.addSportive_activity(
                            productSnapshot.getKey(),
                            productSnapshot.child("orange").getValue(int.class));
                    RedRules.addSportive_activity(
                            productSnapshot.getKey(),
                            productSnapshot.child("red").getValue(int.class));
                }
                actualReferenceCount++;
                progressBarHandler();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError){
            }
        });

        eventDbRef = FirebaseDatabase.getInstance().getReference("rules/eventi");
        eventDbRef.addListenerForSingleValueEvent(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot){
                WhiteRules.eraseEvents();
                YellowRules.eraseEvents();
                OrangeRules.eraseEvents();
                RedRules.eraseEvents();
                for (DataSnapshot productSnapshot: dataSnapshot.getChildren()) {
                    WhiteRules.addEvent(
                            productSnapshot.getKey(),
                            productSnapshot.child("white").getValue(int.class));
                    YellowRules.addEvent(
                            productSnapshot.getKey(),
                            productSnapshot.child("yellow").getValue(int.class));
                    OrangeRules.addEvent(
                            productSnapshot.getKey(),
                            productSnapshot.child("orange").getValue(int.class));
                    RedRules.addEvent(
                            productSnapshot.getKey(),
                            productSnapshot.child("red").getValue(int.class));
                }
                actualReferenceCount++;
                progressBarHandler();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError){
            }
        });

        instructionDbRef = FirebaseDatabase.getInstance().getReference("rules/istruzione");
        instructionDbRef.addListenerForSingleValueEvent(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot){
                WhiteRules.eraseInstruction();
                YellowRules.eraseInstruction();
                OrangeRules.eraseInstruction();
                RedRules.eraseInstruction();
                for (DataSnapshot productSnapshot: dataSnapshot.getChildren()) {
                    WhiteRules.addInstruction(
                            productSnapshot.getKey(),
                            productSnapshot.child("white").getValue(int.class));
                    YellowRules.addInstruction(
                            productSnapshot.getKey(),
                            productSnapshot.child("yellow").getValue(int.class));
                    OrangeRules.addInstruction(
                            productSnapshot.getKey(),
                            productSnapshot.child("orange").getValue(int.class));
                    RedRules.addInstruction(
                            productSnapshot.getKey(),
                            productSnapshot.child("red").getValue(int.class));
                }
                actualReferenceCount++;
                progressBarHandler();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError){
            }
        });

        displacementsDbRef = FirebaseDatabase.getInstance().getReference("rules/spostamenti");
        displacementsDbRef.addListenerForSingleValueEvent(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot){
                WhiteRules.eraseDisplacements();
                YellowRules.eraseDisplacements();
                OrangeRules.eraseDisplacements();
                RedRules.eraseDisplacements();
                for (DataSnapshot productSnapshot: dataSnapshot.getChildren()) {
                    WhiteRules.addDisplacements(
                            productSnapshot.getKey(),
                            productSnapshot.child("white").getValue(int.class));
                    YellowRules.addDisplacements(
                            productSnapshot.getKey(),
                            productSnapshot.child("yellow").getValue(int.class));
                    OrangeRules.addDisplacements(
                            productSnapshot.getKey(),
                            productSnapshot.child("orange").getValue(int.class));
                    RedRules.addDisplacements(
                            productSnapshot.getKey(),
                            productSnapshot.child("red").getValue(int.class));
                }
                actualReferenceCount++;
                progressBarHandler();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError){
            }
        });
    }


}