package sjj.schedule.helper;

import sjj.schedule.OnErrorListener;
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
    void execute(final Object o, final OnErrorListener listener) {
        if (enable) {
            runnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        Object run = task.run(disposable,o);
                        ThreadHelper next = UIThreadHelper.this.next;
                        if (next != null) {
                            next.execute(run,listener);
                        }
                    } catch (Throwable e) {
                        if (listener != null) {
                            listener.onError(e);
                        }
                    }
                }
            };
            Pool.post(runnable);
        }

    }

    @Override
    protected void stop() {
        if (runnable != null)
            Pool.cancel(runnable);
    }
}
