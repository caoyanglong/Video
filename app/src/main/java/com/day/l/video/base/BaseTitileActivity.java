package com.day.l.video.base;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.TextView;

import com.day.l.video.R;


public abstract class BaseTitileActivity extends FragmentActivity {
    protected FragmentManager fragmentManager;
    protected Context context;
    protected Resources resources;
    private TextView back;
	private TextView center;
	private TextView right;
	@Override
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		fragmentManager = getSupportFragmentManager();
		context = this;
		resources = context.getResources();
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(setContentView());
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.custommenu);
		back = (TextView) findViewById(R.id.back);
		center = (TextView) findViewById(R.id.title);
		right = (TextView) findViewById(R.id.right);
		oncreate(back, center, right);
		setTitleOnclickListener();
	}
	/**
	 * oncreate 写自己的业务逻辑代码
	 */
	public abstract void oncreate(TextView back,TextView center,TextView right);
	/**
	 * 防止遗忘   设置布局
	 * @return
	 */
	public abstract int setContentView();
	
	public void setBackTitle(String title){
		back.setText(title);
	}
	public void setBackTitle(int titleid){
		back.setText(resources.getString(titleid));
	}
	public void setCenterTitle(String title){
		center.setText(title);
	}
	public void setCenterTitle(int titleid){
		center.setText(resources.getString(titleid));
	}
	public void setRightTitle(String title){
		right.setText(title);
	}
	public void setRightTitle(int titleid){
		right.setText(resources.getString(titleid));
	}
	private void setTitleOnclickListener(){
		back.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				onTitleBackPressed(v);
			}
		});
		center.setOnClickListener(new OnClickListener() {
					
					public void onClick(View v) {
						onTitleCenterPressed(v);
					}
				});
		right.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				onTitleRightPressed(v);
			}
		});
	}
	public void onTitleBackPressed(View v){
		
	}
	public void onTitleCenterPressed(View v){
			
		}
	public void onTitleRightPressed(View v){
		
	}

}
