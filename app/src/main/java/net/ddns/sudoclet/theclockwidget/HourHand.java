package net.ddns.sudoclet.theclockwidget;

/**
 * Created by win8p on 7/21/2016.
 */
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class HourHand extends ClockElement {
    public HourHand() {
        super();
        paint.setStrokeWidth(9);
        paint.setColor(Color.argb(255, 255, 255, 255));
        innerRadius = (float) 0.0;
        outerRadius = (float) 0.25;
    }

    @Override
    public void drawElement(Canvas clockCanvas, float canvasSize, int hour, int minute, int second) {
        super.drawElement(clockCanvas, canvasSize, hour, minute, second);
        float hourAngle = (float) (Math.PI/6*(hour + (float)minute/60));
        innerPoint = bitmapCoordAtAngleAndRadius(canvasSize * innerRadius, hourAngle, canvasSize);
        outerPoint = bitmapCoordAtAngleAndRadius(canvasSize * outerRadius, hourAngle, canvasSize);
        clockCanvas.drawLine(innerPoint.getX(), innerPoint.getY(), outerPoint.getX(), outerPoint.getY(), paint);
    }
}
