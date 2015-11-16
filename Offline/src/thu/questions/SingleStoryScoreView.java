package thu.questions;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import osshelper.Helper;

public class SingleStoryScoreView extends LinearLayout {
	public int vClick, pClick;


    TextView keyWord;
    EditText paraphraseEdit, psvB, psvA;
    Button vBtn, pBtn;

    private String pair1, pair2;
    public int wordEditType;

	private EditText pair;
	public EditText text;
	public Button easyScore, hardScore, palBtn, relatedBtn, notRelBtn, noGuessBtn, perseyBtn, repeatBtn;

    private List<String> words;
    private List<Integer> wordType;

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
	public SingleStoryScoreView(final Context context, final AttributeSet attrSet) {
		super(context, attrSet);
		instantiate(context, null);
	}

	public void InitWordList(Helper helper) {
        helper.Init();
        words = new ArrayList<String>();
        wordType = new ArrayList<Integer>();
        try {
            String maps = helper.GetObject("00_Config/" + pair1 + ".txt");
            String[] IDmaps = maps.split("\n");
            for (int i = 0; i < IDmaps.length; ++i) {
                if (IDmaps.length == 0) continue;
                String[] IDs = IDmaps[i].split(" ");
                wordType.add(Integer.parseInt(IDs[1]));
                words.add(IDs[0]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	public void Init(int score, String mpair1, String mpair2, Helper helper) {

	}

    public void Save(Helper helper) {
        // TODO
    }

	public SingleStoryScoreView(final Context context) {
		super(context);
		Context mContext = context;

        LinearLayout mLayout = new LinearLayout(mContext);
        mLayout.setOrientation(LinearLayout.VERTICAL);

        LayoutParams marginLayout = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        marginLayout.setMargins(2, 2, 2, 2);

        LinearLayout viewLayout = new LinearLayout(mContext);
        viewLayout.setOrientation(LinearLayout.HORIZONTAL);
        viewLayout.setBackgroundColor(Color.BLACK);

        keyWord = new EditText(context);
        keyWord.setWidth(160);
        keyWord.setText("Anna Thompson");
        keyWord.setLayoutParams(marginLayout);
        keyWord.setTextSize(10);
        keyWord.setBackgroundColor(Color.WHITE);

        vClick = 0;
        pClick = 0;

        vBtn = new Button(context);
        vBtn.setWidth(50);
        vBtn.setText("2");
        vBtn.setTextSize(10);
        vBtn.setLayoutParams(marginLayout);
        vBtn.setBackgroundColor(Color.LTGRAY);
        vBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (vClick == 1) {
                    v.setBackgroundColor(Color.LTGRAY);
                    vClick = 0;
                } else {
                    v.setBackgroundColor(Color.GREEN);
                    vClick = 1;
                }
            }
        });

        pBtn = new Button(context);
        pBtn.setWidth(50);
        pBtn.setText("1");
        pBtn.setTextSize(10);
        pBtn.setLayoutParams(marginLayout);
        pBtn.setBackgroundColor(Color.LTGRAY);
        pBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (pClick == 1) {
                    v.setBackgroundColor(Color.LTGRAY);
                    pClick = 0;
                } else {
                    v.setBackgroundColor(Color.GREEN);
                    pClick = 1;
                }
            }
        });

        paraphraseEdit = new EditText(context);
        paraphraseEdit.setWidth(220);
        paraphraseEdit.setLayoutParams(marginLayout);
        paraphraseEdit.setBackgroundColor(Color.WHITE);
        paraphraseEdit.setTextSize(10);

        psvB = new EditText(context);
        psvB.setWidth(50);
        psvB.setTextSize(10);
        psvB.setLayoutParams(marginLayout);
        psvB.setBackgroundColor(Color.WHITE);

        psvA = new EditText(context);
        psvA.setWidth(50);
        psvA.setTextSize(10);
        psvA.setLayoutParams(marginLayout);
        psvA.setBackgroundColor(Color.WHITE);

        viewLayout.addView(keyWord);
        viewLayout.addView(vBtn);
        viewLayout.addView(paraphraseEdit);
        viewLayout.addView(pBtn);
        viewLayout.addView(psvA);
        viewLayout.addView(psvB);

        mLayout.addView(viewLayout);
        addView(mLayout);
		return;
	}

    public SingleStoryScoreView(final Context context, String title) {
        super(context);
        Context mContext = context;

        LinearLayout mLayout = new LinearLayout(mContext);
        mLayout.setOrientation(LinearLayout.VERTICAL);

        LayoutParams marginLayout = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        marginLayout.setMargins(2, 2, 2, 2);

        LinearLayout viewLayout = new LinearLayout(mContext);
        viewLayout.setOrientation(LinearLayout.HORIZONTAL);
        viewLayout.setBackgroundColor(Color.BLACK);

        marginLayout = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        marginLayout.setMargins(2, 2, 2, 2);

        keyWord = new EditText(context);
        keyWord.setWidth(300);
        keyWord.setText(title);
        keyWord.setLayoutParams(marginLayout);
        keyWord.setTextSize(10);
        keyWord.setBackgroundColor(Color.WHITE);

        vClick = 0;
        pClick = 0;

        vBtn = new Button(context);
        vBtn.setWidth(60);
        vBtn.setText("2");
        vBtn.setTextSize(10);
        vBtn.setLayoutParams(marginLayout);
        vBtn.setBackgroundColor(Color.LTGRAY);
        vBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (vClick == 1) {
                    v.setBackgroundColor(Color.LTGRAY);
                    vClick = 0;
                } else {
                    v.setBackgroundColor(Color.GREEN);
                    vClick = 1;
                }
            }
        });

        pBtn = new Button(context);
        pBtn.setWidth(60);
        pBtn.setText("1");
        pBtn.setTextSize(10);
        pBtn.setLayoutParams(marginLayout);
        pBtn.setBackgroundColor(Color.LTGRAY);
        pBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (pClick == 1) {
                    v.setBackgroundColor(Color.LTGRAY);
                    pClick = 0;
                } else {
                    v.setBackgroundColor(Color.GREEN);
                    pClick = 1;
                }
            }
        });

        paraphraseEdit = new EditText(context);
        paraphraseEdit.setWidth(330);
        paraphraseEdit.setLayoutParams(marginLayout);
        paraphraseEdit.setBackgroundColor(Color.WHITE);
        paraphraseEdit.setTextSize(10);

        psvB = new EditText(context);
        psvB.setWidth(60);
        psvB.setTextSize(10);
        psvB.setLayoutParams(marginLayout);
        psvB.setBackgroundColor(Color.WHITE);

        psvA = new EditText(context);
        psvA.setWidth(60);
        psvA.setTextSize(10);
        psvA.setLayoutParams(marginLayout);
        psvA.setBackgroundColor(Color.WHITE);

        viewLayout.addView(keyWord);
        viewLayout.addView(vBtn);
        viewLayout.addView(paraphraseEdit);
        viewLayout.addView(pBtn);
        viewLayout.addView(psvA);
        viewLayout.addView(psvB);

        mLayout.addView(viewLayout);
        addView(mLayout);
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
	public SingleStoryScoreView(final Context context, final String imageUrl,
                                Handler handler) {
		super(context);
		this.callbackHandler = handler;
		instantiate(context, imageUrl);
	}

	private void instantiate(final Context context, final String imageUrl) {
		Context mContext = context;
        LinearLayout mLayout = new LinearLayout(mContext);
        mLayout.setOrientation(LinearLayout.VERTICAL);

        LayoutParams marginLayout = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        marginLayout.setMargins(2, 2, 2, 2);

        LinearLayout viewLayout = new LinearLayout(mContext);
        viewLayout.setOrientation(LinearLayout.HORIZONTAL);
        viewLayout.setBackgroundColor(Color.BLACK);

        keyWord = new EditText(context);
        keyWord.setWidth(160);
        keyWord.setText("Anna Thompson");
        keyWord.setLayoutParams(marginLayout);
        keyWord.setTextSize(10);
        keyWord.setBackgroundColor(Color.WHITE);

        vClick = 0;
        pClick = 0;

        vBtn = new Button(context);
        vBtn.setWidth(50);
        vBtn.setText("2");
        vBtn.setTextSize(10);
        vBtn.setLayoutParams(marginLayout);
        vBtn.setBackgroundColor(Color.LTGRAY);
        vBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (vClick == 1) {
                    v.setBackgroundColor(Color.LTGRAY);
                    vClick = 0;
                } else {
                    v.setBackgroundColor(Color.GREEN);
                    vClick = 1;
                }
            }
        });

        pBtn = new Button(context);
        pBtn.setWidth(50);
        pBtn.setText("1");
        pBtn.setTextSize(10);
        pBtn.setLayoutParams(marginLayout);
        pBtn.setBackgroundColor(Color.LTGRAY);
        pBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (pClick == 1) {
                    v.setBackgroundColor(Color.LTGRAY);
                    pClick = 0;
                } else {
                    v.setBackgroundColor(Color.GREEN);
                    pClick = 1;
                }
            }
        });

        paraphraseEdit = new EditText(context);
        paraphraseEdit.setWidth(220);
        paraphraseEdit.setLayoutParams(marginLayout);
        paraphraseEdit.setBackgroundColor(Color.WHITE);
        paraphraseEdit.setTextSize(10);

        psvB = new EditText(context);
        psvB.setWidth(50);
        psvB.setTextSize(10);
        psvB.setLayoutParams(marginLayout);
        psvB.setBackgroundColor(Color.WHITE);

        psvA = new EditText(context);
        psvA.setWidth(50);
        psvA.setTextSize(10);
        psvA.setLayoutParams(marginLayout);
        psvA.setBackgroundColor(Color.WHITE);

        viewLayout.addView(keyWord);
        viewLayout.addView(vBtn);
        viewLayout.addView(paraphraseEdit);
        viewLayout.addView(pBtn);
        viewLayout.addView(psvA);
        viewLayout.addView(psvB);

        mLayout.addView(viewLayout);
        addView(mLayout);
		return;
	}

}
