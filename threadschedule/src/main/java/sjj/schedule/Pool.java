package sjj.schedule;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by SJJ on 2017/3/12.
 */

public class Pool {
    private static ScheduledExecutorService executorService = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors() * 2);
    private static Handler handler = new Handler(Looper.getMainLooper());

    static void post(Runnable runnable) {
        handler.post(runnable);
    }

    static void cancel(Runnable runnable) {
        handler.removeCallbacks(runnable);
    }

    static Future run(Runnable runnable) {
        return executorService.submit(runnable);
    }

}
