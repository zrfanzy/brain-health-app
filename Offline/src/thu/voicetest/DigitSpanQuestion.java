package thu.voicetest;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DigitSpanQuestion extends VoiceQuestionItem{
	String spanType; // forward or backward
	List<List<Integer>> digits;
    List<List<Integer>> userAnswer = new ArrayList<List<Integer>>();
	List<String> digitVoiceFile;
	int score1, score2;
	
	String[] englishNumbers = {"zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};
	
	public String GetSpanType()
	{
		return spanType;
	}

	public void LoadAnswer(JSONObject reader) {
		try {
            if (reader.has("skip")) {
                skip = false;
                return;
            }
            if (reader.has("score1"))
                score1 = reader.getInt("score1");
            if (reader.has("score2"))
            score2 = reader.getInt("score2");

            userAnswer = new ArrayList<List<Integer>>();
            JSONArray array = reader.getJSONArray("user_answers");
            for (int i = 0; i < array.length(); ++i) {
                JSONArray t = array.getJSONArray(i);
                List<Integer> tmpArray = new ArrayList<Integer>();
                for (int j = 0; j < t.length(); ++j) {
                    tmpArray.add(t.getInt(j));
                }
                userAnswer.add(tmpArray);
            }
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public void GetAnswer(List<DigitAllView> digs) {
        userAnswer = new ArrayList<List<Integer>>();
        for (int i = 0; i < digs.size(); ++i) {
            List<Integer> tmpAnswer = new ArrayList<Integer>();
            for (int j = 0; j < digs.get(i).userAnswer.size(); ++j)
                tmpAnswer.add(digs.get(i).userAnswer.get(j));
            userAnswer.add(tmpAnswer);
        }
	}

	public JSONObject Save() {
		JSONObject saver = new JSONObject();
		try {
			if (skip)
				saver.put("skip", 1);
			else {
				saver.put("score1", score1);
				saver.put("score2", score2);

                JSONArray array = new JSONArray();
                for (int i = 0; i < userAnswer.size(); ++i) {
                    JSONArray tmpArray = new JSONArray();
                    for (int j = 0; j < userAnswer.get(i).size(); ++j) {
                        tmpArray.put(userAnswer.get(i).get(j));
                    }
                    array.put(tmpArray);
                }
                saver.put("user_answers", array);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return saver;
	}

	@Override
	protected void Load(JSONObject reader)
	{
		score1 = 0;
		score2 = 0;
		type = "digit_span";
		questionId = 0;
		digits = new ArrayList<List<Integer>>();
		digitVoiceFile = new ArrayList<String>();
		try {
			if (reader.has("skip")) {
				JSONArray skips = reader.getJSONArray("skip");
				for (int i = 0; i < skips.length(); ++i)
					skipQues.add(skips.getInt(i));
			}
		} catch (Exception e) {

		}
		try {
			guideWords = reader.getString("guide_words");
			spanType = reader.getString("span_type");
			voiceFile = reader.getString("voice");
			JSONArray questions = reader.getJSONArray("questions");
			for (int idx = 0; idx < questions.length(); ++idx)
			{
				JSONObject questionItem = questions.getJSONObject(idx);
				JSONArray digitJson = questionItem.getJSONArray("digits");
				JSONArray voiceFileName = questionItem.getJSONArray("voice");
				for (int i = 0; i < 2; ++i)
				{
					List<Integer> digitNumbers = new ArrayList<Integer>();
					for (int j = 0; j < digitJson.getJSONArray(i).length(); ++j)
						digitNumbers.add(digitJson.getJSONArray(i).getInt(j));
					digits.add(digitNumbers);
					digitVoiceFile.add(voiceFileName.getString(i));
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public String GetDigitVoice()
	{
		return digitVoiceFile.get(questionId);
	}
	
	@Override
	public String GetType()
	{
		return type;
	}
	
	@Override
	public int Calculate(List<String> words) {
		Boolean allCounted = true;
		Boolean correct = false;
		List<Integer> toCheckDigits = digits.get(questionId);
		
		for (int i = 0; i < toCheckDigits.size(); i++)
		{
			int num = toCheckDigits.get(i);
			Boolean thisCounted = false;
			for (int j = 0; j < words.size(); ++j)
				if (words.get(j).contains(englishNumbers[num]))
				{
					thisCounted = true; break;
				}
			if (!thisCounted)
			{
				allCounted = false; break;
			}
		}
		
		if (!allCounted || (words.size() > toCheckDigits.size() + 2))
		{
			// SCORE 0
			if (spanType.equals("backward") && questionId < 2)
			{
				// do nothing
			}
			else if (questionId % 2 == 1 && score2 < digits.size()) // both of the trials failed
				questionId += digits.size(); // quit test
			questionId += 1;
		}
		else
		{
			int startID, step, counted;
			counted = 0;
			if (spanType.equals("forward"))
			{
				startID = 0; step = 1;
			}
			else
			{
				startID = words.size() - 1; step = -1;
			}
			while (counted < toCheckDigits.size() && startID >= 0 && startID < words.size())
			{
				int num = toCheckDigits.get(counted);
				if (words.get(startID).contains(englishNumbers[num]))
					counted ++;
				startID += step;
			}
			
			if (counted >= toCheckDigits.size())
			{
				// SCORE 2
				score2 = toCheckDigits.size();
				//if (score1 == 0 || (score1 == (toCheckDigits.size() - 1)))
				score1 = toCheckDigits.size();
				if (spanType.equals("backward") && questionId < 2)
				{
					if (questionId == 0)
						questionId = 6;
					else
						questionId = 4;
					return 1;
				}
				if (score1 == 0 || (score1 == (toCheckDigits.size() - 1)))
					score1 = toCheckDigits.size();
				questionId ++;
				if (questionId % 2 == 1)
					questionId ++;
				correct = true;
			}
			else
			{
				// SCORE 1
				score2 = toCheckDigits.size();
				questionId ++;
			}
		}
		
		System.out.println(questionId);
		if (questionId >= digits.size())
			return -1;
		else if (correct)
			return 1;
		else return 0;
	}
	
}
