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
import android.widget.AdapterView;
import android.widget.ListView;
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
public class MainFragment extends Fragment implements Callback, AdapterView.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener,StatusRVAdapter.RVAdapterOnClick {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mLVFStatuses;//关注好友的最新微博
    private LinearLayoutManager mLayoutManager ;
    private List<Status> mStatuses = new ArrayList<Status>();
//    private StatusAdapter mAdapter;
    private StatusRVAdapter mStatusRVAdapter;
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
        HttpManager.getStattuesFriends(getActivity(),UrlUitl.STATUSES_FRIENDS_TIMELINE, 0L, 0L, 100, 1, 0, 0, 0, this);
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        mLVFStatuses = (RecyclerView) view.findViewById(R.id.lv_f_statuses);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swiperefresh);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setRefreshing(true);
//        mAdapter = new StatusAdapter(mStatuses, getActivity());
//        mLVFStatuses.setAdapter(mAdapter);
//        mLVFStatuses.setOnItemClickListener(this);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mLVFStatuses.setLayoutManager(mLayoutManager);
        mStatusRVAdapter = new StatusRVAdapter(mStatuses,this);
        mLVFStatuses.setAdapter(mStatusRVAdapter);
        mLVFStatuses.setItemAnimator(new DefaultItemAnimator());
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
//                List<Status> statuses = gson.fromJson(str, new TypeToken<List<Status>>() {}.getType());
        final List<Status> statuse = statuses.getStatuses();
        if (statuse != null && statuse.size() > 0) {
            mStatuse.addAll(0, statuse);
            mHandler.post(new Runnable() {
                @Override
                public void run() {
//                    mAdapter.setmStatuses(mStatuse);
                    mStatusRVAdapter.setMStatuses(mStatuse);
                }
            });
        }
        System.out.println("response" + statuses.toString());
}

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        System.out.println("position = " + position);
    }

    @Override
    public void onRefresh() {
        if (mStatuse.size() > 0) {
            HttpManager.getStattuesFriends(getActivity(), UrlUitl.STATUSES_FRIENDS_TIMELINE, mStatuse.get(0).getId(), 0L, 100, 1, 0, 0, 0, this);
        } else {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onMainClick(Status status) {
        Intent intent = new Intent(getActivity(), WeiBoDetailActivity.class);
        intent.putExtra("status",status);
        startActivity(intent);
        System.out.println(status.getText());
}

    @Override
    public void onUserPicClick(Status status) {

    }

    @Override
    public void onStatusPicClick(ArrayList<ThumbnailPic> thumbnailPics, int pos) {
        Intent intent = new Intent(getActivity(), PreviewPicActivity.class);
        intent.putParcelableArrayListExtra("thumbnailPics",thumbnailPics);
        intent.putExtra("pos",pos);
        startActivity(intent);
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
