package sjj.schedule;


import android.content.res.ObbInfo;
import android.util.Log;

/**
 * Created by SJJ on 2017/3/12.
 */

public class ThreadHelper<T> {
    private ThreadHelper<T> originalHelper;
    private ThreadHelper next;
    private T object;

    public ThreadHelper() {
    }

    public ThreadHelper(T object) {
        this.object = object;
    }

    ThreadHelper(ThreadHelper<T> threadHelper) {
        originalHelper = threadHelper;
    }

    public <R> ThreadHelper<R> post(Task<R,T> task) {
        BGThreadHelper<R> helper = new BGThreadHelper<>(this, task);
        next = helper;
        return helper;
    }

    public <R> ThreadHelper<R> ui(Task<R,T> task) {
        UIThreadHelper<R> helper = new UIThreadHelper<R>(this, task);
        next = helper;
        return helper;
    }

    private ThreadHelper<T> getOriginalHelper() {
        if (originalHelper != null) {
            return originalHelper.getOriginalHelper();
        }
        return this;
    }

    private ThreadHelper next() {
        return next;
    }

    void run(Object o) {
        if (next() != null) {
            next().run(o);
        }
    }

    public void run() {
        ThreadHelper originalHelper = getOriginalHelper();
        originalHelper.run(originalHelper.object);
    }
}
