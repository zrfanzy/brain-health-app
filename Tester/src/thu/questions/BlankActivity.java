package thu.questions;
  
import utility.BlankQuestion;
import utility.FillInSet;
import utility.QuestionItemSet;

import com.example.tester.R;

import android.app.Activity;  
import android.content.Intent;
import android.os.Bundle;  
import android.view.View;  
import android.view.View.OnClickListener;  
import android.widget.Button;  
import android.widget.TextView;  
  
public class BlankActivity extends Activity implements OnClickListener  
{  
	QuestionItemSet mQuestionItemSet;
	BlankQuestion blankQuestion;
  
    private TextView questionTextView; 
    private Button nextButton, lastButton;  
    Boolean toCheck;
      
    
    @Override  
    protected void onCreate(Bundle savedInstanceState)   
    {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.blank);  
        InitView();  
    }  
    
    private void InitView()  
    {  
    	questionTextView = (TextView) findViewById (R.id.blankQuestion);
    	nextButton = (Button) findViewById(R.id.btnBlankNext);
    	lastButton = (Button) findViewById(R.id.btnBlankLast);
    	nextButton.setOnClickListener(this);
    	lastButton.setOnClickListener(this);
    	lastButton.setEnabled(false);
    	
    	mQuestionItemSet = (QuestionItemSet) getApplicationContext();
        blankQuestion = (BlankQuestion) (((FillInSet)(mQuestionItemSet.GetQuestion())).GetQuestion());
        questionTextView.setText(blankQuestion.question);
        
    }  
    
    @Override  
    public void onClick(View v)   
    {  
    	switch(v.getId())  
        {  
        case R.id.btnBlankNext:
        	TextView view = (TextView) findViewById(R.id.blankEdit);
        	blankQuestion.answer = (String) view.getText().toString();
     	    FillInSet fillinset = (FillInSet)(mQuestionItemSet.GetQuestion());
       	    fillinset.fillInQuestionId += 1;
       	    if (fillinset.fillInQuestionId >= fillinset.questionItemSet.size())
         		mQuestionItemSet.questionId ++;
         	Intent intent = new Intent();
         	intent.setClass(BlankActivity.this, mQuestionItemSet.GetActivity());
            startActivity(intent);
            finish();
         	break;
        case R.id.btnBlankLast:	
     	   // TODO
     	   break;
        }  
    }  
  
      
}  