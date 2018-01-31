package qi.saoma.com.barcodereader.renwu;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.sunflower.FlowerCollector;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import qi.saoma.com.barcodereader.R;
import qi.saoma.com.barcodereader.adapter.Shuruadapter;
import qi.saoma.com.barcodereader.base.BaseActivity;
import qi.saoma.com.barcodereader.bean.Numberbean;
import qi.saoma.com.barcodereader.utils.BaseDialog;
import qi.saoma.com.barcodereader.utils.IatSettings;
import qi.saoma.com.barcodereader.utils.JsonParser;
import qi.saoma.com.barcodereader.utils.SoftKeyboardTool;
import qi.saoma.com.barcodereader.widget.VirtualKeyboardView;

/**
 * date : ${Date}
 * author:衣鹏宇(ypu)
 */

public class ShurumaActivity extends BaseActivity implements View.OnClickListener {
    private ImageView title_callback;
    private TextView title_zhong;
    private ImageView title_imgright;
    private TextView title_right;
    private TextView text_shou;
    private TextView text_yu;
    private View view_one;
    private View view_two;
    private TextView text_num;
    private RecyclerView recy_view;
   ArrayList <Numberbean> list=new ArrayList<>();
    private EditText ed_shuru;
    private TextView que_ok;
    Numberbean numberbean;
    private Shuruadapter shuruadapter;
    private LinearLayout liner_shuru;
    private LinearLayout liner_yuyin;
    private TextView text_yuyin;
    private String biaoshi;
    private static String TAG = ShurumaActivity.class.getSimpleName();
    // 语音听写对象
    private SpeechRecognizer mIat;
    // 语音听写UI
    private RecognizerDialog mIatDialog;
    // 用HashMap存储听写结果
    private HashMap<String, String> mIatResults = new LinkedHashMap<String, String>();

    private Toast mToast;
    private SharedPreferences mSharedPreferences;
    // 引擎类型
    private String mEngineType = SpeechConstant.TYPE_CLOUD;

    private boolean mTranslateEnable = false;
    int ret = 0; // 函数调用返回值
    private TextView tv_content;
    private Animation enterAnim;

    private Animation exitAnim;
    private VirtualKeyboardView virtualKeyboardView;

    private GridView gridView;

    private ArrayList<Map<String, String>> valueList;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shuruma);
        virtualKeyboardView = (VirtualKeyboardView) findViewById(R.id.virtualKeyboardView);
        initView();
        initAnim();
        virtualKeyboardView.startAnimation(exitAnim);
        virtualKeyboardView.setVisibility(View.GONE);



        valueList = virtualKeyboardView.getValueList();


    }
    /**
     * 数字键盘显示动画
     */
    private void initAnim() {

        enterAnim = AnimationUtils.loadAnimation(this, R.anim.push_bottom_in);
        exitAnim = AnimationUtils.loadAnimation(this, R.anim.push_bottom_out);
        // 设置不调用系统键盘
        if (android.os.Build.VERSION.SDK_INT <= 10) {
            ed_shuru.setInputType(InputType.TYPE_NULL);
        } else {
            this.getWindow().setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            try {
                Class<EditText> cls = EditText.class;
                Method setShowSoftInputOnFocus;
                setShowSoftInputOnFocus = cls.getMethod("setShowSoftInputOnFocus",
                        boolean.class);
                setShowSoftInputOnFocus.setAccessible(true);
                setShowSoftInputOnFocus.invoke(ed_shuru, false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        virtualKeyboardView.getLayoutBack().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                virtualKeyboardView.startAnimation(exitAnim);
                virtualKeyboardView.setVisibility(View.GONE);
            }
        });

        gridView = virtualKeyboardView.getGridView();
        gridView.setOnItemClickListener(onItemClickListener);
        ed_shuru.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_UP:
                        virtualKeyboardView.startAnimation(enterAnim);
                        virtualKeyboardView.setVisibility(View.VISIBLE);
                        break;
                }
                return false;
            }
        });
