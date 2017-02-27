package cn.edu.sjtu.wenbo1188.httppostdemo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
    private Button button;
    private DrawHookView cycle;
    private String response_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public int SendRequestPost(final String address, final String content){
        new Thread(new Runnable(){
            @Override
            public void run() {
                String response = NetUtils.post(address, content);
                handler.post(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }
        }).start();

        return 200;
    }

    public void ProblemRequest(View view){
        Intent intent = new Intent(this, Main2Activity.class);
        EditText editText = (EditText) findViewById(R.id.problem_num);
        String problem_num = editText.getText().toString();
        intent.putExtra("data", problem_num);
        startActivity(intent);
    }

    public void AddUser(View view){
        int response_code;
        int len = table.length;
        int count = 0;
        for (int i = 0;i < len;++i){
            String address = protocol + ip_address + "/add_user";
            String content = "name=" + table[i][0] + "&address=" + table[i][1] + "&skills=" + table[i][2];
            response_code = SendRequestPost(address, content);
            if (response_code == 200){
                ++count;
            }
        }
        if (count == len){
            button = (Button)findViewById(R.id.add_user);
            cycle = (DrawHookView)findViewById(R.id.cycle);
            button.setBackgroundColor(Color.parseColor("#00FF00"));
            cycle.setVisibility(View.VISIBLE);
        }
        else{
            button = (Button)findViewById(R.id.add_user);
            cycle = (DrawHookView)findViewById(R.id.cycle);
            button.setBackgroundColor(Color.parseColor("#FF0000"));
        }
    }
}