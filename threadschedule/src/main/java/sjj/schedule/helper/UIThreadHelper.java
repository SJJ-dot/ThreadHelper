package sjj.schedule.helper;

import sjj.schedule.Pool;
import sjj.schedule.Task;

/**
 * Created by SJJ on 2017/3/25.
 */

class UIThreadHelper<T> extends ThreadHelper<T> {
    private final Task task;
    private Runnable runnable;

    UIThreadHelper(ThreadHelper helper, Task task) {
        super(helper);
        this.task = task;
    }

    @Override
    void run(final Object o) {
        if (enable) {
            runnable = new Runnable() {
                @Override
                public void run() {
                    Object run = task.run(UIThreadHelper.this,o);
                    ThreadHelper next = UIThreadHelper.this.next;
                    if (next != null) {
                        next.run(run);
                    }
                }
            };
            Pool.post(runnable);
        }

    }

    @Override
    protected void disable() {
        if (runnable != null)
            Pool.cancel(runnable);
    }
}
