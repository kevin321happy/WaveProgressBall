package com.fips.huashun.ui.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fips.huashun.ApplicationEx;
import com.fips.huashun.R;
import com.fips.huashun.common.Constants;
import com.fips.huashun.modle.bean.UserInfo;
import com.fips.huashun.net.HttpUtil;
import com.fips.huashun.net.LoadDatahandler;
import com.fips.huashun.net.LoadJsonHttpResponseHandler;
import com.fips.huashun.ui.utils.NavigationBar;
import com.fips.huashun.ui.utils.ToastUtil;
import com.fips.huashun.widgets.CircleImageView;
import com.fips.huashun.widgets.FlowLayout;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

/**
 * 功能：个人信息
 * Created by Administrator on 2016/8/26.
 *
 * @author 张柳 时间：2016年8月26日16:36:07
 */
public class PersonalInfoActivity extends BaseActivity implements View.OnClickListener
{
    private NavigationBar navigationBar;
    private LinearLayout headLayout,usreNameLayout, nameLayout, autographLayout, sexLayout, wedlockLayout, birthdayLayout;
    private Button btn_picture, btn_photo, btn_cancle;
    private Bitmap head;// 头像Bitmap
    private CircleImageView ivHead;
    private TextView userNameTv, trueNameTv, phoneTv, birthdayTv, sexTv, marryTv, workNumTv, depTv, jobTv, signatureTv;
    private static String path = "/sdcard/myHead/";// sd路径
    private String urlpath;
    // 生日
    private int mYear;
    private int mMonth;
    private int mDay;
    private static final int DATE_DIALOG_ID = 1;
    private static final int SHOW_DATAPICK = 0;
    private UserInfo userinfo;
    private Intent intent;
    private boolean mFired = false;
    //


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personalinfo);
        initView();
        initData();
    }

    @Override
    protected void initView()
    {
        super.initView();
        intent = getIntent();
        userinfo = (UserInfo) intent.getSerializableExtra("userinfo");
        navigationBar = (NavigationBar) findViewById(R.id.nb_personalinfo);
        navigationBar.setTitle("个人信息");
        navigationBar.setLeftImage(R.drawable.fanhui);
        navigationBar.setListener(new NavigationBar.NavigationListener()
        {
            @Override
            public void onButtonClick(int button)
            {
                if (button == NavigationBar.LEFT_VIEW)
                {
                    finish();
                }
            }
        });
        headLayout = (LinearLayout) findViewById(R.id.ll_personalhead);
        usreNameLayout = (LinearLayout) findViewById(R.id.ll_user_name);
        nameLayout = (LinearLayout) findViewById(R.id.ll_personalname);
        autographLayout = (LinearLayout) findViewById(R.id.ll_sex);
        sexLayout = (LinearLayout) findViewById(R.id.ll_autograph);
        wedlockLayout = (LinearLayout) findViewById(R.id.ll_wedlock);
        birthdayLayout = (LinearLayout) findViewById(R.id.ll_birthday);
        ivHead = (CircleImageView) findViewById(R.id.iv_personalhead);
        birthdayTv = (TextView) findViewById(R.id.tv_birthday);
        userNameTv = (TextView) findViewById(R.id.tv_user_name);
        trueNameTv = (TextView) findViewById(R.id.tv_truename);
        phoneTv = (TextView) findViewById(R.id.tv_phone);
        sexTv = (TextView) findViewById(R.id.tv_sex);
        marryTv = (TextView) findViewById(R.id.tv_marry);
        workNumTv = (TextView) findViewById(R.id.tv_worknum);
        depTv = (TextView) findViewById(R.id.tv_dep);
        jobTv = (TextView) findViewById(R.id.tv_job);
        signatureTv = (TextView) findViewById(R.id.tv_signature);
        headLayout.setOnClickListener(this);
        nameLayout.setOnClickListener(this);
        autographLayout.setOnClickListener(this);
        sexLayout.setOnClickListener(this);
        wedlockLayout.setOnClickListener(this);
        birthdayLayout.setOnClickListener(this);
        usreNameLayout.setOnClickListener(this);
    }

    /**
     * 功能：设置数据
     */
    private void initData()
    {
        String birthday = userinfo.getBirthday();
        if (null == birthday || birthday.isEmpty())
        {
            setDateTime(null);
        } else
        {
            setDateTime(birthday);
        }
        ImageLoader.getInstance().displayImage(Constants.IMG+userinfo.getFilepath(),ivHead,ApplicationEx.head_options);
        userNameTv.setText(userinfo.getUser_name());
        trueNameTv.setText(userinfo.getTruename());
        phoneTv.setText(userinfo.getPhone());
        // 空是未设置，1，男 2，女
        String sex = userinfo.getSex();
        if(TextUtils.isEmpty(sex))
        {
            sexTv.setText("未设置");
        } else if ("1".equals(sex))
        {
            sexTv.setText("男");
        } else if ("2".equals(sex))
        {
            sexTv.setText("女");
        }
        // 空是未设置，1，未婚 2，已婚
        String marry = userinfo.getMarry();
        if(TextUtils.isEmpty(sex))
        {
            marryTv.setText("未设置");
        } else if ("1".equals(marry))
        {
            marryTv.setText("未婚");
        } else if ("2".equals(marry))
        {
            marryTv.setText("已婚");
        }
        workNumTv.setText(userinfo.getWorknum());
        depTv.setText(userinfo.getDep());
        jobTv.setText(userinfo.getJob());
        signatureTv.setText(userinfo.getSignature());
    }

    @Override
    public boolean isSystemBarTranclucent()
    {
        return false;
    }

    /**
     * 功能：初始化日期
     */
    private void setDateTime(String birthday)
    {
        if(null == birthday)
        {
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);
        } else
        {
            String[] birthdayString = birthday.split("-");
            mYear = Integer.valueOf(birthdayString[0]);
            mMonth = Integer.valueOf(birthdayString[1])-1;
            mDay = Integer.valueOf(birthdayString[2]);
        }
        updateDisplay();
    }
    /**
     * 更新日期
     */

    private void updateDisplay()
    {
        birthdayTv.setText(new StringBuilder().append(mYear).append("-").append(
                (mMonth + 1) < 10 ? "0" + (mMonth + 1) : (mMonth + 1)).append("-").append(
                (mDay < 10) ? "0" + mDay : mDay));
    }

    /**
     * 日期控件的事件
     */
    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener()
    {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
        {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            if (!mFired)
            {
                mFired = true;
                updateDisplay();
                updateBirthday();
            }
        }
    };

    @Override
    protected Dialog onCreateDialog(int id)
    {
        switch (id)
        {
            case DATE_DIALOG_ID:
                Log.e("onCreateDialog","onCreateDialog");
                return new DatePickerDialog(this, mDateSetListener, mYear, mMonth, mDay);
        }
        return null;
    }

    @Override
    protected void onPrepareDialog(int id, Dialog dialog)
    {
        switch (id)
        {
            case DATE_DIALOG_ID:
                ((DatePickerDialog) dialog).updateDate(mYear, mMonth, mDay);
                break;
        }
    }

    /**
     * 处理日期控件的Handler
     */
    Handler saleHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            switch (msg.what)
            {
                case PersonalInfoActivity.SHOW_DATAPICK:
                    showDialog(DATE_DIALOG_ID);
                    break;
            }
        }
    };

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.ll_personalhead:
                // 头像
                showDialog();
                break;
            case R.id.ll_personalname:
                // 姓名
                Intent intentToName = new Intent(getApplicationContext(), UpdateNameActivity.class);
                intentToName.putExtra("name", userinfo.getTruename());
                startActivityForResult(intentToName, 4);
                break;
            case  R.id.ll_autograph:
                // 个性签名
                Intent intentTosignTrue = new Intent(getApplicationContext(), UpdateAutographActivity.class);
                intentTosignTrue.putExtra("signature", userinfo.getSignature());
                startActivityForResult(intentTosignTrue, 7);
                break;
            case R.id.ll_sex:
            // 性别
                Intent intentTosex = new Intent(getApplicationContext(), UpdateSexActivity.class);
                intentTosex.putExtra("sex", userinfo.getSex());
                startActivityForResult(intentTosex, 5);
                break;
            case R.id.ll_wedlock:
            // 婚恋状态
                Intent intentToMarry = new Intent(getApplicationContext(), UpdateWedlockActivity.class);
                intentToMarry.putExtra("marry", userinfo.getMarry());
                startActivityForResult(intentToMarry, 6);
                break;
            case R.id.ll_birthday:
                // 生日
                mFired = false;
                Message msg = new Message();
                msg.what = PersonalInfoActivity.SHOW_DATAPICK;
                PersonalInfoActivity.this.saleHandler.sendMessage(msg);
                break;
            case R.id.ll_user_name:
                // 姓名
                Intent intentToUserName = new Intent(getApplicationContext(), UpdateUserNameActivity.class);
                intentToUserName.putExtra("userName", userinfo.getUser_name());
                startActivityForResult(intentToUserName, 8);
                break;
        }
    }

    private void showDialog()
    {
        View view = getLayoutInflater().inflate(R.layout.photo_choose_dialog, null);
        final Dialog dialog = new Dialog(this, R.style.transparentFrameWindowStyle);
        dialog.setContentView(view, new FlowLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        Window window = dialog.getWindow();
        // 设置显示动画
        window.setWindowAnimations(R.style.main_menu_animstyle);
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.x = 0;
        wl.y = getWindowManager().getDefaultDisplay().getHeight();
        // 以下这两句是为了保证按钮可以水平满屏
        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;

        // 设置显示位置
        dialog.onWindowAttributesChanged(wl);
        // 设置点击外围解散
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();

        btn_picture = (Button) window.findViewById(R.id.btn_picture);
        btn_photo = (Button) window.findViewById(R.id.btn_photo);
        btn_cancle = (Button) window.findViewById(R.id.btn_cancle);

        btn_picture.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent1 = new Intent(Intent.ACTION_PICK, null);
                intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent1, 1);
                dialog.dismiss();
            }
        });
        btn_photo.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent2.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "head.jpg")));
                startActivityForResult(intent2, 2);// 采用ForResult打开
                dialog.dismiss();
            }
        });
        btn_cancle.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                dialog.dismiss();
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        switch (requestCode)
        {
            case 1:
                if (resultCode == RESULT_OK)
                {
                    cropPhoto(data.getData());// 裁剪图片
                }
                break;
            case 2:
                if (resultCode == RESULT_OK)
                {
                    File temp = new File(Environment.getExternalStorageDirectory() + "/head.jpg");
                    cropPhoto(Uri.fromFile(temp));// 裁剪图片
                }
                break;
            case 3:
                if (data != null)
                {
                    Bundle extras = data.getExtras();
                    head = extras.getParcelable("data");
                    if (head != null)
                    {
                        /**
                         * 上传服务器代码
                         */
                        setPicToView(head);// 保存在SD卡中
                        ivHead.setImageBitmap(head);// 用ImageView显示出来
                        uploadHead();
                    }
                }
                break;
            case 4:
                // 姓名
                if (resultCode == 1)
                {
                    trueNameTv.setText(data.getStringExtra("truename"));
                    userinfo = ApplicationEx.getInstance().getUserInfo();
                }
                break;
            case 5:
                // 性别
                if (resultCode == 1)
                {
                    String dataSex = data.getStringExtra("sex");
                    if("1".equals(dataSex))
                    {
                        sexTv.setText("男");
                    } else
                    {
                        sexTv.setText("女");
                    }
                    userinfo = ApplicationEx.getInstance().getUserInfo();
                }
                break;
            case 6:
                // 婚恋状态
                if (resultCode == 1)
                {
                    String dataMarry = data.getStringExtra("marry");
                    if ("1".equals(dataMarry))
                    {
                        marryTv.setText("未婚");
                    } else
                    {
                        marryTv.setText("已婚");
                    }
                    userinfo = ApplicationEx.getInstance().getUserInfo();
                }
                break;
            case 7:
                // 个性签名
                if (resultCode == 1)
                {
                    signatureTv.setText(data.getStringExtra("signature"));
                    userinfo = ApplicationEx.getInstance().getUserInfo();
                }
                break;
            case 8:
                // 昵称
                if (resultCode == 1)
                {
                    userNameTv.setText(data.getStringExtra("userName"));
                    userinfo = ApplicationEx.getInstance().getUserInfo();
                }
                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 调用系统的裁剪
     *
     * @param uri
     */
    public void cropPhoto(Uri uri)
    {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 3);
    }

    private void setPicToView(Bitmap mBitmap)
    {
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED))
        { // 检测sd是否可用
            return;
        }
        FileOutputStream b = null;
        File file = new File(path);
        file.mkdirs();// 创建文件夹
        String fileName = path + ApplicationEx.getInstance().getUserInfo().getUserid()+".jpg";// 图片名字

        try
        {
            b = new FileOutputStream(fileName);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件
            urlpath = fileName;
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        } finally
        {
            try
            {
                // 关闭流
                b.flush();
                b.close();
            } catch (IOException e)
            {
                e.printStackTrace();
            }

        }
    }

    /**
     * 功能：头像上传
     */
    private void uploadHead()
    {
        Log.e("uploadHead", "头像上传");
        RequestParams requestParams = new RequestParams();
        try
        {
            File file = new File(urlpath);
            requestParams.put("file", file);
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
            Log.e("printStackTrace", e.getMessage());
        }
        HttpUtil.post(Constants.HEAD_URL, requestParams, new LoadJsonHttpResponseHandler(this, new LoadDatahandler()
        {
            @Override
            public void onStart()
            {
                super.onStart();
                Log.e("onStart", "onStart");
                showLoadingDialog();
            }

            @Override
            public void onSuccess(JSONObject data)
            {
                super.onSuccess(data);
                dimissLoadingDialog();
                try
                {
                    String suc = data.get("suc").toString();
                    String msg = data.get("msg").toString();
                    Log.e("data","data="+data.toString());
                    if ("y".equals(suc))
                    {
                        ToastUtil.getInstant().show(msg);
                    } else
                    {
                        ToastUtil.getInstant().show(msg);
                    }
                } catch (JSONException e)
                {
                    e.printStackTrace();
                    Log.e("JSONException", e.getMessage());
                }
            }
            @Override
            public void onFailure(String error, String message)
            {
                super.onFailure(error, message);
                dimissLoadingDialog();
                Log.e("message", message);
            }
        }));
    }

    /**
     * 功能：修改生日
     */
    private void updateBirthday()
    {
        Log.e("uploadHead", "修改生日");
        RequestParams requestParams = new RequestParams();
        requestParams.put("birthday",birthdayTv.getText().toString());
        HttpUtil.post(Constants.UPDATE_USERINFO_URL, requestParams, new LoadJsonHttpResponseHandler(this, new LoadDatahandler()
        {
            @Override
            public void onStart()
            {
                super.onStart();
                Log.e("onStart", "onStart");
                showLoadingDialog();
            }

            @Override
            public void onSuccess(JSONObject data)
            {
                super.onSuccess(data);
                dimissLoadingDialog();
                try
                {
                    String suc = data.get("suc").toString();
                    String msg = data.get("msg").toString();
                    ToastUtil.getInstant().show(msg);
                    if("y".equals(suc))
                    {
                        UserInfo userinfo = ApplicationEx.getInstance().getUserInfo();
                        userinfo.setBirthday(birthdayTv.getText().toString());
                        ApplicationEx.getInstance().setUserInfo(userinfo);
                    }
                } catch (JSONException e)
                {
                    e.printStackTrace();
                    Log.e("JSONException", e.getMessage());
                }
            }
            @Override
            public void onFailure(String error, String message)
            {
                super.onFailure(error, message);
                dimissLoadingDialog();
                Log.e("message", message);
            }
        }));
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("PersonalInfoActivity");
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("PersonalInfoActivity");
    }
}
