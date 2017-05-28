package sjj.schedule;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Created by SJJ on 2017/3/12.
 */

public class Pool {
    private static ScheduledExecutorService executorService = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors() * 2);
    private static Handler handler = new Handler(Looper.getMainLooper());

    public static void post(Runnable runnable) {
        handler.post(runnable);
    }

    public static void cancel(Runnable runnable) {
        handler.removeCallbacks(runnable);
    }

    public static Future submit(Runnable runnable) {
        return executorService.submit(runnable);
    }

    public static ScheduledFuture<?> schedule(Runnable runnable, long delay) {
        return executorService.schedule(runnable, delay, TimeUnit.MILLISECONDS);
    }
    public static ScheduledFuture<?> scheduleWithFixedDelay(Runnable command, long initialDelay, long delay) {
        return executorService.scheduleWithFixedDelay(command,initialDelay,delay,TimeUnit.MILLISECONDS);
    }
}
