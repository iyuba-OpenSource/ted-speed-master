package com.sdiyuba.tedenglish.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.iyuba.headlinelibrary.util.ScreenUtil;
import com.iyuba.module.toolbox.DensityUtil;
import com.iyuba.sdk.data.iyu.IyuNative;
import com.iyuba.sdk.data.ydsdk.YDSDKTemplateNative;
import com.iyuba.sdk.data.youdao.YDNative;
import com.iyuba.sdk.mixnative.MixAdRenderer;
import com.iyuba.sdk.mixnative.MixNative;
import com.iyuba.sdk.mixnative.MixViewBinder;
import com.iyuba.sdk.mixnative.PositionLoadWay;
import com.iyuba.sdk.mixnative.StreamType;
import com.iyuba.sdk.nativeads.NativeAdPositioning;
import com.iyuba.sdk.nativeads.NativeEventListener;
import com.iyuba.sdk.nativeads.NativeRecyclerAdapter;
import com.iyuba.sdk.nativeads.NativeResponse;
import com.sdiyuba.tedenglish.Constant;
import com.sdiyuba.tedenglish.R;
import com.sdiyuba.tedenglish.adapter.HomeAdpter;
import com.sdiyuba.tedenglish.databinding.FragmentTabBinding;
import com.sdiyuba.tedenglish.model.bean.AdEntryBean;
import com.sdiyuba.tedenglish.model.bean.homeBean;
import com.sdiyuba.tedenglish.presenter.home.RequestAdPresenter;
import com.sdiyuba.tedenglish.presenter.home.homePresenter;
import com.sdiyuba.tedenglish.util.PullUpLoading;
import com.sdiyuba.tedenglish.view.home.RequestAdContract;
import com.sdiyuba.tedenglish.view.home.homesContract;
import com.youdao.sdk.nativeads.RequestParameters;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import io.reactivex.annotations.Nullable;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class TabFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, homesContract.homeView, RequestAdContract.RequestAdView {

    FragmentTabBinding binding;

    private HomeAdpter homeAdpter;

    int parentID = -1;

    private homePresenter homePresenter;

    private RequestAdPresenter requestAdPresenter;


    private PullUpLoading pullUpLoading;

    private boolean isFirstRequest = true;

    int pages = 1;

    public static TabFragment newInstance(String label) {
        Bundle args = new Bundle();
        args.putString("label", label);
        TabFragment fragment = new TabFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = FragmentTabBinding.inflate(inflater, container, false);
        View view = binding.getRoot();


        homePresenter = new homePresenter();
        homePresenter.attchView(this);

        requestAdPresenter = new RequestAdPresenter();
        requestAdPresenter.attchView(this);


        //      下拉刷新
        binding.MainSrLayoutNewsList.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                isFirstRequest = true;

                homePresenter.getHome("iOS", "json", Constant.AppId, 0, 1, 10, parentID);

                Toast.makeText(requireContext(), "刷新成功", Toast.LENGTH_SHORT).show();
                binding.MainSrLayoutNewsList.setRefreshing(false);
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
        binding.homeRv2.setLayoutManager(linearLayoutManager);


        //上滑加载
        pullUpLoading = new PullUpLoading(linearLayoutManager) {
            @Override
            public void onLoadMore() {
                binding.loadingLoadend.setVisibility(View.VISIBLE);
                binding.homeRv2.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (homeAdpter == null) {
                            return;
                        }
                        pages = pages + 1;
                        homePresenter.getHome("iOS", "json", Constant.AppId, 0, pages, 10, parentID);

                        binding.loadingLoadend.setVisibility(View.GONE);
                    }
                }, 1000);
            }
        };
        binding.homeRv2.addOnScrollListener(pullUpLoading);


        assert getArguments() != null;
        if (Objects.equals(getArguments().getString("label"), "全部")) {
            parentID = -1;

            homePresenter.getHome("iOS", "json", Constant.AppId, 0, 1, 10, parentID);

        } else if (Objects.equals(getArguments().getString("label"), "文化")) {

            parentID = 201;
            homePresenter.getHome("iOS", "json", Constant.AppId, 0, 1, 10, parentID);


        } else if (Objects.equals(getArguments().getString("label"), "经济")) {

            parentID = 202;
            homePresenter.getHome("iOS", "json", Constant.AppId, 0, 1, 10, parentID);

        } else if (Objects.equals(getArguments().getString("label"), "科学")) {

            //203政治不体现
            parentID = 204;
            homePresenter.getHome("iOS", "json", Constant.AppId, 0, 1, 10, parentID);

        } else if (Objects.equals(getArguments().getString("label"), "科技")) {
            parentID = 205;
            homePresenter.getHome("iOS", "json", Constant.AppId, 0, 1, 10, parentID);

        } else if (Objects.equals(getArguments().getString("label"), "健康")) {
            parentID = 206;
            homePresenter.getHome("iOS", "json", Constant.AppId, 0, 1, 10, parentID);
        } else if (Objects.equals(getArguments().getString("label"), "艺术")) {
            parentID = 207;
            homePresenter.getHome("iOS", "json", Constant.AppId, 0, 1, 10, parentID);

        } else if (Objects.equals(getArguments().getString("label"), "音乐")) {
            parentID = 208;
            homePresenter.getHome("iOS", "json", Constant.AppId, 0, 1, 10, parentID);

        } else if (Objects.equals(getArguments().getString("label"), "教育")) {
            parentID = 209;
            homePresenter.getHome("iOS", "json", Constant.AppId, 0, 1, 10, parentID);

        } else if (Objects.equals(getArguments().getString("label"), "天才")) {
            parentID = 210;
            homePresenter.getHome("iOS", "json", Constant.AppId, 0, 1, 10, parentID);

        } else if (Objects.equals(getArguments().getString("label"), "自然")) {
            parentID = 211;
            homePresenter.getHome("iOS", "json", Constant.AppId, 0, 1, 10, parentID);

        }


        return view;


    }


    @Override
    public void onRefresh() {

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
    public void getHome(homeBean homebean) {

//        List<homeBean.DataDTO> list = homebean.getData();
//        homeAdpter =  new HomeAdpter(list,requireContext());
//        binding.homeRv2.setAdapter(homeAdpter);
//


        if (pullUpLoading.isLoading()) {   //执行上拉刷新

            List<homeBean.DataDTO> list = homebean.getData();


            if (homeAdpter == null) {

                homeAdpter = new HomeAdpter(list, requireContext());
                binding.homeRv2.setAdapter(homeAdpter);

            } else {

                List<homeBean.DataDTO> dataDTOS = homeAdpter.getDatas();
                dataDTOS.addAll(list);   //添加进去 ,并且刷新


                homeAdpter.notifyDataSetChanged();
            }

        } else {

            List<homeBean.DataDTO> list = homebean.getData();

            homeAdpter = new HomeAdpter(list, requireContext());
            binding.homeRv2.setAdapter(homeAdpter);

        }
        pullUpLoading.setLoading(false);

        //会员去广告 22是不知道怎么有个22
//        if (Constant.vipStatus == 0) {
//
//            requestAdPresenter.getAdEntryAll(Constant.AdAppID + "", 2, Constant.uid);
//
//        }


    }

    @Override
    public void getAdEntryAllComplete(AdEntryBean adEntryBean) {



        OkHttpClient mClient = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();

        EnumSet<RequestParameters.NativeAdAsset> desiredAssets = EnumSet.of(
                RequestParameters.NativeAdAsset.TITLE,
                RequestParameters.NativeAdAsset.TEXT,
                RequestParameters.NativeAdAsset.ICON_IMAGE,
                RequestParameters.NativeAdAsset.MAIN_IMAGE,
                RequestParameters.NativeAdAsset.CALL_TO_ACTION_TEXT);
        RequestParameters requestParameters = new RequestParameters.RequestParametersBuilder()
                .location(null)
                .keywords(null)
                .desiredAssets(desiredAssets)
                .build();


        YDNative ydNative = new YDNative(requireActivity(), "edbd2c39ce470cd72472c402cccfb586", requestParameters);

        IyuNative iyuNative = new IyuNative(requireActivity(), "2291", mClient);

        iyuNative.setBase("http://192.168.101.105:8777/getAdEntryAll");
        iyuNative.setImageUrlPrefix("http://192.168.101.105:8777/static/image/");


        // csj - ylh - ks -bd  0224  0229 0236 0233
        YDSDKTemplateNative csjTemplateNative = new YDSDKTemplateNative(requireActivity(), "0224");
        YDSDKTemplateNative ylhTemplateNative = new YDSDKTemplateNative(requireActivity(), "0229");
        YDSDKTemplateNative ksTemplateNative = new YDSDKTemplateNative(requireActivity(), "0236");
        YDSDKTemplateNative bdTemplateNative = new YDSDKTemplateNative(requireActivity(), "0233");

        //添加key
        HashMap<Integer, YDSDKTemplateNative> ydsdkMap = new HashMap<>();
        ydsdkMap.put(StreamType.TT, csjTemplateNative);
        ydsdkMap.put(StreamType.GDT, ylhTemplateNative);
        ydsdkMap.put(StreamType.KS, ksTemplateNative);
        ydsdkMap.put(StreamType.BAIDU, bdTemplateNative);


        MixNative mixNative = new MixNative(ydNative, iyuNative, ydsdkMap);
        PositionLoadWay loadWay = new PositionLoadWay();



        loadWay.setStreamSource(new int[]{
                Integer.parseInt(adEntryBean.getData().getFirstLevel()),
                Integer.parseInt(adEntryBean.getData().getSecondLevel()),
                Integer.parseInt(adEntryBean.getData().getThirdLevel())
        });
        mixNative.setLoadWay(loadWay);


        int dp8px = DensityUtil.dp2px(requireContext(), 8.0f);
        int dp16px = DensityUtil.dp2px(requireContext(), 16.0f);
        int screenWidth = ScreenUtil.getScreenWidth(requireContext());
        int viewWidth = screenWidth - (2 * dp8px);
        int adWidth =  viewWidth;
        int picWidth = (int) (viewWidth * 0.43);
        int picHeight = (picWidth * 2) / 3;
        int adHeight = picHeight + dp16px;
        mixNative.setWidthHeight(adWidth, 0);

//        DisplayMetrics displayMetrics = new DisplayMetrics();
//        WindowManager windowManager = (WindowManager) requireActivity().getSystemService(Context.WINDOW_SERVICE);
//        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
//        mixNative.setWidthHeight(displayMetrics.widthPixels, DensityUtil.dp2px(MyApplication.getContext(), 120));


        int startPosition = 3;
        int positionInterval = 5;
        NativeAdPositioning.ClientPositioning positioning = new NativeAdPositioning.ClientPositioning();

        positioning.addFixedPosition(startPosition);
        positioning.enableRepeatingPositions(positionInterval);
        NativeRecyclerAdapter mAdAdapter = new NativeRecyclerAdapter(requireActivity(), homeAdpter, positioning);




        mAdAdapter.setNativeEventListener(new NativeEventListener() {
            @Override
            public void onNativeImpression(View view, NativeResponse nativeResponse) {

                Log.d("fang789654", "onNativeImpression: ");


            }

            @Override
            public void onNativeClick(View view, NativeResponse nativeResponse) {

                Log.d("fang789654", "onNativeClick: ");



            }
        });
        mAdAdapter.setAdSource(mixNative);

        //关闭广告

        mixNative.setYDSDKTemplateNativeClosedListener(new MixNative.YDSDKTemplateNativeClosedListener() {
            @Override
            public void onClosed(View view) {

                View itemView = (View) view.getParent();
                RecyclerView.ViewHolder viewHolder = binding.homeRv2.getChildViewHolder(itemView);
                int position =viewHolder.getBindingAdapterPosition();
                mAdAdapter.removeAdsWithAdjustedPosition(position);


            }
        });

        MixViewBinder mixViewBinder = new MixViewBinder.Builder(R.layout.item_home_ad)
                .templateContainerId(R.id.item_home_ad)
                .nativeContainerId(R.id.kuangjia)
                .nativeImageId(R.id.photo)
                .nativeTitleId(R.id.home_texttitle)
                .build();

        MixAdRenderer mixAdRenderer = new MixAdRenderer(mixViewBinder);
        mAdAdapter.registerAdRenderer(mixAdRenderer);
        binding.homeRv2.setAdapter(mAdAdapter);
        mAdAdapter.loadAds();
    }

    public void getAdShow(){

        //账号切换  刷新列表

        isFirstRequest = true;

        assert getArguments() != null;
        if (Objects.equals(getArguments().getString("label"), "全部")) {
            parentID = -1;

            homePresenter.getHome("iOS", "json", Constant.AppId, 0, 1, 10, parentID);

        } else if (Objects.equals(getArguments().getString("label"), "文化")) {

            parentID = 201;
            homePresenter.getHome("iOS", "json", Constant.AppId, 0, 1, 10, parentID);


        } else if (Objects.equals(getArguments().getString("label"), "经济")) {

            parentID = 202;
            homePresenter.getHome("iOS", "json", Constant.AppId, 0, 1, 10, parentID);

        } else if (Objects.equals(getArguments().getString("label"), "科学")) {

            //203政治不体现
            parentID = 204;
            homePresenter.getHome("iOS", "json", Constant.AppId, 0, 1, 10, parentID);

        } else if (Objects.equals(getArguments().getString("label"), "科技")) {
            parentID = 205;
            homePresenter.getHome("iOS", "json", Constant.AppId, 0, 1, 10, parentID);

        } else if (Objects.equals(getArguments().getString("label"), "健康")) {
            parentID = 206;
            homePresenter.getHome("iOS", "json", Constant.AppId, 0, 1, 10, parentID);
        } else if (Objects.equals(getArguments().getString("label"), "艺术")) {
            parentID = 207;
            homePresenter.getHome("iOS", "json", Constant.AppId, 0, 1, 10, parentID);

        } else if (Objects.equals(getArguments().getString("label"), "音乐")) {
            parentID = 208;
            homePresenter.getHome("iOS", "json", Constant.AppId, 0, 1, 10, parentID);

        } else if (Objects.equals(getArguments().getString("label"), "教育")) {
            parentID = 209;
            homePresenter.getHome("iOS", "json", Constant.AppId, 0, 1, 10, parentID);

        } else if (Objects.equals(getArguments().getString("label"), "天才")) {
            parentID = 210;
            homePresenter.getHome("iOS", "json", Constant.AppId, 0, 1, 10, parentID);

        } else if (Objects.equals(getArguments().getString("label"), "自然")) {
            parentID = 211;
            homePresenter.getHome("iOS", "json", Constant.AppId, 0, 1, 10, parentID);

        }

    }
}
