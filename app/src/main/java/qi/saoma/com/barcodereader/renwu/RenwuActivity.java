package qi.saoma.com.barcodereader.renwu;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.listener.CustomListener;

import java.util.ArrayList;
import java.util.List;

import qi.saoma.com.barcodereader.R;
import qi.saoma.com.barcodereader.base.BaseActivity;
import qi.saoma.com.barcodereader.login.FiristOneActivity;
import qi.saoma.com.barcodereader.utils.SoftKeyboardTool;
import qi.saoma.com.barcodereader.utils.SpUtils;

/**
 * date : ${Date}
 * author:衣鹏宇(ypu)
 */

public class RenwuActivity extends BaseActivity implements View.OnClickListener {

    private ImageView title_callback;
    private TextView title_zhong;
    private TextView title_right;
    private TextView one;
    private EditText ed_companyname;
    private TextView Two;
    private EditText ed_number;
    private TextView xinxin;
    private TextView Three;
    private EditText ed_shopname;
    private TextView Four;
    private EditText ed_pername;
    private TextView xinxins;
    private TextView Five;
    private TextView ed_shopedanwei;
    private TextView Six;
    private EditText ed_money;
    private TextView next_ok;
    private OptionsPickerView pvCustomOptions;
    private List<String> list;
    private ImageView title_img;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_renwu);
        initView();
        inindate();
    }

    private void initView() {
        title_callback = (ImageView) findViewById(R.id.title_callback);
        title_zhong = (TextView) findViewById(R.id.title_zhong);
        title_right = (TextView) findViewById(R.id.title_right);
        one = (TextView) findViewById(R.id.one);
        ed_companyname = (EditText) findViewById(R.id.ed_companyname);
        Two = (TextView) findViewById(R.id.Two);
        ed_number = (EditText) findViewById(R.id.ed_number);
        xinxin = (TextView) findViewById(R.id.xinxin);
        Three = (TextView) findViewById(R.id.Three);
        ed_shopname = (EditText) findViewById(R.id.ed_shopname);
        Four = (TextView) findViewById(R.id.Four);
        ed_pername = (EditText) findViewById(R.id.ed_pername);
        xinxins = (TextView) findViewById(R.id.xinxins);
        Five = (TextView) findViewById(R.id.Five);
        ed_shopedanwei = (TextView) findViewById(R.id.ed_shopedanwei);
        Six = (TextView) findViewById(R.id.Six);
        ed_money = (EditText) findViewById(R.id.ed_money);
        next_ok = (TextView) findViewById(R.id.next_ok);
        title_callback.setOnClickListener(this);
        boolean bool = SpUtils.getBoolean(this, "bool", false);
        if(bool){
            title_zhong.setText("开始新任务");
        }
        else {
            title_zhong.setText("开始第一个任务");
        }
         next_ok.setOnClickListener(this);
        title_img = (ImageView) findViewById(R.id.title_imgright);
        title_img.setVisibility(View.GONE);
        ed_shopedanwei.setOnClickListener(this);
    }

    private void submit() {
        // validate
        String companyname = ed_companyname.getText().toString().trim();
        if (TextUtils.isEmpty(companyname)) {
            Toast.makeText(this, "请输入公司名称", Toast.LENGTH_SHORT).show();
            return;
        }

        String number = ed_number.getText().toString().trim();
        if (TextUtils.isEmpty(number)) {
            Toast.makeText(this, "       请输入厂号/批号", Toast.LENGTH_SHORT).show();
            return;
        }

        String shopname = ed_shopname.getText().toString().trim();
        if (TextUtils.isEmpty(shopname)) {
            Toast.makeText(this, "        请输入商品名称", Toast.LENGTH_SHORT).show();
            return;
        }

        String pername = ed_pername.getText().toString().trim();
        if (TextUtils.isEmpty(pername)) {
            Toast.makeText(this, "       请输入客户名称", Toast.LENGTH_SHORT).show();
            return;
        }

        String shopedanwei = ed_shopedanwei.getText().toString().trim();
        if (TextUtils.isEmpty(shopedanwei)) {
            Toast.makeText(this, "       请输入商品单位", Toast.LENGTH_SHORT).show();
            return;
        }

        String money = ed_money.getText().toString().trim();
        if (TextUtils.isEmpty(money)) {
            Toast.makeText(this, "       请输入商品单价", Toast.LENGTH_SHORT).show();
            return;
        }

        // TODO validate success, do something


    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.next_ok://下一步
                Intent intent=new Intent(this, FiristOneActivity.class);
                 startActivity(intent);
                finish();
                break;
            case R.id.title_callback:
                finish();
                break;
            case R.id.ed_shopedanwei:
                initCustomOptionPicker(list);
                pvCustomOptions.show();
                //关闭软键盘
                SoftKeyboardTool.closeKeyboard(this);
                break;
        }
    }
         public  void inindate(){
              list = new ArrayList<>();
              list.add("Kg");
              list.add("g");
              list.add("斤");
              list.add("两");
              list.add("磅");

         }
    private void initCustomOptionPicker(final List<String> data){
        pvCustomOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = data.get(options1);
                ed_shopedanwei.setText(tx);

            }
        })
                .setLayoutRes(R.layout.pickerview_custom_options, new CustomListener() {
                    @Override
                    public void customLayout(View v) {
                        final TextView tvSubmit = (TextView) v.findViewById(R.id.tv_finish);
                        final TextView tvCancle = (TextView) v.findViewById(R.id.tv_cancle);

                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomOptions.returnData();
                                pvCustomOptions.dismiss();
                            }
                        });

                        tvCancle.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomOptions.dismiss();
                            }
                        });
                    }
                })
                .setSelectOptions(2)//默认选中项
                .setContentTextSize(20)//设置滚轮文字大小
                .setBgColor(getResources().getColor(R.color.linecolor))
                .setTextColorOut(getResources().getColor(R.color.colortext))
                .setDividerColor(getResources().getColor(R.color.colortext))
                .setTextColorCenter(getResources().getColor(R.color.zicolor)) //设置选中项文字颜色
                .build();
        pvCustomOptions.setPicker(data);//添加数据

    }
}
