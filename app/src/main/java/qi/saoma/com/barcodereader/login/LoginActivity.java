package qi.saoma.com.barcodereader.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.zackratos.ultimatebar.UltimateBar;

import qi.saoma.com.barcodereader.MainActivity;
import qi.saoma.com.barcodereader.R;
import qi.saoma.com.barcodereader.base.BaseActivity;
import qi.saoma.com.barcodereader.utils.SpUtils;

/**
 * date : ${Date}
 * author:衣鹏宇(ypu)
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private ImageView set_phone;
    private EditText login_phone;
    private ImageView set_lpwd;
    private EditText login_pwd;
    private ImageView look_pwd;
    private TextView forget_pwd;
    private TextView login_sso;
    private TextView register_sso;
    private ImageView img_weixin;
    boolean abool=true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UltimateBar ultimateBar = new UltimateBar(this);
        ultimateBar.setImmersionBar();
     //   MyContants.windows(this);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        set_phone = (ImageView) findViewById(R.id.set_phone);
        login_phone = (EditText) findViewById(R.id.login_phone);
        set_lpwd = (ImageView) findViewById(R.id.set_lpwd);
        login_pwd = (EditText) findViewById(R.id.login_pwd);
        look_pwd = (ImageView) findViewById(R.id.look_pwd);
        forget_pwd = (TextView) findViewById(R.id.forget_pwd);
        login_sso = (TextView) findViewById(R.id.login_sso);
        register_sso = (TextView) findViewById(R.id.register_sso);
        img_weixin = (ImageView) findViewById(R.id.img_weixin);
        register_sso.setOnClickListener(this);
        login_sso.setOnClickListener(this);
        forget_pwd.setOnClickListener(this);
        look_pwd.setOnClickListener(this);
        login_pwd.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD|InputType.TYPE_CLASS_TEXT);
    }

    private void submit() {
        // validate
        String phone = login_phone.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "请输入账号/手机号", Toast.LENGTH_SHORT).show();
            return;
        }

        String pwd = login_pwd.getText().toString().trim();
        if (TextUtils.isEmpty(pwd)) {
            Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
            return;
        }

        // TODO validate success, do something


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.register_sso://注册
                Intent intent=new Intent(this,RegisterActivity.class);
                  startActivity(intent);
                break;
            case R.id.login_sso://登录
                boolean bool = SpUtils.getBoolean(this, "bool", false);
                if(bool){
                    //跳首页
                      Intent intents=new Intent(this, MainActivity.class);
                     startActivity(intents);
                }
                else {
                    Intent intent1 = new Intent(this, FirstrenwuActivity.class);
                    startActivity(intent1);
                    finish();
                }
                break;
            case R.id.forget_pwd:
                 Intent intent2=new Intent(this,ForgetActivity.class);
                 startActivity(intent2);
                break;
            case R.id.look_pwd:
                   if(abool){
                       login_pwd.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                       abool=false;
                   }
                else {
                       login_pwd.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD|InputType.TYPE_CLASS_TEXT);

                       abool=true;
                   }
                break;
        }
    }

}
