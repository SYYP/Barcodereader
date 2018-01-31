package qi.saoma.com.barcodereader.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.zackratos.ultimatebar.UltimateBar;

import qi.saoma.com.barcodereader.R;
import qi.saoma.com.barcodereader.base.BaseActivity;
import qi.saoma.com.barcodereader.renwu.ShurumaActivity;

/**
 * date : ${Date}
 * author:衣鹏宇(ypu)
 */

public class FiristOneActivity extends BaseActivity implements View.OnClickListener {

    private TextView text_shoudong;
    private TextView text_yuyin;
    private ImageView callback;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UltimateBar ultimateBar = new UltimateBar(this);
        ultimateBar.setImmersionBar();
        setContentView(R.layout.activity_renwuone);
        initView();
    }

    private void initView() {
        text_shoudong = (TextView) findViewById(R.id.text_shoudong);
        text_yuyin = (TextView) findViewById(R.id.text_yuyin);
        callback = (ImageView) findViewById(R.id.callback);
        callback.setOnClickListener(this);
        text_shoudong.setOnClickListener(this);
        text_yuyin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.callback:
                finish();
                break;
            case R.id.text_shoudong://手动输入
                  Intent intent=new Intent(this, ShurumaActivity.class);
                  intent.putExtra("biaoshi","hand");
                  startActivity(intent);
                finish();
                break;
            case R.id.text_yuyin://语音输入
                  Intent intent1=new Intent(this,ShurumaActivity.class);
                  intent1.putExtra("biaoshi","song");
                  startActivity(intent1);
                finish();
                break;
        }
    }
}
