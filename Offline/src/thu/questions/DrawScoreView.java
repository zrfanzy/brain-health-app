package thu.questions;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class DrawScoreView extends LinearLayout {
	List<SingleDrawScoreView> drawView;

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
	public DrawScoreView(final Context context, final AttributeSet attrSet) {
		super(context, attrSet);
		instantiate(context, null);
	}

	public DrawScoreView(final Context context) {
		super(context);
		Context mContext = context;

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
	public DrawScoreView(final Context context, final String imageUrl,
                         Handler handler) {
		super(context);
		this.callbackHandler = handler;
		instantiate(context, imageUrl);
	}

	private void instantiate(final Context context, final String imageUrl) {
		Context mContext = context;
        LinearLayout myLayout = new LinearLayout(context);
        myLayout.setOrientation(VERTICAL);

        drawView = new ArrayList<SingleDrawScoreView>();
        drawView.add(new SingleDrawScoreView(context, "Draws right to left"));
        drawView.add(new SingleDrawScoreView(context, "Contamination from another VR design"));
        drawView.add(new SingleDrawScoreView(context, "Perseveration"));
        drawView.add(new SingleDrawScoreView(context, "Micrographia"));
        drawView.add(new SingleDrawScoreView(context, "Tremors"));
        drawView.add(new SingleDrawScoreView(context, "Confabulation"));
        drawView.add(new SingleDrawScoreView(context, "Starts to draw before told to begin"));
        drawView.add(new SingleDrawScoreView(context, "No attempt to produce drawing"));
        drawView.add(new SingleDrawScoreView(context, "If no attempt to produce drawing, participant reports “I do not remember” and indicates a specific location for the design."));

        for (int i = 0; i < drawView.size(); ++i)
            myLayout.addView(drawView.get(i));

        addView(myLayout);
		return;
	}

}
