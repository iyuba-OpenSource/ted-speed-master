package com.sdiyuba.tedenglish.fragment.heardDetailFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;


import com.sdiyuba.tedenglish.Constant;
import com.sdiyuba.tedenglish.util.MD5;
import com.sdiyuba.tedenglish.databinding.FragmentSentenceCollectBinding;
import com.sdiyuba.tedenglish.adapter.MyCollectAdpter;
import com.sdiyuba.tedenglish.model.bean.CollectBean;
import com.sdiyuba.tedenglish.model.bean.exportpdfBean;
import com.sdiyuba.tedenglish.presenter.home.MyCollectPresenter;
import com.sdiyuba.tedenglish.presenter.home.likePresenter;
import com.sdiyuba.tedenglish.util.DateUtil;
import com.sdiyuba.tedenglish.view.home.LikeContract;
import com.sdiyuba.tedenglish.view.home.MyCollectContract;

import java.util.List;
import java.util.Objects;

/**
 * 我的收藏
 */
public class SentenceCollectFragment extends Fragment implements MyCollectContract.MycollectView , LikeContract.likeView {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FragmentSentenceCollectBinding binding;
    private MyCollectPresenter myCollectPresenter;

    private MyCollectAdpter myCollectAdpter;

    private List<CollectBean.DataDTO> list;

    private likePresenter likepresenter;

    public SentenceCollectFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static SentenceCollectFragment newInstance(String param1, String param2) {
        SentenceCollectFragment fragment = new SentenceCollectFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = FragmentSentenceCollectBinding.inflate(inflater, container, false);
        View view = binding.getRoot();


        myCollectPresenter = new MyCollectPresenter();
        myCollectPresenter.attchView(this);

        likepresenter =new likePresenter();
        likepresenter.attchView(this);


        init();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
        binding.mycollectRv.setLayoutManager(linearLayoutManager);



        return view;
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
    public void getLike(int result, String type, String voaId, String timing) {
        if(result== 1){
            //重新请求
            String sign = MD5.md5("iyuba" + Constant.uid +Constant.EvaType + Constant.AppId + DateUtil.getDays());
            myCollectPresenter.getCollect(Constant.uid, Constant.EvaType, Constant.AppId, 0, "json", sign);

            Toast.makeText(requireActivity(), "删除成功", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(requireActivity(), "删除失败", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void getexport(exportpdfBean exportpdfBean) {

    }

    @Override
    public void getCollect(CollectBean collectBean) {
        list = collectBean.getData();

        int sum =list.size();
        if (sum!=0){

            binding.collectNullLine.setVisibility(View.GONE);
            binding.mycollectRv.setVisibility(View.VISIBLE);

            for (int i = 0; i <sum; i++) {
                delDate();
            }

            myCollectAdpter = new MyCollectAdpter(list, requireActivity());
            binding.mycollectRv.setAdapter(myCollectAdpter);

            //只能放bean里,  这里不是触发,只是承接adpter里设置得样式点击事件   在这里设置触发事件以后得操作(网络请求等)
            myCollectAdpter.setCallBack(new MyCollectAdpter.CallBack() {
                @Override
                public void clickDel() {

                    //voaid没确认

                    likepresenter.getLike("Iyuba", 0, Constant.AppId, Constant.uid, "del", Constant.orivoaid + "", 0, Constant.EvaType, "json");
                }
            });
        }else {

            binding.collectNullLine.setVisibility(View.VISIBLE);
            binding.mycollectRv.setVisibility(View.GONE);
        }

    }

    public void delDate(){

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getTitleCn()==null){
                list.remove(i);
            }
        }
    }

    public void init(){



        if (Objects.equals(Constant.uid, "0")) {

            binding.collectNullLine.setVisibility(View.VISIBLE);
            binding.mycollectRv.setVisibility(View.GONE);

        }else{

            binding.collectNullLine.setVisibility(View.GONE);
            binding.mycollectRv.setVisibility(View.VISIBLE);

            //获取收藏列表   type传的news,添加收藏得也是, 评测处传的headline
            String sign = MD5.md5("iyuba" + Constant.uid + Constant.EvaType + Constant.AppId + DateUtil.getDays());
            myCollectPresenter.getCollect(Constant.uid, Constant.EvaType, Constant.AppId, 0, "json", sign);

        }

    }

}