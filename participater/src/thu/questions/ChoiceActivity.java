package thu.questions;

import osshelper.Helper;
import utility.ChoiceQuestion;
import utility.FillInSet;
import utility.QuestionItemSet;

import com.alibaba.sdk.android.oss.OSSServiceProvider;
import com.alibaba.sdk.android.oss.model.OSSException;

import com.example.paticipanter.R;

import android.app.Activity;  
import android.content.Intent;
import android.os.Bundle;   
import android.view.View;  
import android.view.View.OnClickListener;  
import android.widget.Button;  
import android.widget.RadioButton;
import android.widget.TextView;  
  
public class ChoiceActivity extends Activity implements OnClickListener  
{  
	QuestionItemSet mQuestionItemSet;
	ChoiceQuestion choiceQuestion;
  
    private TextView questionTextView; 
    private Button nextButton;  
    Boolean toCheck; 
    
    @Override  
    protected void onCreate(Bundle savedInstanceState)   
    {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.short_choice);  
        InitView();   
    }  
    
    private void InitView()  
    {  
    	questionTextView = (TextView) findViewById (R.id.choiceQuestion);
    	nextButton = (Button) findViewById(R.id.btnChoiceNext);
    	nextButton.setOnClickListener(this);
    	
    	mQuestionItemSet = (QuestionItemSet) getApplicationContext();
    	choiceQuestion = (ChoiceQuestion) (((FillInSet)(mQuestionItemSet.GetQuestion())).GetQuestion());
        questionTextView.setText(choiceQuestion.question);
        
        RadioButton[] choiceButtons = new RadioButton[7];
        choiceButtons[0] = (RadioButton) findViewById(R.id.choice0);
        choiceButtons[1] = (RadioButton) findViewById(R.id.choice1);
        choiceButtons[2] = (RadioButton) findViewById(R.id.choice2);
        choiceButtons[3] = (RadioButton) findViewById(R.id.choice3);
        choiceButtons[4] = (RadioButton) findViewById(R.id.choice4);
        choiceButtons[5] = (RadioButton) findViewById(R.id.choice5);
        choiceButtons[6] = (RadioButton) findViewById(R.id.choice6);
        for (int i = 0; i < choiceButtons.length; i++)
        {
        	choiceButtons[i].setVisibility(View.GONE);
        }
        choiceButtons[0].setChecked(true);
        
        for (int i = 0; i < choiceQuestion.choices.size(); ++i)
        {
        	choiceButtons[i].setVisibility(View.VISIBLE);
        	int rtn = getResources().getIdentifier(choiceQuestion.choices.get(i), "raw", getPackageName());
        	choiceButtons[i].setBackgroundResource(rtn);
        	//choiceButtons[i].setText(choiceQuestion.choices.get(i));
        }
    }  
    
    @Override  
    public void onClick(View v)   
    {  
       switch(v.getId())  
       {
           case R.id.btnChoiceNext:
               RadioButton[] choiceButtons = new RadioButton[7];
               choiceButtons[0] = (RadioButton) findViewById(R.id.choice0);
               choiceButtons[1] = (RadioButton) findViewById(R.id.choice1);
               choiceButtons[2] = (RadioButton) findViewById(R.id.choice2);
               choiceButtons[3] = (RadioButton) findViewById(R.id.choice3);
               choiceButtons[4] = (RadioButton) findViewById(R.id.choice4);
               choiceButtons[5] = (RadioButton) findViewById(R.id.choice5);
               choiceButtons[6] = (RadioButton) findViewById(R.id.choice6);
               Boolean flag = false;
               for (int i = 0; i < choiceQuestion.jump.size(); i++)
               {
                   if (choiceButtons[i].isChecked())
                   {
                       flag = true;
                       choiceQuestion.answer = i;

                       //upload answeuyr
                       String json = mQuestionItemSet.Save();
                       byte[] data = json.getBytes();

                       Helper helper = new Helper();
                       helper.ossService = OSSServiceProvider.getService();
                       helper.ossService.setApplicationContext(getApplicationContext());
                       helper.Init();

                       try {
                           helper.PutObject(mQuestionItemSet.folderName + "paticipanter.json", data);
                       } catch (OSSException e) {
                           // TODO Auto-generated catch block
                           e.printStackTrace();
                       }

                       FillInSet fillinset = (FillInSet)(mQuestionItemSet.GetQuestion());
                       fillinset.fillInQuestionId += choiceQuestion.jump.get(i);
                       if (fillinset.fillInQuestionId >= fillinset.questionItemSet.size())
                           mQuestionItemSet.questionId ++;
                       Intent intent = new Intent();
                       System.out.println(mQuestionItemSet.GetActivity().getName());
                       intent.setClass(ChoiceActivity.this, mQuestionItemSet.GetActivity());
                       startActivity(intent);
                       finish();
                       break;
                   }
               }
               if (!flag)
               {
                   FillInSet fillinset = (FillInSet)(mQuestionItemSet.GetQuestion());
                   fillinset.fillInQuestionId ++;
                   if (fillinset.fillInQuestionId >= fillinset.questionItemSet.size())
                       mQuestionItemSet.questionId ++;
                   Intent intent = new Intent();
                   System.out.println(mQuestionItemSet.GetActivity().getName());
                   intent.setClass(ChoiceActivity.this, mQuestionItemSet.GetActivity());
                   startActivity(intent);
                   finish();
               }
               break;
       }
    }  
  
      
}  