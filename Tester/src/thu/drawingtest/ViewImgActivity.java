package thu.drawingtest;

import osshelper.Helper;
import utility.QuestionItemSet;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TableRow;

import com.alibaba.sdk.android.oss.OSSServiceProvider;
import com.alibaba.sdk.android.oss.model.OSSException;
import com.example.tester.R;

public class ViewImgActivity extends Activity {

    QuestionItemSet mQuestionItemSet;
    DrawQuestion drawQuestion;

    Handler imageLoadedHandler;
    LoaderImageView showImgA, showImgB, showImgC;
    Button btnA, btnB, btnC, btnN;
    boolean downloadedA,downloadedB,downloadedC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.draw_show);

        mQuestionItemSet = (QuestionItemSet) getApplicationContext();
        drawQuestion = (DrawQuestion) mQuestionItemSet.GetQuestion();

        downloadedA = true;
        downloadedB = false;
        downloadedC = false;
        showImgA = (LoaderImageView) findViewById(R.id.loaderImageViewA);
        showImgA.setVisibility(View.GONE);
        showImgB = (LoaderImageView) findViewById(R.id.loaderImageViewB);
        showImgB.setVisibility(View.GONE);
        showImgC = (LoaderImageView) findViewById(R.id.loaderImageViewC);
        showImgC.setVisibility(View.GONE);

        btnA = (Button) findViewById(R.id.tryaginbtn);
        btnB = (Button) findViewById(R.id.tryaginbtnB);
        btnB.setEnabled(false);
        btnC = (Button) findViewById(R.id.tryaginbtnC);
        btnC.setEnabled(false);
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
                    helper.PutObject(mQuestionItemSet.folderName + "tester.json", data);
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

        btnA.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                handleClick(v);
                showImgA.setVisibility(View.VISIBLE);
                int rtn = getResources().getIdentifier(drawQuestion.picNameA, "raw", getPackageName());
                showImgA.setImageDrawable(rtn);
                btnB.setEnabled(true);
                downloadedB = true;
                TableRow row = (TableRow) findViewById(R.id.row1);
                row.setVisibility(View.GONE);
                row = (TableRow) findViewById(R.id.row2);
                row.setVisibility(View.GONE);
                row = (TableRow) findViewById(R.id.row3);
                row.setVisibility(View.GONE);
                row = (TableRow) findViewById(R.id.row4);
                row.setVisibility(View.GONE);
                row = (TableRow) findViewById(R.id.row5);
                row.setVisibility(View.GONE);
                row = (TableRow) findViewById(R.id.row6);
                row.setVisibility(View.GONE);
                row = (TableRow) findViewById(R.id.row7);
                row.setVisibility(View.GONE);
                row = (TableRow) findViewById(R.id.row8);
                row.setVisibility(View.GONE);
                row = (TableRow) findViewById(R.id.row9);
                row.setVisibility(View.GONE);
                row = (TableRow) findViewById(R.id.row10);
                row.setVisibility(View.GONE);
            }
        });
        btnB.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                handleClick(v);
                showImgB.setVisibility(View.VISIBLE);
                int rtn = getResources().getIdentifier(drawQuestion.picNameB, "raw", getPackageName());
                showImgB.setImageDrawable(rtn);
                btnC.setEnabled(true);
                downloadedC = true;
                TableRow row = (TableRow) findViewById(R.id.row1);
                row.setVisibility(View.GONE);
                row = (TableRow) findViewById(R.id.row2);
                row.setVisibility(View.GONE);
                row = (TableRow) findViewById(R.id.row3);
                row.setVisibility(View.GONE);
                row = (TableRow) findViewById(R.id.row4);
                row.setVisibility(View.GONE);
                row = (TableRow) findViewById(R.id.row5);
                row.setVisibility(View.GONE);
                row = (TableRow) findViewById(R.id.row6);
                row.setVisibility(View.GONE);
                row = (TableRow) findViewById(R.id.row7);
                row.setVisibility(View.GONE);
                row = (TableRow) findViewById(R.id.row8);
                row.setVisibility(View.GONE);
                row = (TableRow) findViewById(R.id.row9);
                row.setVisibility(View.GONE);
                row = (TableRow) findViewById(R.id.row10);
                row.setVisibility(View.GONE);
            }
        });
        btnC.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                handleClick(v);
                showImgC.setVisibility(View.VISIBLE);
                int rtn = getResources().getIdentifier(drawQuestion.picNameC, "raw", getPackageName());
                showImgC.setImageDrawable(rtn);
                TableRow row = (TableRow) findViewById(R.id.row1);
                row.setVisibility(View.GONE);
                row = (TableRow) findViewById(R.id.row2);
                row.setVisibility(View.GONE);
                row = (TableRow) findViewById(R.id.row3);
                row.setVisibility(View.GONE);
                row = (TableRow) findViewById(R.id.row4);
                row.setVisibility(View.GONE);
                row = (TableRow) findViewById(R.id.row5);
                row.setVisibility(View.GONE);
                row = (TableRow) findViewById(R.id.row6);
                row.setVisibility(View.GONE);
                row = (TableRow) findViewById(R.id.row7);
                row.setVisibility(View.GONE);
                row = (TableRow) findViewById(R.id.row8);
                row.setVisibility(View.GONE);
                row = (TableRow) findViewById(R.id.row9);
                row.setVisibility(View.GONE);
                row = (TableRow) findViewById(R.id.row10);
                row.setVisibility(View.GONE);
            }
        });
    }

    public void handleClick(View tager)
    {
        if(downloadedA)
        {
            showImgA.mImage.setVisibility(View.VISIBLE);
            new CountDownTimer(10000, 1000) {

                public void onTick(long millisUntilFinished) {
                    //countdownTextView.setText(desc + millisUntilFinished / 1000);
                }

                public void onFinish() {
                    showImgA.mImage.setVisibility(View.GONE);
                    downloadedA = false;
                    btnB.setEnabled(true);
                    btnA.setEnabled(false);
                    TableRow row = (TableRow) findViewById(R.id.row1);
                    row.setVisibility(View.VISIBLE);
                    row = (TableRow) findViewById(R.id.row2);
                    row.setVisibility(View.VISIBLE);
                    row = (TableRow) findViewById(R.id.row3);
                    row.setVisibility(View.VISIBLE);
                    row = (TableRow) findViewById(R.id.row4);
                    row.setVisibility(View.VISIBLE);
                    row = (TableRow) findViewById(R.id.row5);
                    row.setVisibility(View.VISIBLE);
                    row = (TableRow) findViewById(R.id.row6);
                    row.setVisibility(View.VISIBLE);
                    row = (TableRow) findViewById(R.id.row7);
                    row.setVisibility(View.VISIBLE);
                    row = (TableRow) findViewById(R.id.row8);
                    row.setVisibility(View.VISIBLE);
                    row = (TableRow) findViewById(R.id.row9);
                    row.setVisibility(View.VISIBLE);
                    row = (TableRow) findViewById(R.id.row10);
                    row.setVisibility(View.VISIBLE);
                }
            }.start();
        }
        if(downloadedB)
        {
            showImgB.mImage.setVisibility(View.VISIBLE);
            new CountDownTimer(10000, 1000) {

                public void onTick(long millisUntilFinished) {
                    //countdownTextView.setText(desc + millisUntilFinished / 1000);
                }

                public void onFinish() {
                    showImgB.mImage.setVisibility(View.GONE);
                    downloadedB = false;
                    btnC.setEnabled(true);
                    btnB.setEnabled(false);
                    TableRow row = (TableRow) findViewById(R.id.row1);
                    row.setVisibility(View.VISIBLE);
                    row = (TableRow) findViewById(R.id.row2);
                    row.setVisibility(View.VISIBLE);
                    row = (TableRow) findViewById(R.id.row3);
                    row.setVisibility(View.VISIBLE);
                    row = (TableRow) findViewById(R.id.row4);
                    row.setVisibility(View.VISIBLE);
                    row = (TableRow) findViewById(R.id.row5);
                    row.setVisibility(View.VISIBLE);
                    row = (TableRow) findViewById(R.id.row6);
                    row.setVisibility(View.VISIBLE);
                    row = (TableRow) findViewById(R.id.row7);
                    row.setVisibility(View.VISIBLE);
                    row = (TableRow) findViewById(R.id.row8);
                    row.setVisibility(View.VISIBLE);
                    row = (TableRow) findViewById(R.id.row9);
                    row.setVisibility(View.VISIBLE);
                    row = (TableRow) findViewById(R.id.row10);
                    row.setVisibility(View.VISIBLE);
                }
            }.start();
        }
        if(downloadedC)
        {
            showImgC.mImage.setVisibility(View.VISIBLE);
            new CountDownTimer(10000, 1000) {

                public void onTick(long millisUntilFinished) {
                    //countdownTextView.setText(desc + millisUntilFinished / 1000);
                }

                public void onFinish() {
                    showImgC.mImage.setVisibility(View.GONE);
                    btnC.setEnabled(false);
                    TableRow row = (TableRow) findViewById(R.id.row1);
                    row.setVisibility(View.VISIBLE);
                    row = (TableRow) findViewById(R.id.row2);
                    row.setVisibility(View.VISIBLE);
                    row = (TableRow) findViewById(R.id.row3);
                    row.setVisibility(View.VISIBLE);
                    row = (TableRow) findViewById(R.id.row4);
                    row.setVisibility(View.VISIBLE);
                    row = (TableRow) findViewById(R.id.row5);
                    row.setVisibility(View.VISIBLE);
                    row = (TableRow) findViewById(R.id.row6);
                    row.setVisibility(View.VISIBLE);
                    row = (TableRow) findViewById(R.id.row7);
                    row.setVisibility(View.VISIBLE);
                    row = (TableRow) findViewById(R.id.row8);
                    row.setVisibility(View.VISIBLE);
                    row = (TableRow) findViewById(R.id.row9);
                    row.setVisibility(View.VISIBLE);
                    row = (TableRow) findViewById(R.id.row10);
                    row.setVisibility(View.VISIBLE);
                }
            }.start();
        }

    }

}
