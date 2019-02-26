package com.android.lucid.data;

import com.android.lucid.model.UserExperienceModel;
import com.google.firebase.database.FirebaseDatabase;

public class FBDb {

    private static FBDb instance;

    private FirebaseDatabase mDb;

    public static FBDb getInstance() {
        if (instance == null) instance = new FBDb();
        return instance;
    }

    private FBDb() {
        mDb = FirebaseDatabase.getInstance();
        mDb.setPersistenceEnabled(true);
    }


    public void saveUserExperience(UserExperienceModel userExperienceModel) {
        final String userId = FBAuth.getInstance().getUserId();
        String newId = mDb.getReference("userExperience").child(userId).push().getKey();
        mDb.getReference("userExperience")
                .child(userId)
                .child(newId)
                .setValue(userExperienceModel);
    }
}
