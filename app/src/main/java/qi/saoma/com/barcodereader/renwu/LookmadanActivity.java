package qi.saoma.com.barcodereader.renwu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import qi.saoma.com.barcodereader.R;
import qi.saoma.com.barcodereader.base.BaseActivity;

/**
 * date : ${Date}
 * author:衣鹏宇(ypu)
 */

public class LookmadanActivity extends BaseActivity implements View.OnClickListener {

    private ImageView title_callback;
    private TextView title_zhong;
    private ImageView title_imgright;
    private TextView title_right;
    private WebView web_view;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lookmadan);
        initView();

    }

    private void initView() {
        title_callback = (ImageView) findViewById(R.id.title_callback);
        title_zhong = (TextView) findViewById(R.id.title_zhong);
        title_imgright = (ImageView) findViewById(R.id.title_imgright);
        title_right = (TextView) findViewById(R.id.title_right);
        web_view = (WebView) findViewById(R.id.web_view);
        title_zhong.setText("我的码单");
        title_callback.setOnClickListener(this);
        title_right.setVisibility(View.GONE);
        title_imgright.setImageResource(R.drawable.img_whiteshare);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.title_callback:
                finish();
                break;
        }
    }
}
