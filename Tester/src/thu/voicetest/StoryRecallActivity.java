package thu.voicetest;

import java.io.File;
import java.io.FileInputStream;

import osshelper.Helper;
import utility.QuestionItemSet;

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
  
public class StoryRecallActivity extends Activity implements OnClickListener  
{  
	public static String saved_local_path_dir = Environment.getExternalStorageDirectory().getPath() + "/";
	QuestionItemSet mQuestionItemSet;
	MediaRecorder recorder = new MediaRecorder();
	StoryRecallQuestion question;
	Boolean flag;
	
	MediaPlayer mediaPlayer = new MediaPlayer(); 
	
        /** 界面布局元素 **/  
    private Button BtnStart, BtnRecord, BtnStop, BtnNext;
		private FileInputStream fileInputStream;  
      
    @Override  
    protected void onCreate(Bundle savedInstanceState)   
    {  
        super.onCreate(savedInstanceState); 
        mQuestionItemSet = (QuestionItemSet) getApplicationContext();
        question = (StoryRecallQuestion) mQuestionItemSet.GetQuestion();
        if (question.delayed == 1)
        {
        	setContentView(R.layout.story_later);
        	BtnStop = (Button) findViewById(R.id.storyStopBtn);
            BtnRecord = (Button) findViewById(R.id.storyRecordBtn);  
            BtnRecord.setOnClickListener(this);
            BtnStop.setOnClickListener(this);
            BtnNext = (Button) findViewById(R.id.btnStoryNext);
            BtnNext.setOnClickListener(this);
            BtnStop.setEnabled(false);
            BtnRecord.setEnabled(true);
        }
        else
        {
        	setContentView(R.layout.story_tester);  
        	InitView();  
        }
    }  
      
    /* 
     * 界面初始化 
     */  
    private void InitView()  
    {  
    	BtnStart = (Button) findViewById(R.id.storyStartBtn);  
        BtnStop = (Button) findViewById(R.id.storyStopBtn);
        BtnRecord = (Button) findViewById(R.id.storyRecordBtn);  
        BtnRecord.setOnClickListener(this);
        BtnStop.setOnClickListener(this);
        BtnStart.setOnClickListener(this);   
        BtnNext = (Button) findViewById(R.id.btnStoryNext);
        BtnNext.setOnClickListener(this);
        BtnStart.setEnabled(true);
        BtnStop.setEnabled(false);
        BtnRecord.setEnabled(false);
        
        String voiceFile = question.storyFile;
        int rtn = getResources().getIdentifier(voiceFile, "raw", getPackageName());
        mediaPlayer = MediaPlayer.create(getBaseContext(), rtn);
    }  
    
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
      
    /* 
     * 处理Click事件 
     */  
    @Override  
    public void onClick(View v)   
    {  
       switch(v.getId())  
       {  
       case R.id.storyStartBtn:
    	   String voiceFile = question.storyFile;
    	   int rtn = getResources().getIdentifier(voiceFile, "raw", getPackageName());
    	   
    	   mediaPlayer = MediaPlayer.create(getBaseContext(), rtn);
    	   mediaPlayer.start();
    	   BtnStart.setEnabled(false);
    	   mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {  
    	   @Override public void onCompletion(MediaPlayer arg0) { 
    	      BtnRecord.setEnabled(true);
    	   }  });
    	   break;
       case R.id.storyRecordBtn:  
    	   // TODO record and save
    	   BtnRecord.setEnabled(false);
    	   BtnStop.setEnabled(true);
    	   try {
    		   recorder.setAudioSource(MediaRecorder.AudioSource.MIC);  
    		   recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);  
    		   recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);  
    		    
    		   recorder.setOutputFile(saved_local_path_dir + question.recordName);  
    		   recorder.prepare();  
    		   recorder.start();
    	   } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
    	   }
           break;  
       case R.id.storyStopBtn:
    	   try {
    		    recorder.stop();  
    		    recorder.reset();
    		    recorder.release();
    		    Helper helper = new Helper();
        	    helper.ossService = OSSServiceProvider.getService();
       		    helper.ossService.setApplicationContext(getApplicationContext());
       		    helper.Init();
	       		String fileName = saved_local_path_dir + question.recordName;
	    		
	    		int bufferSize;
	    		byte[] buffer = null;
	    		File sourceFile = new File(fileName);
	
	    		try
	    		{
	    			fileInputStream = new FileInputStream(sourceFile);
	    			bufferSize = fileInputStream.available(); 
	    			buffer = new byte[bufferSize];
	    			fileInputStream.read(buffer, 0, bufferSize);
	    			helper.PutObject(mQuestionItemSet.folderName + question.recordName, buffer);
	    		}
	    		catch (Exception e)
	    		{
	    			Log.e("Upload file to server Exception", "Exception : " + e.getMessage(), e);
	    		}
	    		helper.PutObject(mQuestionItemSet.folderName + question.recordName, buffer);
	    		deleteTempFile(fileName);
   		   } catch (Exception e) {
   			   // TODO Auto-generated catch block
   			   e.printStackTrace();
   		   }
    	   BtnStop.setEnabled(false);
    	   break;
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
   			   helper.PutObject(mQuestionItemSet.folderName + "tester.json", data);
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