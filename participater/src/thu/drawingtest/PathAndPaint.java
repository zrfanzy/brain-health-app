package thu.drawingtest;
import android.graphics.Paint;
import android.graphics.Path;

public class PathAndPaint {
	private Path mPath;
	private Paint mPaint;
	
	public PathAndPaint(Path path, Paint paint) {
		mPath = path;
		mPaint = paint;
	}
	public Path getPath() {
		return mPath;
	}
	public Paint getPaint() {
		return mPaint;
	}
}