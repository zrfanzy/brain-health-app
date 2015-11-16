package thu.voicetest;

import java.io.FileOutputStream;
import java.util.List;

import osshelper.Helper;
import thu.questions.TrailerView;
import utility.QuestionItemSet;
import utility.TrailerQuestion;

import com.alibaba.sdk.android.oss.OSSServiceProvider;
import com.alibaba.sdk.android.oss.model.OSSException;
import com.baidu.voicerecognition.android.Candidate;
import com.baidu.voicerecognition.android.VoiceRecognitionClient;
import com.baidu.voicerecognition.android.VoiceRecognitionConfig;
import com.example.offline.R;

import android.app.Activity;  
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;  
import android.os.Environment;
import android.os.Handler;
import android.view.View;  
import android.view.View.OnClickListener;  
import android.widget.Button;  
import android.widget.CheckBox;
import android.widget.EditText;
  
public class StoryRecallActivity extends Activity implements OnClickListener  
{
    // TextEdit for voice recognition
    EditText voiceEdit;

	// voice recognition part
	private String API_KEY = "8MAxI5o7VjKSZOKeBzS4XtxO";
	private String SECRET_KEY = "Ge5GXVdGQpaxOmLzc8fOM8309ATCz9Ha";

	private VoiceRecognitionClient mClient;
	private VoiceRecognitionConfig config;

	private Handler mHandler;
	private boolean IsRecognition = false;

	private VoiceRecognitionClient.VoiceClientStatusChangeListener mListener = new VoiceRecognitionClient.VoiceClientStatusChangeListener() {
		public void onClientStatusChange(int status, Object obj) {
			switch (status) {
				case VoiceRecognitionClient.CLIENT_STATUS_START_RECORDING:
					break;
				case VoiceRecognitionClient.CLIENT_STATUS_SPEECH_START:
                    voiceEdit.setText("Loading recognition result...");
					break;
				case VoiceRecognitionClient.CLIENT_STATUS_AUDIO_DATA:
					break;
				case VoiceRecognitionClient.CLIENT_STATUS_SPEECH_END:
					break;
				case VoiceRecognitionClient.CLIENT_STATUS_FINISH:
                    if (obj != null && obj instanceof List) {
                        //Result.setText(result.toString());
                        @SuppressWarnings("unchecked")
                        List<String> sentences = ((List<String>) obj);
                        String text = new String();
                        for (int i = 0; i < sentences.size(); ++i)
                            text = text + sentences.get(i);
                        voiceEdit.setText(text);
                    }
                    //voiceEdit.setText(obj.toString());
					break;
				case VoiceRecognitionClient.CLIENT_STATUS_UPDATE_RESULTS:
                    if (obj != null && obj instanceof List) {
                        //Result.setText(result.toString());
                        @SuppressWarnings("unchecked")
                        List<String> sentences = ((List<String>) obj);
                        String text = new String();
                        for (int i = 0; i < sentences.size(); ++i)
                            text = text + sentences.get(i);
                        voiceEdit.setText(text);
                    }
                    break;
				case VoiceRecognitionClient.CLIENT_STATUS_USER_CANCELED:
					break;
				default:
					break;
			}

		}

		@Override
		public void onNetworkStatusChange(int i, Object o) {

		}

		@Override
		public void onError(int i, int i1) {

		}
	};


	public static String saved_local_path_dir = Environment.getExternalStorageDirectory().getPath() + "/";
	QuestionItemSet mQuestionItemSet;
	StoryRecallQuestion question;
	Boolean flag;
	
	MediaPlayer mediaPlayer = new MediaPlayer();

    Button bt1, bt2, bt3, bt4;
      
