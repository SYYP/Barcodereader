package qi.saoma.com.barcodereader.renwu;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zaaach.toprightmenu.MenuItem;
import com.zaaach.toprightmenu.TopRightMenu;

import java.util.ArrayList;
import java.util.List;

import qi.saoma.com.barcodereader.R;
import qi.saoma.com.barcodereader.base.BaseActivity;

/**
 * date : ${Date}
 * author:衣鹏宇(ypu)
 */

public class RenwudetailActivity extends BaseActivity implements View.OnClickListener {

    private ImageView title_callback;
    private TextView title_zhong;
    private ImageView title_imgright;
    private TextView title_right;
    private TextView Six;
    private TextView text_shop_name;
    private TextView text_changhao;
    private TextView text_kehu;
    private TextView text_zhongliang;
    private TextView text_num;
    private TextView text_price;
    private TextView text_zong;
    private TextView look_dan;
    TopRightMenu mTopRightMenu;
    private ImageView title_img;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_renwudetail);

        initView();
    }

    private void indata() {
        mTopRightMenu = new TopRightMenu(this);
        List<MenuItem> menuitems=new ArrayList<>();
        menuitems.add(new MenuItem(R.drawable.img_daochu,"导出Excel"));
        menuitems.add(new MenuItem(R.drawable.img_share,"分享"));
        menuitems.add(new MenuItem(R.drawable.img_delete,"删除任务"));
        mTopRightMenu
                .setHeight(480)     //默认高度480
                .setWidth(320)      //默认宽度wrap_content
                .showIcon(true)     //显示菜单图标，默认为true
                .dimBackground(false)        //背景变暗，默认为true
                .needAnimationStyle(true)   //显示动画，默认为true
                .setAnimationStyle(R.style.TRM_ANIM_STYLE)
                .addMenuList(menuitems)
                .setOnMenuItemClickListener(new TopRightMenu.OnMenuItemClickListener() {
                    @Override
                    public void onMenuItemClick(int position) {
                          if(position==0){
                              Toast.makeText(RenwudetailActivity.this,"Excel",Toast.LENGTH_SHORT).show();
                          }
                        else if(position==1){
                              Toast.makeText(RenwudetailActivity.this,"share",Toast.LENGTH_SHORT).show();
                          }
                        else if(position==2){
                              Toast.makeText(RenwudetailActivity.this,"delete",Toast.LENGTH_SHORT).show();
                          }
                    }
                })
                .showAsDropDown(title_imgright, -225, 0);    //带偏移量
    }

    private void initView() {
        title_callback = (ImageView) findViewById(R.id.title_callback);
        title_zhong = (TextView) findViewById(R.id.title_zhong);
        title_imgright = (ImageView) findViewById(R.id.title_imgright);
        title_right = (TextView) findViewById(R.id.title_right);
        Six = (TextView) findViewById(R.id.Six);
        text_shop_name = (TextView) findViewById(R.id.text_shop_name);
        text_changhao = (TextView) findViewById(R.id.text_changhao);
        text_kehu = (TextView) findViewById(R.id.text_kehu);
        text_zhongliang = (TextView) findViewById(R.id.text_zhongliang);
        text_num = (TextView) findViewById(R.id.text_num);
        text_price = (TextView) findViewById(R.id.text_price);
        text_zong = (TextView) findViewById(R.id.text_zong);
        look_dan = (TextView) findViewById(R.id.look_dan);
        title_callback.setOnClickListener(this);
        title_imgright.setOnClickListener(this);
        title_img = (ImageView) findViewById(R.id.title_imgright);
         title_zhong.setText("任务详情");
        look_dan.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.title_callback:
                finish();
                break;
            case R.id.title_imgright:
                indata();
                break;

            case R.id.look_dan:
                Intent intent2=new Intent(this, LookmadanActivity.class);
                startActivity( intent2);
                break;
        }

    }
}
