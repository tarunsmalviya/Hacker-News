package com.tarunsmalviya.hackernews.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tarunsmalviya.hackernews.R;
import com.tarunsmalviya.hackernews.adapter.ListAdapter;
import com.tarunsmalviya.hackernews.model.Item;
import com.tarunsmalviya.hackernews.network.SyncItemList;
import com.tarunsmalviya.hackernews.util.CommonFunction;
import com.tarunsmalviya.hackernews.util.ListType;

import javax.annotation.Nullable;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class ListFragment extends Fragment {

    private static final String TAG = ListFragment.class.getName();
    private static final String ARG_TYPE = "type";

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;

    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    public ListFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static ListFragment newInstance(ListType type) {
        ListFragment fragment = new ListFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_TYPE, type);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();
    }

    public void init() {
        adapter = new ListAdapter(getContext(), getList());
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
    }

    private RealmResults getList() {
        String field = (getType() == ListType.TOP_STORIES ? "isTopItem" : (getType() == ListType.NEW_STORIES ? "isNewItem" : "isBestItem"));
        return Realm.getDefaultInstance().where(Item.class).equalTo(field, true).sort("timestamp", Sort.DESCENDING).findAll();
    }

    public ListType getType() {
        if (null != getArguments())
            return (ListType) getArguments().getSerializable(ARG_TYPE);
        else return null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @android.support.annotation.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);
        setUpRecycleView();
    }

    public void initView(View rootView) {
        swipeRefreshLayout = rootView.findViewById(R.id.swipe_refresh_layout);
        recyclerView = rootView.findViewById(R.id.recycle_view);
    }

    public void setUpRecycleView() {
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Intent intent = new Intent(getActivity(), SyncItemList.class);
        intent.putExtra(ARG_TYPE, getType());
        getActivity().startService(intent);
    }
}
