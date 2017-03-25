package sjj.schedule;

import android.util.Log;

/**
 * Created by SJJ on 2017/3/25.
 */

class BGThreadHelper<T> extends ThreadHelper<T> {
    private Task task;

    public BGThreadHelper() {
    }

    BGThreadHelper(ThreadHelper threadHelper, Task task) {
        super(threadHelper);
        this.task = task;
    }

    @Override
    void run(final Object o) {
        Pool.run(new Runnable() {
            @Override
            public void run() {
                BGThreadHelper.super.run(task.run(o));
            }
        });
    }
}
