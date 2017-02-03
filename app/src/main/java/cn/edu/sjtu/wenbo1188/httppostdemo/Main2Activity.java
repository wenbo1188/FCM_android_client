package cn.edu.sjtu.wenbo1188.httppostdemo;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {

    private final String protocol = "http://";
    private final String ip_address = "10.42.0.1:5000";
    private Handler handler = new Handler();
    private TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Intent intent = getIntent();
        String problem_num = intent.getStringExtra("data");
        SendRequestGet(protocol + ip_address + "/distribute/" + problem_num);
    }

    public void SendRequestGet(final String address){
        new Thread(new Runnable() {
            @Override
            public void run() {
                result = (TextView) findViewById(R.id.problem_result);
                //从网络获取数据
                final String response = NetUtils.get(address);
                //向Handler发送处理操作
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //在UI线程更新UI
                        result.setText(response);
                    }
                });
            }
        }).start();
    }
}
