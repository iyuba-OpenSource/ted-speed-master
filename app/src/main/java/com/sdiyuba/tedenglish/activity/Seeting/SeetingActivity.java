package com.sdiyuba.tedenglish.activity.Seeting;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.sdiyuba.tedenglish.Constant;
import com.sdiyuba.tedenglish.R;
import com.sdiyuba.tedenglish.activity.privice.TiaokuanActivity;
import com.sdiyuba.tedenglish.activity.privice.YinsiActivity;
import com.sdiyuba.tedenglish.databinding.ActivitySeetingBinding;
import com.sdiyuba.tedenglish.fragment.myFragment;
import com.sdiyuba.tedenglish.model.bean.LoginBean;
import com.sdiyuba.tedenglish.model.bean.LogoutBean;
import com.sdiyuba.tedenglish.presenter.home.LoginPresenter;
import com.sdiyuba.tedenglish.util.MD5;
import com.sdiyuba.tedenglish.view.home.LoginContract;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
安全与隐私
 */
public class SeetingActivity extends AppCompatActivity implements LoginContract.LoginView {

    private ActivitySeetingBinding binding;

    private LoginPresenter loginPresenter;

    private String password;
    private myFragment myFragment =new myFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySeetingBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        loginPresenter =  new LoginPresenter();
        loginPresenter.attchView(this);

        binding.backs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        //隐私
        binding.privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SeetingActivity.this, YinsiActivity.class);
                startActivity(intent);
            }

        });

        //条款
        binding.tiaokuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SeetingActivity.this, TiaokuanActivity.class);
                startActivity(intent);
            }

        });

        //注销
        binding.zhuxiao.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                if (Constant.username != null) {
                    AlertDialog alertDialog2 = new AlertDialog.Builder(SeetingActivity.this)
                            .setTitle("注销账号")  //标题
                            .setMessage("是否确定注销")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {//添加"Yes"按钮
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    //Toast.makeText(requireActivity(), "这是确定按钮", Toast.LENGTH_SHORT).show();
                                    View view1 = LayoutInflater.from(SeetingActivity.this).inflate(R.layout.querenmima, null);
                                    //2.获取布局中的控件
                                    EditText querenmima = view1.findViewById(R.id.mima);

                                    AlertDialog alertDialog3 = new AlertDialog.Builder(SeetingActivity.this)

                                            .setTitle("注销账号")
                                            .setView(view1)
                                            .setPositiveButton("确认注销", new DialogInterface.OnClickListener() {//添加"Yes"按钮
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {


                                                    password = querenmima.getText().toString().trim();

                                                    String ss;
                                                    try {
                                                        ss = URLEncoder.encode(Constant.username, "UTF-8");
                                                    } catch (UnsupportedEncodingException e) {
                                                        throw new RuntimeException(e);
                                                    }

                                                    String pwd = MD5.md5(password);
                                                    String sign = MD5.md5("11004" + Constant.username + pwd + "iyubaV2");



                                                    loginPresenter.getLogin(11004, ss, pwd, sign, "json");
                                                }
                                            })

                                            .setNegativeButton("取消注销", new DialogInterface.OnClickListener() {//添加取消
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
//                                                Toast.makeText(requireActivity(), "您还未登录", Toast.LENGTH_SHORT).show();
                                                }
                                            })
                                            .create();
                                    alertDialog3.show();


                                }
                            })

                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {//添加取消
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

//                        Toast.makeText(requireActivity(), "您还未登录", Toast.LENGTH_SHORT).show();

                                }
                            })
                            .create();
                    alertDialog2.show();
                }else {
                    Toast.makeText(SeetingActivity.this, "请先登录~", Toast.LENGTH_SHORT).show();

                }


            }
        });





        //第三方清单
        binding.tripartelist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SeetingActivity.this, TripartitelistActivity.class));
            }
        });

        //个人信息清单
        binding.personlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SeetingActivity.this, PersonallistActivity.class));
            }
        });

    }

    @Override
    public void showLoading(String msg) {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void toast(String msg) {

    }

    @Override
    public void getLogin(LoginBean loginBean) {
        if ((loginBean.getResult()).equals("101")) {

            String pwd = MD5.md5(password);
            String username;
            try {
                username = URLEncoder.encode(Constant.username, "UTF-8");
            } catch (
                    UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
            String sign = MD5.md5(11005 + Constant.username + pwd + "iyubaV2");
            loginPresenter.getLogout("11005", "json", username, pwd, sign);


        }else {

            Toast.makeText(SeetingActivity.this, "密码错误,请重新输入", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void getLogout(LogoutBean logoutbean) {
        String result = logoutbean.getResult();


        if (result.equals("101")) {

            SharedPreferences userInfo =this.getSharedPreferences("useruid", MODE_PRIVATE);
            SharedPreferences.Editor editor = userInfo.edit();
            editor.remove("useruid");
            editor.commit();


            Constant.username = null;
            Constant.uid = String.valueOf(0);

            Constant.vipStatus = 0;
            Constant.Eva_Sum = 0;


            Toast.makeText(SeetingActivity.this, "账户已注销", Toast.LENGTH_SHORT).show();


            myFragment.onResume();

            finish();

        } else {
            Toast.makeText(SeetingActivity.this, "注销失败", Toast.LENGTH_SHORT).show();
        }
    }
}