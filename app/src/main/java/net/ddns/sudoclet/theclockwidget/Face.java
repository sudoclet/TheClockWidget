package net.ddns.sudoclet.theclockwidget;

/**
 * Created by win8p on 7/21/2016.
 */
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Face extends ClockElement {
    public Face() {
        super();
        // TODO Auto-generated constructor stub
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.argb(255, 128, 128, 128));
    }

    @Override
    public void drawElement(Canvas clockCanvas, float canvasSize, int hour, int minute, int second) {
        // TODO Auto-generated method stub
        super.drawElement(clockCanvas, canvasSize, hour, minute, second);
        clockCanvas.drawCircle(canvasSize/2, canvasSize/2, (float)(canvasSize*0.45), paint);
    }
}
