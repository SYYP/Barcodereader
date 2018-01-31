package qi.saoma.com.barcodereader.renwu;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import qi.saoma.com.barcodereader.R;
import qi.saoma.com.barcodereader.base.BaseActivity;

/**
 * date : ${Date}
 * author:衣鹏宇(ypu)
 */

public class EndmaActivity extends BaseActivity implements View.OnClickListener{
    private ImageView title_callback;
    private TextView title_zhong;
    private ImageView title_imgright;
    private TextView title_right;
    private TextView end_geshu;
    private TextView end_name;
    private TextView end_num;
    private TextView end_price;
    private TextView end_weight;
    private TextView zong_price;
    private TextView look_madan;
    private LinearLayout liner_view;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_endma);
        initView();
    }

    private void initView() {
        title_callback = (ImageView) findViewById(R.id.title_callback);
        title_zhong = (TextView) findViewById(R.id.title_zhong);
        title_imgright = (ImageView) findViewById(R.id.title_imgright);
        title_right = (TextView) findViewById(R.id.title_right);
        end_geshu = (TextView) findViewById(R.id.end_geshu);
        end_name = (TextView) findViewById(R.id.end_name);
        end_num = (TextView) findViewById(R.id.end_num);
        end_price = (TextView) findViewById(R.id.end_price);
        end_weight = (TextView) findViewById(R.id.end_weight);
        zong_price = (TextView) findViewById(R.id.zong_price);
        look_madan = (TextView) findViewById(R.id.look_madan);
        liner_view = (LinearLayout) findViewById(R.id.liner_view);
        title_callback.setOnClickListener(this);
        title_imgright.setVisibility(View.GONE);
        title_zhong.setText("抄码结束");
        title_right.setVisibility(View.VISIBLE);
        title_right.setText("信息发送");
        title_right.setOnClickListener(this);
        look_madan.setOnClickListener(this);
        liner_view.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.liner_view:
                Intent intents=new Intent(this,RenwudetailActivity.class);
                startActivity(intents);
                break;
            case R.id.title_callback:
                finish();
                break;
            case R.id.title_right://信息发送
                 Intent intent=new Intent();
                 intent.setAction("android.intent.action.SENDTO");
                 intent.putExtra("sms_body","你好哦哦");
                 intent.setData(Uri.parse("smsto:"+""));
                 startActivity(intent);

                break;
            case R.id.look_madan://查看码单
                 Intent intent1=new Intent(this,LookmadanActivity.class);
                  startActivity(intent1);
                break;
        }
    }
}
