package thu.questions;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import osshelper.Helper;

public class StoryScoreView extends LinearLayout {
	List<SingleStoryScoreView> scoreView;

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
	public StoryScoreView(final Context context, final AttributeSet attrSet) {
		super(context, attrSet);
		instantiate(context, null);
	}

	public StoryScoreView(final Context context) {
		super(context);
		Context mContext = context;

        LinearLayout myLayout = new LinearLayout(context);
        myLayout.setOrientation(VERTICAL);

        scoreView = new ArrayList<SingleStoryScoreView>();
        scoreView.add(new SingleStoryScoreView(context, "Anna Thompson"));
        scoreView.add(new SingleStoryScoreView(context, "of South"));
        scoreView.add(new SingleStoryScoreView(context, "Boston"));
        scoreView.add(new SingleStoryScoreView(context, "Employed"));
        scoreView.add(new SingleStoryScoreView(context, "as a scrub woman"));
        scoreView.add(new SingleStoryScoreView(context, "in an office building"));

        for (int i = 0; i < scoreView.size(); ++i)
            myLayout.addView(scoreView.get(i));

        addView(myLayout);
		return;
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
	public StoryScoreView(final Context context, final String imageUrl,
                          Handler handler) {
		super(context);
		this.callbackHandler = handler;
		instantiate(context, imageUrl);
	}

	private void instantiate(final Context context, final String imageUrl) {
		Context mContext = context;
        LinearLayout myLayout = new LinearLayout(context);
        myLayout.setOrientation(VERTICAL);

        scoreView = new ArrayList<SingleStoryScoreView>();
        scoreView.add(new SingleStoryScoreView(context, "Anna Thompson"));
        scoreView.add(new SingleStoryScoreView(context, "of South"));
        scoreView.add(new SingleStoryScoreView(context, "Boston"));
        scoreView.add(new SingleStoryScoreView(context, "Employed"));
        scoreView.add(new SingleStoryScoreView(context, "as a scrub woman"));
        scoreView.add(new SingleStoryScoreView(context, "in an office building"));
        scoreView.add(new SingleStoryScoreView(context, "Reported"));
        scoreView.add(new SingleStoryScoreView(context, "at the City Hall"));
        scoreView.add(new SingleStoryScoreView(context, "Station"));
        scoreView.add(new SingleStoryScoreView(context, "That she had been held up"));
        scoreView.add(new SingleStoryScoreView(context, "on State Street"));
        scoreView.add(new SingleStoryScoreView(context, "the night before"));
        scoreView.add(new SingleStoryScoreView(context, "and robbed"));
        scoreView.add(new SingleStoryScoreView(context, "of fifteen dollars"));
        scoreView.add(new SingleStoryScoreView(context, "She had four"));
        scoreView.add(new SingleStoryScoreView(context, "little children"));
        scoreView.add(new SingleStoryScoreView(context, "the rent"));
        scoreView.add(new SingleStoryScoreView(context, "was due"));
        scoreView.add(new SingleStoryScoreView(context, "and they had not eaten"));
        scoreView.add(new SingleStoryScoreView(context, "for two days"));
        scoreView.add(new SingleStoryScoreView(context, "The officers"));
        scoreView.add(new SingleStoryScoreView(context, "touched by the woman’s story"));
        scoreView.add(new SingleStoryScoreView(context, "made up a purse"));
        scoreView.add(new SingleStoryScoreView(context, "for her"));

        for (int i = 0; i < scoreView.size(); ++i)
            myLayout.addView(scoreView.get(i));

        addView(myLayout);
		return;
	}

}
