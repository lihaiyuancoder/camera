package com.njty.xinehome.camera;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;



import android.os.Bundle;
import android.app.Activity;


import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

/**
 * @Description(描述):         主类
 * @Package(包名): com.example.cameratest
 * @ClassName(类名): MainActivity
 * @author(作者): lihaiyuan
 * @date(时间): 2016-3-26 下午12:43:17
 * @version(版本): V1.0
 */
public class MainActivity extends Activity implements OnClickListener {



	private CameraView view;
	private ImageView iv_discount    //丢弃
			,iv_ok        //确认
			,iv_takepic   //拍照
			;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		view=(CameraView)findViewById(R.id.view);
		iv_discount=(ImageView)findViewById(R.id.discount);
		iv_ok=(ImageView)findViewById(R.id.ok);
		iv_takepic=(ImageView)findViewById(R.id.takepic);
		iv_takepic.setOnClickListener(this);
		iv_ok.setOnClickListener(this);
		iv_discount.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}


	public void onClickPic(View v){
		view.takePicture(new CameraView.OnTakePicFinishListener() {

			@Override
			public void onTakePicFinish(String bigFile, String smallFile) {
				// TODO Auto-generated method stub
				iv_takepic.setClickable(false);
				iv_takepic.setVisibility(View.INVISIBLE);
				iv_ok.setClickable(true);
				iv_discount.setClickable(true);
				iv_ok.setVisibility(View.VISIBLE);
				iv_discount.setVisibility(View.VISIBLE);
			}
		},"/sdcard/pang/",100, CameraView.FlashMode.AUTO);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
			case R.id.ok:

				break;
			case R.id.takepic:
				onClickPic(arg0);
				break;

			case R.id.discount:
				iv_takepic.setVisibility(View.VISIBLE);
				iv_takepic.setClickable(true);
				iv_discount.setVisibility(view.INVISIBLE);
				iv_discount.setClickable(false);
				iv_ok.setClickable(false);

				iv_ok.setVisibility(View.INVISIBLE);
				view.startPreView();
				break;
			default:
				break;
		}
	}























}
