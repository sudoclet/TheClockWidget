package net.ddns.sudoclet.theclockwidget;

/**
 * Created by win8p on 7/21/2016.
 */
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Dial extends ClockElement {
    public Dial() {
        super();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(6);
        paint.setColor(Color.argb(255, 255, 255, 255));
    }

    @Override
    public void drawElement(Canvas clockCanvas, float canvasSize, int hour, int minute, int second) {
        super.drawElement(clockCanvas, canvasSize, hour, minute, second);
        clockCanvas.drawCircle(canvasSize/2, canvasSize/2, (float)(canvasSize*0.45), paint);
    }
}
