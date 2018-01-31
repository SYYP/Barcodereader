package qi.saoma.com.barcodereader.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zaaach.toprightmenu.MenuItem;
import com.zaaach.toprightmenu.TopRightMenu;

import org.zackratos.ultimatebar.UltimateBar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import qi.saoma.com.barcodereader.R;
import qi.saoma.com.barcodereader.base.BaseActivity;
import qi.saoma.com.barcodereader.renwu.RenwuActivity;
import qi.saoma.com.barcodereader.utils.SpUtils;

/**
 * date : ${Date}
 * author:衣鹏宇(ypu)
 */

public class FirstrenwuActivity extends BaseActivity implements View.OnClickListener {
    private TextView text_time;
    private ImageView img_more;
    private TextView text_name;
    private TextView shou_renwu;
    private SimpleDateFormat df;
    TopRightMenu mTopRightMenu;
    private long preTime;
    private List<MenuItem> menuitems;
    private RelativeLayout real_more;
    private TextView text_jieshao;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UltimateBar ultimateBar = new UltimateBar(this);
        ultimateBar.setImmersionBar();
        setContentView(R.layout.activity_shouye);
        initView();
        df = new SimpleDateFormat("yyyy-MM-dd");

         indata();
    }

    private void indata() {
        text_time.setText(df.format(System.currentTimeMillis()));
    }


    private void initView() {
        text_time = (TextView) findViewById(R.id.text_time);
        img_more = (ImageView) findViewById(R.id.img_more);
        text_name = (TextView) findViewById(R.id.text_name);
        shou_renwu = (TextView) findViewById(R.id.shou_renwu);
        real_more = (RelativeLayout) findViewById(R.id.real_more);
         shou_renwu.setOnClickListener(this);
        img_more.setOnClickListener(this);
        menuitems = new ArrayList<>();
        menuitems.add(new MenuItem(R.drawable.img_alterpwd,"修改密码"));
        menuitems.add(new MenuItem(R.drawable.img_tui,"退出登录"));
        real_more.setOnClickListener(this);
        text_jieshao = (TextView) findViewById(R.id.text_jieshao);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.shou_renwu:
                SpUtils.putBoolean(this,"bool",true);
                Intent intent=new Intent(this,RenwuActivity.class);
                startActivity(intent);
                break;
            case R.id.real_more:
                indatas();
                break;
            case R.id.text_jieshao:
                Intent intent1=new Intent(this,JieShaoActivity.class);
                  startActivity(intent1);
                break;

        }
    }
    private void indatas() {
        mTopRightMenu = new TopRightMenu(this);
        mTopRightMenu
                .setHeight(300)     //默认高度480
                .setWidth(400)      //默认宽度wrap_content
                .showIcon(true)     //显示菜单图标，默认为true
                .dimBackground(false)        //背景变暗，默认为true
                .needAnimationStyle(false)   //显示动画，默认为true
                .setAnimationStyle(R.style.TRM_ANIM_STYLE)
                .addMenuItem(new MenuItem(R.drawable.img_alterpwd,"修改密码"))
                .addMenuItem(new MenuItem(R.drawable.img_tui,"退出登录"))
                .setOnMenuItemClickListener(new TopRightMenu.OnMenuItemClickListener() {
                    @Override
                    public void onMenuItemClick(int position) {
                        if(position==0){
                            Intent intent=new Intent(FirstrenwuActivity.this,AlterActivity.class);
                            startActivity(intent);
                        }
                        else if(position==1){

                                realBack();//删除所有引用

                        }
                    }
                })
                .showAsDropDown(img_more, -270, 0);    //带偏移量
    }
}
