package cn.edu.sjtu.wenbo1188.httppostdemo;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    private final String table[][] = {{"Alice", "0101", "6"}, {"Bob", "1101", "9"}, {"Catherina", "1010", "2"},
                                {"Diana", "1111", "3"}, {"Eason", "0111", "9"}, {"Frank", "1011", "4"},
            {"George", "0000", "3"}};
    private String ip_address = "10.42.0.1:5000";
    private String protocol = "http://";
    private Handler handler = new Handler();
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void SendRequestPost(final String address, final String content){
        textView = (TextView) findViewById(R.id.text);
        new Thread(new Runnable() {
            @Override
            public void run() {
                //从网络获取数
                final String response = NetUtils.post(address, content);
                //向Handler发送处理操作
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //在UI线程更新UI
                        textView.setText(response);
                    }
                });
            }
        }).start();
    }

    public void ProblemRequest(View view){
        Intent intent = new Intent(this, Main2Activity.class);
        EditText editText = (EditText) findViewById(R.id.problem_num);
        String problem_num = editText.getText().toString();
        intent.putExtra("data", problem_num);
        startActivity(intent);
    }

    public void AddUser(View view){
        for (int i = 0;i < table.length;++i){
            String address = protocol + ip_address + "/add_user";
            String content = "name=" + table[i][0] + "&address=" + table[i][1] + "&skills=" + table[i][2];
            SendRequestPost(address, content);
        }
    }
}
