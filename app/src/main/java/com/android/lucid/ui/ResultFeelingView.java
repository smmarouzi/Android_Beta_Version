package com.android.lucid.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.lucid.R;
import com.android.lucid.model.UserExperienceModel;
import com.warkiz.widget.IndicatorSeekBar;
import com.warkiz.widget.IndicatorType;
import com.warkiz.widget.OnSeekChangeListener;
import com.warkiz.widget.SeekParams;
import com.warkiz.widget.TickMarkType;

public class ResultFeelingView extends BaseView {

    private UserExperienceModel mUserExperienceModel;
    private IndicatorSeekBar mSeekBar;
    private TextView tvSeekbarValue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_feeling_view);
        mUserExperienceModel = UserExperienceModel.getPack(getIntent().getStringExtra("feeling_model" ));
        setUpUi();
    }

    private void setUpUi() {
        tvSeekbarValue = findViewById(R.id.tv_value);
        tvSeekbarValue.setText("1" );
        String title;
        int captionMinResId, captionMaxResId;
        if (mUserExperienceModel.isEnergize()) {
            title = "How energized do you feel now?";
            captionMaxResId = R.drawable.txt_energized;
            captionMinResId = R.drawable.txt_not_energized;
        } else {
            captionMaxResId = R.drawable.txt_calm;
            title = "How calm do you feel now?";
            captionMinResId = R.drawable.txt_not_calm;
        }
        ((TextView) findViewById(R.id.tv_title)).setText(title);
        ((ImageView) findViewById(R.id.iv_caption_min)).setImageResource(captionMinResId);
        ((ImageView) findViewById(R.id.iv_caption_max)).setImageResource(captionMaxResId);
        mSeekBar = IndicatorSeekBar
                .with(this)
                .max(10)
                .min(1)
                .progress(1)
//                .tickCount(7)
                .showTickMarksType(TickMarkType.OVAL)
//                .tickMarksColor(getResources().getColor(R.color.color_blue, null))
//                .tickMarksSize(13)//dp
                .showTickTexts(true)
//                .tickTextsColor(getResources().getColor(R.color.color_pink))
//                .tickTextsSize(13)//sp
//                .tickTextsTypeFace(Typeface.MONOSPACE)
                .showIndicatorType(IndicatorType.CIRCULAR_BUBBLE)
                .indicatorColor(0xFFFFFFFF)
                .indicatorTextColor(0xFF383838)
                .indicatorTextSize(14)//sp
                .thumbColor(0xFFa8a8a8)
                .thumbSize(24)
                .trackProgressColor(0xFFFFFFFF)
                .trackProgressSize(6)
                .trackBackgroundColor(0xFFa8a8a8)
                .trackBackgroundSize(6)
                .onlyThumbDraggable(false)
                .build();
        ((FrameLayout) findViewById(R.id.seekbar_container)).addView(mSeekBar);
        mSeekBar.setOnSeekChangeListener(new OnSeekChangeListener() {
            @Override
            public void onSeeking(SeekParams seekParams) {
                tvSeekbarValue.setText(seekParams.progress + "" );
            }

            @Override
            public void onStartTrackingTouch(IndicatorSeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(IndicatorSeekBar seekBar) {

            }
        });
    }

    public void onBackClick(View v) {
        finish();
    }

    public void onNextClick(View v) {
        finish();
        Intent i = new Intent(this, ClosingView.class);
        mUserExperienceModel.resultFeelingValue = mSeekBar.getProgress();
        i.putExtra("feeling_model", mUserExperienceModel.toJson());
        startActivity(i);
    }
}
