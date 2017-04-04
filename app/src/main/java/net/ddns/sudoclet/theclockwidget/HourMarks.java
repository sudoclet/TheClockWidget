package net.ddns.sudoclet.theclockwidget;

/**
 * Created by win8p on 7/21/2016.
 */
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class HourMarks extends ClockElement {
    public HourMarks() {
        super();
        paint.setStrokeWidth(8);
        paint.setColor(Color.argb(255, 0, 0, 255));
        innerRadius = (float) 0.3;
        outerRadius = (float) 0.4;
    }

    @Override
    public void drawElement(Canvas clockCanvas, float canvasSize, int hour, int minute, int second) {
        super.drawElement(clockCanvas, canvasSize, hour, minute, second);

        for (int h = 0; h < 12; h++) {
            float hourAngle = (float) (Math.PI / 6 * h);
            innerPoint = bitmapCoordAtAngleAndRadius(canvasSize * innerRadius, hourAngle, canvasSize);
            outerPoint = bitmapCoordAtAngleAndRadius(canvasSize * outerRadius, hourAngle, canvasSize);
            clockCanvas.drawLine(innerPoint.getX(), innerPoint.getY(), outerPoint.getX(), outerPoint.getY(), paint);
        }
    }
}
