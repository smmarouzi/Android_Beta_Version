package com.android.lucid.ui;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.android.lucid.R;
import com.android.lucid.data.FBDb;
import com.android.lucid.model.UserExperienceModel;

public class ClosingView extends BaseView {

    private UserExperienceModel mUserExperienceModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.closing_view);
        mUserExperienceModel = UserExperienceModel.getPack(getIntent().getStringExtra("feeling_model"));
    }

    public void onDoneClick(View v) {
        FBDb.getInstance().saveUserExperience(mUserExperienceModel);
        showLoading();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                hideLoading();
                finish();
            }
        }, 1000);
    }

}
