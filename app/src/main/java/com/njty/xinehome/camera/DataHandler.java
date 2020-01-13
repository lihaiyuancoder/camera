package com.njty.xinehome.camera;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.SimpleFormatter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.text.format.DateFormat;
import android.util.Log;

/**
 * @Description(描述):    处理拍照返回的Byte数组
 * @Package(包名): com.example.cameratest
 * @ClassName(类名): DataHandler
 * @author(作者): lihaiyuan
 * @date(时间): 2016-3-26 下午12:43:48
 * @version(版本): V1.0
 */
public  class DataHandler{
	private String filePath="";
	/** 压缩后的图片最大值 单位KB*/
	private int maxSize=200;


	private String smallFilePath="";
	private String bigFilePath="";

	public DataHandler(String filePath){
		this.filePath=filePath;
		File folder=new File(filePath);
		if(!folder.exists()){
			folder.mkdirs();
		}

		if(folder.isFile()){
			folder.delete();
			folder.mkdirs();
		}

	}

	/**
	 * 保存图片
	 * @param 相机返回的文件流
	 * @return 解析流生成的缩略图
	 */
	public Bitmap save(byte[] data,int maxSize){
		this.maxSize=maxSize;
		if(data!=null){
			//解析生成相机返回的图片
			Bitmap bm=BitmapFactory.decodeByteArray(data, 0, data.length);
			//获取加水印的图片
			//bm=getBitmapWithWaterMark(bm);
			//生成缩略图
			Bitmap thumbnail=ThumbnailUtils.extractThumbnail(bm, 213, 213);
			//产生新的文件名
			String imgName=new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+".jpg" ;
			smallFilePath=filePath+imgName;
			bigFilePath=filePath+imgName.replace(".","_.");

			Log.e("pang","small"+smallFilePath);
			Log.e("pang","big"+bigFilePath);


			File file=new File(smallFilePath);
			File thumFile=new File(bigFilePath);
			try{
				//存图片大图
				FileOutputStream fos=new FileOutputStream(file);
				ByteArrayOutputStream bos=compress(bm);
				fos.write(bos.toByteArray());
				fos.flush();
				fos.close();
				//存图片小图
				BufferedOutputStream bufferos=new BufferedOutputStream(new FileOutputStream(thumFile));
				thumbnail.compress(Bitmap.CompressFormat.JPEG, 50, bufferos);
				bufferos.flush();
				bufferos.close();
				return bm;
			}catch(Exception e){
				//	Log.e(TAG, e.toString());
				//Toast.makeText(getContext(), "解析相机返回流失败", Toast.LENGTH_SHORT).show();

			}
		}else{
			//Toast.makeText(getContext(), "拍照失败，请重试", Toast.LENGTH_SHORT).show();
		}
		return null;
	}









	/**
	 * @Description（描述）:   图片压缩到指定大小
	 *  @param bitmap
	 *  @return ByteArrayOutputStream
	 * @author Pang
	 * @date 2016-3-26 下午12:33:51
	 */
	private ByteArrayOutputStream compress(Bitmap bitmap){
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
		int options = 99;
		while ( baos.toByteArray().length / 1024 > maxSize) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
			options -= 3;// 每次都减少10
			//压缩比小于0，不再压缩
			if (options<0) {
				break;
			}
			//Log.i(TAG,baos.toByteArray().length / 1024+"");
			baos.reset();// 重置baos即清空baos
			bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
		}
		return baos;
	}






	/**
	 * @Description（描述）:    返回小图片路径
	 *  @return String
	 * @author Pang
	 * @date 2016-3-26 下午12:34:04
	 */
	public String getSmallFilePath(){
		return smallFilePath;
	}



	/**
	 * @Description（描述）:   返回大图片路径
	 *  @return String
	 * @author Pang
	 * @date 2016-3-26 下午12:34:20
	 */
	public String getBigFilePath(){
		return smallFilePath;
	}



}

