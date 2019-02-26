package com.android.lucid.ui;

import android.content.DialogInterface;
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

public class SignUpView extends BaseView {

    private EditText etPassword, etRePassword, etEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_view);
        initUi();
    }

    private void initUi() {
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        etRePassword = findViewById(R.id.et_re_password);
    }

    public void onSignUpClick(View v) {
        if (validateFields()) {
            createAccount();
        }
    }

    private boolean validateFields() {
        Utility.hideKeyboard(this);
        final String email = etEmail.getEditableText().toString();
        final String password = etPassword.getEditableText().toString();
        final String rePassword = etRePassword.getEditableText().toString();
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
        if (TextUtils.isEmpty(rePassword) || !password.equals(rePassword)) {
            String err = TextUtils.isEmpty(rePassword) ? "This field is required" : "Password is not match";
            etRePassword.setError(err);
            return false;
        }
        return true;
    }

    private void createAccount() {
        final String email = etEmail.getEditableText().toString();
        final String password = etPassword.getEditableText().toString();
        showLoading();
        FBAuth.getInstance().getAuth()
                .createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
//                            sendConfirmationEmailَAndLogout();
                            openMainActivity();
                        } else {
                            hideLoading();
                            String errorMessage = task.getException().getMessage();
                            showAlert(errorMessage);
                        }
                    }
                });
    }

    private void sendConfirmationEmailَAndLogout() {
        FBAuth.getInstance().getAuth()
                .getCurrentUser()
                .sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        hideLoading();
                        FBAuth.getInstance().logout();
                        DialogInterface.OnDismissListener dismissListener = null;
                        String msg;
                        if (task.isSuccessful()) {
                            msg = "A confirmation email has been sent to your email.\nPlease check and activate your account then login.";
                            dismissListener = new DialogInterface.OnDismissListener() {
                                @Override
                                public void onDismiss(DialogInterface dialogInterface) {
                                    finish();
                                }
                            };
                        } else {
                            msg = "Create account failed. Please try again.";
                            dismissListener = null;
                        }
                        showAlert(msg).setOnDismissListener(dismissListener);
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
