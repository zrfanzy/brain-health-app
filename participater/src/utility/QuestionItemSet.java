package utility;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Application;
import thu.drawingtest.DrawGraphic;
import thu.drawingtest.DrawQuestion;
import thu.questions.EndActivity;

public class QuestionItemSet extends Application{
	public String folderName;
	List<QuestionItem> questionItemSet = new ArrayList<QuestionItem>();
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
				if (type.equals("fill_in_set"))
				{
					newQuestion = new FillInSet();
					newQuestion.Load(questionItem);
				}
				else if (type.equals("draw"))
				{
					newQuestion = new DrawQuestion();
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

	public Class<?> GetActivity() {
		if (questionId >= questionItemSet.size())
			return EndActivity.class;
		String typeName = questionItemSet.get(questionId).type;
		if (typeName.equals("fill_in_set"))
		{
			return ((FillInSet)(questionItemSet.get(questionId))).GetActivity();
		}
		else if (typeName.equals("draw"))
		{
			return DrawGraphic.class;
		}
		return null;
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
}
