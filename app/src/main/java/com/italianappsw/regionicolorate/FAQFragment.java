package com.italianappsw.regionicolorate;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class FAQFragment extends Fragment {

    View view;
    private WebView mWebView;
    private DatabaseReference dbRef;

    public FAQFragment() {
        // Required empty public constructor
    }

    public static FAQFragment newInstance(String param1, String param2) {
        FAQFragment fragment = new FAQFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_f_a_q, container, false);

        mWebView = (WebView)view.findViewById(R.id.faqWebView);
        //Prendo il link da firebase
        dbRef = FirebaseDatabase.getInstance().getReference();
        mWebView.getSettings().setJavaScriptEnabled(true);
        dbRef.child("faq_link").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                mWebView.loadUrl(snapshot.getValue().toString());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }

        });

        return view;
    }
}