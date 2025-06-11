package com.sdiyuba.tedenglish.fragment.heardDetailFragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sdiyuba.tedenglish.Constant;
import com.sdiyuba.tedenglish.adapter.HeardHistoryAdapter;
import com.sdiyuba.tedenglish.sql.AppDataBase;
import com.sdiyuba.tedenglish.sql.TestDao;
import com.sdiyuba.tedenglish.sql.TestRoom;
import com.sdiyuba.tedenglish.databinding.FragmentHeardHistoryBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 *  收听记录
 */
public class HeardHistoryFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FragmentHeardHistoryBinding binding;

    private HeardHistoryAdapter adpter;

    public HeardHistoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment heardHistoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HeardHistoryFragment newInstance(String param1, String param2) {
        HeardHistoryFragment fragment = new HeardHistoryFragment();
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

        binding = FragmentHeardHistoryBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
//        linearLayoutManager.setStackFromEnd(true);//列表再底部开始展示，反转后由上面开始展示
//        linearLayoutManager.setReverseLayout(true);//列表翻转
        binding.downloadRv.setLayoutManager(linearLayoutManager);


        return view;
    }

    public void init() {

        if (!Objects.equals(Constant.uid, "0")) {

            binding.nullLine.setVisibility(View.GONE);
            binding.downloadRv.setVisibility(View.VISIBLE);

            new Thread(() -> {


                AppDataBase db = Room.databaseBuilder(requireActivity(), AppDataBase.class, "testRoom").fallbackToDestructiveMigration().build();
                TestDao testDao = db.testDao();


//            List<TestRoom> list = testDao.getAll();

                List<TestRoom> list = testDao.selectUid(Constant.uid);

                List<TestRoom> reviewingdataTemp = new ArrayList<>();

                for (int j = 0; j < list.size(); j++) {

                    String timess = list.get(j).getStorageTime().split(" ")[0];
                    System.out.println(list.get(j).getStorageTime().split(" ")[0] + "***********/" + timess);

                    if (list.get(j).getStorageTime().split(" ")[0].equals(timess)) {
                        reviewingdataTemp.add(list.get(j));
                    }
                }
//
//            reviewingdataTemp.get(reviewingdataTemp.size()-1).setStorageTime("2024-06-15");
//            reviewingdataTemp.get(reviewingdataTemp.size()-3).setStorageTime("2024-06-14");


                //列表倒叙显示
                Collections.reverse(list);


                //线程操作UI   用UI线程
                requireActivity().runOnUiThread(() -> {


                    if (list.size() != 0) {

                        adpter = new HeardHistoryAdapter(requireActivity(), list, reviewingdataTemp);
                        binding.downloadRv.setAdapter(adpter);

                        adpter.setCallBack(new HeardHistoryAdapter.CallBack() {
                            @Override
                            public void delete(TestRoom testRoom) {
//                        new Thread() {
//                            @Override
//                            public void run() {
//                                AppDataBase db1 = Room.databaseBuilder(MydownloadActivity.this
//                                        , AppDataBase.class, "testRoom").fallbackToDestructiveMigration().build();
//                                TestDao testDao1 = db1.testDao();
//                                TestRoom testRoom = new TestRoom();
//
//                                OriDateBase oridb = Room.databaseBuilder(MydownloadActivity.this, OriDateBase.class, "OriRoom").fallbackToDestructiveMigration().build();
//                                OriDao oriDao = oridb.oriDao();
//                                OriRoom oriRoom = new OriRoom();
//
//                                List<OriRoom> list = oriDao.getOri(Constant.downvoaid + "");
//                                for (int i = 0; i < list.size(); i++) {
//                                    oriRoom.voaid = Constant.downvoaid;
//                                    oriRoom.ParaId = list.get(i).ParaId;
//                                    oriRoom.IdIndex = list.get(i).IdIndex;
//                                    oriDao.deleteRoom(oriRoom);
//                                }
//
//
//                                testRoom.voaid = Constant.downvoaid;
//                                testDao1.deleteRoom(testRoom);
//
//                                for (int i = 0; i < adpter.getDatas().size(); i++) {
//
//                                    if (adpter.getDatas().get(i).voaid.equals(testRoom.voaid)) {
//
//                                        adpter.getDatas().remove(i);
//
//
//                                        runOnUiThread(new Runnable() {
//                                            @Override
//                                            public void run() {
//
//                                                adpter.notifyDataSetChanged();
//
//                                            }
//
//                                        });
//
//                                        deleteFolderFilepic(Constant.DOWNLOAD_PIC + Constant.downvoaid + ".jpg", true);
//                                        deleteFolderFilevideo(Constant.DOWNLOAD_VIDEO + Constant.downvoaid + ".mp4", true);
//
//                                        break;
//                                    }
//
//                                }
//
//
//                            }
//                        }.start();

                            }
                        });
                    } else {
                        binding.nullLine.setVisibility(View.VISIBLE);
                        binding.downloadRv.setVisibility(View.GONE);
                    }



                });


            }).start();
        } else {
            binding.nullLine.setVisibility(View.VISIBLE);
            binding.downloadRv.setVisibility(View.GONE);
        }


    }
}