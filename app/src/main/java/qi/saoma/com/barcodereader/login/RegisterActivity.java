package qi.saoma.com.barcodereader.login;

import android.content.Intent;
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
import qi.saoma.com.barcodereader.utils.ToastUtils;

/**
 * date : ${Date}
 * author:衣鹏宇(ypu)
 */

public class RegisterActivity extends BaseActivity implements View.OnClickListener {
    private ImageView title_callback;
    private TextView title_zhong;
    private TextView title_right;
    private ImageView set_lpwd;
    private EditText register_user;
    private ImageView set_phone;
    private EditText register_phone;
    private ImageView set_pwd;
    private EditText register_pwd;
    private ImageView set_repwd;
    private EditText register_repwd;
    private TextView register_ok;
    private ImageView title_imgright;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
    }

    private void initView() {
        title_callback = (ImageView) findViewById(R.id.title_callback);
        title_zhong = (TextView) findViewById(R.id.title_zhong);
        title_right = (TextView) findViewById(R.id.title_right);
        set_lpwd = (ImageView) findViewById(R.id.set_lpwd);
        register_user = (EditText) findViewById(R.id.register_user);
        set_phone = (ImageView) findViewById(R.id.set_phone);
        register_phone = (EditText) findViewById(R.id.register_phone);
        set_pwd = (ImageView) findViewById(R.id.set_pwd);
        register_pwd = (EditText) findViewById(R.id.register_pwd);
        set_repwd = (ImageView) findViewById(R.id.set_repwd);
        register_repwd = (EditText) findViewById(R.id.register_repwd);
        register_ok = (TextView) findViewById(R.id.register_ok);
        title_imgright = (ImageView) findViewById(R.id.title_imgright);
         register_ok.setOnClickListener(this);
        title_callback.setOnClickListener(this);
        title_imgright.setOnClickListener(this);
        title_imgright.setVisibility(View.GONE);

    }

    private void submit() {
        // validate
        String user = register_user.getText().toString().trim();
        if (TextUtils.isEmpty(user)) {
            Toast.makeText(this, "请输入用户名", Toast.LENGTH_SHORT).show();
            return;
        }
        boolean chinese = isChinese(user.toString().trim());
        if(chinese){
            Toast.makeText(this, "用户名只能是英文或数字", Toast.LENGTH_SHORT).show();
            return;
        }

        String phone = register_phone.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, " 请输入手机号", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!MyUtils.isMobileNO(register_phone.getText().toString())) {
            Toast.makeText(this, "手机号格式不正确", Toast.LENGTH_SHORT).show();
            return;
        }

        String pwd = register_pwd.getText().toString().trim();
        if (TextUtils.isEmpty(pwd)) {
            Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
            return;
        }
        if (register_pwd.getText().toString().length() < 5 || register_pwd.getText().toString().length() > 20) {
            Toast.makeText(this, "请输入6-20位密码", Toast.LENGTH_SHORT).show();
            return;
        }

        String repwd = register_repwd.getText().toString().trim();
        if (TextUtils.isEmpty(repwd)) {
            Toast.makeText(this, "请输入确认密码", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!(pwd.equals(repwd))){
            Toast.makeText(this, "两次密码不一致", Toast.LENGTH_SHORT).show();
            return;

        }
      /*
         网络请求
         
       */
         network();



    }

    private void network() {

        Map<String,String> map=new HashMap<>();
          map.put("mobile",register_phone.getText().toString().trim());
          map.put("password",register_pwd.getText().toString().trim());
          map.put("user_name",register_user.getText().toString().trim());
        RetrofitManager.post(MyContants.BASEURL +"register/set-one", map, new BaseObserver1<Successbean>("") {
            @Override
            public void onSuccess(Successbean result, String tag) throws RemoteException {
              if(result.getCode().equals(200)){

                  ToastUtils.showShort(RegisterActivity.this,"注册成功");
                                  Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
                                   startActivity(intent);
                  finish();
              }
                else {
                  ToastUtils.showShort(RegisterActivity.this,result.getMessage()+"");
              }
            }

            @Override
            public void onFailed(int code, String data) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.title_callback:
                finish();
                break;
            case R.id.register_ok:
                submit();

                break;
        }
    }

    // 判断一个字符是否是中文
    public boolean isChinese(char c) {
        return c >= 0x4E00 && c <= 0x9FA5;// 根据字节码判断
    }

    // 判断一个字符串是否含有中文
    public boolean isChinese(String str) {
        if (str == null)
            return false;
        for (char c : str.toCharArray()) {
            if (isChinese(c))
                return true;// 有一个中文字符就返回
        }
        return false;
    }
}
