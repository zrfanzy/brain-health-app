package utility;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Application;
import thu.drawingtest.DrawQuestion;
import thu.drawingtest.ViewImgActivity;
import thu.questions.ChoiceTesterActivity;
import thu.questions.EndActivity;
import thu.questions.TrailerActivity;
import thu.voicetest.DigitSpanQuestion;
import thu.voicetest.DigitSpanActivity;
import thu.voicetest.StoryRecallQuestion;
import thu.voicetest.StoryRecallActivity;

public class QuestionItemSet extends Application{
	public String folderName;
	public List<QuestionItem> questionItemSet = new ArrayList<QuestionItem>();
	public int questionId;
	
	@Override
    public void onCreate() {
		super.onCreate();
    }

	public String Save()
	{
		JSONArray saver = new JSONArray();
		for (int idx = 0; idx < questionItemSet.size(); ++idx)
		{
			String typeName = questionItemSet.get(idx).type;
			JSONObject tmpJson = new JSONObject();
			if (typeName.equals("fill_in_set"))
			{
				tmpJson = ((FillInSet)(questionItemSet.get(idx))).Save();
			}
			else if (typeName.equals("story_recall"))
			{
				tmpJson = ((StoryRecallQuestion)(questionItemSet.get(idx))).Save();
			}
			else if (typeName.equals("trailer"))
			{
				tmpJson = ((TrailerQuestion)(questionItemSet.get(idx))).Save();
			}
			else if (typeName.equals("draw"))
			{
				tmpJson = ((DrawQuestion)(questionItemSet.get(idx))).Save();
			}
			else if (typeName.equals("digit_span"))
			{
				tmpJson = ((DigitSpanQuestion)(questionItemSet.get(idx))).Save();
			}
			saver.put(tmpJson);
		}
		JSONObject obj = new JSONObject();
		try {
			obj.put("answers", saver);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return obj.toString();
	}
	
	public void Load(String buffer)
	{
		questionId = 0;
		try {
			JSONObject reader = new JSONObject(buffer);
			JSONArray questions = reader.getJSONArray("question_items");
			for (int idx = 0; idx < questions.length(); ++idx)
			{
				JSONObject questionItem = questions.getJSONObject(idx);
				String type = questionItem.getString("type");
				QuestionItem newQuestion = null;
				if (type.equals("digit_span"))
				{
					newQuestion = new DigitSpanQuestion();
					newQuestion.Load(questionItem);
				}
				else if (type.equals("fill_in_set"))
				{
					newQuestion = new FillInSet();
					newQuestion.Load(questionItem);
				}
				else if (type.equals("story_recall"))
				{
					newQuestion = new StoryRecallQuestion();
					newQuestion.Load(questionItem);
				}
				else if (type.equals("draw"))
				{
					newQuestion = new DrawQuestion();
					newQuestion.Load(questionItem);
				}
				else if (type.equals("trailer"))
				{
					newQuestion = new TrailerQuestion();
					newQuestion.Load(questionItem);
				}
				questionItemSet.add(newQuestion);
				System.out.println(newQuestion.type);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void LoadAnswer(String buffer)
	{
		questionId = 0;
		try {
			JSONObject reader = new JSONObject(buffer);
			JSONArray questions = reader.getJSONArray("answers");
			int index = 0;
			for (int idx = 0; idx < questionItemSet.size(); ++idx) {
				String type = questionItemSet.get(idx).type;
				JSONObject questionItem = questions.getJSONObject(index);
				if (type.equals("digit_span"))
				{
                    questionItemSet.get(idx).LoadAnswer(questionItem);
                    index++;
				}
				else if (type.equals("fill_in_set"))
				{
                    questionItemSet.get(idx).LoadAnswer(questionItem);
                    index++;
				}
				else if (type.equals("story_recall"))
				{
                    //questionItemSet.get(idx).LoadAnswer(null);
				}
				else if (type.equals("draw"))
				{
                    //questionItemSet.get(idx).LoadAnswer(questionItem);
				}
				else if (type.equals("trailer"))
				{
                    questionItemSet.get(idx).LoadAnswer(questionItem);
                    index++;
				}

			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Class<?> GetActivity() {
		if (questionId >= questionItemSet.size())
			return EndActivity.class;
		String typeName = questionItemSet.get(questionId).type;
		if (typeName.equals("digit_span"))
		{
			return DigitSpanActivity.class;
		}
		else if (typeName.equals("fill_in_set"))
		{
			return ChoiceTesterActivity.class;
		}
		else if (typeName.equals("story_recall"))
		{
			return StoryRecallActivity.class;
		}
		else if (typeName.equals("draw"))
		{
			DrawQuestion question = (DrawQuestion) questionItemSet.get(questionId);
			if (question.view)
			{
				return ViewImgActivity.class;
			}
			else
				return EndActivity.class;
		}
		else if (typeName.equals("trailer"))
		{
			return TrailerActivity.class;
		}
		return EndActivity.class;
	}
	
	public String GetGuideWord() {
		return questionItemSet.get(questionId).guideWords;
	}
	
	public String GetType() {
		return questionItemSet.get(questionId).type;
	}
	
	public QuestionItem GetQuestion(){
		return questionItemSet.get(questionId);
	}

	public QuestionItem GetQuestion(int index){
		return questionItemSet.get(index);
	}
}
