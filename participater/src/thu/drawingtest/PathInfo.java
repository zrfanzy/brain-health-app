package thu.drawingtest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.Log;

public class PathInfo implements Serializable {
	private static final long serialVersionUID = -5568568529548959041L;
	private static final String TAG = "PathInfo";

	class SerPoint implements Serializable {
		private static final long serialVersionUID = -2262755099592284491L;

		private float x;
		private float y;

		public SerPoint(float x, float y) {
			this.x = x;
			this.y = y;
		}
	}
	
	class SerPath implements Serializable {
		private static final long serialVersionUID = -900016536427010833L;
		private int mColor = Color.RED;
		private List<SerPoint> mPoints = new ArrayList<SerPoint>();
	}
	
	List<SerPath> mSerPaths = new ArrayList<PathInfo.SerPath>();
	
	private SerPath mCurPath;
	public void lineStart(float x, float y) {
		mCurPath = new SerPath();
		mCurPath.mPoints.add(new SerPoint(x, y));
	}
	public void lineMove(float x, float y) {
		mCurPath.mPoints.add(new SerPoint(x, y));
	}
	public void lineEnd(float x, float y, int color) {
		mCurPath.mPoints.add(new SerPoint(x, y));
		mCurPath.mColor = color;
		mSerPaths.add(mCurPath);
	}
	
	
	private Paint transferPaint(SerPath sp) {
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(3);
		paint.setColor(sp.mColor);
		return paint;
	}
	private Path transferPath(SerPath sp) {
		Path path = new Path();
		SerPoint p;
		int size = sp.mPoints.size();
		
		if (size < 3) {
			return path;
		}
		
		p = sp.mPoints.get(0);
		path.moveTo(p.x, p.y);
		
		float ox = p.x;
		float oy = p.y;
		
		for (int i = 1; i < size-1; i++) {
			p = sp.mPoints.get(i);
			path.quadTo(ox, oy, (ox + p.x)/2, (oy + p.y)/2);
			ox = p.x;
			oy = p.y;
		}
		
		p = sp.mPoints.get(size-1);
		path.lineTo(p.x, p.y);
		
		return path;
	}
	public List<PathAndPaint> transfer() {
		List<PathAndPaint> pps = new ArrayList<PathAndPaint>();
//		Log.i(TAG, "mSerPaths.size() = " + mSerPaths.size());
		for (SerPath sp : mSerPaths) {
			Paint paint = transferPaint(sp);
			Path path = transferPath(sp);
			pps.add(new PathAndPaint(path, paint));
		}
		return pps;
	}

	public static PathInfo load(String mSavePath) {
		PathInfo pi = null;
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(new FileInputStream(mSavePath));
			pi = (PathInfo)ois.readObject();
			Log.i(TAG, "load ok, size = " + pi.mSerPaths.size());
		} catch (StreamCorruptedException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (ois != null) {
				try {
					ois.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				ois = null;
			}
//			if (pi == null) {
//				pi = new PathInfo();
//			}
		}
		return pi;
	}
	
	public boolean save(String mSavePath) {
		ObjectOutputStream oos = null;
		boolean ret = true;
		try {
			oos = new ObjectOutputStream(new FileOutputStream(mSavePath));
			oos.writeObject(this);
			Log.i(TAG, "save ok, size = " + mSerPaths.size());
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			ret = false;
		} catch (IOException e) {
			e.printStackTrace();
			ret = false;
		} finally {
			if (oos != null) {
				try {
					oos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				oos = null;
			}	
		}
		return ret;
	}
	
	public void clean(String mSavePath) {
		File f = new File(mSavePath);
		if (f.exists()) {
			f.delete();
		}
		mSerPaths = new ArrayList<PathInfo.SerPath>();
	}
}
