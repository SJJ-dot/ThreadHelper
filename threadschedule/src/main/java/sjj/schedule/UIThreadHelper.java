package sjj.schedule;

import android.util.Log;

/**
 * Created by SJJ on 2017/3/25.
 */

class UIThreadHelper<T> extends ThreadHelper<T> {
    private final Task task;

    UIThreadHelper(ThreadHelper helper, Task task) {
        super(helper);
        this.task = task;
    }

    @Override
    void run(final Object o) {
        Pool.post(new Runnable() {
            @Override
            public void run() {
                UIThreadHelper.super.run(task.run(o));
            }
        });
    }
}
