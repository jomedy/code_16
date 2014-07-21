package com.example.myfloatdemo1;

import com.ljp.laucher.myanimations.MyAnimations;

import android.os.Bundle;
import android.app.Activity;
import android.content.SharedPreferences;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class MainActivity extends Activity {

	private boolean areButtonsShowing;
	private RelativeLayout composerButtonsWrapper;
	private ImageView composerButtonsShowHideButtonIcon;
	private RelativeLayout composerButtonsShowHideButton;
	
	ImageButton btn_skin;
	SharedPreferences sp_skin;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        initPath();
    }


    public void initPath() {
		MyAnimations.initOffset(MainActivity.this);
		btn_skin = (ImageButton) findViewById(R.id.composer_button_sleep);
		sp_skin = getSharedPreferences("skin", MODE_PRIVATE);
		btn_skin.setBackgroundResource(sp_skin.getBoolean("id", true)?R.drawable.composer_sleep:R.drawable.composer_sun);
		composerButtonsWrapper = (RelativeLayout) findViewById(R.id.composer_buttons_wrapper);
		composerButtonsShowHideButton = (RelativeLayout) findViewById(R.id.composer_buttons_show_hide_button);
		composerButtonsShowHideButtonIcon = (ImageView) findViewById(R.id.composer_buttons_show_hide_button_icon);
		//
		composerButtonsShowHideButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!areButtonsShowing) {
					composerButtonsShowHideButtonIcon
					.startAnimation(MyAnimations.getRotateAnimation(0, -270,300));
					MyAnimations.startAnimationsIn(composerButtonsWrapper, 300);
				} else {
					composerButtonsShowHideButtonIcon
					.startAnimation(MyAnimations.getRotateAnimation(-270,0, 300));
					MyAnimations.startAnimationsOut(composerButtonsWrapper, 300);
				}
				areButtonsShowing = !areButtonsShowing;
			}
		});
    
}
}