//        ed_shuru.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                 Log.d("taggg","------尺子-----");
//
////                virtualKeyboardView.setFocusable(true);
////                virtualKeyboardView.setFocusableInTouchMode(true);
//                virtualKeyboardView.startAnimation(enterAnim);
//                virtualKeyboardView.setVisibility(View.VISIBLE);
//            }
//        });
    }
    private void indata() {

       numberbean=new Numberbean();
        numberbean.setMunber(ed_shuru.getText().toString().trim());
         list.add(0,numberbean);
        recy_view.setLayoutManager(new GridLayoutManager(this,5));
        shuruadapter = new Shuruadapter(list,ShurumaActivity.this);
        recy_view.setAdapter(shuruadapter);
        shuruadapter.notifyDataSetChanged();
        int size = list.size();
        int i = size + 1;
        text_num.setText(i+"");

    }

    private void initView() {
        title_callback = (ImageView) findViewById(R.id.title_callback);
        title_zhong = (TextView) findViewById(R.id.title_zhong);
        title_imgright = (ImageView) findViewById(R.id.title_imgright);
        title_right = (TextView) findViewById(R.id.title_right);
        text_shou = (TextView) findViewById(R.id.text_shou);
        text_yu = (TextView) findViewById(R.id.text_yu);
        view_one = (View) findViewById(R.id.view_one);
        view_two = (View) findViewById(R.id.view_two);
        recy_view = (RecyclerView) findViewById(R.id.recy_view);
        ed_shuru = (EditText) findViewById(R.id.ed_shuru);
        que_ok = (TextView) findViewById(R.id.que_ok);
        liner_shuru = (LinearLayout) findViewById(R.id.liner_shuru);
        liner_yuyin = (LinearLayout) findViewById(R.id.liner_yuyin);
        text_yuyin = (TextView) findViewById(R.id.text_yuyin);
        text_num = (TextView) findViewById(R.id.text_nums);
        SpeechUtility.createUtility(ShurumaActivity.this, "appid="+"5a66b446");
        mIat = SpeechRecognizer.createRecognizer(ShurumaActivity.this, mInitListener);

        // 初始化听写Dialog，如果只使用有UI听写功能，无需创建SpeechRecognizer
        // 使用UI听写功能，请根据sdk文件目录下的notice.txt,放置布局文件和图片资源
        mIatDialog = new RecognizerDialog(ShurumaActivity.this, mInitListener);

        mSharedPreferences = getSharedPreferences(IatSettings.PREFER_NAME,
                Activity.MODE_PRIVATE);
        mToast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
         //得到intent传值
         Intent intent= getIntent();
        biaoshi = intent.getStringExtra("biaoshi");
        if(biaoshi.equals("hand")){
            text_yu.setTextColor(getResources().getColor(R.color.colortext));
            text_shou .setTextColor(getResources().getColor(R.color.textcolcr));
            liner_shuru.setVisibility(View.VISIBLE);
            liner_yuyin.setVisibility(View.GONE);
            view_one.setVisibility(View.VISIBLE);
            view_two.setVisibility(View.INVISIBLE);
        }
        else if(biaoshi.equals("song")){
            text_shou.setTextColor(getResources().getColor(R.color.colortext));
            text_yu.setTextColor(getResources().getColor(R.color.textcolcr));
            SoftKeyboardTool.closeKeyboard(ed_shuru);
            liner_shuru.setVisibility(View.GONE);
            liner_yuyin.setVisibility(View.VISIBLE);
            view_one.setVisibility(View.INVISIBLE);
            view_two.setVisibility(View.VISIBLE);
        }
        que_ok.setOnClickListener(this);
        title_zhong.setText("商品名称");
        title_imgright.setVisibility(View.GONE);
        title_right.setVisibility(View.VISIBLE);
        title_right.setText("结束保存");
        title_callback.setOnClickListener(this);
        text_shou.setOnClickListener(this);
        text_yu.setOnClickListener(this);
        title_right.setOnClickListener(this);

        //语音输入
        text_yuyin.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if( null == mIat ){
                Log.d("mia","-------------");
                }
                switch (motionEvent.getAction()){

                    case MotionEvent.ACTION_DOWN:
                        Log.d("mias",mIat+"-------------");

                        // 移动数据分析，收集开始听写事件
                        FlowerCollector.onEvent(ShurumaActivity.this, "iat_recognize");
                        // 设置参数
                        setParam();
                        // 不显示听写对话框
                        ret = mIat.startListening(mRecognizerListener);
                        if (ret != ErrorCode.SUCCESS) {
                            showTip("听写失败,错误码：" + ret);
                        } else {
                            showTip(getString(R.string.text_begin));
                        }
                        break;
                    case MotionEvent.ACTION_MOVE:
                        System.out.println("action_move");
                        break;
                    case MotionEvent.ACTION_UP:
                        mIat.stopListening();
                        shumaDialog(Gravity.CENTER,R.style.Alpah_aniamtion);
                        break;
                }
                return true;
                }

        });



    }

    @Override
    public void onClick(View view) {
        if( null == mIat ){
            // 创建单例失败，与 21001 错误为同样原因，参考 http://bbs.xfyun.cn/forum.php?mod=viewthread&tid=9688
            this.showTip( "创建对象失败，请确认 libmsc.so 放置正确，且有调用 createUtility 进行初始化" );
            return;
        }
        switch (view.getId()){
            case R.id.que_ok:
                indata();
                ed_shuru.setText("");
                break;
            case R.id.title_callback:
                finish();
                break;
            case R.id.text_shou:
                 text_shou.setTextColor(getResources().getColor(R.color.textcolcr));
                 liner_shuru.setVisibility(View.VISIBLE);
                liner_yuyin.setVisibility(View.GONE);
                view_one.setVisibility(View.VISIBLE);
                view_two.setVisibility(View.INVISIBLE);
                text_yu.setTextColor(getResources().getColor(R.color.colortext));
                break;
            case R.id.text_yu:
                text_shou.setTextColor(getResources().getColor(R.color.colortext));
                text_yu.setTextColor(getResources().getColor(R.color.textcolcr));
                SoftKeyboardTool.closeKeyboard(ed_shuru);
                liner_shuru.setVisibility(View.GONE);
                liner_yuyin.setVisibility(View.VISIBLE);
                view_one.setVisibility(View.INVISIBLE);
                view_two.setVisibility(View.VISIBLE);
                break;

            case R.id.title_right://结束保存
               tuichuDialog(Gravity.CENTER,R.style.Alpah_aniamtion);
                break;

        }
    }
    private void tuichuDialog(int grary, int animationStyle) {
        BaseDialog.Builder builder = new BaseDialog.Builder(this);
        final BaseDialog dialog = builder.setViewId(R.layout.dialog_jilu)
                //设置dialogpadding
                .setPaddingdp(0, 10, 0, 10)
                //设置显示位置
                .setGravity(grary)
                //设置动画
                .setAnimation(animationStyle)
                //设置dialog的宽高
                .setWidthHeightpx(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                //设置触摸dialog外围是否关闭
                .isOnTouchCanceled(true)
                //设置监听事件
                .builder();
        dialog.show();
        TextView tv_content = dialog.getView(R.id.tv_content);
        tv_content.setText("您一共录用了"+list.size()+"件商品，确定要\n     结束任务吗？");
        TextView tv_canel = dialog.getView(R.id.tv_canel);
        tv_canel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //关闭dialog
                dialog.close();
            }
        });
        TextView tv_yes = dialog.getView(R.id.tv_yes);
        tv_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                 //跳转到详情
                  Intent intent=new Intent(ShurumaActivity.this,EndmaActivity.class);
                    startActivity(intent);
                finish();
            }
        });
    }
    private void shumaDialog(int grary, int animationStyle) {
        BaseDialog.Builder builder = new BaseDialog.Builder(this);
        final BaseDialog dialog = builder.setViewId(R.layout.dialog_yuyin)
                //设置dialogpadding
                .setPaddingdp(0, 10, 0, 10)
                //设置显示位置
                .setGravity(grary)
                //设置动画
                .setAnimation(animationStyle)
                //设置dialog的宽高
                .setWidthHeightpx(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                //设置触摸dialog外围是否关闭
                .isOnTouchCanceled(true)
                //设置监听事件
                .builder();
        dialog.show();
        tv_content = dialog.getView(R.id.text_content);

        TextView tv_yes = dialog.getView(R.id.text_ok);
        tv_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean containChinese = isContainChinese(tv_content.getText().toString().trim());
                if( containChinese ){
                    Toast.makeText(ShurumaActivity.this,"不能含有中文",Toast.LENGTH_SHORT).show();
                }
                else {
                    numberbean = new Numberbean();
                    numberbean.setMunber(tv_content.getText().toString().trim());
                    list.add(0, numberbean);
                    recy_view.setLayoutManager(new GridLayoutManager(ShurumaActivity.this, 5));
                    shuruadapter = new Shuruadapter(list, ShurumaActivity.this);
                    recy_view.setAdapter(shuruadapter);
                    shuruadapter.notifyDataSetChanged();
                    int size = list.size();
                    int i = size + 1;
                    text_num.setText(i + "");
                    //关闭dialog
                    dialog.close();
                }
            }
        });
        TextView tv_canel = dialog.getView(R.id.text_pause);
        tv_canel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
    /*
      判断字符串是否含有中文
     */
    public static boolean isContainChinese(String str) {


        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }
    /**
     * 初始化监听器。
     */
    private InitListener mInitListener = new InitListener() {

        @Override
        public void onInit(int code) {
             Log.d("aaaa",code+"ssssssssssssssss");
            Log.d(TAG, "SpeechRecognizer init() code = " + code);
            if (code != ErrorCode.SUCCESS) {
                showTip("初始化失败，错误码：" + code);
            }
        }
    };
    private void showTip(final String str) {
        mToast.setText(str);
        mToast.show();
    }
    /**
     * 参数设置
     *
     * @return
     */
    public void setParam() {
        // 清空参数
       // mIat.setParameter(SpeechConstant.PARAMS, null);

        // 设置听写引擎
        mIat.setParameter(SpeechConstant.ENGINE_TYPE, mEngineType);
        // 设置返回结果格式
        mIat.setParameter(SpeechConstant.RESULT_TYPE, "json");

        this.mTranslateEnable = mSharedPreferences.getBoolean( this.getString(R.string.pref_key_translate), false );
        if( mTranslateEnable ){
            Log.i( TAG, "translate enable" );
            mIat.setParameter( SpeechConstant.ASR_SCH, "1" );
            mIat.setParameter( SpeechConstant.ADD_CAP, "translate" );
            mIat.setParameter( SpeechConstant.TRS_SRC, "its" );
        }

        String lag = mSharedPreferences.getString("iat_language_preference",
                "mandarin");
        if (lag.equals("en_us")) {
            // 设置语言
            mIat.setParameter(SpeechConstant.LANGUAGE, "en_us");
            mIat.setParameter(SpeechConstant.ACCENT, null);

            if( mTranslateEnable ){
                mIat.setParameter( SpeechConstant.ORI_LANG, "en" );
                mIat.setParameter( SpeechConstant.TRANS_LANG, "cn" );
            }
        } else {
            // 设置语言
            mIat.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
            // 设置语言区域
            mIat.setParameter(SpeechConstant.ACCENT, lag);

            if( mTranslateEnable ){
                mIat.setParameter( SpeechConstant.ORI_LANG, "cn" );
                mIat.setParameter( SpeechConstant.TRANS_LANG, "en" );
            }
        }

        // 设置语音前端点:静音超时时间，即用户多长时间不说话则当做超时处理
        mIat.setParameter(SpeechConstant.VAD_BOS, mSharedPreferences.getString("iat_vadbos_preference", "4000"));

        // 设置语音后端点:后端点静音检测时间，即用户停止说话多长时间内即认为不再输入， 自动停止录音
        mIat.setParameter(SpeechConstant.VAD_EOS, mSharedPreferences.getString("iat_vadeos_preference", "1000"));

        // 设置标点符号,设置为"0"返回结果无标点,设置为"1"返回结果有标点
        mIat.setParameter(SpeechConstant.ASR_PTT, mSharedPreferences.getString("iat_punc_preference", "0"));

        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
        // 注：AUDIO_FORMAT参数语记需要更新版本才能生效
        mIat.setParameter(SpeechConstant.AUDIO_FORMAT,"wav");
        mIat.setParameter(SpeechConstant.ASR_AUDIO_PATH, Environment.getExternalStorageDirectory()+"/msc/iat.wav");
    }

    private void printTransResult (RecognizerResult results) {
        String trans  = JsonParser.parseTransResult(results.getResultString(),"dst");
        String oris = JsonParser.parseTransResult(results.getResultString(),"src");

        if( TextUtils.isEmpty(trans)||TextUtils.isEmpty(oris) ){
            showTip( "解析结果失败，请确认是否已开通翻译功能。" );
        }else{
            tv_content.setText( "原始语言:\n"+oris+"\n目标语言:\n"+trans );
        }

    }


    private RecognizerListener mRecognizerListener = new RecognizerListener() {

        @Override
        public void onBeginOfSpeech() {
            // 此回调表示：sdk内部录音机已经准备好了，用户可以开始语音输入
            showTip("开始说话");
        }

        @Override
        public void onError(SpeechError error) {
            // Tips：
            // 错误码：10118(您没有说话)，可能是录音机权限被禁，需要提示用户打开应用的录音权限。
            // 如果使用本地功能（语记）需要提示用户开启语记的录音权限。
            if(mTranslateEnable && error.getErrorCode() == 14002) {
                showTip( error.getPlainDescription(true)+"\n请确认是否已开通翻译功能" );
            } else {
                showTip(error.getPlainDescription(true));
            }
        }

        @Override
        public void onEndOfSpeech() {
            // 此回调表示：检测到了语音的尾端点，已经进入识别过程，不再接受语音输入
            //	showTip("结束说话");
        }

        @Override
        public void onResult(RecognizerResult results, boolean isLast) {
            Log.d(TAG, results.getResultString());
            if( mTranslateEnable ){
                printTransResult( results );
            }else{
                printResult(results);
            }

            if (isLast) {
                // TODO 最后的结果
            }
        }

        @Override
        public void onVolumeChanged(int volume, byte[] data) {
            //	showTip("当前正在说话，音量大小：" + volume);
            Log.d(TAG, "返回音频数据："+data.length);
        }

        @Override
        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
            // 以下代码用于获取与云端的会话id，当业务出错时将会话id提供给技术支持人员，可用于查询会话日志，定位出错原因
            // 若使用本地能力，会话id为null
            //	if (SpeechEvent.EVENT_SESSION_ID == eventType) {
            //		String sid = obj.getString(SpeechEvent.KEY_EVENT_SESSION_ID);
            //		Log.d(TAG, "session id =" + sid);
            //	}
        }
    };

    private void printResult(RecognizerResult results) {
        String text = JsonParser.parseIatResult(results.getResultString());

        String sn = null;
        // 读取json结果中的sn字段
        try {
            JSONObject resultJson = new JSONObject(results.getResultString());
            sn = resultJson.optString("sn");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mIatResults.put(sn, text);

        StringBuffer resultBuffer = new StringBuffer();
        for (String key : mIatResults.keySet()) {
            resultBuffer.append(mIatResults.get(key));
        }

        tv_content.setText(resultBuffer.toString());
        //tv_content.setSelection(tv_content.length());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if( null != mIat ){
            // 退出时释放连接
            mIat.cancel();
            mIat.destroy();
        }
    }

    @Override
    protected void onResume() {
        // 开放统计 移动数据统计分析
        FlowerCollector.onResume(ShurumaActivity.this);
        FlowerCollector.onPageStart(TAG);
        super.onResume();
    }

    @Override
    protected void onPause() {
        // 开放统计 移动数据统计分析
        FlowerCollector.onPageEnd(TAG);
        FlowerCollector.onPause(ShurumaActivity.this);
        super.onPause();
    }
    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

            if (position < 11 && position != 9) {    //点击0~9按钮

                String amount = ed_shuru.getText().toString().trim();
                amount = amount + valueList.get(position).get("name");

                ed_shuru.setText(amount);

                Editable ea = ed_shuru.getText();
                ed_shuru.setSelection(ea.length());
            } else {

                if (position == 9) {      //点击退格键
                    String amount = ed_shuru.getText().toString().trim();
                    if (!amount.contains(".")) {
                        amount = amount + valueList.get(position).get("name");
                        ed_shuru.setText(amount);

                        Editable ea = ed_shuru.getText();
                        ed_shuru.setSelection(ea.length());
                    }
                }

                if (position == 11) {      //点击退格键
                    String amount = ed_shuru.getText().toString().trim();
                    if (amount.length() > 0) {
                        amount = amount.substring(0, amount.length() - 1);
                        ed_shuru.setText(amount);

                        Editable ea = ed_shuru.getText();
                        ed_shuru.setSelection(ea.length());
                    }
                }
            }
        }
    };
}
