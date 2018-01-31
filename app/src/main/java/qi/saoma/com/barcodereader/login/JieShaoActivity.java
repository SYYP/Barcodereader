package qi.saoma.com.barcodereader.login;

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

public class JieShaoActivity extends BaseActivity {
    private ImageView title_callback;
    private TextView title_zhong;
    private ImageView title_imgright;
    private TextView title_right;
    private WebView web_view;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jieshao);
        initView();
    }

    private void initView() {
        title_callback = (ImageView) findViewById(R.id.title_callback);
        title_zhong = (TextView) findViewById(R.id.title_zhong);
        title_imgright = (ImageView) findViewById(R.id.title_imgright);
        title_right = (TextView) findViewById(R.id.title_right);
        web_view = (WebView) findViewById(R.id.web_view);
        title_zhong.setText("软件介绍");
         title_imgright.setVisibility(View.GONE);

          title_callback.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  finish();
              }
          });

    }
}
