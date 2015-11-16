package thu.questions;

import osshelper.Helper;
import utility.QuestionItemSet;
import utility.TrailerQuestion;

import com.alibaba.sdk.android.oss.OSSServiceProvider;
import com.alibaba.sdk.android.oss.model.OSSException;
import com.example.offline.R;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;  
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.TableRow;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;


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
    
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
    private void InitView() {
        mQuestionItemSet = (QuestionItemSet) getApplicationContext();
        trailerQuestion = (TrailerQuestion) ((mQuestionItemSet.GetQuestion()));
        TrailerView trailers = (TrailerView) findViewById(R.id.trailer1);
        trailers.Init(trailerQuestion, helper);
        Button btn = (Button) findViewById(R.id.trailGetNext);
        btn.setOnClickListener(this);

        final String finalR = trailerQuestion.record;

        helper.Init();
        try {
            byte [] bytes = helper.GetObjectData(mQuestionItemSet.folderName + trailerQuestion.record);
            FileOutputStream fout = new FileOutputStream(saved_local_path_dir + finalR);
            //byte [] bytes = tmpString.getBytes();
            fout.write(bytes);
            fout.close();
        }
        catch (Exception E) {

        }

        trailers.playBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mediaPlayer = new MediaPlayer();
                    mediaPlayer.setDataSource(saved_local_path_dir + finalR);
                    mediaPlayer.prepare();
                    //mediaPlayer = MediaPlayer.create(getBaseContext(), rtn);
                    mediaPlayer.start();
                }
                catch (Exception e) {

                }

            }
        });

        trailers.scoreBtn.callOnClick();

        if (trailerQuestion.num == 1)
            return;

        mQuestionItemSet.questionId++;
        trailerQuestion = (TrailerQuestion) ((mQuestionItemSet.GetQuestion()));
        trailers = (TrailerView) findViewById(R.id.trailer2);
        trailers.Init(trailerQuestion, helper);

        final String finalR1 = trailerQuestion.record;

        helper.Init();
        try {
            byte [] bytes = helper.GetObjectData(mQuestionItemSet.folderName + finalR1);
            FileOutputStream fout = new FileOutputStream(saved_local_path_dir + finalR1);
            //byte [] bytes = tmpString.getBytes();
            fout.write(bytes);
            fout.close();
        }
        catch (Exception E) {

        }

        trailers.playBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mediaPlayer = new MediaPlayer();
                    mediaPlayer.setDataSource(saved_local_path_dir + finalR1);
                    mediaPlayer.prepare();
                    //mediaPlayer = MediaPlayer.create(getBaseContext(), rtn);
                    mediaPlayer.start();
                } catch (Exception e) {

                }

            }
        });

        trailers.scoreBtn.callOnClick();
        mQuestionItemSet.questionId++;
        trailerQuestion = (TrailerQuestion) ((mQuestionItemSet.GetQuestion()));
        trailers = (TrailerView) findViewById(R.id.trailer3);
        trailers.Init(trailerQuestion, helper);

        final String finalR2 = trailerQuestion.record;

        helper.Init();
        try {
            byte [] bytes = helper.GetObjectData(mQuestionItemSet.folderName + finalR2);
            FileOutputStream fout = new FileOutputStream(saved_local_path_dir + finalR2);
            //byte [] bytes = tmpString.getBytes();
            fout.write(bytes);
            fout.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        trailers.playBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mediaPlayer = new MediaPlayer();
                    mediaPlayer.setDataSource(saved_local_path_dir + finalR2);
                    mediaPlayer.prepare();
                    //mediaPlayer = MediaPlayer.create(getBaseContext(), rtn);
                    mediaPlayer.start();
                } catch (Exception e) {

                }

            }
        });

        trailers.scoreBtn.callOnClick();
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
                helper.PutObject(mQuestionItemSet.folderName + "offline.json", data);
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