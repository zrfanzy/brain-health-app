package thu.drawingtest;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import osshelper.Helper;

import com.alibaba.sdk.android.oss.OSSServiceProvider;
import com.example.paticipanter.R;

import utility.QuestionItemSet;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

@SuppressLint({ "NewApi", "SimpleDateFormat" })
public class DrawGraphic extends Activity {
	QuestionItemSet mQuestionItemSet;
	DrawQuestion drawQuestion;
	
	Button resetbtn, savebtn, readbtn, nextbtn;
	Freedrawing freeDrawingCanvas;
	Handler imageLoadedHandler;
	LinearLayout layout;
	Drawable bitmap;
	int t = 0;

	int _width, _height;
	int serverResponseCode;
	ProgressDialog dialog;

	ProgressDialog downloadListDialog;
	ProgressDialog downloadFileDialog;

	public static String saved_local_path_dir = Environment
			.getExternalStorageDirectory().getPath() + "/";

	public static String savedName = "";
	public static String pathAffix = ".pth";
	public static String imgAffix = ".jpg";
	private FileInputStream fileInputStream;


	public void deleteTempFile(String fileName) {
		try {
			File file = new File(fileName);
			file.delete();
		} catch (Exception e) {
			System.out.println("can't delete " + fileName);
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.draw_graph);
		
		mQuestionItemSet = (QuestionItemSet) getApplicationContext();
    	drawQuestion = (DrawQuestion) mQuestionItemSet.GetQuestion();

        TextView tmpView = (TextView) findViewById(R.id.desctxtview);
        if (!drawQuestion.show)
            tmpView.setText("VR_DR(WMS)");
		
    	freeDrawingCanvas = (Freedrawing) findViewById(R.id.freedrawingcanvasB);
		if (drawQuestion.show)
    		freeDrawingCanvas.Disable();
        else freeDrawingCanvas.Enable();
    	freeDrawingCanvas = (Freedrawing) findViewById(R.id.freedrawingcanvasC);
		if (drawQuestion.show)
    		freeDrawingCanvas.Disable();
        else freeDrawingCanvas.Enable();
		freeDrawingCanvas = (Freedrawing) findViewById(R.id.freedrawingcanvasA);
		freeDrawingCanvas.Enable();
		layout = (LinearLayout)findViewById(R.id.layout1);
		
		savebtn = (Button) findViewById(R.id.btnSave);
		savebtn.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (drawQuestion.show) {
                    // VR_IR
					String fileName;
					if (t == 0) fileName = "A";
					else if (t == 1) fileName = "B";
					else fileName = "C";
					DrawGraphic.savedName = fileName;

					freeDrawingCanvas
							.savePathAsFile(DrawGraphic.saved_local_path_dir + DrawGraphic.savedName + DrawGraphic.pathAffix);

					Bitmap bitmap = Bitmap.createBitmap(
							freeDrawingCanvas.getWidth(),
							freeDrawingCanvas.getHeight(), Bitmap.Config.ARGB_8888);
					freeDrawingCanvas.draw(new Canvas(bitmap));

					try {
						OutputStream out = new BufferedOutputStream(
								new FileOutputStream(DrawGraphic.saved_local_path_dir + DrawGraphic.savedName + DrawGraphic.imgAffix));
						bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
					} catch (IOException e) {
						Log.w("IOException", e);
					}

					int response = uploadFile(DrawGraphic.savedName + DrawGraphic.imgAffix);
					System.out.println("RES : " + response);

					System.out.println("uploading path file...");
					response = uploadFile(DrawGraphic.savedName + DrawGraphic.pathAffix);
					System.out.println("RES : " + response);

                    deleteTempFile(DrawGraphic.saved_local_path_dir + DrawGraphic.savedName + DrawGraphic.imgAffix);
                    deleteTempFile(DrawGraphic.saved_local_path_dir + DrawGraphic.savedName + DrawGraphic.pathAffix);
                }
				else {
                    // VR_DR
					for (t = 0; t < 3; ++t) {
						String fileName;
						if (t == 0) {
							fileName = "A_1";
							freeDrawingCanvas = (Freedrawing) findViewById(R.id.freedrawingcanvasA);
						}
						else if (t == 1) {
							fileName = "B_1";
							freeDrawingCanvas = (Freedrawing) findViewById(R.id.freedrawingcanvasB);
						}
						else {
							fileName = "C_1";
							freeDrawingCanvas = (Freedrawing) findViewById(R.id.freedrawingcanvasC);
						}
						DrawGraphic.savedName = fileName;

						freeDrawingCanvas
								.savePathAsFile(DrawGraphic.saved_local_path_dir + DrawGraphic.savedName + DrawGraphic.pathAffix);

						Bitmap bitmap = Bitmap.createBitmap(
								freeDrawingCanvas.getWidth(),
								freeDrawingCanvas.getHeight(), Bitmap.Config.ARGB_8888);
						freeDrawingCanvas.draw(new Canvas(bitmap));

						try {
							OutputStream out = new BufferedOutputStream(
									new FileOutputStream(DrawGraphic.saved_local_path_dir + DrawGraphic.savedName + DrawGraphic.imgAffix));
							bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
						} catch (IOException e) {
							Log.w("IOException", e);
						}

						int response = uploadFile(DrawGraphic.savedName + DrawGraphic.imgAffix);
						System.out.println("RES : " + response);

						System.out.println("uploading path file...");
						response = uploadFile(DrawGraphic.savedName + DrawGraphic.pathAffix);
						System.out.println("RES : " + response);
                        deleteTempFile(DrawGraphic.saved_local_path_dir + DrawGraphic.savedName + DrawGraphic.imgAffix);
                        deleteTempFile(DrawGraphic.saved_local_path_dir + DrawGraphic.savedName + DrawGraphic.pathAffix);
					}
				}
				
			}
		});

		nextbtn = (Button) findViewById(R.id.btnNext);
		nextbtn.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				t = t + 1;
				if (t >= 3 || !drawQuestion.show)
				{
					mQuestionItemSet.questionId ++;
					Intent intent = new Intent();
                    System.out.println(mQuestionItemSet.GetActivity().getName());
					intent.setClass(DrawGraphic.this, mQuestionItemSet.GetActivity());
			     	startActivity(intent);
			     	finish();
				}
				else if (t == 1)
				{
					freeDrawingCanvas.Disable();
					freeDrawingCanvas = (Freedrawing) findViewById(R.id.freedrawingcanvasC);
                    freeDrawingCanvas.clearCanvas();
					freeDrawingCanvas = (Freedrawing) findViewById(R.id.freedrawingcanvasB);
					freeDrawingCanvas.Enable();
					freeDrawingCanvas.clearCanvas();
				}
				else if (t == 2)
				{
					freeDrawingCanvas.Disable();
					freeDrawingCanvas = (Freedrawing) findViewById(R.id.freedrawingcanvasC);
					freeDrawingCanvas.Enable();
					freeDrawingCanvas.clearCanvas();
				}
			}
		});

	}

	public int uploadFile(String picName) {
		String fileName = DrawGraphic.saved_local_path_dir + picName;

		Helper helper = new Helper();
		helper.ossService = OSSServiceProvider.getService();
		helper.ossService.setApplicationContext(getApplicationContext());
		helper.Init();
		
		int bufferSize;
		byte[] buffer;
		File sourceFile = new File(fileName);

		try
		{
			fileInputStream = new FileInputStream(sourceFile);
			bufferSize = fileInputStream.available(); 
			buffer = new byte[bufferSize];
			fileInputStream.read(buffer, 0, bufferSize);
			helper.PutObject(mQuestionItemSet.folderName + picName, buffer);
		}
		catch (Exception e)
		{
			// Log.e("Upload file to server Exception", "Exception : " + e.getMessage(), e);
			return -1;
		}
		return 0;
	}

}

