package com.sdiyuba.tedenglish.activity.search;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;


import com.sdiyuba.tedenglish.adapter.search.SearchAdpter;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.sdiyuba.tedenglish.Constant;
import com.sdiyuba.tedenglish.MyApplication;
import com.sdiyuba.tedenglish.R;
import com.sdiyuba.tedenglish.databinding.ActivitySearchBinding;
import com.sdiyuba.tedenglish.model.bean.InQuireBean;
import com.sdiyuba.tedenglish.model.bean.KeyWordBean;
import com.sdiyuba.tedenglish.model.bean.WordQueryBean;
import com.sdiyuba.tedenglish.presenter.home.SearchPresenter;
import com.sdiyuba.tedenglish.view.home.SearchContract;

/*
搜索
 */
public class SearchActivity extends AppCompatActivity implements SearchContract.SearchView {


    ActivitySearchBinding binding;

    private SearchPresenter presenter;

    private SearchAdpter searchAdpter;

    private RecyclerView rv;

    String words;

    private FlexboxLayoutManager flexboxLayoutManager;

    private EditText word;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySearchBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        rv=findViewById(R.id.KeywordRv);


        word = findViewById(R.id.keywords);

        word.setText(Constant.search_wordKey);



        //点击进入Activity后就让 EditText自动获取焦点并弹出键盘
        word.setFocusable(true);
        word.setFocusableInTouchMode(true);
        word.requestFocus();

        //状态栏变色
        getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);


        searchAdpter = new SearchAdpter(Constant.keyWordList, this);
        rv.setAdapter(searchAdpter);

        flexboxLayoutManager = new FlexboxLayoutManager(this);
        //flexDirection 属性决定主轴的方向（即项目的排列方向）。类似 LinearLayout 的 vertical 和 horizontal。
        flexboxLayoutManager.setFlexDirection(FlexDirection.ROW); //主轴为水平方向，起点在左端。
        //flexWrap 默认情况下 Flex 跟 LinearLayout 一样，都是不带换行排列的，但是flexWrap属性可以支持换行排列。
        flexboxLayoutManager.setFlexWrap(FlexWrap.WRAP); //按正常方向换行
        //justifyContent 属性定义了项目在主轴上的对齐方式。
        flexboxLayoutManager.setJustifyContent(JustifyContent.FLEX_START); //交叉轴的起点对齐。
        rv.setLayoutManager(flexboxLayoutManager);

        //清除
        binding.searchOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                word.setText("");

            }
        });


        binding.search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                words = word.getText().toString().trim();
                if (words.equals("")){
                    Toast.makeText(MyApplication.getContext(),"输入的为空",Toast.LENGTH_SHORT).show();
                } else {

                    Constant.search_wordKey = words;

                    Intent intent = new Intent(SearchActivity.this, InQuireActivity.class);
                    //activity与activity之间的传值
                    intent.putExtra("word",words);
                    Bundle bundle=new Bundle();
                    bundle.putString("word",words);
                    bundle.putString("numss","1");
                    intent.putExtra("numss","1");
                    intent.putExtra("bundle",bundle);
                    startActivity(intent);

                }


            }
        });


    }

    @Override
    public void getKeyWord(KeyWordBean keyWordBean) {



    }

    @Override
    public void getInQuire(InQuireBean inQuireBean) {

    }

    @Override
    public void getWordQuery(WordQueryBean wordQueryBean) {

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
}