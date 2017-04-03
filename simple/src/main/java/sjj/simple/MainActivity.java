package sjj.simple;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import sjj.alog.Log;
import sjj.schedule.Disposable;
import sjj.schedule.Task;
import sjj.schedule.helper.ThreadHelper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new ThreadHelper<>("aa").post(new Task<Integer, String>() {
            @Override
            public Integer run(Disposable disposable, String s) {
                Log.e("-1-1-------------"+s+" ========= "+Thread.currentThread().getName());
                return 1234;
            }
        }).post(new Task<String, Integer>() {
            @Override
            public String run(Disposable disposable, Integer integer) {
                disposable.stop();
                Log.e("-1-2------------"+integer+" ======== "+Thread.currentThread().getName());
                return 12345+"str";
            }
        }).ui(new Task<String, String>() {
            @Override
            public String run(Disposable disposable, String s) {
                Log.e("-1-3------------"+s+" ======== "+Thread.currentThread().getName());
                return "ui next";
            }
        }).post(new Task<Object, String>() {
            @Override
            public Object run(Disposable disposable, String s) {
                Log.e("-1-4------------"+s+" ======== "+Thread.currentThread().getName());
                return null;
            }
        }).run();

        Disposable disposable = new ThreadHelper<>("aa2").post(new Task<Integer, String>() {
            @Override
            public Integer run(Disposable disposable, String s) {
                Log.e("-2-1-------------" + s + " ========= " + Thread.currentThread().getName());
                return 1234;
            }
        }).post(new Task<String, Integer>() {
            @Override
            public String run(Disposable disposable, Integer integer) {
                Log.e("-2-2------------" + integer + " ======== " + Thread.currentThread().getName());
                return 12345 + "str";
            }
        }).ui(new Task<String, String>() {
            @Override
            public String run(Disposable disposable, String s) {
                disposable.stop();
                Log.e("-2-3------------" + s + " ======= " + Thread.currentThread().getName());
                return "ui next";
            }
        }).post(new Task<Object, String>() {
            @Override
            public Object run(Disposable disposable, String s) {
                Log.e("-2-4------------" + s + " ======== " + Thread.currentThread().getName());
                return null;
            }
        }).run();
    }
}
