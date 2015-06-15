package thu.voicetest;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class DigitAllView extends LinearLayout {
	int choice = -1;
	int answer = -1;
	TextView view, cue1, cue2, cueT;
	List<Integer> answers;
	public List<Integer> userAnswer = new ArrayList<Integer>();
    int cues = 0;

	public List<DigitView> digits;
	public Handler callbackHandler;

	/**
	 * This is used when creating the view in XML To have an image load in XML
	 * use the tag
	 * 'image="http://developer.android.com/images/dialog_buttons.png"'
	 * Replacing the url with your desired image Once you have instantiated the
	 * XML view you can call setImageDrawable(url) to change the image
	 * 
	 * @param context
	 * @param attrSet
	 */
	public DigitAllView(final Context context, final AttributeSet attrSet) {
		super(context, attrSet);
		instantiate(context, null);
	}

	public void Init(List<Integer> numbers) {
		String text = "";
		answers = new ArrayList<Integer>();
		for (int i = 0; i < numbers.size(); ++i)
		{
			text = text + Integer.toString(numbers.get(i)) + "-";
			//digits.get(i).Init(numbers.get(i));
			answers.add(numbers.get(i));
		}
		//for (int i = numbers.size(); i < 10; ++i)
		//	digits.get(i).Dis();
		for (int i = 0; i < 10; ++i)
			digits.get(i).Dis();
		view.setText(text);
	}

	public void Enable() {
		//answers = new ArrayList<Integer>();
		for (int i = 0; i < answers.size(); ++i)
		{
			digits.get(i).Init(answers.get(i));
		}
		for (int i = answers.size(); i < 10; ++i)
			digits.get(i).Dis();
	}
	
	public int Calculate() {
		userAnswer = new ArrayList<Integer>();
		int cues = 0;
		Boolean allCounted = true;
        List<Boolean> checked = new ArrayList<Boolean>();
		int count = 0;
        //if (digits.size() != answers.size())
		//	return -1;
        for (int i = 0; i < digits.size(); ++i) {
			checked.add(true);
            if (digits.get(i).GetChoice() < 0)
                continue;
            count++;
			userAnswer.add(digits.get(i).GetChoice());
		}
        if (count != answers.size())
            return -1;
		for (int i = 0; i < answers.size(); i++)
		{
			int num = answers.get(i);
			Boolean thisCounted = false;
			for (int j = 0; j < digits.size(); ++j)
				if ((checked.get(j) == true) && (digits.get(j).GetChoice() == num))
				{
                    checked.set(j, false);
					thisCounted = true; break;
				}
			if (!thisCounted)
			{
				allCounted = false; break;
			}
		}
		
		if (!allCounted)
		{
			return 0;
		}
		else
		{
			int startID, step, counted;
			startID = 0;
			counted = 0;
			step = 1;
			while (counted < answers.size() && startID >= 0 && startID < digits.size())
			{
				int num = answers.get(counted);
				if (digits.get(startID).GetChoice() == num)
					counted ++;
				startID += step;
			}
			
			if (counted >= answers.size())
			{
				return 2;
			}
			else
			{
				return 1;
			}
		}
	}
	
	public DigitAllView(final Context context) {
		super(context);
	}

	/**
	 * This is used when creating the view programatically Once you have
	 * instantiated the view you can call setImageDrawable(url) to change the
	 * image
	 * 
	 * @param context
	 *            the Activity context
	 * @param imageUrl
	 *            the Image URL you wish to load
	 */
	public DigitAllView(final Context context, final String imageUrl,
						Handler handler) {
		super(context);
		this.callbackHandler = handler;
		instantiate(context, imageUrl);
	}

	/**
	 * First time loading of the LoaderImageView Sets up the LayoutParams of the
	 * view, you can change these to get the required effects you want
	 */
	private void instantiate(final Context context, final String imageUrl) {
		Context mContext = context;
		LinearLayout mLayout = new LinearLayout(mContext);
		mLayout.setOrientation(LinearLayout.VERTICAL);

        LinearLayout lLayout = new LinearLayout(mContext);
        lLayout.setOrientation(LinearLayout.HORIZONTAL);
		
		TableLayout mytable = new TableLayout(mContext); 
		view = new TextView(mContext);
		view.setText("1-2-3-4-5");
		lLayout.addView(view);

        cueT = new TextView(mContext);
        cueT.setText("        # of cues:");
        cueT.setTextSize(10);
        lLayout.addView(cueT);
        cue1 = new TextView(mContext);
        cue1.setText(" 1 ");
        lLayout.addView(cue1);
        cue2 = new TextView(mContext);
        cue2.setText(" 2 ");
        lLayout.addView(cue2);
        cueT.setVisibility(View.GONE);
        cue1.setVisibility(View.GONE);
        cue2.setVisibility(View.GONE);

        mLayout.addView(lLayout);
        TableRow tableRow = new TableRow(context);
		tableRow.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
		digits = new ArrayList<DigitView>();
		for (int i = 0; i < 10; ++i)
		{
			DigitView digit = new DigitView(mContext);
			//digit.Dis();
			digits.add(digit);
			tableRow.addView(digit);
		}
		mLayout.addView(tableRow);
		mytable.addView(mLayout);
		addView(mytable);
		return;
	}

	/**
	 * Set's the view's drawable, this uses the internet to retrieve the image
	 * don't forget to add the correct permissions to your manifest
	 * 
	 * @param imageUrl
	 *            the url of the image you wish to load
	 */
	public void setImageDrawable(String url) {
		return;
	}

	public void InitB(List<Integer> numbers) {
        cueT.setVisibility(View.VISIBLE);
        cue1.setVisibility(View.VISIBLE);
        cue2.setVisibility(View.VISIBLE);
        cue1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cues == 0) {
                    cues = 1;
                    v.setBackgroundColor(Color.RED);
                }
                else if (cues == 2) {
                    cues = 1;
                    cue2.setBackgroundColor(Color.WHITE);
                    v.setBackgroundColor(Color.RED);
                } else {
                    cues = 0;
                    v.setBackgroundColor(Color.WHITE);
                }
            }
        });
        cue2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cues == 0) {
                    cues = 2;
                    v.setBackgroundColor(Color.RED);
                }
                else if (cues == 1) {
                    cues = 2;
                    cue1.setBackgroundColor(Color.WHITE);
                    v.setBackgroundColor(Color.RED);
                }
                else {
                    cues = 0;
                    v.setBackgroundColor(Color.WHITE);
                }
            }
        });
        String text = "";
		answers = new ArrayList<Integer>();
		for (int i = 0; i < numbers.size(); ++i)
		{
			text = text + Integer.toString(numbers.get(i)) + "-";
		}
		for (int i = numbers.size() - 1; i >= 0; --i)
		{
			//digits.get(numbers.size() - 1 - i).Init(numbers.get(i));
			answers.add(numbers.get(i));
		}
		for (int i = 0; i < 10; ++i)
			digits.get(i).Dis();
		//for (int i = numbers.size(); i < 10; ++i)
		//	digits.get(i).Dis();
		view.setText(text);
	}

}
