package thu.voicetest;
  
import java.util.ArrayList;
import java.util.List;

import osshelper.Helper;
import utility.QuestionItemSet;

import com.alibaba.sdk.android.oss.OSSServiceProvider;
import com.alibaba.sdk.android.oss.model.OSSException;
import com.example.offline.R;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;  
import android.view.View.OnClickListener;  
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
  
public class DigitSpanActivity extends Activity implements OnClickListener  
{  
	QuestionItemSet mQuestionItemSet;
	DigitSpanQuestion question;
	Boolean flag;
	List<DigitAllView> digs;
    int toCheck;
    private int back;

    private Handler mHandler;
	
	MediaPlayer mediaPlayer = new MediaPlayer(); 
	
    List<String> words = new ArrayList<String>();
      
    @Override  
    protected void onCreate(Bundle savedInstanceState)   
    {  
        super.onCreate(savedInstanceState);
        setContentView(R.layout.digit_span);  
        InitView();
    }  
      
    /* 
     * 界面初始化 
     */  
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
	private void InitView()
    {  
    	mQuestionItemSet = (QuestionItemSet) getApplicationContext();
        question = (DigitSpanQuestion) ((mQuestionItemSet.GetQuestion()));
        digs = new ArrayList<DigitAllView>();
        toCheck = 0;
        back = 0;
    	if (question.spanType.equals("backward"))
    	{
            back = -1;
    		TextView view = (TextView)findViewById(R.id.description);
    		view.setText("Digit Span(Backward) (WAIS)");
			view = (TextView)findViewById(R.id.guideWord);
			view.setText("Now I am going to say some more numbers, but this time when I stop I want you to say them backwards.\n  For example, if I say “7-1-9” what would you say?\n");
			view = (TextView)findViewById(R.id.down);
			view.setText("NP008");
	        DigitAllView digits = (DigitAllView)findViewById(R.id.digit_1);
	    	digits.InitB(question.digits.get(0));
	    	digs.add(digits);
	    	digits = (DigitAllView)findViewById(R.id.digit_2);
	    	digits.InitB(question.digits.get(1));
	    	digs.add(digits);
	    	digits = (DigitAllView)findViewById(R.id.digit1);
	    	digits.setVisibility(View.GONE);
	    	digits = (DigitAllView)findViewById(R.id.digit2);
	    	digits.setVisibility(View.GONE);
	    	digits = (DigitAllView)findViewById(R.id.digit3);
	    	digits.InitB(question.digits.get(2));
	    	digs.add(digits);
	    	digits = (DigitAllView)findViewById(R.id.digit4);
	    	digits.InitB(question.digits.get(3));
	    	digs.add(digits);
	    	digits = (DigitAllView)findViewById(R.id.digit5);
	    	digits.InitB(question.digits.get(4));
	    	digs.add(digits);
	    	digits = (DigitAllView)findViewById(R.id.digit6);
	    	digits.InitB(question.digits.get(5));
	    	digs.add(digits);
	    	digits = (DigitAllView)findViewById(R.id.digit7);
	    	digits.InitB(question.digits.get(6));
	    	digs.add(digits);
	    	digits = (DigitAllView)findViewById(R.id.digit8);
	    	digits.InitB(question.digits.get(7));
	    	digs.add(digits);
	    	digits = (DigitAllView)findViewById(R.id.digit9);
	    	digits.InitB(question.digits.get(8));
	    	digs.add(digits);
	    	digits = (DigitAllView)findViewById(R.id.digit10);
	    	digits.InitB(question.digits.get(9));
	    	digs.add(digits);
	    	digits = (DigitAllView)findViewById(R.id.digit11);
	    	digits.InitB(question.digits.get(10));
	    	digs.add(digits);
	    	digits = (DigitAllView)findViewById(R.id.digit12);
	    	digits.InitB(question.digits.get(11));
	    	digs.add(digits);
	    	digits = (DigitAllView)findViewById(R.id.digit13);
	    	digits.InitB(question.digits.get(12));
	    	digs.add(digits);
	    	digits = (DigitAllView)findViewById(R.id.digit14);
	    	digits.InitB(question.digits.get(13));
	    	digs.add(digits);
    	}
    	else
    	{
    		DigitAllView digits = (DigitAllView)findViewById(R.id.digit1);
	    	digits.Init(question.digits.get(0));
	    	digs.add(digits);
	    	digits = (DigitAllView)findViewById(R.id.digit2);
	    	digits.Init(question.digits.get(1));
	    	digs.add(digits);
	    	digits = (DigitAllView)findViewById(R.id.digit3);
	    	digits.Init(question.digits.get(2));
	    	digs.add(digits);
	    	digits = (DigitAllView)findViewById(R.id.digit4);
	    	digits.Init(question.digits.get(3));
	    	digs.add(digits);
	    	digits = (DigitAllView)findViewById(R.id.digit5);
	    	digits.Init(question.digits.get(4));
	    	digs.add(digits);
	    	digits = (DigitAllView)findViewById(R.id.digit6);
	    	digits.Init(question.digits.get(5));
	    	digs.add(digits);
	    	digits = (DigitAllView)findViewById(R.id.digit7);
	    	digits.Init(question.digits.get(6));
	    	digs.add(digits);
	    	digits = (DigitAllView)findViewById(R.id.digit8);
	    	digits.Init(question.digits.get(7));
	    	digs.add(digits);
	    	digits = (DigitAllView)findViewById(R.id.digit9);
	    	digits.Init(question.digits.get(8));
	    	digs.add(digits);
	    	digits = (DigitAllView)findViewById(R.id.digit10);
	    	digits.Init(question.digits.get(9));
	    	digs.add(digits);
	    	digits = (DigitAllView)findViewById(R.id.digit11);
	    	digits.Init(question.digits.get(10));
	    	digs.add(digits);
	    	digits = (DigitAllView)findViewById(R.id.digit12);
	    	digits.Init(question.digits.get(11));
	    	digs.add(digits);
	    	digits = (DigitAllView)findViewById(R.id.digit13);
	    	digits.Init(question.digits.get(12));
	    	digs.add(digits);
	    	digits = (DigitAllView)findViewById(R.id.digit14);
	    	digits.Init(question.digits.get(13));
	    	digs.add(digits);
	    	digits = (DigitAllView)findViewById(R.id.digit_1);
	    	digits.setVisibility(View.GONE);
	    	digits = (DigitAllView)findViewById(R.id.digit_2);
	    	digits.setVisibility(View.GONE);
	    	TextView view = (TextView)findViewById(R.id.digit_1view);
	    	view.setVisibility(View.GONE);
	    	view = (TextView)findViewById(R.id.digit_2view);
	    	view.setVisibility(View.GONE);
    	}
		for (int i = 0; i < digs.size(); ++i) {
			digs.get(i).Enable();
			try {
				digs.get(i).InitAnswer(question.userAnswer.get(i));
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}

    	Button btn = (Button)findViewById(R.id.getNext);
    	btn.setOnClickListener(this);
		btn = (Button)findViewById(R.id.getScore);
		btn.setOnClickListener(this);
		btn.callOnClick();
    }  
    
    
    public void onClick(View v)   
    {  
       switch(v.getId())  
       {  
       case R.id.getNext:
		   CheckBox checkBox = (CheckBox)findViewById(R.id.checkBox1);
		   if (checkBox.isChecked()) {
			   question.skip = true;
			   for (int i = 0; i < question.skipQues.size(); ++i)
				   mQuestionItemSet.questionItemSet.get(question.skipQues.get(i)).skip = true;
		   }
		   else {
			   question.GetAnswer(digs);
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
           intent.setClass(DigitSpanActivity.this, mQuestionItemSet.GetActivity());
           startActivity(intent);
           finish();
    	   break;
         case R.id.getScore:  
        	 int score1 = 0;
        	 int score2 = 0;
           for (int i = 0; i < 14; ++i) {
        	   int num = digs.get(i).Calculate();
        	   if ((back == -1) && (i < 1)) {
        		   if (i == 0) {
        			   if (num == 2) {
        				   score1 = 3;
        				   score2 = 3;
        				   i += 3;
        			   } else if (num == 1) {
        				   score1 = 3;
        			   }
        		   }
        		   if (i == 1) {
        			   if (num == 2) {
        				   score1 = 2;
        				   score2 = 2;
        				   i += 2;
        			   } else if (num == 1)
        				   score1 = 3;
        		   }
        		   continue;
        	   }
        	   if (num == 2) {
        		   score1 = i / 2 + 3 + back;
        		   score2 = i / 2 + 3 + back;
        		   if (i % 2 == 0)
        			   i += 1;
        	   }
        	   else if (num == 1) {
        		   score1 = i / 2 + 3 + back;
        		   if (i % 2 == 0)
        			   i += 1;
        	   }
        	   else if (num == 0 && i % 2 == 1) {
        		   break;
        	   }
           }
           TextView t1 = (TextView)findViewById(R.id.score1);
           t1.setText("The longest correct span = " + Integer.toString(score2));
           t1 = (TextView)findViewById(R.id.score2);
           t1.setText("The longest span = " + Integer.toString(score1));
           break;  
       }  
    }  
  
      
}  