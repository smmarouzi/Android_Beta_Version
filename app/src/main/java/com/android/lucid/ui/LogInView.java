package com.android.lucid.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.android.lucid.R;
import com.android.lucid.data.FBAuth;
import com.android.lucid.utils.Utility;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

public class LogInView extends BaseView {

    private EditText etPassword, etEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_in_view);
        initUi();
    }

    private void initUi() {
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
    }

    public void onLogInClick(View v) {
        if (validateFields()) {
            logIn();
        }
    }

    private boolean validateFields() {
        Utility.hideKeyboard(this);
        final String email = etEmail.getEditableText().toString();
        final String password = etPassword.getEditableText().toString();
        if (TextUtils.isEmpty(email) || !isEmailValid(email)) {
            String err = TextUtils.isEmpty(email) ? "This field is required" : "Email address is invalid";
            etEmail.setError(err);
            return false;
        }

        if (TextUtils.isEmpty(password) || !isPasswordValid(password)) {
            String err = TextUtils.isEmpty(password) ? "This field is required" : "Password is too short";
            etPassword.setError(err);
            return false;
        }
        return true;
    }

    private void logIn() {
        final String email = etEmail.getEditableText().toString();
        final String password = etPassword.getEditableText().toString();
        showLoading();
        FBAuth.getInstance()
                .getAuth()
                .signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            openMainActivity();
                        } else {
                            hideLoading();
                            String errorMessage = task.getException().getMessage();
                            showAlert(errorMessage);
                        }
                    }
                });
    }

    private boolean isEmailValid(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

    private void openMainActivity() {
        hideLoading();
        finish();
    }

}
