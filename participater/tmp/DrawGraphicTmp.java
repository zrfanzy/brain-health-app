package thu.drawingtest;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import thu.questions.E_5_feicheng;

import com.example.thu.cs.media.test.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.Handler.Callback;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint({ "NewApi", "SimpleDateFormat" })
public class DrawGraphic extends Activity {
	LoaderImageView drawImgBackgnd;
	ArrayList<String> imgURLs;
	boolean downloaded;
	Button resetbtn, savebtn, readbtn, nextbtn;
	TextView drawdesc;
	TextView hinttext;
	Freedrawing freeDrawingCanvas;
	Handler imageLoadedHandler;
	Drawable bitmap;

	int _width, _height;
	int serverResponseCode;
	ProgressDialog dialog;

	ProgressDialog downloadListDialog;
	ProgressDialog downloadFileDialog;

	public static String saved_local_path_dir = Environment
			.getExternalStorageDirectory().getPath() + "/";
	public static String downloaded_local_path_dir = Environment
			.getExternalStorageDirectory().getPath() + "/";
	public static String server_ip = "";
	public String php_upload_url;
	public String php_download_url;
	public String resource_url_prefix;
	public static String savedName = "";
	public static String pathAffix = ".pth";
	public static String imgAffix = ".jpg";

	static final int upload_img_error = -10;
	static final int upload_path_error = -20;

	static final int upload_img_completed = 10;
	static final int upload_path_completed = 20;

	static final int download_file_begin = 40;
	static final int download_file_completed = 50;
	static final int download_progress = 60;
	static final int download_file_failed = 70;

	Handler uiHandler;

	static final int download_list_completed = 30;
	static final int download_list_failed = 31;
	Handler downloadHandler;

	private String load(String tag) {
		SharedPreferences sharedPreferences = getApplicationContext()
				.getSharedPreferences("appPreference", Context.MODE_PRIVATE);
		return sharedPreferences.getString(tag, "166.111.139.112");
	}

	public void delteTempFile(String fileName) {
		try {
			File file = new File(fileName);
			//file.delete();
		} catch (Exception e) {
			System.out.println("can't delete " + fileName);
		}
	}

	public boolean downloadObjFileOnSdCard(String filename) {
		uiHandler.sendEmptyMessage(download_file_begin);

		boolean res = true;
		try {
			// set the download URL, a url that points to a file on the internet
			// this is the file to be downloaded
			URL url = new URL(resource_url_prefix + filename);

			// create the new connection
			HttpURLConnection urlConnection = (HttpURLConnection) url
					.openConnection();

			// set up some things on the connection
			urlConnection.setRequestMethod("GET");
			urlConnection.setDoOutput(true);

			// and connect!
			urlConnection.connect();

			// set the path where we want to save the file
			// in this case, going to save it on the root directory of the
			// sd card.
			// File SDCardRoot = Environment.getExternalStorageDirectory();

			// create a new file, specifying the path, and the filename
			// which we want to save the file as.
			// File file = new File(downloaded_local_path_dir, filename);

			// this will be used to write the downloaded data into the file we
			// created
			FileOutputStream fileOutput = new FileOutputStream(
					downloaded_local_path_dir + filename, false);

			// this will be used in reading the data from the internet
			InputStream inputStream = urlConnection.getInputStream();

			// this is the total size of the file
			int totalSize = urlConnection.getContentLength();
			// variable to store total downloaded bytes
			int downloadedSize = 0;

			// create a buffer...
			byte[] buffer = new byte[1024];
			int bufferLength = 0; // used to store a temporary size of the
									// buffer

			// now, read through the input buffer and write the contents to the
			// file
			while ((bufferLength = inputStream.read(buffer)) > 0) {
				// add the data in the buffer to the file in the file output
				// stream (the file on the sd card
				fileOutput.write(buffer, 0, bufferLength);
				// add up the size so we know how much is downloaded
				downloadedSize += bufferLength;
				// this is where you would do something to report the prgress,
				// like this maybe

				// updateProgress(downloadedSize, totalSize);
				Message s = new Message();
				s.what = download_progress;
				s.obj = (downloadedSize / (double) totalSize);
				uiHandler.sendMessage(s);
			}
			// close the output stream when done
			fileOutput.close();

			// catch some possible errors...
		} catch (MalformedURLException e) {
			res = false;
			uiHandler.sendEmptyMessage(download_file_failed);
			e.printStackTrace();
		} catch (IOException e) {
			res = false;
			uiHandler.sendEmptyMessage(download_file_failed);
			e.printStackTrace();
		}

		Message msg = new Message();
		msg.obj = filename;
		msg.what = download_file_completed;
		uiHandler.sendMessage(msg);

		return res;
	}

