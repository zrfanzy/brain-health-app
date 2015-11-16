package thu.drawingtest;

import java.util.ArrayList;
import java.util.List;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

@SuppressLint("ClickableViewAccessibility")
public class Freedrawing extends SurfaceView implements SurfaceHolder.Callback {
	private List<PathAndPaint> _graphics = new ArrayList<PathAndPaint>();
	private PathInfo mPathInfo;
	private Paint mPaint;
	private DrawingThread _thread;
	private Path path;
	public Drawable background;
	
	public void Enable()
	{
		_thread.setRunning(true);
		_thread.start();
	}
	
	public void Disable()
	{
		_thread.setRunning(false);
	}

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
	public Freedrawing(Context context, final AttributeSet attrSet) {
		super(context, attrSet);
		setWillNotDraw(false);
		getHolder().addCallback(this);
		mPaint = new Paint();
		mPaint.setDither(true);
		mPaint.setColor(0xFFFF0000);
		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setStrokeJoin(Paint.Join.ROUND);
		mPaint.setStrokeCap(Paint.Cap.ROUND);
		mPaint.setStrokeWidth(3);
		
		mPathInfo = new PathInfo();
		
		_thread = new DrawingThread(getHolder(), this);
	}
	
	public boolean LoadFromFile(String filePath)
	{
		PathInfo pinfo = PathInfo.load(filePath);
		if(pinfo == null)
			return false;
		
		/* Old stuff*/
		/*mPathInfo = pinfo;
		_graphics = pinfo.transfer();*/
		
		/*  
		 * New stuff*/
		List<PathAndPaint> paths = pinfo.transfer();
		for(int i=0; i<paths.size(); i++)
		{
			if(i == 0)
				_graphics.add(paths.get(0));
			else
			{
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				_graphics.add(paths.get(i));
			}
		}
		Toast.makeText(this.getContext(), "Completed!",
			     Toast.LENGTH_SHORT).show();
		
		
		return true;
	}
	
	public void clearCanvas()
	{
		_graphics.clear();
		mPathInfo = new PathInfo();
		invalidate();
	}
	
	public boolean savePathAsFile(String savedPathFilePath)
	{
		return mPathInfo.save(savedPathFilePath);
	}
	
	public boolean onTouchEvent(MotionEvent event) {
		synchronized (_thread.getSurfaceHolder()) {
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				path = new Path();
				path.moveTo(event.getX(), event.getY());
				path.lineTo(event.getX(), event.getY());
				
				this.mPathInfo.lineStart(event.getX(), event.getY());
				
			} else if (event.getAction() == MotionEvent.ACTION_MOVE) {
				path.lineTo(event.getX(), event.getY());
				this.mPathInfo.lineMove(event.getX(), event.getY());
				
				//if you want to make the drawing in real-time, add the following line, otherwise, remove it.
				_graphics.add(new PathAndPaint(new Path(path), new Paint(mPaint)));
				
			} else if (event.getAction() == MotionEvent.ACTION_UP) {
				path.lineTo(event.getX(), event.getY());
				_graphics.add(new PathAndPaint(new Path(path), new Paint(mPaint)));

				this.mPathInfo.lineEnd(event.getX(), event.getY(), mPaint.getColor());
				
			}

			return true;
		}
	}

	@Override
	public void setBackgroundDrawable(Drawable background) {
		this.background = background;
	}

	@Override
	public void setBackground(Drawable background) {
		this.background = background;
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		super.onLayout(changed, left, top, right, bottom);

		if (background != null)
			background.setBounds(left, top, right, bottom);
	}

	@Override
	public void onDraw(Canvas canvas) {
		//if (!enabled)
		//	return;
		// if( this.background != null)
		// {
		// this.background.draw(canvas);
		// }
//		synchronized(_graphics)
//		{
			for (PathAndPaint path : _graphics) {
				// canvas.drawPoint(graphic.x, graphic.y, mPaint);
				if (canvas != null)
					canvas.drawPath(path.getPath(), path.getPaint());
			}
//		}
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub

	}

	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		//_thread.setRunning(true);
		//_thread.start();
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		boolean retry = true;
		_thread.setRunning(false);
		while (retry) {
			try {
				_thread.join();
				retry = false;
			} catch (InterruptedException e) {
				// we will try it again and again...
			}
		}
	}
}

@SuppressLint("WrongCall")
class DrawingThread extends Thread {
	private SurfaceHolder _surfaceHolder;
	private Freedrawing _panel;
	private boolean _run = false;

	public DrawingThread(SurfaceHolder surfaceHolder, Freedrawing panel) {
		_surfaceHolder = surfaceHolder;
		_panel = panel;
	}

	public void setRunning(boolean run) {
		_run = run;
	}

	public SurfaceHolder getSurfaceHolder() {
		return _surfaceHolder;
	}

	@Override
	public void run() {
		Canvas c;
		while (_run) {
			c = null;
			try {
				c = _surfaceHolder.lockCanvas(null);
				
				if( c!=null )
					c.drawColor(Color.WHITE);
				
				try {
					if (c!=null && _panel.background != null) // draw background
						_panel.background.draw(c);
				} catch (Exception e) {
					Log.println(0, "Drawing Error", e.toString());
				}

				synchronized (_surfaceHolder) {
					_panel.onDraw(c);
				}
			} finally {
				// do this in a finally so that if an exception is thrown
				// during the above, we don't leave the Surface in an
				// inconsistent state
				if (c != null) {
					_surfaceHolder.unlockCanvasAndPost(c);
				}
			}
		}
	}
}