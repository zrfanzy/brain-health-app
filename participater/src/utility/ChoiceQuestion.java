package utility;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ChoiceQuestion extends FillinQuestion{
	public List<String> choices;
	public List<Integer> score;
	public List<Integer> jump;
	public int answer;
	
	public JSONObject Save() {
		JSONObject saver = new JSONObject();
		try {
			saver.put("answer", answer);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return saver;
	}
	
	@Override
	protected void Load(JSONObject reader)
	{
		choices = new ArrayList<String>();
		score = new ArrayList<Integer>();
		jump = new ArrayList<Integer>();
		type = "choice";
		try {
			question = reader.getString("question");
			JSONArray questionChoices = reader.getJSONArray("choices");
			for (int i = 0; i < questionChoices.length(); i++)
			{
				choices.add(questionChoices.getJSONObject(i).getString("choice"));
				score.add(questionChoices.getJSONObject(i).getInt("score"));
				if (questionChoices.getJSONObject(i).has("jump"))
					jump.add(questionChoices.getJSONObject(i).getInt("jump"));
				else
					jump.add(1);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	void SetAnswer(int t)
	{
		answer = t;
	}
}
