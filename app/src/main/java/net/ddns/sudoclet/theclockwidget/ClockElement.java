package net.ddns.sudoclet.theclockwidget;

/**
 * Created by win8p on 7/21/2016.
 */
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class ClockElement {
    Paint paint;
    CustomClockPoint innerPoint, outerPoint;
    float innerRadius;
    float outerRadius;

    public ClockElement() {
        super();
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(12);
        paint.setColor(Color.argb(255, 255, 255, 255));
    }

    public void drawElement(Canvas clockCanvas, float canvasSize, int hour, int minute, int second) {
    }

    CustomClockPoint bitmapCoordAtAngleAndRadius(float radius, float argument, float canvasSize) {
        double xCoordOrig = Math.cos(argument) * radius;
        double yCoordOrig = Math.sin(argument) * radius;

        // Flip clock about the line y = x
        double xCoordFlipped = yCoordOrig;
        double yCoordFlipped = xCoordOrig;

        // Flip clock about the line y = 0
        yCoordFlipped *= -1;

        // Translate to center of Bitmap
        double xCoord = xCoordFlipped + canvasSize / 2;
        double yCoord = yCoordFlipped + canvasSize / 2;

        return (new CustomClockPoint((float) xCoord, (float) yCoord));
    }
}
