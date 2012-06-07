package com.shivgadhia.android.imgshrink;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ViewSwitcher;

public class ImgShrinkActivity extends FragmentActivity {
    private Button btnContinue;
	private ViewSwitcher mMainViewSwitcher;
	private OnClickListener mContinueListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			mMainViewSwitcher.showNext();
			
		}
	};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        btnContinue = (Button)findViewById(R.id.btnContinue);
        if(btnContinue != null){
        	btnContinue.setOnClickListener(mContinueListener );
        }
        mMainViewSwitcher = (ViewSwitcher)findViewById(R.id.vsMain);
    }
}
/* 
 * Still need to create Preference activity for settings
 * - Default output path
 * - Auto create smaller images - when picture is taken
 * 
 * Intents / Broadcast receivers
 * - Share - DataType images
 * - Presets - phone res, tablet res, tv res
 * 
 * Content Provider? - gallery 
 * - Sub-folder only?
 */