    @Override
    protected void onCreate(Bundle savedInstanceState)   
    {  
        super.onCreate(savedInstanceState); 
        mQuestionItemSet = (QuestionItemSet) getApplicationContext();
        question = (StoryRecallQuestion) mQuestionItemSet.GetQuestion();
        setContentView(R.layout.story_tester);
        voiceEdit = (EditText)findViewById(R.id.voicetxt);
        config = new VoiceRecognitionConfig();
        config.setSpeechMode(VoiceRecognitionConfig.SPEECHMODE_MULTIPLE_SENTENCE);
        config.enableVoicePower(true);
        config.setProp(VoiceRecognitionConfig.PROP_INPUT);
        config.setLanguage(VoiceRecognitionConfig.LANGUAGE_ENGLISH);
        config.setUseDefaultAudioSource(false);
        config.setSampleRate(8000);

        Helper helper = new Helper();
        helper.ossService = OSSServiceProvider.getService();
        helper.ossService.setApplicationContext(getApplicationContext());
        helper.Init();
        byte[] bytes = null;
        try {
            bytes = helper.GetObjectData(mQuestionItemSet.folderName + question.recordName);
        } catch (Exception e) {

        }
        mClient = VoiceRecognitionClient.getInstance(this);
        mClient.setTokenApis(API_KEY, SECRET_KEY);
        mClient.startVoiceRecognition(mListener, config);
        mClient.feedAudioBuffer(bytes, 0, bytes.length);

        Button playBtn = (Button)findViewById(R.id.btnPlay);

        final String finalR = question.recordName;

        helper.Init();
        try {
            bytes = helper.GetObjectData(mQuestionItemSet.folderName + finalR);
            FileOutputStream fout = new FileOutputStream(saved_local_path_dir + finalR);
            fout.write(bytes);
            fout.close();
        }
        catch (Exception E) {

        }

        playBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mediaPlayer = new MediaPlayer();
                    mediaPlayer.setDataSource(saved_local_path_dir + finalR);
                    mediaPlayer.prepare();
                    //mediaPlayer = MediaPlayer.create(getBaseContext(), rtn);
                    mediaPlayer.start();
                } catch (Exception e) {

                }
            }
        });

        bt1 = (Button)findViewById(R.id.bt1);
        bt2 = (Button)findViewById(R.id.bt2);
        bt3 = (Button)findViewById(R.id.bt3);
        bt4 = (Button)findViewById(R.id.bt4);

        bt1.setBackgroundColor(Color.LTGRAY);
        bt2.setBackgroundColor(Color.LTGRAY);
        bt3.setBackgroundColor(Color.LTGRAY);
        bt4.setBackgroundColor(Color.LTGRAY);

        bt1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                bt1.setBackgroundColor(Color.GREEN);
                bt2.setBackgroundColor(Color.LTGRAY);
                bt3.setBackgroundColor(Color.LTGRAY);
                bt4.setBackgroundColor(Color.LTGRAY);
            }
        });

        bt2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                bt2.setBackgroundColor(Color.GREEN);
                bt1.setBackgroundColor(Color.LTGRAY);
                bt3.setBackgroundColor(Color.LTGRAY);
                bt4.setBackgroundColor(Color.LTGRAY);
            }
        });

        bt3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                bt3.setBackgroundColor(Color.GREEN);
                bt2.setBackgroundColor(Color.LTGRAY);
                bt1.setBackgroundColor(Color.LTGRAY);
                bt4.setBackgroundColor(Color.LTGRAY);
            }
        });

        bt4.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                bt4.setBackgroundColor(Color.GREEN);
                bt2.setBackgroundColor(Color.LTGRAY);
                bt3.setBackgroundColor(Color.LTGRAY);
                bt1.setBackgroundColor(Color.LTGRAY);
            }
        });

        Button nextBtn = (Button)findViewById(R.id.btnStoryNext);
        nextBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox checkBox = (CheckBox)findViewById(R.id.checkBox1);
                if (checkBox.isChecked()) {
                    question.skip = true;
                    for (int i = 0; i < question.skipQues.size(); ++i)
                        mQuestionItemSet.questionItemSet.get(question.skipQues.get(i)).skip = true;
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
                intent.setClass(StoryRecallActivity.this, mQuestionItemSet.GetActivity());
                startActivity(intent);
                finish();
            }
        });

    }  

    /* 
     * 处理Click事件 
     */  
    @Override  
    public void onClick(View v)   
    {
        switch (v.getId())
       {  
       case R.id.btnStoryNext:
    	   CheckBox checkBox = (CheckBox)findViewById(R.id.checkBox1);
    	   if (checkBox.isChecked()) {
			   question.skip = true;
			   for (int i = 0; i < question.skipQues.size(); ++i)
				   mQuestionItemSet.questionItemSet.get(question.skipQues.get(i)).skip = true;
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
    	   
    	   mQuestionItemSet.questionId ++;
           while (mQuestionItemSet.questionId < mQuestionItemSet.questionItemSet.size() && mQuestionItemSet.questionItemSet.get(mQuestionItemSet.questionId).skip)
               mQuestionItemSet.questionId ++;
    	   Intent intent = new Intent();
           intent.setClass(StoryRecallActivity.this, mQuestionItemSet.GetActivity());
           startActivity(intent);
           finish();
    	   break;
      }  
   }  
        
      
}  