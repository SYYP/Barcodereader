package qi.saoma.com.barcodereader.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import qi.saoma.com.barcodereader.R;
import qi.saoma.com.barcodereader.base.BaseActivity;

/**
 * date : ${Date}
 * author:衣鹏宇(ypu)
 */

public class AlterActivity extends BaseActivity implements View.OnClickListener{

    private ImageView title_callback;
    private TextView title_zhong;
    private TextView title_right;
    private ImageView set_phone;
    private EditText alter_phone;
    private ImageView set_lpwd;
    private EditText alter_mesaage;
    private TextView text_huoqu;
    private ImageView set_pwd;
    private EditText alter_pwd;
    private ImageView set_repwd;
    private EditText alter_newpwd;
    private TextView register_ok;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alterpwd);
        initView();
    }

    private void initView() {
        title_callback = (ImageView) findViewById(R.id.title_callback);
        title_zhong = (TextView) findViewById(R.id.title_zhong);
        title_right = (TextView) findViewById(R.id.title_right);
        set_phone = (ImageView) findViewById(R.id.set_phone);
        alter_phone = (EditText) findViewById(R.id.alter_phone);
        set_lpwd = (ImageView) findViewById(R.id.set_lpwd);
        alter_mesaage = (EditText) findViewById(R.id.alter_mesaage);
        text_huoqu = (TextView) findViewById(R.id.text_huoqu);
        set_pwd = (ImageView) findViewById(R.id.set_pwd);
        alter_pwd = (EditText) findViewById(R.id.alter_pwd);
        set_repwd = (ImageView) findViewById(R.id.set_repwd);
        alter_newpwd = (EditText) findViewById(R.id.alter_newpwd);
        register_ok = (TextView) findViewById(R.id.register_ok);
        title_callback.setOnClickListener(this);
       ImageView titleimg= (ImageView) findViewById(R.id.title_imgright);
        title_zhong.setText("修改密码");
        titleimg.setVisibility(View.GONE);
        register_ok.setOnClickListener(this);
    }

    private void submit() {
        // validate
        String phone = alter_phone.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, " 请输入手机号", Toast.LENGTH_SHORT).show();
            return;
        }

        String mesaage = alter_mesaage.getText().toString().trim();
        if (TextUtils.isEmpty(mesaage)) {
            Toast.makeText(this, "请输入验证码", Toast.LENGTH_SHORT).show();
            return;
        }

        String pwd = alter_pwd.getText().toString().trim();
        if (TextUtils.isEmpty(pwd)) {
            Toast.makeText(this, "请输入新密码", Toast.LENGTH_SHORT).show();
            return;
        }

        String newpwd = alter_newpwd.getText().toString().trim();
        if (TextUtils.isEmpty(newpwd)) {
            Toast.makeText(this, "请输入确认密码", Toast.LENGTH_SHORT).show();
            return;
        }

        // TODO validate success, do something


    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.title_callback:
                finish();
                break;
            case R.id.register_ok:
                Intent intent=new Intent(this,LoginActivity.class);
                startActivity(intent);
                finish();
                break;
        }

    }
}
