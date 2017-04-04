package net.ddns.sudoclet.theclockwidget;

/**
 * Created by win8p on 7/21/2016.
 */
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class SecondHand extends ClockElement {
    public SecondHand() {
        super();
        paint.setStrokeWidth(7);
        paint.setColor(Color.argb(255, 0, 0, 0));
        innerRadius = (float) 0.0;
        outerRadius = (float) 0.4;
    }

    @Override
    public void drawElement(Canvas clockCanvas, float canvasSize, int hour, int minute, int second) {
        super.drawElement(clockCanvas, canvasSize, hour, minute, second);
        float secondAngle = (float) (second * Math.PI / 30);
        innerPoint = bitmapCoordAtAngleAndRadius(canvasSize * innerRadius, secondAngle, canvasSize);
        outerPoint = bitmapCoordAtAngleAndRadius(canvasSize * outerRadius, secondAngle, canvasSize);
        clockCanvas.drawLine(innerPoint.getX(), innerPoint.getY(), outerPoint.getX(), outerPoint.getY(), paint);
    }
}
