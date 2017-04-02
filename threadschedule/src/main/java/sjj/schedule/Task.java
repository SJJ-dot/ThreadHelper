package sjj.schedule;

/**
 * Created by SJJ on 2017/3/12.
 */

public interface Task<R,P> {
    R run(Disposable disposable, P p);
}
