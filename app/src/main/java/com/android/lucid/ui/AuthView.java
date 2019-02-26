package com.android.lucid.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Toast;

import com.android.lucid.R;
import com.android.lucid.data.FBAuth;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.auth.api.signin.GoogleSignInStatusCodes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;


public class AuthView extends BaseView {

    protected static enum AuthStatus {
        Verified, NotVerified, Unknown
    }

    protected GoogleApiClient mGoogleApiClient;
    protected FBAuth mAuth = FBAuth.getInstance();

    protected FirebaseAuth.AuthStateListener mAuthStateListener = new FirebaseAuth.AuthStateListener() {
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            if (mAuth.isEmailVerified()) {
                onAuthChange(AuthStatus.Verified);
            } else if (mAuth.isLogin()) {
                onAuthChange(AuthStatus.NotVerified);
            } else {
                onAuthChange(AuthStatus.Unknown);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeAuth();
    }

    protected void initializeAuth() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, null)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        mAuth.getAuth().addAuthStateListener(mAuthStateListener);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                startActivity(new Intent(this, LogInView.class));
                break;
            case R.id.btn_sign_up:
                startActivity(new Intent(this, SignUpView.class));
                break;
            case R.id.btn_sign_in_google:
                openGoogleSignInIntent();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {
                String errorMessage = "";
                int errorCode = result.getStatus().getStatusCode();
                switch (errorCode) {
                    case GoogleSignInStatusCodes.SIGN_IN_CANCELLED:
                        errorMessage = "Sign in canceled.";
                        break;
                    case GoogleSignInStatusCodes.SIGN_IN_FAILED:
                        errorMessage = "The sign in attempt did not succeed. Make sure you are online and try again.";
                        break;
                    case GoogleSignInStatusCodes.SIGN_IN_REQUIRED:
                        errorMessage = "The sign in attempt did not succeed with the current account.Try another account.";
                        break;
                    default:
                        if (resultCode != 0) {
                            Toast.makeText(this, "error code : " + resultCode, Toast.LENGTH_LONG).show();
                        }
                        break;
                }
                if (!errorMessage.isEmpty()) {
                    showAlert(errorMessage);
                }
            }
        }
    }

    private void firebaseAuthWithGoogle(final GoogleSignInAccount acct) {
        showLoading();
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.getAuth().signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        hideLoading();
                        if (!task.isSuccessful()) {
                            showAlert("Sign in filed!" );
                        } else {
                            FirebaseUser firebaseUser = task.getResult().getUser();
                            String uuid = firebaseUser.getUid();
                            String email = firebaseUser.getEmail();
                            String firstname = firebaseUser.getDisplayName();
//                            mDb.saveGMailUserData(uuid, email, firstname, "" );
                        }
                    }
                });
    }

    private void openGoogleSignInIntent() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    protected void onAuthChange(AuthStatus status) {
        switch (status) {
            case Verified:
            case NotVerified:
                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
                break;
            case Unknown:
                setContentView(R.layout.auth_view);
                break;
        }

    }

    @Override
    public void onDestroy() {
        if (mAuthStateListener != null) {
            mAuth.getAuth().removeAuthStateListener(mAuthStateListener);
        }
        super.onDestroy();
    }

}