	public boolean loadPathFromFile(String filename) {
		return freeDrawingCanvas.LoadFromFile(downloaded_local_path_dir
				+ filename);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.drawgraph);

		String ip_str =  load("ip");
		php_upload_url = "http://" + ip_str
				+ "/upload_test/upload_media_test.php"; // "http://166.111.139.112/upload_test/upload_media_test.php";
		php_download_url = "http://" + ip_str
				+ "/upload_test/down_media_list.php";
		resource_url_prefix = "http://" + ip_str
				+ "/upload_test/uploads/";
		
		downloadHandler = new Handler(new Callback() {
			@Override
			public boolean handleMessage(Message msg) {
				downloadListDialog.dismiss();
				readbtn.setEnabled(true);
				switch (msg.what) {
				case download_list_completed:
					String res = msg.obj.toString();
					res = res.substring(1);
					final String[] fileList = res.split("#");

					new AlertDialog.Builder(DrawGraphic.this)
							.setTitle("����ѡ��")
							.setItems(fileList,
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												final int which) {
											Runnable runnable = new Runnable() {
												@Override
												public void run() {
													downloadObjFileOnSdCard(fileList[which]);
												}
											};
											new Thread(runnable).start();
										}
									}).show();

					// hinttext.setText(msg.obj.toString());
					break;
				case download_list_failed:
					hinttext.setText(msg.obj.toString());
					break;
				}
				return true;
			}
		});

		uiHandler = new Handler(new Callback(){
			@Override
			public boolean handleMessage(Message msg) {
				if (msg.obj != null) {
					String data = msg.obj.toString();
					hinttext.setText(data);
				}

				switch (msg.what) {

				case upload_img_error:
					dialog.dismiss();
					delteTempFile(DrawGraphic.saved_local_path_dir
							+ DrawGraphic.savedName + DrawGraphic.imgAffix);
					break;

				case upload_img_completed:
					dialog.dismiss();
					delteTempFile(DrawGraphic.saved_local_path_dir
							+ DrawGraphic.savedName + DrawGraphic.imgAffix);
					break;

				case upload_path_error:
					dialog.dismiss();
					delteTempFile(DrawGraphic.saved_local_path_dir
							+ DrawGraphic.savedName + DrawGraphic.pathAffix);
					savebtn.setEnabled(true);
					break;

				case upload_path_completed:
					dialog.dismiss();
					delteTempFile(DrawGraphic.saved_local_path_dir
							+ DrawGraphic.savedName + DrawGraphic.pathAffix);
					hinttext.setText("�ϴ��ɹ�");
					savebtn.setEnabled(true);
					break;

				case download_file_begin:
					downloadFileDialog = ProgressDialog.show(DrawGraphic.this,
							"", "downloding ", true);
					break;
				case download_file_completed:
					downloadFileDialog.dismiss();
					if (loadPathFromFile(msg.obj.toString()))
						delteTempFile(downloaded_local_path_dir
								+ msg.obj.toString());
					break;
				case download_progress:
					downloadFileDialog.setMessage("downloading "
							+ msg.obj.toString());
					break;
				case download_file_failed:
					downloadFileDialog.dismiss();
					hinttext.setText("����ʧ��");
					break;
				default:
					break;
				}
				return true;
			}
		});

		drawdesc = (TextView) findViewById(R.id.desctxtview);
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			imgURLs = extras.getStringArrayList("imgURLs");
			if (imgURLs == null || imgURLs.size() < 2) {
				drawdesc.setText("Error. Please start from the startpage");
				return;
			}
		} else {
			drawdesc.setText("Error. Please start from the startpage");
			return;
		}

		hinttext = (TextView) findViewById(R.id.hinttextview);

		freeDrawingCanvas = (Freedrawing) findViewById(R.id.freedrawingcanvas);

		imageLoadedHandler = new Handler(new Callback() {
			@Override
			public boolean handleMessage(Message msg) {
				switch (msg.what) {
				case LoaderImageView.COMPLETE:
					drawImgBackgnd.mImage.setVisibility(View.VISIBLE);

					bitmap = drawImgBackgnd.mImage.getDrawable();
					hinttext.setText("");

					freeDrawingCanvas.setBackgroundDrawable(bitmap);
					downloaded = true;
					break;
				case LoaderImageView.FAILED:
					hinttext.setText("Fail to download the image");
					break;
				default:
					// Could change image here to a 'failed' image
					// otherwise will just keep on spinning
					break;
				}

				return true;
			}
		});

		drawImgBackgnd = (LoaderImageView) findViewById(R.id.drawLoaderImg);
		drawImgBackgnd.callbackHandler = imageLoadedHandler;
		drawImgBackgnd.setImageDrawable(imgURLs.get(1));
		drawImgBackgnd.setVisibility(View.INVISIBLE);

		resetbtn = (Button) findViewById(R.id.resetbtn);
		resetbtn.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				freeDrawingCanvas.clearCanvas();
			}
		});

		savebtn = (Button) findViewById(R.id.savebtn);
		savebtn.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				savebtn.setEnabled(false);

				Calendar c = Calendar.getInstance();
				SimpleDateFormat df = new SimpleDateFormat(
						"yyyy-MM-dd-HH-mm-ss");
				String formattedDate = df.format(c.getTime());
				DrawGraphic.savedName = formattedDate;

				// save path file
				freeDrawingCanvas
						.savePathAsFile(DrawGraphic.saved_local_path_dir
								+ DrawGraphic.savedName + DrawGraphic.pathAffix);

				Bitmap bitmap = Bitmap.createBitmap(
						freeDrawingCanvas.getWidth(),
						freeDrawingCanvas.getHeight(), Bitmap.Config.ARGB_8888);
				freeDrawingCanvas.draw(new Canvas(bitmap));
				try {
					OutputStream out = new BufferedOutputStream(
							new FileOutputStream(
									DrawGraphic.saved_local_path_dir
											+ DrawGraphic.savedName
											+ DrawGraphic.imgAffix));
					bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
				} catch (IOException e) {
					Log.w("IOException", e);
				}

				/*dialog = ProgressDialog.show(DrawGraphic.this, "",
						"Uploading files...", true);
				new Thread(new Runnable() {
					public void run() {
						runOnUiThread(new Runnable() {
							public void run() {
								// hinttext.setText("uploading started.....");
							}
						});

						System.out.println("uploading image...");
						int response = uploadFile(
								DrawGraphic.saved_local_path_dir
										+ DrawGraphic.savedName
										+ DrawGraphic.imgAffix,
								upload_img_error, upload_img_completed);
						System.out.println("RES : " + response);

						System.out.println("uploading path file...");
						response = uploadFile(
								DrawGraphic.saved_local_path_dir
										+ DrawGraphic.savedName
										+ DrawGraphic.pathAffix,
								upload_path_error, upload_path_completed);
						System.out.println("RES : " + response);

					}
				}).start();*/

			}
		});

		readbtn = (Button) findViewById(R.id.readbtn);
		readbtn.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				readbtn.setEnabled(false);
				hinttext.setText("reading data from server...");
				downloadListDialog = ProgressDialog.show(DrawGraphic.this, "",
						"reading data from server...", true);
				Runnable runnable = new Runnable() {
					@Override
					public void run() {
						// TODO: http request.
						Message msg = new Message();
						try {
							String res = readStreamFromServer(php_download_url);
							msg.what = DrawGraphic.download_list_completed;
							msg.obj = res;
						} catch (Exception e) {
							msg.what = DrawGraphic.download_list_failed;
							msg.obj = e.toString();
						}

						downloadHandler.sendMessage(msg);
					}
				};
				new Thread(runnable).start();
			}
		});

		nextbtn = (Button) findViewById(R.id.nextbtn);
		nextbtn.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();

				imgURLs.remove(0);
				imgURLs.remove(0);

				if (imgURLs.size() >= 2) {
					intent.putStringArrayListExtra("imgURLs", imgURLs);
					intent.setClass(DrawGraphic.this, ViewImgActivity.class);
				} else {
					//intent.setClass(DrawGraphic.this, endpage.class);
					intent.setClass(DrawGraphic.this, E_5_feicheng.class);
				}
				startActivity(intent);
				finish();
			}
		});

		// freeDrawingCanvas.setLayoutParams(params);

		// drawImgBackgnd.setImageDrawable(imgURLs.get(1));
	}

	public int uploadFile(String sourceFileUri, int upload_error,
			int upload_completed) {
		String upLoadServerUri = php_upload_url;
		String fileName = sourceFileUri;

		HttpURLConnection conn = null;
		DataOutputStream dos = null;
		String lineEnd = "\r\n";
		String twoHyphens = "--";
		String boundary = "*****";
		int bytesRead, bytesAvailable, bufferSize;
		byte[] buffer;
		int maxBufferSize = 1 * 1024 * 1024;
		File sourceFile = new File(sourceFileUri);
		if (!sourceFile.isFile()) {
			Log.e("uploadFile", "Source File Does not exist");

			Message msg = new Message();
			msg.what = upload_error;
			msg.obj = "Source File Does not exist";
			uiHandler.sendMessage(msg);

			return 0;
		}
		try { // open a URL connection to the Servlet
			FileInputStream fileInputStream = new FileInputStream(sourceFile);
			URL url = new URL(upLoadServerUri);
			conn = (HttpURLConnection) url.openConnection(); // Open a HTTP
																// connection to
																// the URL
			conn.setDoInput(true); // Allow Inputs
			conn.setDoOutput(true); // Allow Outputs
			conn.setUseCaches(false); // Don't use a Cached Copy
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setRequestProperty("ENCTYPE", "multipart/form-data");
			conn.setRequestProperty("Content-Type",
					"multipart/form-data;boundary=" + boundary);
			conn.setRequestProperty("uploaded_file", fileName);
			dos = new DataOutputStream(conn.getOutputStream());

			dos.writeBytes(twoHyphens + boundary + lineEnd);
			dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""
					+ fileName + "\"" + lineEnd);
			dos.writeBytes(lineEnd);

			bytesAvailable = fileInputStream.available(); // create a buffer of
															// maximum size

			bufferSize = Math.min(bytesAvailable, maxBufferSize);
			buffer = new byte[bufferSize];

			// read file and write it into form...
			bytesRead = fileInputStream.read(buffer, 0, bufferSize);

			while (bytesRead > 0) {
				dos.write(buffer, 0, bufferSize);
				bytesAvailable = fileInputStream.available();
				bufferSize = Math.min(bytesAvailable, maxBufferSize);
				bytesRead = fileInputStream.read(buffer, 0, bufferSize);
			}

			// send multipart form data necesssary after file data...
			dos.writeBytes(lineEnd);
			dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

			// Responses from the server (code and message)
			serverResponseCode = conn.getResponseCode();
			String serverResponseMessage = conn.getResponseMessage();

			Log.i("uploadFile", "HTTP Response is : " + serverResponseMessage
					+ ": " + serverResponseCode);
			if (serverResponseCode == 200) {
				runOnUiThread(new Runnable() {
					public void run() {
						hinttext.setText("File Upload Completed.");
						Toast.makeText(DrawGraphic.this,
								"File Upload Complete.", Toast.LENGTH_SHORT)
								.show();
					}
				});
			}

			// close the streams //
			fileInputStream.close();
			dos.flush();
			dos.close();

		} catch (MalformedURLException ex) {

			Message msg = new Message();
			msg.what = upload_error;
			msg.obj = ex.toString();
			uiHandler.sendMessage(msg);

			ex.printStackTrace();
			Toast.makeText(DrawGraphic.this, "MalformedURLException",
					Toast.LENGTH_SHORT).show();
			Log.e("Upload file to server", "error: " + ex.getMessage(), ex);
		} catch (Exception e) {

			Message msg = new Message();
			msg.what = upload_error;
			msg.obj = e.toString();
			uiHandler.sendMessage(msg);

			e.printStackTrace();
			Toast.makeText(DrawGraphic.this, "Exception : " + e.getMessage(),
					Toast.LENGTH_SHORT).show();
			Log.e("Upload file to server Exception",
					"Exception : " + e.getMessage(), e);
		}

		Message msg = new Message();
		msg.what = upload_completed;
		msg.obj = "serverResponseCode: " + serverResponseCode;
		uiHandler.sendMessage(msg);

		return serverResponseCode;
	}

	private String readStreamFromServer(String url) {
		String result = "";
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(url);
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			InputStream is = entity.getContent();

			// convert response to string
			new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
			StringBuilder sb = new StringBuilder();

			InputStreamReader r = new InputStreamReader(is, "UTF-8");
			int intch;
			while ((intch = r.read()) != -1) {
				char ch = (char) intch;
				// Log.i("app", Character.toString(ch));
				String s = new String(Character.toString(ch).getBytes(),
						"UTF-8");
				sb.append(s);
			}
			is.close();
			result = sb.toString();
		} catch (Exception e) {
			Log.e("ERROR", "Error converting result " + e.toString());

		}
		return result;

	}

}

/*
 * //upload_media_test.php <?php $target_path1 = "uploads/"; // Add the original
 * filename to our target path. Result is "uploads/filename.extension"
 * $target_path1 = $target_path1 . basename( $_FILES['uploaded_file']['name']);
 * if(move_uploaded_file($_FILES['uploaded_file']['tmp_name'], $target_path1)) {
 * echo "The first file ". basename( $_FILES['uploaded_file']['name']).
 * " has been uploaded."; } else{ echo
 * "There was an error uploading the file, please try again!"; echo "filename: "
 * . basename( $_FILES['uploaded_file']['name']); echo "target_path: "
 * .$target_path1; } ?>
 */