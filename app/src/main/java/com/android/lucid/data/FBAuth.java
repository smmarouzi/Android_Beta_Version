package com.android.lucid.data;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FBAuth {

    private static FBAuth instance;

    public static FBAuth getInstance() {
        if (instance == null) instance = new FBAuth();
        return instance;
    }

    private FBAuth() {
        // initialize class...
    }

    public FirebaseAuth getAuth() {
        return FirebaseAuth.getInstance();
    }

    public FirebaseUser getCurrentUser() {
        return getAuth().getCurrentUser();
    }

    public boolean isLogin() {
        return getCurrentUser() != null;
    }

    public boolean isEmailVerified() {
        return isLogin() && getCurrentUser().isEmailVerified();
    }

    public String getUserId() {
        if (getAuth() == null || getAuth().getCurrentUser() == null)
            return "";
        return getAuth().getCurrentUser().getUid();
    }

    public void logout() {
        getAuth().signOut();
    }
}
