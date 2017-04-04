package net.ddns.sudoclet.theclockwidget;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.lang.*;

//import com.broadway.customclock.R;
//import com.broadway.customclock.R.id;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.appwidget.AppWidgetProviderInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.os.Handler;
import android.util.Log;
import android.widget.RemoteViews;

public class TheWidget extends AppWidgetProvider {

    public static Background background;
    public static Dial clockDial;
    public static Face clockFace;
    public static HourMarks hourMarks;
    public static MinuteMarks minuteMarks;
    public static HourHand hourHand;
    public static MinuteHand minuteHand;
    public static SecondHand secondHand;

    public static final String CLOCK_UPDATE = "com.broadway.customclock.CLOCK_UPDATE";
    public static final String SWITCH_COLORS_ACTION = "com.broadway.customclock.SWITCH_COLORS";
    private static final double SECONDS_IN_DAY = 86400.0;
    private static final double TICK = (SECONDS_IN_DAY / 65536.0) * 1000.0;
    private static final int TWELVE_HOUR_STANDARD = 12;
    private static final int TWENTY_FOUR_HOUR_STANDARD = 24;
    private static final int CANVAS_SIZE = 384;

    private static int currentTime = 0;
    private static boolean isFirstCall = true;
    private static int theHour;
    private static int theMinute;
    private static int theSecond;

    private Handler handler;
    private Runnable runnable;

    public TheWidget() {
        super();
        background = new Background();
        clockDial = new Dial();
        clockFace = new Face();
        hourMarks = new HourMarks();
        minuteMarks = new MinuteMarks();
        hourHand = new HourHand();
        minuteHand = new MinuteHand();
        secondHand = new SecondHand();
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        super.onReceive(context, intent);

        // Get the widget manager and ids for this widget provider, then call
        // the shared clock update method.
        ComponentName thisAppWidget = new ComponentName(context.getPackageName(), getClass().getName());
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);

        // Clock Update Event
        if (CLOCK_UPDATE.equals(intent.getAction())) {
            int ids[] = appWidgetManager.getAppWidgetIds(thisAppWidget);
            for (int appWidgetID : ids) {
                updateAppWidget(context, appWidgetManager, appWidgetID);
            }
        }

        // Touch Event
        if (SWITCH_COLORS_ACTION.equals(intent.getAction())) {
            // changeColor();
        }
    }

    private PendingIntent createClockTickIntent(Context context) {
        Intent intent = new Intent(CLOCK_UPDATE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        return pendingIntent;
    }

    private PendingIntent createColorSwitchIntent(Context context) {
        Intent intent = new Intent(SWITCH_COLORS_ACTION);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        return pendingIntent;
    }

    @Override
    public void onDisabled(Context context) {

        super.onDisabled(context);

        // Stop the Timer
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(createClockTickIntent(context));
    }

    @Override
    public void onEnabled(Context context) {

        super.onEnabled(context);

        // Create the Timer
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        alarmManager.setRepeating(AlarmManager.RTC, calendar.getTimeInMillis(), (long) (1000),
                createClockTickIntent(context));
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        // Perform this loop procedure for each App Widget that belongs to this
        // provider
        for (int i = 0; i < appWidgetIds.length; i++) {

            int appWidgetId = appWidgetIds[i];

            // Get the layout for the App Widget and attach an on-click listener
            // to the button
            AppWidgetProviderInfo appInfo = appWidgetManager.getAppWidgetInfo(appWidgetId);
            if (context == null || appInfo == null) {
                return;
            }
            RemoteViews views = new RemoteViews(context.getPackageName(), appInfo.initialLayout);

            // Update The clock label using a shared method
            updateAppWidget(context, appWidgetManager, appWidgetId);

            // Touch Intent
            PendingIntent p = createColorSwitchIntent(context);
            views.setOnClickPendingIntent(R.id.image, p);

            // Tell the AppWidgetManager to perform an update on the current app
            // widget
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }

    public static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {

        Log.d("customclock", "updateAppWidget");

        // Get a reference to our Remote View
        AppWidgetProviderInfo appInfo = appWidgetManager.getAppWidgetInfo(appWidgetId);
        RemoteViews views = new RemoteViews(context.getPackageName(), appInfo.initialLayout);

        // Update clock
        if (isFirstCall) {
            reSyncTime();
            isFirstCall = false;
        } else {
            // To avoid precision errors
            if (currentTime % 64 == 0)
                reSyncTime();
            else
                currentTime++;
        }

        // Construct the Bitmap, Canvas and Paint Objects.
        Bitmap widgetBitmap = Bitmap.createBitmap(CANVAS_SIZE, CANVAS_SIZE, Config.ARGB_8888);
        Canvas widgetCanvas = new Canvas(widgetBitmap);

        // Draw the Wall. (1)

        // Draw Face (2)
        clockFace.drawElement(widgetCanvas, CANVAS_SIZE, theHour, theMinute, theSecond);

        // Draw the Dial. (3)
        // clockDial.drawElement(widgetCanvas, CANVAS_SIZE, theHour, theMinute,
        // theSecond);

        // Draw Hour Marks. (4)
        hourMarks.drawElement(widgetCanvas, CANVAS_SIZE, theHour, theMinute, theSecond);

        // Draw Minute Marks. (5)
        // minuteMarks.drawElement(widgetCanvas, CANVAS_SIZE, theHour,
        // theMinute, theSecond);

        // Draw the Hour Hand. (6)
        hourHand.drawElement(widgetCanvas, CANVAS_SIZE, theHour, theMinute, theSecond);

        // Draw the Minute Hand. (7)
        minuteHand.drawElement(widgetCanvas, CANVAS_SIZE, theHour, theMinute, theSecond);

        // Draw the Second Hand. (8)
        secondHand.drawElement(widgetCanvas, CANVAS_SIZE, theHour, theMinute, theSecond);

        views.setImageViewBitmap(R.id.image, widgetBitmap);
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    private static void reSyncTime() {
        Calendar gCal = new GregorianCalendar();

        theHour = gCal.get(Calendar.HOUR);
        theMinute = gCal.get(Calendar.MINUTE);
        theSecond = gCal.get(Calendar.SECOND);

        // Time today = new Time(Time.getCurrentTimezone());
        // today.setToNow();
        // int hour = today.hour;
        // int minute = today.minute;
        // int second = today.second;
        //
        // double currentSeconds = second + (minute * 60) + (hour * 60 * 60);
        // currentTime = (int) (currentSeconds / (TICK / 1000.0));
    }

}
