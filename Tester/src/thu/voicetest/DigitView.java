package thu.voicetest;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;

public class DigitView extends LinearLayout {
	int choice = -1;
	int answer = -1;

	List<Button> btns;
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
	public DigitView(final Context context, final AttributeSet attrSet) {
		super(context, attrSet);
		instantiate(context, null);
	}
	
	public int GetChoice(){
		return choice;
	}

	public void Init(int score) {
		answer = score;
        for (int i = 0; i < 10; i++)
        {
            btns.get(i).setEnabled(true);
            btns.get(i).setText(Integer.toString(i));
            //btns.get(i).setText("        ");
            btns.get(i).setTextSize(10);
            btns.get(i).setWidth(60);
        }
		btns.get(answer).setBackgroundColor(Color.GREEN);
	}
	
	public void Dis(){
		for (int i = 0; i < 10; i++)
		{
			btns.get(i).setEnabled(false);
            btns.get(i).setText("      ");
			//btns.get(i).setText("        ");
            btns.get(i).setTextSize(10);
			btns.get(i).setWidth(60);
		}
	}

	public DigitView(final Context context) {
		super(context);
		Context mContext = context;

		TableLayout mytable = new TableLayout(mContext);
		btns = new ArrayList<Button>();
		for (int i = 0; i < 10; ++i)
		{
			Button btn = new Button(mContext);
			btn.setText(Integer.toString(i));
            btn.setWidth(60);
			btn.setTextSize(10);
			btn.setBackgroundColor(Color.LTGRAY);
			btns.add(btn);
			TableRow tableRow = new TableRow(context);
			tableRow.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
			tableRow.addView(btn);
			mytable.addView(tableRow);
		}
		btns.get(0).setOnClickListener(new OnClickListener() {
		@Override
			public void onClick(View v) {
			// TODO Auto-generated method stub
				for (int j = 0; j < 10; ++j)
					if (answer != j)
						btns.get(j).setBackgroundColor(Color.LTGRAY);
				btns.get(answer).setBackgroundColor(Color.GREEN);
				btns.get(0).setBackgroundColor(Color.YELLOW);
				choice = 0;
			}
		});
		btns.get(1).setOnClickListener(new OnClickListener() {
			@Override
				public void onClick(View v) {
				// TODO Auto-generated method stub
					for (int j = 0; j < 10; ++j)
						if (answer != j)
							btns.get(j).setBackgroundColor(Color.LTGRAY);
					btns.get(answer).setBackgroundColor(Color.GREEN);
					btns.get(1).setBackgroundColor(Color.YELLOW);
					choice = 1;
				}
			});
		btns.get(2).setOnClickListener(new OnClickListener() {
			@Override
				public void onClick(View v) {
				// TODO Auto-generated method stub
					for (int j = 0; j < 10; ++j)
						if (answer != j)
							btns.get(j).setBackgroundColor(Color.LTGRAY);
					btns.get(answer).setBackgroundColor(Color.GREEN);
					btns.get(2).setBackgroundColor(Color.YELLOW);
					choice = 2;
				}
			});
		btns.get(3).setOnClickListener(new OnClickListener() {
			@Override
				public void onClick(View v) {
				// TODO Auto-generated method stub
					for (int j = 0; j < 10; ++j)
						if (answer != j)
							btns.get(j).setBackgroundColor(Color.LTGRAY);
					btns.get(answer).setBackgroundColor(Color.GREEN);
					btns.get(3).setBackgroundColor(Color.YELLOW);
					choice = 3;
				}
			});
		btns.get(4).setOnClickListener(new OnClickListener() {
			@Override
				public void onClick(View v) {
				// TODO Auto-generated method stub
					for (int j = 0; j < 10; ++j)
						if (answer != j)
							btns.get(j).setBackgroundColor(Color.LTGRAY);
					btns.get(answer).setBackgroundColor(Color.GREEN);
					btns.get(4).setBackgroundColor(Color.YELLOW);
					choice = 4;
				}
			});
		btns.get(5).setOnClickListener(new OnClickListener() {
			@Override
				public void onClick(View v) {
				// TODO Auto-generated method stub
					for (int j = 0; j < 10; ++j)
						if (answer != j)
							btns.get(j).setBackgroundColor(Color.LTGRAY);
					btns.get(answer).setBackgroundColor(Color.GREEN);
					btns.get(5).setBackgroundColor(Color.YELLOW);choice = 5;
				}
			});
		btns.get(6).setOnClickListener(new OnClickListener() {
			@Override
				public void onClick(View v) {
				// TODO Auto-generated method stub
					for (int j = 0; j < 10; ++j)
						if (answer != j)
							btns.get(j).setBackgroundColor(Color.LTGRAY);
					btns.get(answer).setBackgroundColor(Color.GREEN);
					btns.get(6).setBackgroundColor(Color.YELLOW);
					choice = 6;
				}
			});
		btns.get(7).setOnClickListener(new OnClickListener() {
			@Override
				public void onClick(View v) {
				// TODO Auto-generated method stub
					for (int j = 0; j < 10; ++j)
						if (answer != j)
							btns.get(j).setBackgroundColor(Color.LTGRAY);
					btns.get(answer).setBackgroundColor(Color.GREEN);
					btns.get(7).setBackgroundColor(Color.YELLOW);
					choice = 7;
				}
			});
		btns.get(8).setOnClickListener(new OnClickListener() {
			@Override
				public void onClick(View v) {
				// TODO Auto-generated method stub
					for (int j = 0; j < 10; ++j)
						if (answer != j)
							btns.get(j).setBackgroundColor(Color.LTGRAY);
					btns.get(answer).setBackgroundColor(Color.GREEN);
					btns.get(8).setBackgroundColor(Color.YELLOW);
					choice = 8;
				}
			});
		btns.get(9).setOnClickListener(new OnClickListener() {
			@Override
				public void onClick(View v) {
				// TODO Auto-generated method stub
					for (int j = 0; j < 10; ++j)
						if (answer != j)
							btns.get(j).setBackgroundColor(Color.LTGRAY);
					btns.get(answer).setBackgroundColor(Color.GREEN);
					btns.get(9).setBackgroundColor(Color.YELLOW);
					choice = 9;
				}
			});
		addView(mytable);

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
	public DigitView(final Context context, final String imageUrl,
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

		TableLayout mytable = new TableLayout(mContext);
		btns = new ArrayList<Button>();
		for (int i = 0; i < 10; ++i)
		{
			Button btn = new Button(mContext);
			btn.setText(Integer.toString(i));
            btn.setWidth(60);
			btn.setTextSize(10);
			btn.setBackgroundColor(Color.LTGRAY);
			btns.add(btn);
			TableRow tableRow = new TableRow(context);
			tableRow.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
			tableRow.addView(btn);
			mytable.addView(tableRow);
		}
		btns.get(0).setOnClickListener(new OnClickListener() {
		@Override
			public void onClick(View v) {
			// TODO Auto-generated method stub
				for (int j = 0; j < 10; ++j)
					if (answer != j)
						btns.get(j).setBackgroundColor(Color.LTGRAY);
				btns.get(answer).setBackgroundColor(Color.GREEN);
				btns.get(0).setBackgroundColor(Color.YELLOW);
				choice = 0;
			}
		});
		btns.get(1).setOnClickListener(new OnClickListener() {
			@Override
				public void onClick(View v) {
				// TODO Auto-generated method stub
					for (int j = 0; j < 10; ++j)
						if (answer != j)
							btns.get(j).setBackgroundColor(Color.LTGRAY);
					btns.get(answer).setBackgroundColor(Color.GREEN);
					btns.get(1).setBackgroundColor(Color.YELLOW);
					choice = 1;
				}
			});
		btns.get(2).setOnClickListener(new OnClickListener() {
			@Override
				public void onClick(View v) {
				// TODO Auto-generated method stub
					for (int j = 0; j < 10; ++j)
						if (answer != j)
							btns.get(j).setBackgroundColor(Color.LTGRAY);
					btns.get(answer).setBackgroundColor(Color.GREEN);
					btns.get(2).setBackgroundColor(Color.YELLOW);choice = 2;
				}
			});
		btns.get(3).setOnClickListener(new OnClickListener() {
			@Override
				public void onClick(View v) {
				// TODO Auto-generated method stub
					for (int j = 0; j < 10; ++j)
						if (answer != j)
							btns.get(j).setBackgroundColor(Color.LTGRAY);
					btns.get(answer).setBackgroundColor(Color.GREEN);
					btns.get(3).setBackgroundColor(Color.YELLOW);
					choice = 3;
				}
			});
		btns.get(4).setOnClickListener(new OnClickListener() {
			@Override
				public void onClick(View v) {
				// TODO Auto-generated method stub
					for (int j = 0; j < 10; ++j)
						if (answer != j)
							btns.get(j).setBackgroundColor(Color.LTGRAY);
					btns.get(answer).setBackgroundColor(Color.GREEN);
					btns.get(4).setBackgroundColor(Color.YELLOW);
					choice = 4;
				}
			});
		btns.get(5).setOnClickListener(new OnClickListener() {
			@Override
				public void onClick(View v) {
				// TODO Auto-generated method stub
					for (int j = 0; j < 10; ++j)
						if (answer != j)
							btns.get(j).setBackgroundColor(Color.LTGRAY);
					btns.get(answer).setBackgroundColor(Color.GREEN);
					btns.get(5).setBackgroundColor(Color.YELLOW);choice = 5;
				}
			});
		btns.get(6).setOnClickListener(new OnClickListener() {
			@Override
				public void onClick(View v) {
				// TODO Auto-generated method stub
					for (int j = 0; j < 10; ++j)
						if (answer != j)
							btns.get(j).setBackgroundColor(Color.LTGRAY);
					btns.get(answer).setBackgroundColor(Color.GREEN);
					btns.get(6).setBackgroundColor(Color.YELLOW);
					choice = 6;
				}
			});
		btns.get(7).setOnClickListener(new OnClickListener() {
			@Override
				public void onClick(View v) {
				// TODO Auto-generated method stub
					for (int j = 0; j < 10; ++j)
						if (answer != j)
							btns.get(j).setBackgroundColor(Color.LTGRAY);
					btns.get(answer).setBackgroundColor(Color.GREEN);
					btns.get(7).setBackgroundColor(Color.YELLOW);
					choice = 7;
				}
			});
		btns.get(8).setOnClickListener(new OnClickListener() {
			@Override
				public void onClick(View v) {
				// TODO Auto-generated method stub
					for (int j = 0; j < 10; ++j)
						if (answer != j)
							btns.get(j).setBackgroundColor(Color.LTGRAY);
					btns.get(answer).setBackgroundColor(Color.GREEN);
					btns.get(8).setBackgroundColor(Color.YELLOW);
					choice = 9;
				}
			});
		btns.get(9).setOnClickListener(new OnClickListener() {
			@Override
				public void onClick(View v) {
				// TODO Auto-generated method stub
					for (int j = 0; j < 10; ++j)
						if (answer != j)
							btns.get(j).setBackgroundColor(Color.LTGRAY);
					btns.get(answer).setBackgroundColor(Color.GREEN);
					btns.get(9).setBackgroundColor(Color.YELLOW);
					choice = 9;
				} 
			}); 
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

}
