package net.ddns.sudoclet.theclockwidget;

/**
 * Created by win8p on 7/21/2016.
 */
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class MinuteHand extends ClockElement {
    public MinuteHand() {
        super();
        paint.setStrokeWidth(8);
        paint.setColor(Color.argb(255, 255, 255, 255));
        innerRadius = (float) 0.0;
        outerRadius = (float) 0.35;
    }

    @Override
    public void drawElement(Canvas clockCanvas, float canvasSize, int hour, int minute, int second) {
        super.drawElement(clockCanvas, canvasSize, hour, minute, second);
        float minuteAngle = (float) (Math.PI / 30 * minute);
        innerPoint = bitmapCoordAtAngleAndRadius(canvasSize * innerRadius, minuteAngle, canvasSize);
        outerPoint = bitmapCoordAtAngleAndRadius(canvasSize * outerRadius, minuteAngle, canvasSize);
        clockCanvas.drawLine(innerPoint.getX(), innerPoint.getY(), outerPoint.getX(), outerPoint.getY(), paint);
    }
}
