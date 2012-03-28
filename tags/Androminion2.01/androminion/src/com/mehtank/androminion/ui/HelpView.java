package com.mehtank.androminion.ui;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewParent;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.mehtank.androminion.R;

public class HelpView extends FrameLayout {
	Context top;
	
	TextView helpText;
	FrameLayout callout;
	Button helpNext;
	View[] showViews;
	View[] parentViews;

	public void setShowViews(View[] showViews) {
		this.showViews = showViews;
	}
	
	public HelpView(Context context, View[] showViews, View[] parentViews) {
		super(context);
		top = context;
		this.showViews = showViews;
		this.parentViews = parentViews;
		
		callout = new FrameLayout(this.top);
		helpText = new TextView(this.top);
		// helpText.setTextSize(helpText.getTextSize()*1.2f);
		helpText.setTextColor(Color.BLACK);
		callout.addView(helpText);
		
		helpNext = new Button(this.top);
		helpNext.setText(R.string.help_next);

		Button helpQuit = new Button(this.top);
		helpQuit.setText(R.string.help_quit);
		helpQuit.setOnClickListener(new OnClickListener(){
			public void onClick(View v) { showHelp(0); }
		});

		FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
				FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT, 
				Gravity.BOTTOM + Gravity.LEFT);
		callout.addView(helpNext, lp);
		
		lp = new FrameLayout.LayoutParams(
				FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT, 
				Gravity.BOTTOM + Gravity.RIGHT);
		callout.addView(helpQuit, lp);
		addView(callout);
		setVisibility(INVISIBLE);
	}

	int helpText(int stringID, View parent, final int page, int bgID) {
		helpNext.setOnClickListener(new OnClickListener(){
			public void onClick(View v) { showHelp(page+1); }
		});

		int left = parent.getLeft();
		int top = parent.getTop();
		ViewParent vp = parent.getParent();
		while (vp != getRootView()) {
			left += ((View)vp).getLeft();
			top += ((View)vp).getTop();
			vp = vp.getParent();
		}
		
		if (bgID != 0)
			callout.setBackgroundResource(bgID);
				
		helpText.setText(stringID);
		
		FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(parent.getWidth() + left, parent.getHeight() + top);
		setLayoutParams(lp);
		
		setPadding(left, top, 0, 0);
		return 0;
	}

	public int showHelp(int page) {
		int curpage = 1;
		int chapter = -1;
		int stringID = 0;
		int bgID = 0;
		boolean fadein = false;
		boolean fadeout = false;
		
		hide();
				
		if (page == curpage++) {
			stringID = R.string.help_supply1;
			chapter = 0;
			bgID = R.drawable.help_below;
			fadeout = true;
		} else if (page == curpage++) {
			stringID = R.string.help_supply2;
			chapter = 0;
		} else if (page == curpage++) {
			stringID = R.string.help_supply3;
			chapter = 0;
		} else if (page == curpage++) {
			stringID = R.string.help_supply4;
			chapter = 0;

		} else if (page == curpage++) {
			stringID = R.string.help_turn1;
			chapter = 1;
			bgID = R.drawable.help_above_right;
			fadein = true;
			fadeout = true;
		} else if (page == curpage++) {
			stringID = R.string.help_turn2;
			chapter = 1;
		} else if (page == curpage++) {
			stringID = R.string.help_turn3;
			chapter = 1;
		} else if (page == curpage++) {
			stringID = R.string.help_turn4;
			chapter = 1;
		} else if (page == curpage++) {
			stringID = R.string.help_turn5;
			chapter = 1;
			
		} else if (page == curpage++) {
			stringID = R.string.help_hand1;
			chapter = 2;
			bgID = R.drawable.help_above_left;
			fadein = true;
			fadeout = true;
		} else if (page == curpage++) {
			stringID = R.string.help_hand2;
			chapter = 2;
		} else if (page == curpage++) {
			stringID = R.string.help_hand3;
			chapter = 2;
		} else if (page == curpage++) {
			stringID = R.string.help_hand4;
			chapter = 2;
		} else if (page == curpage++) {
			stringID = R.string.help_hand5;
			chapter = 2;
			
		} else if (page == curpage++) {
			stringID = R.string.help_log1;
			chapter = 3;
			bgID = R.drawable.help_above_right;
			fadein = true;
			fadeout = true;
		} else if (page == curpage++) {
			stringID = R.string.help_end;
			chapter = 3;
			
		} else if (page == curpage++) {
			chapter = 4;
			fadein = true;
		}

		for (int i = 0; i < showViews.length; i++) {
			final View v = showViews[i];
			AnimationSet fullfade = new AnimationSet(true);

			Animation fade = AnimationUtils.loadAnimation(top, android.R.anim.fade_in);
			if (page == 0) { 
				v.setVisibility(VISIBLE);
				fade.setDuration(50L);
				fullfade.addAnimation(fade);
			} else if (fadein && (i != chapter-1)) {
				v.setVisibility(VISIBLE);
				fade.setDuration(700L);
				fullfade.addAnimation(fade);				
			}
			
			fade = AnimationUtils.loadAnimation(top, android.R.anim.fade_out);
			if (fadeout && (i != chapter)) {
				fade.setDuration(700L);
				if (fadein) fade.setStartOffset(700L);
				fade.setAnimationListener(new AnimationListener(){
					@Override
					public void onAnimationEnd(Animation anim) {
						v.setVisibility(INVISIBLE);
						setVisibility(VISIBLE);
					}
					@Override public void onAnimationRepeat(Animation animation) {}
					@Override public void onAnimationStart(Animation animation) {}
				} );

				fullfade.addAnimation(fade);
			}
			if (fullfade.getAnimations().size() > 0)
				v.startAnimation(fullfade);
		}
		

		if (stringID != 0) {
			if (!fadeout)
				setVisibility(VISIBLE);
			helpText(stringID, parentViews[chapter], page, bgID);
		}
		
		return 0;
	}	

	public void hide() {
		setVisibility(INVISIBLE);
	}

}