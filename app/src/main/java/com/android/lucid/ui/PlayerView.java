package com.android.lucid.ui;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;

import com.android.lucid.R;
import com.android.lucid.data.FBDb;
import com.android.lucid.model.UserExperienceModel;

public class PlayerView extends BaseView implements View.OnClickListener {

    private MediaPlayer mMediaPlayer = null;
    private UserExperienceModel mUserExperienceModel;

    private ImageButton btnTogglePlayer, btnToggleType1, btnToggleType2;
    int bgNormalResId, bgToggleResId;
    int btnMinNormalResId, btnMaxNormalResId, btnMinSelectedResId, btnMaxSelectedResId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player_view);
        mUserExperienceModel = UserExperienceModel.getPack(getIntent().getStringExtra("feeling_model" ));
        setUpUi();
    }

    private void setUpUi() {
        btnToggleType1 = findViewById(R.id.btn_toggle_type_1);
        btnToggleType2 = findViewById(R.id.btn_toggle_type_2);
        btnTogglePlayer = findViewById(R.id.btn_toggle_player);

        if (mUserExperienceModel.isEnergize()) {
            bgNormalResId = R.drawable.bg_energize_choose_your_exp;
            bgToggleResId = R.drawable.bg_energize_toggle;
            btnMinNormalResId = R.drawable.btn_energize_min;
            btnMaxNormalResId = R.drawable.btn_energize_max;
            btnMinSelectedResId = R.drawable.btn_energize_min_selected;
            btnMaxSelectedResId = R.drawable.btn_energize_max_selected;
        } else {
            bgNormalResId = R.drawable.bg_calm_choose_your_exp;
            bgToggleResId = R.drawable.bg_calm_toggle;
            btnMinNormalResId = R.drawable.btn_calm_min;
            btnMaxNormalResId = R.drawable.btn_calm_max;
            btnMinSelectedResId = R.drawable.btn_calm_min_selected;
            btnMaxSelectedResId = R.drawable.btn_calm_max_seleceted;

        }
        findViewById(R.id.root).setBackgroundResource(bgNormalResId);
        btnToggleType1.setImageResource(btnMinNormalResId);
        btnToggleType2.setImageResource(btnMaxNormalResId);

        btnToggleType1.setOnClickListener(this);
        btnToggleType2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        btnToggleType1.setSelected(v.getId() == R.id.btn_toggle_type_1);
        btnToggleType2.setSelected(v.getId() == R.id.btn_toggle_type_2);
        btnToggleType1.setImageResource(btnToggleType1.isSelected() ? btnMinSelectedResId : btnMinNormalResId);
        btnToggleType2.setImageResource(btnToggleType2.isSelected() ? btnMaxSelectedResId : btnMaxNormalResId);

        if (btnToggleType1.isSelected() || btnToggleType2.isSelected()) {
            findViewById(R.id.root).setBackgroundResource(bgToggleResId);
        } else {
            findViewById(R.id.root).setBackgroundResource(bgNormalResId);
        }
    }

    public void onBackClick(View v) {
        FBDb.getInstance().saveUserExperience(mUserExperienceModel);
        onBackPressed();
    }

    public void onTogglePlayerClick(View v) {
        if (!btnToggleType1.isSelected() && !btnToggleType2.isSelected()) {
            showMessage("Please choose your track." );
            return;
        }
        btnToggleType1.setEnabled(mMediaPlayer != null);
        btnToggleType2.setEnabled(mMediaPlayer != null);

        if (mMediaPlayer != null) {
            mMediaPlayer.reset();
            mMediaPlayer.release();
            mMediaPlayer = null;
            btnTogglePlayer.setImageResource(R.drawable.btn_play);
            return;
        }
        btnTogglePlayer.setImageResource(R.drawable.btn_pause);
        int soundFleResId=0;
        if (mUserExperienceModel.isEnergize()) {
            switch (mUserExperienceModel.beginFeelingValue) {
                case 1:
                    if (btnToggleType1.isSelected()) {
                        soundFleResId = R.raw.feeling_energize_type1_level1;
                        mUserExperienceModel.trackName = "feeling_energize_wakeup_level1.mp3";
                    } else {
                        soundFleResId = R.raw.feeling_energize_type2_level1;
                        mUserExperienceModel.trackName = "feeling_energize_re_energize_level1.mp3";
                    }
                    break;
                case 2:
                    if (btnToggleType1.isSelected()) {
                        soundFleResId = R.raw.feeling_energize_type1_level1;
                        mUserExperienceModel.trackName = "feeling_energize_re_energize_level1.mp3";
                    } else {
                        soundFleResId = R.raw.feeling_energize_type2_level1;
                        mUserExperienceModel.trackName = "feeling_energize_type_2.mp3";
                    }
                    break;
                case 3:
                    if (btnToggleType1.isSelected()) {
                        soundFleResId = R.raw.feeling_energize_type1_level2;
                        mUserExperienceModel.trackName = "feeling_energize_wakeup_level2.mp3";
                    } else {
                        soundFleResId = R.raw.feeling_energize_type2_level2;
                        mUserExperienceModel.trackName = "feeling_energize_re_energize_level2.mp3";
                    }
                    break;
                case 4:
                    if (btnToggleType1.isSelected()) {
                        soundFleResId = R.raw.feeling_energize_type1_level2;
                        mUserExperienceModel.trackName = "feeling_energize_wakeup_level2.mp3";
                    } else {
                        soundFleResId = R.raw.feeling_energize_type2_level2;
                        mUserExperienceModel.trackName = "feeling_energize_re_energize_level2.mp3";
                    }
                    break;
                case 5:
                    if (btnToggleType1.isSelected()) {
                        soundFleResId = R.raw.feeling_energize_type1_level3;
                        mUserExperienceModel.trackName = "feeling_energize_wakeup_level3.mp3";
                    } else {
                        soundFleResId = R.raw.feeling_energize_type2_level3;
                        mUserExperienceModel.trackName = "feeling_energize_re_energize_level3.mp3";
                    }
                    break;
                case 6:
                    if (btnToggleType1.isSelected()) {
                        soundFleResId = R.raw.feeling_energize_type1_level3;
                        mUserExperienceModel.trackName = "feeling_energize_wakeup_level3.mp3";
                    } else {
                        soundFleResId = R.raw.feeling_energize_type2_level3;
                        mUserExperienceModel.trackName = "feeling_energize_re_energize_level3.mp3";
                    }
                    break;
                case 7:
                    if (btnToggleType1.isSelected()) {
                        soundFleResId = R.raw.feeling_energize_type1_level4;
                        mUserExperienceModel.trackName = "feeling_energize_wakeup_level4.mp3";
                    } else {
                        soundFleResId = R.raw.feeling_energize_type2_level4;
                        mUserExperienceModel.trackName = "feeling_energize_re_energize_level4.mp3";
                    }
                    break;
                case 8:
                    if (btnToggleType1.isSelected()) {
                        soundFleResId = R.raw.feeling_energize_type1_level4;
                        mUserExperienceModel.trackName = "feeling_energize_wakeup_level4.mp3";
                    } else {
                        soundFleResId = R.raw.feeling_energize_type2_level4;
                        mUserExperienceModel.trackName = "feeling_energize_re_energize_level4.mp3";
                    }
                    break;
                case 9:
                    if (btnToggleType1.isSelected()) {
                        soundFleResId = R.raw.feeling_energize_type1_level5;
                        mUserExperienceModel.trackName = "feeling_energize_wakeup_level5.mp3";
                    } else {
                        soundFleResId = R.raw.feeling_energize_type2_level5;
                        mUserExperienceModel.trackName = "feeling_energize_re_energize_level5.mp3";
                    }
                    break;
                case 10:
                    if (btnToggleType1.isSelected()) {
                        soundFleResId = R.raw.feeling_energize_type1_level5;
                        mUserExperienceModel.trackName = "feeling_energize_wakeup_level5.mp3";
                    } else {
                        soundFleResId = R.raw.feeling_energize_type2_level5;
                        mUserExperienceModel.trackName = "feeling_energize_re_energize_level5.mp3";
                    }
                    break;
            }

            /*
            if (btnToggleType1.isChecked()) {
                soundFleResId = R.raw.feeling_energize_type_1;
                mUserExperienceModel.trackName = "feeling_energize_type_1.mp3";
            } else {
                soundFleResId = R.raw.feeling_energize_type_2;
                mUserExperienceModel.trackName = "feeling_energize_type_2.mp3";
            }*/
        } else {
            switch (mUserExperienceModel.beginFeelingValue) {
                case 1:
                    if (btnToggleType1.isSelected()) {
                        soundFleResId = R.raw.feeling_calm_type1_level1;
                        mUserExperienceModel.trackName = "feeling_calm_3.5_level1.mp3";
                    } else {
                        soundFleResId = R.raw.feeling_calm_type2_level1;
                        mUserExperienceModel.trackName = "feeling_calm_7_level1.mp3";
                    }
                    break;
                case 2:
                    if (btnToggleType1.isSelected()) {
                        soundFleResId = R.raw.feeling_calm_type1_level1;
                        mUserExperienceModel.trackName = "feeling_calm_3.5_level1.mp3";
                    } else {
                        soundFleResId = R.raw.feeling_calm_type2_level1;
                        mUserExperienceModel.trackName = "feeling_calm_7_level1.mp3";
                    }
                    break;
                case 3:
                    if (btnToggleType1.isSelected()) {
                        soundFleResId = R.raw.feeling_calm_type1_level2;
                        mUserExperienceModel.trackName = "feeling_calm_3.5_level2.mp3";
                    } else {
                        soundFleResId = R.raw.feeling_calm_type2_level2;
                        mUserExperienceModel.trackName = "feeling_calm_7_level2.mp3";
                    }
                    break;
                case 4:
                    if (btnToggleType1.isSelected()) {
                        soundFleResId = R.raw.feeling_calm_type1_level2;
                        mUserExperienceModel.trackName = "feeling_calm_3.5_level2.mp3";
                    } else {
                        soundFleResId = R.raw.feeling_calm_type2_level2;
                        mUserExperienceModel.trackName = "feeling_calm_7_level2.mp3";
                    }
                    break;
                case 5:
                    if (btnToggleType1.isSelected()) {
                        soundFleResId = R.raw.feeling_calm_type1_level3;
                        mUserExperienceModel.trackName = "feeling_calm_3.5_level3.mp3";
                    } else {
                        soundFleResId = R.raw.feeling_calm_type2_level3;
                        mUserExperienceModel.trackName = "feeling_calm_7_level3.mp3";
                    }
                    break;
                case 6:
                    if (btnToggleType1.isSelected()) {
                        soundFleResId = R.raw.feeling_calm_type1_level3;
                        mUserExperienceModel.trackName = "feeling_calm_3.5_level3.mp3";
                    } else {
                        soundFleResId = R.raw.feeling_calm_type2_level3;
                        mUserExperienceModel.trackName = "feeling_calm_7_level3.mp3";
                    }
                    break;
                case 7:
                    if (btnToggleType1.isSelected()) {
                        soundFleResId = R.raw.feeling_calm_type1_level4;
                        mUserExperienceModel.trackName = "feeling_calm_3.5_level4.mp3";
                    } else {
                        soundFleResId = R.raw.feeling_calm_type2_level4;
                        mUserExperienceModel.trackName = "feeling_calm_7_level4.mp3";
                    }
                    break;
                case 8:
                    if (btnToggleType1.isSelected()) {
                        soundFleResId = R.raw.feeling_calm_type1_level4;
                        mUserExperienceModel.trackName = "feeling_calm_3.5_level4.mp3";
                    } else {
                        soundFleResId = R.raw.feeling_calm_type2_level4;
                        mUserExperienceModel.trackName = "feeling_calm_7_level4.mp3";
                    }
                    break;
                case 9:
                    if (btnToggleType1.isSelected()) {
                        soundFleResId = R.raw.feeling_calm_type1_level5;
                        mUserExperienceModel.trackName = "feeling_calm_3.5_level5.mp3";
                    } else {
                        soundFleResId = R.raw.feeling_calm_type2_level5;
                        mUserExperienceModel.trackName = "feeling_calm_7_level5.mp3";
                    }
                    break;
                case 10:
                    if (btnToggleType1.isSelected()) {
                        soundFleResId = R.raw.feeling_calm_type1_level5;
                        mUserExperienceModel.trackName = "feeling_calm_3.5_level5.mp3";
                    } else {
                        soundFleResId = R.raw.feeling_calm_type2_level5;
                        mUserExperienceModel.trackName = "feeling_calm_7_level5.mp3";
                    }
                    break;
            }
        }
        /*
        if (btnToggleType1.isChecked()) {
            soundFleResId = R.raw.feeling_calm_type_1;
            mUserExperienceModel.trackName = "feeling_calm_type_1.mp3";
        } else {
            mUserExperienceModel.trackName = "feeling_calm_type_2.mp3";
            soundFleResId = R.raw.feeling_calm_type_2;
        }*/
        mMediaPlayer = MediaPlayer.create(getApplicationContext(), soundFleResId);
        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mMediaPlayer.reset();
                mMediaPlayer.release();
                mMediaPlayer = null;
                finish();
                Intent i = new Intent(PlayerView.this, ResultFeelingView.class);
                i.putExtra("feeling_model", mUserExperienceModel.toJson());
                startActivity(i);
            }
        });
        mMediaPlayer.start();
    }

    @Override
    public void onResume() {
        super.onResume();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    @Override
    protected void onPause() {
        super.onPause();
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    @Override
    public void onBackPressed() {
        if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
            showMessage("Please pause first." );
            return;
        }
        super.onBackPressed();
    }
}
