package sjj.simple;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.Date;

import sjj.schedule.LogUtils;
import sjj.schedule.Task;
import sjj.schedule.ThreadHelper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new ThreadHelper<>("aa").post(new Task<Integer, String>() {
            @Override
            public Integer run(String s) {
                LogUtils.e("---------------"+s+" ========= "+Thread.currentThread().getName());
                return 1234;
            }
        }).post(new Task<String, Integer>() {
            @Override
            public String run(Integer integer) {
                LogUtils.e(integer+" ======== "+Thread.currentThread().getName());
                return 12345+"str";
            }
        }).ui(new Task<String, String>() {
            @Override
            public String run(String s) {
                LogUtils.e(s+" ======== "+Thread.currentThread().getName());
                return "ui next";
            }
        }).post(new Task<Object, String>() {
            @Override
            public Object run(String s) {
                LogUtils.e(s+" ======== "+Thread.currentThread().getName());
                return null;
            }
        }).run();
    }
}
