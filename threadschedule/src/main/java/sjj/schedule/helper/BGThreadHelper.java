package sjj.schedule.helper;

import java.util.concurrent.Future;

import sjj.schedule.OnErrorListener;
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
    void execute(final Object object, final OnErrorListener listener) {
        future = Pool.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    Object o1 = task.run(disposable,object);
                    ThreadHelper next = BGThreadHelper.this.next;
                    if (next != null) {
                        next.execute(o1,listener);
                    }
                } catch (final Exception e) {
                    if (listener != null) {
                        Pool.post(new Runnable() {
                            @Override
                            public void run() {
                                listener.onError(e);
                            }
                        });
                    }
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
