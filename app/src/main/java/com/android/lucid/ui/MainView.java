package com.android.lucid.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import com.android.lucid.R;
import com.android.lucid.model.UserExperienceModel;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

public class MainView extends AuthView {


    private final View.OnClickListener mFeelStateViewClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent i = new Intent(MainView.this, BeginFeelingView.class);
            UserExperienceModel model = new UserExperienceModel();
            if (view.getId() == R.id.btn_calm) {
                model.mode = UserExperienceModel.MODE_CALM;
            } else if (view.getId() == R.id.btn_energize) {
                model.mode = UserExperienceModel.MODE_ENERGIZE;
            }
            i.putExtra("feeling_model", model.toJson());
            startActivity(i);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onAuthChange(AuthStatus status) {
        switch (status) {
            case Verified:
                initUi();
                break;
            case NotVerified:
                showAlert("You need to active your account." );
                initUi();
                break;
            case Unknown:
                Intent intent = new Intent(this, AuthView.class);
                startActivityForResult(intent, REQUEST_AUTH);
                break;
        }
    }

    private void initUi() {
        setContentView(R.layout.main_view);
        findViewById(R.id.btn_energize).setOnClickListener(mFeelStateViewClick);
        findViewById(R.id.btn_calm).setOnClickListener(mFeelStateViewClick);
    }

    public void logout(View v) {
        showAlert("Are you sure you want to log out?", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                showLoading();
                mAuth.logout();
                Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        hideLoading();
                    }
                });
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_AUTH:
                if (resultCode == RESULT_OK) {
                    // already auth status detected
                } else {
                    finish();
                }
                break;
            default:
                break;
        }
    }

}
