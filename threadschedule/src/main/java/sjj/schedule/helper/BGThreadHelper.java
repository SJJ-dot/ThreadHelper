package sjj.schedule.helper;

import java.util.concurrent.Future;

import sjj.schedule.Pool;
import sjj.schedule.Task;

/**
 * Created by SJJ on 2017/3/25.
 */

class BGThreadHelper<T> extends ThreadHelper<T> {
    private Task task;
    private Future future;


    public BGThreadHelper() {
    }

    BGThreadHelper(ThreadHelper threadHelper, Task task) {
        super(threadHelper);
        this.task = task;
    }

    @Override
    void run(final Object o) {
        future = Pool.run(new Runnable() {
            @Override
            public void run() {
                Object o1 = task.run(disposable,o);
                ThreadHelper next = BGThreadHelper.this.next;
                if (next != null) {
                    next.run(o1);
                }
            }
        });
    }

    @Override
    protected void stop() {
        if (future != null) {
            future.cancel(true);
            future = null;
        }
    }
}
