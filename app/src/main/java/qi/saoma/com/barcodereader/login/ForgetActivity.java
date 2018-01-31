package qi.saoma.com.barcodereader.login;

import android.os.Bundle;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import qi.saoma.com.barcodereader.R;
import qi.saoma.com.barcodereader.base.BaseActivity;
import qi.saoma.com.barcodereader.bean.Successbean;
import qi.saoma.com.barcodereader.network.BaseObserver1;
import qi.saoma.com.barcodereader.network.RetrofitManager;
import qi.saoma.com.barcodereader.utils.MyContants;
import qi.saoma.com.barcodereader.utils.MyUtils;

/**
 * date : ${Date}
 * author:衣鹏宇(ypu)
 */

public class ForgetActivity extends BaseActivity implements View.OnClickListener {
    private ImageView title_callback;
    private TextView title_zhong;
    private TextView title_right;
    private ImageView set_phone;
    private EditText forget_phone;
    private ImageView set_lpwd;
    private EditText forget_mesaage;
    private TextView text_huoqu;
    private ImageView set_pwd;
    private EditText forget_pwd;
    private ImageView set_repwd;
    private EditText forget_newpwd;
    private TextView forget_ok;
    private ImageView titleimage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpwd);
        initView();

    }

    private void initView() {
        title_callback = (ImageView) findViewById(R.id.title_callback);
        title_zhong = (TextView) findViewById(R.id.title_zhong);
        title_right = (TextView) findViewById(R.id.title_right);
        set_phone = (ImageView) findViewById(R.id.set_phone);
        forget_phone = (EditText) findViewById(R.id.forget_phone);
        set_lpwd = (ImageView) findViewById(R.id.set_lpwd);
        forget_mesaage = (EditText) findViewById(R.id.forget_mesaage);
        text_huoqu = (TextView) findViewById(R.id.text_huoqu);
        set_pwd = (ImageView) findViewById(R.id.set_pwd);
        forget_pwd = (EditText) findViewById(R.id.forget_pwd);
        set_repwd = (ImageView) findViewById(R.id.set_repwd);
        forget_newpwd = (EditText) findViewById(R.id.forget_newpwd);
        forget_ok = (TextView) findViewById(R.id.forget_ok);
        titleimage = (ImageView) findViewById(R.id.title_imgright);
        title_callback.setOnClickListener(this);
        title_zhong.setText("忘记密码");
        titleimage.setVisibility(View.GONE);
        forget_ok.setOnClickListener(this);
        text_huoqu.setOnClickListener(this);
    }

    private void submit() {
        // validate
        String phone = forget_phone.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, " 请输入手机号", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!MyUtils.isMobileNO(forget_phone.getText().toString())) {
            Toast.makeText(this, "手机号格式不正确", Toast.LENGTH_SHORT).show();
            return;
        }

        String mesaage = forget_mesaage.getText().toString().trim();
        if (TextUtils.isEmpty(mesaage)) {
            Toast.makeText(this, "请输入验证码", Toast.LENGTH_SHORT).show();
            return;
        }

        String pwd = forget_pwd.getText().toString().trim();
        if (TextUtils.isEmpty(pwd)) {
            Toast.makeText(this, "请输入新密码", Toast.LENGTH_SHORT).show();
            return;
        }

        String newpwd = forget_newpwd.getText().toString().trim();
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
            case R.id.forget_ok://立即找回
                submit();
                break;
            case R.id.text_huoqu:
                //获取验证码
                   huoqu();
                break;

        }
    }

    private void huoqu() {
        // validate
        String phone = forget_phone.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, " 请输入手机号", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!MyUtils.isMobileNO(forget_phone.getText().toString())) {
            Toast.makeText(this, "手机号格式不正确", Toast.LENGTH_SHORT).show();
            return;
        }
           gomessage();
    }

    private void gomessage() {
        Map<String,String> map=new HashMap<>();
           map.put("mobile",forget_phone.getText().toString().trim());
        RetrofitManager.post(MyContants.BASEURL+"verifycodes", map, new BaseObserver1<Successbean>("") {
            @Override
            public void onSuccess(Successbean result, String tag) throws RemoteException {

                String code = result.getCode();
            }

            @Override
            public void onFailed(int code, String data) {

            }
        });

    }
}
