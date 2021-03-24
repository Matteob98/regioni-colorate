package com.italianappsw.regionicolorate.screens.dpcm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.play.core.review.ReviewInfo;
import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.review.ReviewManagerFactory;
import com.google.android.play.core.tasks.OnCompleteListener;
import com.google.android.play.core.tasks.Task;
import com.italianappsw.regionicolorate.R;
import com.italianappsw.regionicolorate.models.Rule;
import com.italianappsw.regionicolorate.rules.OrangeRules;
import com.italianappsw.regionicolorate.rules.RedRules;
import com.italianappsw.regionicolorate.rules.WhiteRules;
import com.italianappsw.regionicolorate.rules.YellowRules;

import java.util.ArrayList;
import java.util.List;

public class DpcmActivity extends AppCompatActivity {

    private static final String REGION_COLOR_KEY = "COLORKEY";
    private static final String REVIEW_PREFERENCE_KEY = "in-app-key";
    private static final String NO_REVIEW_PREFERENCE = "no-review";


    private Toolbar mToolbar;
    private String regionColor;
    private RecyclerView
            mCommercialActivitiesListView,
            mSportiveActivitiesListView,
            mEventsListView,
            mInstructionListView,
            mDisplacementsListView;

    ReviewManager reviewManager;
    ReviewInfo reviewInfo = null;
    SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dpcm);

        regionColor = getIntent().getStringExtra(REGION_COLOR_KEY);

        setToolbar();

        setCommercialActivitiesListView();
        setSportiveActivitiesListView();
        setEventsListView();
        setInstructionListView();
        setDisplacementListView();

        sharedPref = getSharedPreferences(REVIEW_PREFERENCE_KEY, Context.MODE_PRIVATE);
        if (!(sharedPref.getString(REVIEW_PREFERENCE_KEY, "si-review").equals(NO_REVIEW_PREFERENCE))) {
            getReviewInfo();
        }
    }

    private void setDisplacementListView() {
        mDisplacementsListView = findViewById(R.id.displacementsListView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mDisplacementsListView.setLayoutManager(linearLayoutManager);
        List<Rule> ruleList = new ArrayList<>();
        switch (regionColor) {
            case "0":
                ruleList = WhiteRules.displacements;
                break;
            case "1":
                ruleList = YellowRules.displacements;
                break;
            case "2":
                ruleList = OrangeRules.displacements;
                break;
            case "3":
                ruleList = RedRules.displacements;
                break;

        }
        mDisplacementsListView.setAdapter(new RuleRecyclerViewAdapter(ruleList, this));
        mDisplacementsListView.setHasFixedSize(true);
    }

    private void setInstructionListView() {
        mInstructionListView = findViewById(R.id.instructionListView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mInstructionListView.setLayoutManager(linearLayoutManager);
        List<Rule> ruleList = new ArrayList<>();
        switch (regionColor) {
            case "0":
                ruleList = WhiteRules.instruction;
                break;
            case "1":
                ruleList = YellowRules.instruction;
                break;
            case "2":
                ruleList = OrangeRules.instruction;
                break;
            case "3":
                ruleList = RedRules.instruction;
                break;

        }
        mInstructionListView.setAdapter(new RuleRecyclerViewAdapter(ruleList, this));
        mInstructionListView.setHasFixedSize(true);
    }

    private void setEventsListView() {
        mEventsListView = findViewById(R.id.eventsListView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mEventsListView.setLayoutManager(linearLayoutManager);
        List<Rule> ruleList = new ArrayList<>();
        switch (regionColor) {
            case "0":
                ruleList = WhiteRules.events;
                break;
            case "1":
                ruleList = YellowRules.events;
                break;
            case "2":
                ruleList = OrangeRules.events;
                break;
            case "3":
                ruleList = RedRules.events;
                break;

        }
        mEventsListView.setAdapter(new RuleRecyclerViewAdapter(ruleList, this));
        mEventsListView.setHasFixedSize(true);
    }

    private void setSportiveActivitiesListView() {
        mSportiveActivitiesListView = findViewById(R.id.sportiveActivitiesListView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mSportiveActivitiesListView.setLayoutManager(linearLayoutManager);
        List<Rule> ruleList = new ArrayList<>();
        switch (regionColor) {
            case "0":
                ruleList = WhiteRules.sportive_activities;
                break;
            case "1":
                ruleList = YellowRules.sportive_activities;
                break;
            case "2":
                ruleList = OrangeRules.sportive_activities;
                break;
            case "3":
                ruleList = RedRules.sportive_activities;
                break;

        }
        mSportiveActivitiesListView.setAdapter(new RuleRecyclerViewAdapter(ruleList, this));
        mSportiveActivitiesListView.setHasFixedSize(true);
    }

    private void setCommercialActivitiesListView() {
        mCommercialActivitiesListView = findViewById(R.id.commercialActivitiesListView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mCommercialActivitiesListView.setLayoutManager(linearLayoutManager);
        List<Rule> ruleList = new ArrayList<>();
        switch (regionColor) {
            case "0":
                ruleList = WhiteRules.commercial_activities;
                break;
            case "1":
                ruleList = YellowRules.commercial_activities;
                break;
            case "2":
                ruleList = OrangeRules.commercial_activities;
                break;
            case "3":
                ruleList = RedRules.commercial_activities;
                break;

        }
        mCommercialActivitiesListView.setAdapter(new RuleRecyclerViewAdapter(ruleList, this));
        mCommercialActivitiesListView.setHasFixedSize(true);
    }

    private void setToolbar() {
        mToolbar = findViewById(R.id.appBarDpcmActivity);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        switch (regionColor) {
            case "0":
                mToolbar.setBackgroundColor(getResources().getColor(R.color.white));
                mToolbar.setTitleTextColor(getResources().getColor(R.color.black));
                getSupportActionBar().setTitle(getResources().getString(R.string.white_regions));
                getWindow().setStatusBarColor(getResources().getColor(R.color.grey200));
                break;
            case "1":
                mToolbar.setBackgroundColor(getResources().getColor(R.color.yellow));
                mToolbar.setTitleTextColor(getResources().getColor(R.color.black));
                getSupportActionBar().setTitle(getResources().getString(R.string.yellow_regions));
                getWindow().setStatusBarColor(getResources().getColor(R.color.yellowDark));
                break;
            case "2":
                mToolbar.setBackgroundColor(getResources().getColor(R.color.orange));
                mToolbar.setTitleTextColor(getResources().getColor(R.color.white));
                getSupportActionBar().setTitle(getResources().getString(R.string.orange_regions));
                getWindow().setStatusBarColor(getResources().getColor(R.color.orangeDark));
                break;
            case "3":
                mToolbar.setBackgroundColor(getResources().getColor(R.color.red));
                mToolbar.setTitleTextColor(getResources().getColor(R.color.white));
                getSupportActionBar().setTitle(getResources().getString(R.string.red_regions));
                getWindow().setStatusBarColor(getResources().getColor(R.color.redDark));
                break;
        }
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static Intent getIntentInstance(Context context, String regionColor)
    {
        Intent intent = new Intent(context, DpcmActivity.class);
        intent.putExtra(REGION_COLOR_KEY,regionColor);
        return intent;
    }

    private void getReviewInfo() {
        reviewManager = ReviewManagerFactory.create(getApplicationContext());
        Task<ReviewInfo> manager = reviewManager.requestReviewFlow();
        manager.addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                reviewInfo = task.getResult();
            } else {
                Toast.makeText(getApplicationContext(), "In App ReviewFlow failed to start", Toast.LENGTH_LONG).show();
            }
        });

        showReviewDialog();
    }

    private void showReviewDialog() {
        MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(this);
        dialog
                .setTitle("Vuoi lasciare una recensione?")
                .setMessage("Il tuo parere è molto importante per noi, lascia una recensione")
                .setNeutralButton("Ricordamelo dopo", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startReviewFlow();
                    }
                })
                .show();
    }

    public void startReviewFlow() {
        if (reviewInfo != null) {
            Task<Void> flow = reviewManager.launchReviewFlow(this, reviewInfo);
            flow.addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(Task<Void> task) {
                   setNoReviewPreference();
                }
            });
        }
    }

    public void setNoReviewPreference() {
        sharedPref.edit().putString(REVIEW_PREFERENCE_KEY, NO_REVIEW_PREFERENCE).apply();
    }
}