package utility;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class QuestionItem {
	
	protected String type;
	public String guideWords;
	public boolean skip;
	public List<Integer> skipQues = new ArrayList<Integer>();
	
	protected void Load(JSONObject reader)
	{
		
	}
	
	public String GetType()
	{
		return type;
	}
	
}
