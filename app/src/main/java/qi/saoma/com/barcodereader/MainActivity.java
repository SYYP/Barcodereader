package qi.saoma.com.barcodereader;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;

import qi.saoma.com.barcodereader.adapter.Mainadapter;
import qi.saoma.com.barcodereader.login.JieShaoActivity;
import qi.saoma.com.barcodereader.renwu.RenwuActivity;
import qi.saoma.com.barcodereader.renwu.RenwudetailActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView text_title;
    private TextView text_date;
    private TextView text_name;
    private RecyclerView recy_view;
    private RecyclerView activity_main;
    private SpringView spring_view;
    private Mainadapter mainadapter;
    private TextView login_sso;
    private TextView text_jianjie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        text_title = (TextView) findViewById(R.id.text_title);
        text_date = (TextView) findViewById(R.id.text_date);
        text_name = (TextView) findViewById(R.id.text_name);
        recy_view = (RecyclerView) findViewById(R.id.recy_view);
        spring_view = (SpringView) findViewById(R.id.spring_view);
        mainadapter = new Mainadapter(this);
        recy_view.setLayoutManager(new LinearLayoutManager(this));
        login_sso = (TextView) findViewById(R.id.login_sso);
        recy_view.setAdapter(mainadapter);
        spring_view.setType(SpringView.Type.FOLLOW);
        login_sso.setOnClickListener(this);
        text_jianjie = (TextView) findViewById(R.id.text_jianjie);
        text_jianjie.setOnClickListener(this);
        text_name.setOnClickListener(this);
        recy_view.setNestedScrollingEnabled(false);
        spring_view.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        mainadapter.notifyDataSetChanged();
                    }
                }, 5000);
                spring_view.onFinishFreshAndLoad();
            }

            @Override
            public void onLoadmore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {


                    }
                }, 5000);
                spring_view.onFinishFreshAndLoad();
            }
        });
       spring_view.setFooter(new DefaultFooter(this));
        spring_view.setHeader(new DefaultHeader(this));
        //点击事件
       mainadapter.setOnItemClickListener(new Mainadapter.OnItemClickListener() {
           @Override
           public void onItemClick(View view, int position) {

                 Intent intent=new Intent(MainActivity.this, RenwudetailActivity.class);
               startActivity(intent);
           }
       });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.login_sso:
                   //新建任务
                Intent intent=new Intent(this, RenwuActivity.class);
                 startActivity(intent);
                break;
            case R.id.text_jianjie:
                Intent intent1=new Intent(this, JieShaoActivity.class);
                  startActivity(intent1);
                break;
            case R.id.text_name:

                //查看更多
                break;
        }
    }
}
