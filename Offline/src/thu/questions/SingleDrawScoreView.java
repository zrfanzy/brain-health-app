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

public class SingleDrawScoreView extends LinearLayout {
    TextView findings;
    Button btnA, btnB, btnC1, btnC2;

    int btn1, btn2, btn3, btn4;

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
	public SingleDrawScoreView(final Context context, final AttributeSet attrSet) {
		super(context, attrSet);
		instantiate(context, null);
	}

	public SingleDrawScoreView(final Context context) {
		super(context);
		Context mContext = context;

        LayoutParams marginLayout = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        marginLayout.setMargins(2, 2, 2, 2);

        LinearLayout viewLayout = new LinearLayout(mContext);
        viewLayout.setOrientation(LinearLayout.HORIZONTAL);
        viewLayout.setBackgroundColor(Color.BLACK);

        marginLayout = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        marginLayout.setMargins(2, 2, 2, 2);

        findings = new EditText(context);
        findings.setWidth(500);
        findings.setText("Draws right to left");
        findings.setLayoutParams(marginLayout);
        findings.setTextSize(10);
        findings.setBackgroundColor(Color.WHITE);

        btnA = new Button(context);
        btnA.setWidth(210);
        btnA.setBackgroundColor(Color.WHITE);
        btn1 = 0;
        btnA.setLayoutParams(marginLayout);
        btnA.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (btn1 == 1) {
                    v.setBackgroundColor(Color.WHITE);
                    btn1 = 0;
                } else {
                    v.setBackgroundColor(Color.GREEN);
                    btn1 = 1;
                }
            }
        });

        btnB = new Button(context);
        btnB.setWidth(210);
        btnB.setBackgroundColor(Color.WHITE);
        btn2 = 0;
        btnB.setLayoutParams(marginLayout);
        btnB.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (btn2 == 1) {
                    v.setBackgroundColor(Color.WHITE);
                    btn2 = 0;
                } else {
                    v.setBackgroundColor(Color.GREEN);
                    btn2 = 1;
                }
            }
        });

        btnC1 = new Button(context);
        btnC1.setWidth(210);
        btnC1.setBackgroundColor(Color.WHITE);
        btn3 = 0;
        btnC1.setLayoutParams(marginLayout);
        btnC1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (btn3 == 1) {
                    v.setBackgroundColor(Color.WHITE);
                    btn3 = 0;
                } else {
                    v.setBackgroundColor(Color.GREEN);
                    btn3 = 1;
                }
            }
        });

        btnC2 = new Button(context);
        btnC2.setWidth(210);
        btnC2.setBackgroundColor(Color.WHITE);
        btnC2.setLayoutParams(marginLayout);
        btn4 = 0;
        btnC1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (btn4 == 1) {
                    v.setBackgroundColor(Color.WHITE);
                    btn4 = 0;
                } else {
                    v.setBackgroundColor(Color.GREEN);
                    btn4 = 1;
                }
            }
        });

        viewLayout.addView(findings);
        viewLayout.addView(btnA);
        viewLayout.addView(btnB);
        viewLayout.addView(btnC1);
        viewLayout.addView(btnC2);

        addView(viewLayout);
		return;
	}

    public SingleDrawScoreView(final Context context, String title) {
        super(context);
        Context mContext = context;

        LayoutParams marginLayout = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        marginLayout.setMargins(2, 2, 2, 2);

        LinearLayout viewLayout = new LinearLayout(mContext);
        viewLayout.setOrientation(LinearLayout.HORIZONTAL);
        viewLayout.setBackgroundColor(Color.BLACK);

        marginLayout = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        marginLayout.setMargins(2, 2, 2, 2);

        findings = new EditText(context);
        findings.setWidth(500);
        findings.setText(title);
        findings.setLayoutParams(marginLayout);
        findings.setTextSize(10);
        findings.setBackgroundColor(Color.WHITE);

        btnA = new Button(context);
        btnA.setWidth(210);
        btnA.setBackgroundColor(Color.WHITE);
        btn1 = 0;
        btnA.setLayoutParams(marginLayout);
        btnA.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (btn1 == 1) {
                    v.setBackgroundColor(Color.WHITE);
                    btn1 = 0;
                } else {
                    v.setBackgroundColor(Color.GREEN);
                    btn1 = 1;
                }
            }
        });

        btnB = new Button(context);
        btnB.setWidth(210);
        btnB.setBackgroundColor(Color.WHITE);
        btn2 = 0;
        btnB.setLayoutParams(marginLayout);
        btnB.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (btn2 == 1) {
                    v.setBackgroundColor(Color.WHITE);
                    btn2 = 0;
                } else {
                    v.setBackgroundColor(Color.GREEN);
                    btn2 = 1;
                }
            }
        });

        btnC1 = new Button(context);
        btnC1.setWidth(210);
        btnC1.setBackgroundColor(Color.WHITE);
        btn3 = 0;
        btnC1.setLayoutParams(marginLayout);
        btnC1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (btn3 == 1) {
                    v.setBackgroundColor(Color.WHITE);
                    btn3 = 0;
                } else {
                    v.setBackgroundColor(Color.GREEN);
                    btn3 = 1;
                }
            }
        });

        btnC2 = new Button(context);
        btnC2.setWidth(210);
        btnC2.setBackgroundColor(Color.WHITE);
        btnC2.setLayoutParams(marginLayout);
        btn4 = 0;
        btnC1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (btn4 == 1) {
                    v.setBackgroundColor(Color.WHITE);
                    btn4 = 0;
                } else {
                    v.setBackgroundColor(Color.GREEN);
                    btn4 = 1;
                }
            }
        });

        viewLayout.addView(findings);
        viewLayout.addView(btnA);
        viewLayout.addView(btnB);
        viewLayout.addView(btnC1);
        viewLayout.addView(btnC2);

        addView(viewLayout);
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
	public SingleDrawScoreView(final Context context, final String imageUrl,
                               Handler handler) {
		super(context);
		this.callbackHandler = handler;
		instantiate(context, imageUrl);
	}

	private void instantiate(final Context context, final String imageUrl) {
		Context mContext = context;

        LayoutParams marginLayout = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        marginLayout.setMargins(2, 2, 2, 2);

        LinearLayout viewLayout = new LinearLayout(mContext);
        viewLayout.setOrientation(LinearLayout.HORIZONTAL);
        viewLayout.setBackgroundColor(Color.BLACK);

        marginLayout = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        marginLayout.setMargins(2, 2, 2, 2);

        findings = new EditText(context);
        findings.setWidth(500);
        findings.setText("Draws right to left");
        findings.setLayoutParams(marginLayout);
        findings.setTextSize(10);
        findings.setBackgroundColor(Color.WHITE);

        btnA = new Button(context);
        btnA.setWidth(210);
        btnA.setBackgroundColor(Color.WHITE);
        btn1 = 0;
        btnA.setLayoutParams(marginLayout);
        btnA.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (btn1 == 1) {
                    v.setBackgroundColor(Color.WHITE);
                    btn1 = 0;
                } else {
                    v.setBackgroundColor(Color.GREEN);
                    btn1 = 1;
                }
            }
        });

        btnB = new Button(context);
        btnB.setWidth(210);
        btnB.setBackgroundColor(Color.WHITE);
        btn2 = 0;
        btnB.setLayoutParams(marginLayout);
        btnB.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (btn2 == 1) {
                    v.setBackgroundColor(Color.WHITE);
                    btn2 = 0;
                } else {
                    v.setBackgroundColor(Color.GREEN);
                    btn2 = 1;
                }
            }
        });

        btnC1 = new Button(context);
        btnC1.setWidth(210);
        btnC1.setBackgroundColor(Color.WHITE);
        btn3 = 0;
        btnC1.setLayoutParams(marginLayout);
        btnC1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (btn3 == 1) {
                    v.setBackgroundColor(Color.WHITE);
                    btn3 = 0;
                } else {
                    v.setBackgroundColor(Color.GREEN);
                    btn3 = 1;
                }
            }
        });

        btnC2 = new Button(context);
        btnC2.setWidth(210);
        btnC2.setBackgroundColor(Color.WHITE);
        btnC2.setLayoutParams(marginLayout);
        btn4 = 0;
        btnC2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (btn4 == 1) {
                    v.setBackgroundColor(Color.WHITE);
                    btn4 = 0;
                } else {
                    v.setBackgroundColor(Color.GREEN);
                    btn4 = 1;
                }
            }
        });

        viewLayout.addView(findings);
        viewLayout.addView(btnA);
        viewLayout.addView(btnB);
        viewLayout.addView(btnC1);
        viewLayout.addView(btnC2);

        addView(viewLayout);
		return;
	}

}
