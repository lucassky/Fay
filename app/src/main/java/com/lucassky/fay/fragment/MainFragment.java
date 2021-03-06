package com.lucassky.fay.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lucassky.fay.R;
import com.lucassky.fay.activity.PreviewPicActivity;
import com.lucassky.fay.activity.WeiBoDetailActivity;
import com.lucassky.fay.adapter.StatusAdapter;
import com.lucassky.fay.adapter.StatusRVAdapter;
import com.lucassky.fay.model.StatusesResult;
import com.lucassky.fay.model.base.Status;
import com.lucassky.fay.model.base.ThumbnailPic;
import com.lucassky.fay.utils.newwork.HttpManager;
import com.lucassky.fay.utils.newwork.UrlUitl;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MainFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment implements Callback, SwipeRefreshLayout.OnRefreshListener,AdapterView.OnItemClickListener,StatusAdapter.OnAdapterOnClick {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ListView mLVFStatuses;//关注好友的最新微博
//    private LinearLayoutManager mLayoutManager;
    private List<Status> mStatuses = new ArrayList<Status>();
    private StatusAdapter mStatusAdapter;

    private TextView mFooterTV;

    private LinearLayout mFooter;

    private boolean isLoadingMore = false;

    private int pageIndex = 1;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public MainFragment() {
        // Required empty public constructor
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        HttpManager.getStattuesFriends(getActivity(), HttpManager.LOADLAST, 0L, 0L, 20, pageIndex, 0, 0, 0, this);
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        mFooter = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.footer, null);
        mFooterTV = (TextView) mFooter.findViewById(R.id.footer_tv);
        mLVFStatuses = (ListView) view.findViewById(R.id.lv_f_statuses);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swiperefresh);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setRefreshing(true);
        mStatusAdapter = new StatusAdapter(mStatuses, getActivity(),this);
        mLVFStatuses.addFooterView(mFooter);
        mLVFStatuses.setAdapter(mStatusAdapter);

        mLVFStatuses.setOnItemClickListener(this);
        mLVFStatuses.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if ((visibleItemCount + firstVisibleItem) == mStatuses.size() && mStatuses.size()>19) {
                    if (!isLoadingMore) {
                        System.out.println("需要加载更多了");
                        isLoadingMore = true;
//                        HttpManager.getStattuesFriends(getActivity(), HttpManager.LOADMORE, 0L, 0L, 20, pageIndex, 0, 0, 0, MainFragment.this);
                    }
                }
            }
        });

//        mLayoutManager = new LinearLayoutManager(getActivity());
//        mLVFStatuses.setLayoutManager(mLayoutManager);
//        mStatusRVAdapter = new StatusRVAdapter(mStatuses, this);
//        mLVFStatuses.setAdapter(mStatusRVAdapter);
//        mLVFStatuses.setOn(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//            }
//
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                int lastvisiblePos = mLayoutManager.findLastVisibleItemPosition();
//                lastvisiblePos++;
//                if (lastvisiblePos == mStatuses.size()) {
//                    if (!isLoadingMore) {
//                        System.out.println("需要加载更多了");
//                        isLoadingMore = true;
//                        HttpManager.getStattuesFriends(getActivity(), LOADMORE, 0L, 0L, 20, pageIndex, 0, 0, 0, MainFragment.this);
//                    }
//                }
//            }
//        });

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onFailure(Request request, IOException e) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if(isLoadingMore){
//                    if(mLVFStatuses.getFooterViewsCount()>0)
//                        mLVFStatuses.removeFooterView(mFooter);
                    isLoadingMore = false;
                }
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

    }

    private List<Status> mStatuse = new ArrayList<Status>();

    @Override
    public void onResponse(Response response) throws IOException {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
        String str = response.body().string();
        Gson gson = new Gson();
        final StatusesResult statuses = gson.fromJson(str, StatusesResult.class);
        final List<Status> statuse = statuses.getStatuses();

        if (HttpManager.LOADLAST.equals(response.request().tag())) {//loading last statuses
            if (statuse != null && statuse.size() > 0) {
                mStatuse.addAll(0, statuse);
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mStatusAdapter.setmStatuses(mStatuse);
                    }
                });
            }
        } else {
            if (statuse != null && statuse.size() > 0) {
                pageIndex++;
                isLoadingMore = false;
                mStatuse.addAll(statuse);
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mFooterTV.setText("点击加载更多");
                        mStatusAdapter.setmStatuses(mStatuse);
                    }
                });
            }
        }
        System.out.println("response" + statuses.toString());
    }

    @Override
    public void onRefresh() {
        if (mStatuse.size() > 0) {
            isLoadingMore = true;
            HttpManager.getStattuesFriends(getActivity(), HttpManager.LOADLAST, mStatuse.get(0).getId(), 0L, 20, 1, 0, 0, 0, this);
        } else {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onMainClick(Status status) {
        Intent intent = new Intent(getActivity(), WeiBoDetailActivity.class);
        intent.putExtra("status", status);
        startActivity(intent);
        System.out.println(status.getText());
    }


    @Override
    public void onUserPicClick(Status status) {
        System.out.println(status.getUser().getScreen_name());
    }

    @Override
    public void onStatusPicClick(ArrayList<ThumbnailPic> thumbnailPics, int pos) {
        Intent intent = new Intent(getActivity(), PreviewPicActivity.class);
        intent.putParcelableArrayListExtra("thumbnailPics", thumbnailPics);
        intent.putExtra("pos", pos);
        startActivity(intent);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(mStatuses.size()>position){
            Intent intent = new Intent(getActivity(), WeiBoDetailActivity.class);
            intent.putExtra("status", mStatuses.get(position));
            startActivity(intent);
        }else{
            mFooterTV.setText("正在加载更多...");
            HttpManager.getStattuesFriends(getActivity(), HttpManager.LOADMORE, 0L, 0L, 20, pageIndex, 0, 0, 0, MainFragment.this);
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(Uri uri);
    }

}
