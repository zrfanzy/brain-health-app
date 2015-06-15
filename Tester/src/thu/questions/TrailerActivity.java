package thu.questions;

import osshelper.Helper;
import utility.QuestionItemSet;
import utility.TrailerQuestion;

import com.alibaba.sdk.android.oss.OSSServiceProvider;
import com.alibaba.sdk.android.oss.model.OSSException;
import com.example.tester.R;

import android.app.Activity;  
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;  
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.TableRow;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileInputStream;


public class TrailerActivity extends Activity implements OnClickListener  
{
    public static String saved_local_path_dir = Environment.getExternalStorageDirectory().getPath() + "/";
    QuestionItemSet mQuestionItemSet;
	TrailerQuestion trailerQuestion;
    MediaRecorder recorder = new MediaRecorder();
    MediaPlayer mediaPlayer = new MediaPlayer();
    private FileInputStream fileInputStream;
    Button nextButton;
    Helper helper = new Helper();


    public void deleteTempFile(String fileName) {
        try {
            File file = new File(fileName);
            file.delete();
        }
        catch (Exception e)
        {
            System.out.println("can't delete " + fileName);
        }
    }

    @Override  
    protected void onCreate(Bundle savedInstanceState)   
    {
        helper.ossService = OSSServiceProvider.getService();
        helper.ossService.setApplicationContext(getApplicationContext());
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.trailer);  
        InitView();  
    }  
    
    private void InitView() {
        mQuestionItemSet = (QuestionItemSet) getApplicationContext();
        trailerQuestion = (TrailerQuestion) ((mQuestionItemSet.GetQuestion()));
        TrailerView trailers = (TrailerView) findViewById(R.id.trailer1);
        trailers.Init(trailerQuestion, helper);
        Button btn = (Button) findViewById(R.id.trailGetNext);
        btn.setOnClickListener(this);


        if (trailerQuestion.num == 1) {
            trailers.desView.setVisibility(View.GONE);
            //trailers.playBtn.setText("record");
            trailers.playBtn.setVisibility(View.GONE);
            TextView tmpView = (TextView) findViewById(R.id.down);
            tmpView.setText("NP012");

            tmpView = (TextView) findViewById(R.id.description);
            tmpView.setText("VERBAL PAIRED ASSOCIATES (WMS), Delayed Recall");

            TableRow row = (TableRow) findViewById(R.id.row3);
            row.setVisibility(View.GONE);

            final String finalR = trailerQuestion.record;

            trailers.recordBtn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    v.setEnabled(false);
                    try {
                        recorder = new MediaRecorder();
                        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

                        recorder.setOutputFile(saved_local_path_dir + finalR);
                        recorder.prepare();
                        recorder.start();
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                }
            });

            trailers.stopBtn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    v.setEnabled(false);
                    try {
                        recorder.stop();
                        recorder.reset();
                        recorder.release();

                        helper.Init();
                        String fileName = saved_local_path_dir + finalR;

                        int bufferSize;
                        byte[] buffer = null;
                        File sourceFile = new File(fileName);

                        try {
                            fileInputStream = new FileInputStream(sourceFile);
                            bufferSize = fileInputStream.available();
                            buffer = new byte[bufferSize];
                            fileInputStream.read(buffer, 0, bufferSize);
                            //helper.PutObject(mQuestionItemSet.folderName + finalR, buffer);
                        } catch (Exception e) {
                            //Log.e("Upload file to server Exception", "Exception : " + e.getMessage(), e);
                        }
                        helper.PutObject(mQuestionItemSet.folderName + finalR, buffer);
                        deleteTempFile(fileName);
                        ((TrailerView) findViewById(R.id.trailer1)).Save(helper);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });

            return;
        }

        final String t1 = trailerQuestion.voiceFile;

        trailers.playBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer = new MediaPlayer();
                int rtn = getResources().getIdentifier(t1, "raw", getPackageName());

                mediaPlayer = MediaPlayer.create(getBaseContext(), rtn);
                mediaPlayer.start();

            }
        });

        final String finalR = trailerQuestion.record;

        trailers.recordBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setEnabled(false);
                try {
                    recorder = new MediaRecorder();
                    recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                    recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                    recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

                    recorder.setOutputFile(saved_local_path_dir + finalR);
                    recorder.prepare();
                    recorder.start();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        });

        trailers.stopBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setEnabled(false);
                try {
                    recorder.stop();
                    recorder.reset();
                    recorder.release();

                    helper.Init();
                    String fileName = saved_local_path_dir + finalR;

                    int bufferSize;
                    byte[] buffer = null;
                    File sourceFile = new File(fileName);

                    try {
                        fileInputStream = new FileInputStream(sourceFile);
                        bufferSize = fileInputStream.available();
                        buffer = new byte[bufferSize];
                        fileInputStream.read(buffer, 0, bufferSize);
                        //helper.PutObject(mQuestionItemSet.folderName + finalR, buffer);
                    } catch (Exception e) {
                        //Log.e("Upload file to server Exception", "Exception : " + e.getMessage(), e);
                    }
                    helper.PutObject(mQuestionItemSet.folderName + finalR, buffer);
                    deleteTempFile(fileName);
                    ((TrailerView) findViewById(R.id.trailer1)).Save(helper);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });



        mQuestionItemSet.questionId++;
        trailerQuestion = (TrailerQuestion) ((mQuestionItemSet.GetQuestion()));
        final String t2 = trailerQuestion.voiceFile;
        trailers = (TrailerView) findViewById(R.id.trailer2);
        trailers.Init(trailerQuestion, helper);
        trailers.playBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer = new MediaPlayer();
                int rtn = getResources().getIdentifier(t2, "raw", getPackageName());

                mediaPlayer = MediaPlayer.create(getBaseContext(), rtn);
                mediaPlayer.start();

            }
        });

        final String finalR1 = trailerQuestion.record;

        trailers.recordBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setEnabled(false);
                try {
                    recorder = new MediaRecorder();
                    recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                    recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                    recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

                    recorder.setOutputFile(saved_local_path_dir + finalR1);
                    recorder.prepare();
                    recorder.start();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        });

        trailers.stopBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setEnabled(false);
                try {
                    recorder.stop();
                    recorder.reset();
                    recorder.release();

                    helper.Init();
                    String fileName = saved_local_path_dir + finalR1;

                    int bufferSize;
                    byte[] buffer = null;
                    File sourceFile = new File(fileName);

                    try {
                        fileInputStream = new FileInputStream(sourceFile);
                        bufferSize = fileInputStream.available();
                        buffer = new byte[bufferSize];
                        fileInputStream.read(buffer, 0, bufferSize);
                        //helper.PutObject(mQuestionItemSet.folderName + finalR1, buffer);
                    } catch (Exception e) {
                        //Log.e("Upload file to server Exception", "Exception : " + e.getMessage(), e);
                    }
                    helper.PutObject(mQuestionItemSet.folderName + finalR1, buffer);
                    deleteTempFile(fileName);
                    ((TrailerView) findViewById(R.id.trailer1)).Save(helper);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        mQuestionItemSet.questionId++;

        trailerQuestion = (TrailerQuestion) ((mQuestionItemSet.GetQuestion()));
        final String t3 = trailerQuestion.voiceFile;
        trailers = (TrailerView) findViewById(R.id.trailer3);
        trailers.Init(trailerQuestion, helper);
        trailers.playBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer = new MediaPlayer();
                int rtn = getResources().getIdentifier(t3, "raw", getPackageName());

                mediaPlayer = MediaPlayer.create(getBaseContext(), rtn);
                mediaPlayer.start();

            }
        });

        final String finalR2 = trailerQuestion.record;

        trailers.recordBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setEnabled(false);
                try {
                    recorder = new MediaRecorder();
                    recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                    recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                    recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

                    recorder.setOutputFile(finalR2);
                    recorder.prepare();
                    recorder.start();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        });

        trailers.stopBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setEnabled(false);
                try {
                    recorder.stop();
                    recorder.reset();
                    recorder.release();

                    helper.Init();
                    String fileName = finalR2;

                    int bufferSize;
                    byte[] buffer = null;
                    File sourceFile = new File(fileName);

                    try {
                        fileInputStream = new FileInputStream(sourceFile);
                        bufferSize = fileInputStream.available();
                        buffer = new byte[bufferSize];
                        fileInputStream.read(buffer, 0, bufferSize);
                        //helper.PutObject(mQuestionItemSet.folderName + finalR2, buffer);
                    } catch (Exception e) {
                        //Log.e("Upload file to server Exception", "Exception : " + e.getMessage(), e);
                    }
                    helper.PutObject(mQuestionItemSet.folderName + finalR2, buffer);
                    deleteTempFile(fileName);
                    ((TrailerView) findViewById(R.id.trailer1)).Save(helper);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }
    
    @Override  
    public void onClick(View v)   
    {  
    	switch(v.getId())  
        {  
    	case R.id.trailGetNext:
            CheckBox checkBox = (CheckBox)findViewById(R.id.checkBox1);
            if (checkBox.isChecked()) {
                trailerQuestion.skip = true;
                for (int i = 0; i < trailerQuestion.skipQues.size(); ++i)
                    mQuestionItemSet.questionItemSet.get(trailerQuestion.skipQues.get(i)).skip = true;
            }
            if (trailerQuestion.num == 1)
            {
                ((TrailerQuestion)(mQuestionItemSet.GetQuestion())).GetAnswer((TrailerView)findViewById(R.id.trailer1));

            }
            else
            {
                int index = mQuestionItemSet.questionId;
                ((TrailerQuestion)(mQuestionItemSet.GetQuestion(index - 2))).GetAnswer((TrailerView)findViewById(R.id.trailer1));
                ((TrailerQuestion)(mQuestionItemSet.GetQuestion(index - 1))).GetAnswer((TrailerView)findViewById(R.id.trailer2));
                ((TrailerQuestion)(mQuestionItemSet.GetQuestion(index - 0))).GetAnswer((TrailerView)findViewById(R.id.trailer3));
                ((TrailerView)findViewById(R.id.trailer1)).Save(helper);
                ((TrailerView)findViewById(R.id.trailer2)).Save(helper);
                ((TrailerView)findViewById(R.id.trailer3)).Save(helper);
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
                e.printStackTrace();
            }

    		mQuestionItemSet.questionId ++;
            while (mQuestionItemSet.questionId < mQuestionItemSet.questionItemSet.size() && mQuestionItemSet.questionItemSet.get(mQuestionItemSet.questionId).skip)
                mQuestionItemSet.questionId ++;
     	    Intent intent = new Intent();
            intent.setClass(TrailerActivity.this, mQuestionItemSet.GetActivity());
            startActivity(intent);
            finish();
    		break;
        }  
    }  
      
}  