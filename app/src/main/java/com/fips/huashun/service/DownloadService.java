package com.fips.huashun.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.fips.huashun.R;
import com.fips.huashun.ui.fragment.MainActivity2;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;



public class DownloadService extends Service {
	private NotificationManager nm;
	private Notification notification;
	private File tempFile = null;
	private boolean cancelUpdate = false;
	private MyHandler myHandler;
	private int download_precent = 0;
	private RemoteViews views;
	private int notificationId = 1234;
	private static final String TAG = "DownloadService";

	@Override
	public IBinder onBind(Intent intent) {
		Log.d(TAG, "onBind");
		return null;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onStart(Intent intent, int startId) {
		Log.d(TAG, "onStart");
		super.onStart(intent, startId);
	}

	@SuppressWarnings("deprecation")
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d(TAG, "onStartCommand");
		nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

		long when = System.currentTimeMillis();

		// V7包下的NotificationCompat
		NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
		// Ticker是状态栏显示的提示
		builder.setTicker("应用更新");
		builder.setSmallIcon(R.mipmap.android_template);
		builder.setWhen(when);
		builder.setAutoCancel(true);
		// 自定义contentView
		views = new RemoteViews(getPackageName(), R.layout.remote_view_layout);

		views.setTextViewText(R.id.tvName, getString(R.string.app_name) + "升级");
//		RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.notification_update);
//		contentView.setTextViewText(R.id.notification_update_progress_text, "0%");
//		contentView.setImageViewResource(R.id.notification_update_image, R.mipmap.ic_launcher);
		// 为notification设置contentView
		builder.setContent(views);

		Intent intent2 = new Intent(this, MainActivity2.class);
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent2, PendingIntent.FLAG_UPDATE_CURRENT);
		builder.setContentIntent(contentIntent);
		// 构建一个notification
		notification = builder.build();

//		notification = new Notification();
//		notification.icon = android.R.drawable.stat_sys_download;
//
//		notification.tickerText = getString(R.string.app_name) + " 更新";
//		notification.when = System.currentTimeMillis();
//		notification.defaults = Notification.DEFAULT_LIGHTS;
//		// 设置任务栏中下载进程显示的views
//		views = new RemoteViews(getPackageName(), R.layout.remote_view_layout);
//
//		views.setTextViewText(R.id.tvName, getString(R.string.app_name) + "升级");
//		notification.contentView = views;

//		PendingIntent contentIntent = PendingIntent.getActivity(this, 0,new Intent(this, MainActivity2.class), 0);
//		notification.contentIntent=contentIntent;

		nm.notify(notificationId, notification);

		myHandler = new MyHandler(Looper.myLooper(), this);
		
		Message message = myHandler.obtainMessage(3, 0);
		myHandler.sendMessage(message);
		
		downFile(intent.getStringExtra("url"));
		//Log.i("test","APK下载地址"+intent.getStringExtra("url"));
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		Log.d(TAG, "onDestroy");
		if (nm != null) {
			nm.cancel(notificationId);
		}
		stopSelf();
		super.onDestroy();
	}

	
	private void downFile(final String url) {
		Log.d(TAG, "downFile");
		new Thread() {
			public void run() {
				try {
					HttpClient client = new DefaultHttpClient();
					
					HttpGet get = new HttpGet(url);
					HttpResponse response = client.execute(get);
					HttpEntity entity = response.getEntity();
					long length = entity.getContentLength();
					InputStream is = entity.getContent();
					if (is != null) {

						File rootFile = new File(
								Environment.getExternalStorageDirectory(),
								"/huashun");
						if (!rootFile.exists() && !rootFile.isDirectory())
							rootFile.mkdir();

						tempFile = new File(
								Environment.getExternalStorageDirectory(),
								"/huashun/huashun.apk");
						if (tempFile.exists())
							tempFile.delete();
						tempFile.createNewFile();

						
						BufferedInputStream bis = new BufferedInputStream(is);

						
						FileOutputStream fos = new FileOutputStream(tempFile);
						
						BufferedOutputStream bos = new BufferedOutputStream(fos);

						int read;
						long count = 0;
						int precent = 0;
						byte[] buffer = new byte[1024];
						while ((read = bis.read(buffer)) != -1 && !cancelUpdate) {
							bos.write(buffer, 0, read);
							count += read;
							precent = (int) (((double) count / length) * 100);
							
							if (precent - download_precent >= 5) {
								download_precent = precent;
								Message message = myHandler.obtainMessage(3,
										precent);
								myHandler.sendMessage(message);
							}
						}
						bos.flush();
						bos.close();
						fos.flush();
						fos.close();
						is.close();
						bis.close();
					}

					if (!cancelUpdate) {
						Message message = myHandler.obtainMessage(2, tempFile);
						myHandler.sendMessage(message);
					} else {
						tempFile.delete();
					}
				} catch (ClientProtocolException e) {
					Message message = myHandler.obtainMessage(4, "下载更新文件失败");
					myHandler.sendMessage(message);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}.start();
	}

	private void Instanll(File file, Context context) {
		Log.d(TAG, "Instanll");
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setAction(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(file),
				"application/vnd.android.package-archive");
		context.startActivity(intent);
	}

	class MyHandler extends Handler {
		private Context context;

		public MyHandler(Looper looper, Context c) {
			super(looper);
			this.context = c;
		}

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg != null) {
				switch (msg.what) {
				case 0:
					Toast.makeText(context, msg.obj.toString(),
							Toast.LENGTH_SHORT).show();
					break;
				case 1:
					break;
				case 2:
					download_precent = 0;
					nm.cancel(notificationId);
					Instanll((File) msg.obj, context);
					stopSelf();
					break;
				case 3:
					views.setTextViewText(R.id.tvProgress, "已下载"
							+ download_precent + "%");
					views.setProgressBar(R.id.pbProgress, 100,
							download_precent, false);
					notification.contentView = views;
					nm.notify(notificationId, notification);
					break;
				case 4:
					nm.cancel(notificationId);
					stopSelf();
					break;
				}
			}
		}
	}
}
