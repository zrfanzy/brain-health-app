package thu.drawingtest;

import org.json.JSONException;
import org.json.JSONObject;

import utility.QuestionItem;

public class DrawQuestion extends QuestionItem{
	String picName;
	String picNameA, picNameB, picNameC;
	public boolean view;
	public boolean show;
	
	@Override
	protected void Load(JSONObject reader)
	{
		show = false;
		type = "draw";
		try {
			guideWords = reader.getString("guide_words");
			if (reader.has("pic_name"))
			{
				view = true;
				picName = reader.getString("pic_name");
				picNameA = reader.getString("pic_nameA");
				picNameB = reader.getString("pic_nameB");
				picNameC = reader.getString("pic_nameC");
			}
			else
				view = false;
			if (reader.has("show"))
				show = true;
			else
				show = false;
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public String GetType()
	{
		return type;
	}
	
}
