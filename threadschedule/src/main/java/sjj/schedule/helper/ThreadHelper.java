package sjj.schedule.helper;


import sjj.schedule.Disposable;
import sjj.schedule.OnErrorListener;
import sjj.schedule.Task;

/**
 * Created by SJJ on 2017/3/12.
 */

public class ThreadHelper<T> {
    private ThreadHelper<T> originalHelper;
    protected ThreadHelper next;
    private T object;
    protected boolean enable = true;

    public ThreadHelper() {
    }

    public ThreadHelper(T object) {
        this.object = object;
    }

    ThreadHelper(ThreadHelper<T> threadHelper) {
        originalHelper = threadHelper;
        threadHelper.next = this;
    }

    public <R> ThreadHelper<R> submit(Task<R, T> task) {
        return new BGThreadHelper<>(this, task);
    }

    public <R> ThreadHelper<R> post(Task<R, T> task) {
        return new UIThreadHelper<>(this, task);
    }

    private ThreadHelper<T> getOriginalHelper() {
        if (originalHelper != null) {
            return originalHelper.getOriginalHelper();
        }
        return this;
    }


    void execute(Object object, OnErrorListener listener) {
        ThreadHelper next = this.next;
        if (next != null && enable) {
            next.execute(object, listener);
        }
    }

    public Disposable run(OnErrorListener listener) {
        ThreadHelper originalHelper = getOriginalHelper();
        originalHelper.execute(originalHelper.object,listener);
        return disposable;
    }
    public Disposable run() {
        return run(null);
    }

    protected void stop() {

    }
    private void interrupt(ThreadHelper helper) {
        if (helper == null) {
            return;
        }
        helper.next = null;
        helper.enable = false;
        helper.stop();
        interrupt(helper.originalHelper);
    }
    protected Disposable disposable = new Disposable() {
        @Override
        public void stop() {
            interrupt(ThreadHelper.this);
        }
    };
}
