package sjj.simple;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import sjj.alog.Log;
import sjj.schedule.Disposable;
import sjj.schedule.OnErrorListener;
import sjj.schedule.Task;
import sjj.schedule.helper.ThreadHelper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            new ThreadHelper<>("aa")
                    .submit(new Task<Integer, String>() {
                        @Override
                        public Integer run(Disposable disposable, String s) {
                            Log.e("-1-1-------------" + s + " ====子线程===== " + Thread.currentThread().getName());
                            return 1234;
                        }
                    })
                    .submit(new Task<String, Integer>() {
                        @Override
                        public String run(Disposable disposable, Integer integer) {
                            disposable.stop();
                            Log.e("-1-2------------" + integer + " ====子线程 停止==== " + Thread.currentThread().getName());
                            return 12345 + "str";
                        }
                    })
                    .post(new Task<String, String>() {
                        @Override
                        public String run(Disposable disposable, String s) {
                            Log.e("-1-3------------" + s + " ===主线程===== " + Thread.currentThread().getName());
                            return "ui next";
                        }
                    })
                    .submit(new Task<Object, String>() {
                        @Override
                        public Object run(Disposable disposable, String s) {
                            Log.e("-1-4------------" + s + " ====子线程==== " + Thread.currentThread().getName());
                            return null;
                        }
                    })
                    .run();

            Disposable disposable = new ThreadHelper<>("aa2")
                    .submit(new Task<Integer, String>() {
                        @Override
                        public Integer run(Disposable disposable, String s) {
                            Log.e("-2-1-------------" + s + " ====子线程===== " + Thread.currentThread().getName());
                            return 1234;
                        }
                    })
                    .submit(new Task<String, Integer>() {
                        @Override
                        public String run(Disposable disposable, Integer integer) {
                            Log.e("-2-2------------" + integer + " ====子线程==== " + Thread.currentThread().getName());
                            return 12345 + "str";
                        }
                    })
                    .post(new Task<String, String>() {
                        @Override
                        public String run(Disposable disposable, String s) {
                            disposable.stop();
                            Log.e("-2-3------------" + s + " ===主线程=停止=== " + Thread.currentThread().getName());
                            return "ui next";
                        }
                    })
                    .submit(new Task<Object, String>() {
                        @Override
                        public Object run(Disposable disposable, String s) {
                            Log.e("-2-4------------" + s + " ===子线程===== " + Thread.currentThread().getName());
                            return null;
                        }
                    }).run(new OnErrorListener() {
                        @Override
                        public void onError(Throwable throwable) {
                            Log.e("onError",throwable);
                        }
                    });
            new ThreadHelper<>().submit(new Task<Object, Object>() {
                @Override
                public Object run(Disposable disposable, Object o) {
                    Log.e("3-1");
                    return null;
                }
            }).submit(new Task<Object, Object>() {
                @Override
                public Object run(Disposable disposable, Object o) {
                    Log.e("3-2");
//                    throw new RuntimeException("RuntimeException");
                    return null;
                }
            }).post(new Task<Object, Object>() {
                @Override
                public Object run(Disposable disposable, Object o) {
                    Log.e("3-3");
                    return null;
                }
            }).run(new OnErrorListener() {
                @Override
                public void onError(Throwable throwable) {
                    Log.e("",new RuntimeException(throwable));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
