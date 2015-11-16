package thu.drawingtest;

import osshelper.Helper;
import utility.QuestionItemSet;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TableRow;
import android.widget.ImageView;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.alibaba.sdk.android.oss.OSSServiceProvider;
import com.alibaba.sdk.android.oss.model.OSSException;
import com.example.offline.R;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class ViewImgActivity extends Activity {

    QuestionItemSet mQuestionItemSet;
    DrawQuestion drawQuestion;
    public static String saved_local_path_dir = Environment.getExternalStorageDirectory().getPath() + "/";

    Button bt11, bt12, bt21, bt22;

    Handler imageLoadedHandler;
    LoaderImageView showImgA, showImgB, showImgC;
    Button btnA, btnB, btnC, btnN;
    boolean downloadedA,downloadedB,downloadedC;

    public static Bitmap getLocalBitmap(String url) {
        try {
            FileInputStream fis = new FileInputStream(url);
            return BitmapFactory.decodeStream(fis);  ///把流转化为Bitmap图片

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.draw_show);

        mQuestionItemSet = (QuestionItemSet) getApplicationContext();
        drawQuestion = (DrawQuestion) mQuestionItemSet.GetQuestion();

        Helper helper = new Helper();
        helper.ossService = OSSServiceProvider.getService();
        helper.ossService.setApplicationContext(getApplicationContext());
        helper.Init();
        byte[] bytes = null;
        try {
            bytes = helper.GetObjectData(mQuestionItemSet.folderName + drawQuestion.picNameA);
            FileOutputStream fout = new FileOutputStream(saved_local_path_dir + drawQuestion.picNameA);
            fout.write(bytes);
            fout.close();
            ImageView img = (ImageView)findViewById(R.id.imgA);
            Bitmap bitmap = getLocalBitmap(saved_local_path_dir + drawQuestion.picNameA);
            img.setImageBitmap(bitmap);
            helper.Init();
        }
        catch (Exception E) {

        }

        try {
            bytes = helper.GetObjectData(mQuestionItemSet.folderName + drawQuestion.picNameB);
            FileOutputStream fout = new FileOutputStream(saved_local_path_dir + drawQuestion.picNameB);
            fout.write(bytes);
            fout.close();
            ImageView img = (ImageView)findViewById(R.id.imgB);
            Bitmap bitmap = getLocalBitmap(saved_local_path_dir + drawQuestion.picNameB);
            img.setImageBitmap(bitmap);
            helper.Init();
        }
        catch (Exception E) {

        }
        helper.Init();
        try {
            bytes = helper.GetObjectData(mQuestionItemSet.folderName + drawQuestion.picNameC);
            FileOutputStream fout = new FileOutputStream(saved_local_path_dir + drawQuestion.picNameC);
            fout.write(bytes);
            fout.close();
            ImageView img = (ImageView)findViewById(R.id.imgC);
            Bitmap bitmap = getLocalBitmap(saved_local_path_dir + drawQuestion.picNameC);
            img.setImageBitmap(bitmap);
            helper.Init();
        }
        catch (Exception E) {

        }

        bt11 = (Button) findViewById(R.id.bt1_1);
        bt12 = (Button) findViewById(R.id.bt1_2);
        bt21 = (Button) findViewById(R.id.bt2_1);
        bt22 = (Button) findViewById(R.id.bt2_2);

        bt11.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                bt11.setBackgroundColor(Color.GREEN);
                bt12.setBackgroundColor(Color.WHITE);
            }
        });

        bt12.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                bt12.setBackgroundColor(Color.GREEN);
                bt11.setBackgroundColor(Color.WHITE);
            }
        });

        bt21.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                bt21.setBackgroundColor(Color.GREEN);
                bt22.setBackgroundColor(Color.WHITE);
            }
        });

        bt22.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                bt22.setBackgroundColor(Color.GREEN);
                bt21.setBackgroundColor(Color.WHITE);
            }
        });

        btnN = (Button) findViewById(R.id.showImgNext);

        btnN.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                CheckBox checkBox = (CheckBox)findViewById(R.id.checkBox1);
                if (checkBox.isChecked()) {
                    drawQuestion.skip = true;
                    for (int i = 0; i < drawQuestion.skipQues.size(); ++i)
                        mQuestionItemSet.questionItemSet.get(drawQuestion.skipQues.get(i)).skip = true;
                }
                String json = mQuestionItemSet.Save();
                byte[] data = json.getBytes();
                Helper helper = new Helper();
                helper.ossService = OSSServiceProvider.getService();
                helper.ossService.setApplicationContext(getApplicationContext());
                helper.Init();
                try {
                    helper.PutObject(mQuestionItemSet.folderName + "offline.json", data);
                } catch (OSSException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                Intent intent = new Intent();
                mQuestionItemSet.questionId++;
                while (mQuestionItemSet.questionId < mQuestionItemSet.questionItemSet.size() && mQuestionItemSet.questionItemSet.get(mQuestionItemSet.questionId).skip)
                    mQuestionItemSet.questionId ++;
                //System.out.println(mQuestionItemSet.GetActivity().getName());
                intent.setClass(ViewImgActivity.this, mQuestionItemSet.GetActivity());
                startActivity(intent);
                finish();
            }
        });
    }

}
