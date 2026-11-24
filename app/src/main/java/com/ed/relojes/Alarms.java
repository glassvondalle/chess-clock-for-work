package com.ed.relojes;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

final class Alarms {
    static final String CHANNEL = "alertas_relojes";
    static final int[] IDS = {1, 2, 3, 4};

    private Alarms() {}

    static void createChannel(Context ctx) {
        NotificationManager nm = ctx.getSystemService(NotificationManager.class);
        if (nm == null || nm.getNotificationChannel(CHANNEL) != null) return;
        NotificationChannel ch = new NotificationChannel(CHANNEL, "Alertas de relojes", NotificationManager.IMPORTANCE_HIGH);
        ch.setDescription("Avisos 15 s antes y al terminar");
        ch.enableVibration(true);
        ch.setVibrationPattern(new long[]{0, 600, 200, 600, 200, 600});
        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        if (sound == null) sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        AudioAttributes aa = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_ALARM)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        ch.setSound(sound, aa);
        nm.createNotificationChannel(ch);
    }

    private static PendingIntent pending(Context ctx, int id, String title, String text, int flags) {
        Intent i = new Intent(ctx, AlarmReceiver.class);
        if (title != null) {
            i.putExtra("id", id);
            i.putExtra("title", title);
            i.putExtra("text", text);
        }
        return PendingIntent.getBroadcast(ctx, id, i, flags | PendingIntent.FLAG_IMMUTABLE);
    }

    static void schedule(Context ctx, int id, long atMs, String title, String text) {
        AlarmManager am = ctx.getSystemService(AlarmManager.class);
        if (am == null) return;
        PendingIntent pi = pending(ctx, id, title, text, PendingIntent.FLAG_UPDATE_CURRENT);
        if (Build.VERSION.SDK_INT >= 31 && !am.canScheduleExactAlarms()) {
            am.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, atMs, pi);
        } else {
            am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, atMs, pi);
        }
    }

    static void cancelAll(Context ctx) {
        AlarmManager am = ctx.getSystemService(AlarmManager.class);
        if (am == null) return;
        for (int id : IDS) {
            PendingIntent pi = pending(ctx, id, null, null, PendingIntent.FLAG_NO_CREATE);
            if (pi != null) { am.cancel(pi); pi.cancel(); }
        }
    }
}
