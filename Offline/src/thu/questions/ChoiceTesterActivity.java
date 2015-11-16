package thu.questions;

import java.util.ArrayList;
import java.util.List;

import osshelper.Helper;
import utility.ChoiceQuestion;
import utility.FillInSet;
import utility.QuestionItemSet;

import com.alibaba.sdk.android.oss.OSSServiceProvider;
import com.alibaba.sdk.android.oss.model.OSSException;
import com.example.offline.R;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ChoiceTesterActivity extends Activity implements OnClickListener
{
    QuestionItemSet mQuestionItemSet;
    Button nextButton;
    List<ChoicerView> choices;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choice_tester);
        InitView();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void InitView()
    {

        mQuestionItemSet = (QuestionItemSet) getApplicationContext();
        FillInSet ques = (FillInSet) ((mQuestionItemSet.GetQuestion()));
        LinearLayout row = (LinearLayout)findViewById(R.id.choices);

        TextView tmpView = (TextView)findViewById(R.id.down);
        tmpView.setText(ques.down);

        tmpView = (TextView)findViewById(R.id.guideWord);
        tmpView.setText(ques.guideWords);

        tmpView = (TextView)findViewById(R.id.description);
        tmpView.setText(ques.des);

        LinearLayout.LayoutParams marginLayout = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        marginLayout.setMargins(2, 2, 2, 2);

        LinearLayout viewLayout = (LinearLayout) findViewById(R.id.tableW);
        viewLayout.setOrientation(LinearLayout.HORIZONTAL);
        viewLayout.setBackgroundColor(Color.BLACK);
        //viewLayout.setLayoutParams(marginLayout);
        for (int i = 0; i < ques.tables.size(); ++i)
        {
            tmpView = new TextView(this);
            tmpView.setText(ques.tables.get(i));
            tmpView.setWidth(ques.tableWidth.get(i));
            tmpView.setLayoutParams(marginLayout);
            tmpView.setBackgroundColor(Color.WHITE);
            viewLayout.addView(tmpView);
        }

        if (ques.draw)
        {
            LinearLayout designLayout = new LinearLayout(this);
            designLayout.setOrientation(LinearLayout.HORIZONTAL);
            designLayout.setBackgroundColor(Color.BLACK);
            designLayout.setLayoutParams(marginLayout);

            tmpView = new TextView(this);
            tmpView.setLayoutParams(marginLayout);
            tmpView.setWidth(250);
            tmpView.setBackgroundColor(Color.WHITE);
            tmpView.setText("Design A");
            designLayout.addView(tmpView);

            tmpView = new TextView(this);
            tmpView.setLayoutParams(marginLayout);
            tmpView.setWidth(250);
            tmpView.setBackgroundColor(Color.WHITE);
            tmpView.setText("Design B");
            designLayout.addView(tmpView);

            tmpView = new TextView(this);
            tmpView.setLayoutParams(marginLayout);
            tmpView.setWidth(250);
            tmpView.setBackgroundColor(Color.WHITE);
            tmpView.setText("Design C1");
            designLayout.addView(tmpView);

            tmpView = new TextView(this);
            tmpView.setLayoutParams(marginLayout);
            tmpView.setWidth(250);
            tmpView.setBackgroundColor(Color.WHITE);
            tmpView.setText("Design C2");
            designLayout.addView(tmpView);

            List<Button> dBtns = new ArrayList<Button>(20);
            for (int i = 0; i < 20; ++i) {
                dBtns.add(new Button(this));
                dBtns.get(i).setWidth(250);
                dBtns.get(i).setHeight(200);
            }
            dBtns.get(0).setBackground(getResources().getDrawable(R.drawable.t1_1));
            dBtns.get(1).setBackground(getResources().getDrawable(R.drawable.t2_1));
            dBtns.get(2).setBackground(getResources().getDrawable(R.drawable.t3_1));
            dBtns.get(3).setBackground(getResources().getDrawable(R.drawable.t4_1));
            dBtns.get(4).setBackground(getResources().getDrawable(R.drawable.t1_2));
            dBtns.get(5).setBackground(getResources().getDrawable(R.drawable.t2_2));
            dBtns.get(6).setBackground(getResources().getDrawable(R.drawable.t3_2));
            dBtns.get(7).setBackground(getResources().getDrawable(R.drawable.t4_2));
            dBtns.get(8).setBackground(getResources().getDrawable(R.drawable.t1_3));
            dBtns.get(9).setBackground(getResources().getDrawable(R.drawable.t2_3));
            dBtns.get(10).setBackground(getResources().getDrawable(R.drawable.t3_3));
            dBtns.get(11).setBackground(getResources().getDrawable(R.drawable.t4_3));
            dBtns.get(12).setBackground(getResources().getDrawable(R.drawable.t1_4));
            dBtns.get(13).setBackground(getResources().getDrawable(R.drawable.t2_4));
            dBtns.get(14).setBackground(getResources().getDrawable(R.drawable.t3_4));
            dBtns.get(15).setBackground(getResources().getDrawable(R.drawable.t4_4));
            dBtns.get(16).setBackground(getResources().getDrawable(R.drawable.t1_5));
            dBtns.get(17).setBackground(getResources().getDrawable(R.drawable.t2_5));
            dBtns.get(18).setBackground(getResources().getDrawable(R.drawable.t3_5));
            dBtns.get(19).setBackground(getResources().getDrawable(R.drawable.t4_5));

            choices = new ArrayList<ChoicerView>();
            for (int i = 0; i < ques.questionItemSet.size(); ++i)
            {
                ChoicerView choice = new ChoicerView(this);
                choice.Init((ChoiceQuestion) (ques.questionItemSet.get(i)), ques.qwidth, ques.width);
                try {
                    choice.btns.get(ques.userAnswer.get(i)).callOnClick();
                }
                catch (Exception e) {

                }
                row.addView(choice);
                choices.add(choice);
            }

            LinearLayout ly = (LinearLayout) findViewById(R.id.ly);

            ly.addView(designLayout);
            for (int i = 0; i < 5; ++i)
            {
                designLayout = new LinearLayout(this);
                designLayout.setOrientation(LinearLayout.HORIZONTAL);
                designLayout.setBackgroundColor(Color.BLACK);
                designLayout.setLayoutParams(marginLayout);
                for (int j = i * 4; j < i * 4 + 4; ++j) {
                    designLayout.addView(dBtns.get(j));
                    final int finalI = i;
                    final int finalJ = j % 4;
                    dBtns.get(j).setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            (choices.get(finalJ)).btns.get(finalI).callOnClick();
                        }
                    });
                }
                ly.addView(designLayout);
            }

            Button btn = (Button)(findViewById(R.id.next));
            btn.setOnClickListener(this);
            btn = (Button)(findViewById(R.id.score));
            btn.setOnClickListener(this);
            btn.callOnClick();
            return;
        }

        choices = new ArrayList<ChoicerView>();
        for (int i = 0; i < ques.questionItemSet.size(); ++i)
        {
            ChoicerView choice = new ChoicerView(this);
            choice.Init((ChoiceQuestion) (ques.questionItemSet.get(i)), ques.qwidth, ques.width);
            try {
                choice.btns.get(ques.userAnswer.get(i)).callOnClick();
            }
            catch (Exception e) {

            }
            row.addView(choice);
            choices.add(choice);
        }

        Button btn = (Button)(findViewById(R.id.next));
        btn.setOnClickListener(this);
        btn = (Button)(findViewById(R.id.score));
        btn.setOnClickListener(this);
        btn.callOnClick();
    }

    @Override
    public void onClick(View v)
    {
        switch(v.getId())
        {
            case R.id.score:
                int score = 0;
                for (int i = 0; i < choices.size(); ++i) {
                    score += choices.get(i).GetScore();
                }
                ((FillInSet)(mQuestionItemSet.GetQuestion())).score = score;
                TextView view = (TextView)(findViewById(R.id.scoredes));
                view.setText("SCORE:"+Integer.toString(score));
                break;
            case R.id.next:
                CheckBox checkBox = (CheckBox)findViewById(R.id.checkBox1);
                ((FillInSet)(mQuestionItemSet.GetQuestion())).GetAnswer(choices);
                if (checkBox.isChecked()) {
                    mQuestionItemSet.GetQuestion().skip = true;
                    for (int i = 0; i < mQuestionItemSet.GetQuestion().skipQues.size(); ++i)
                        mQuestionItemSet.questionItemSet.get(mQuestionItemSet.GetQuestion().skipQues.get(i)).skip = true;
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
                intent.setClass(ChoiceTesterActivity.this, mQuestionItemSet.GetActivity());
                startActivity(intent);
                finish();
                break;
        }
    }


}