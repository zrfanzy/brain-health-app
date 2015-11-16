package utility;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import thu.questions.ChoiceActivity;
import thu.questions.EndActivity;

public class FillInSet extends QuestionItem{
	public List<FillinQuestion> questionItemSet = new ArrayList<FillinQuestion>();
	public int fillInQuestionId;
	
	
	public QuestionItem GetQuestion() {
		return questionItemSet.get(fillInQuestionId);
	}
	
	public JSONObject Save() {
		JSONArray saver = new JSONArray();
		for (int idx = 0; idx < questionItemSet.size(); ++idx)
		{
			JSONObject tmpJson = ((ChoiceQuestion)(questionItemSet.get(idx))).Save();
			saver.put(tmpJson);
		}
		JSONObject obj = new JSONObject();
		try {
			obj.put("answers", saver);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return obj;
	}
	
	public void Load(JSONObject reader)
	{
		fillInQuestionId = 0;
		type = "fill_in_set";
		try {
			JSONArray questions = reader.getJSONArray("questions");
			for (int idx = 0; idx < questions.length(); ++idx)
			{
				JSONObject questionItem = questions.getJSONObject(idx);
				String type = questionItem.getString("type");
				FillinQuestion newQuestion = null;
				if (type.equals("choice"))
				{
					newQuestion = new ChoiceQuestion();
					newQuestion.Load(questionItem);
				}
				questionItemSet.add(newQuestion);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Class<?> GetActivity() {
		if (fillInQuestionId >= questionItemSet.size())
			return EndActivity.class;
		String typeName = questionItemSet.get(fillInQuestionId).type;
		if (typeName.equals("choice"))
		{
			return ChoiceActivity.class;
		}
		else
		{
			return null;
		}
	}
}
