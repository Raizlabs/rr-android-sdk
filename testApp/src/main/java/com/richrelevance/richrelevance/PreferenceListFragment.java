package com.richrelevance.richrelevance;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class PreferenceListFragment extends Fragment{

    private RecyclerView recyclerView;

    private PreferenceAdapter adapter;

    private List<String> products;

    public interface LoadingListener {
        void startLoading();

        void stopLoading();
    }

    LoadingListener loadingListener;

    @Override
    public void onAttach(Activity activity) {
        if(!(activity instanceof LoadingListener)) {
            throw new RuntimeException(activity.getLocalClassName() + " does not implement " + LoadingListener.class.getSimpleName());
        }
        loadingListener = (LoadingListener) activity;
        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);
        adapter = new PreferenceAdapter();
        recyclerView = (RecyclerView) rootView.findViewById(R.id.list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        recyclerView.setAdapter(adapter);

        adapter.loadData(products);

        return rootView;
    }

    @Override
    public void onDetach() {
        loadingListener = null;
        super.onDetach();
    }

    public void loadProducts(List<String> products){
        this.products = products;
        if(adapter != null){
            adapter.loadData(products);
        }
    }

    public static class PreferenceAdapter extends RecyclerView.Adapter<PreferenceAdapter.ViewHolder> {

        protected List<String> list = new ArrayList<>();

        public void loadData(List<String> list){
            if(list == null){
                list = new ArrayList<>();
            }
            this.list = list;
            notifyDataSetChanged();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(ViewHolder.LAYOUT_RESOURCE, parent, false);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            String productId = list.get(position);
            holder.productName.setText(productId);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            private static final int LAYOUT_RESOURCE = R.layout.list_item;

            public TextView productName;

            public ViewHolder(View v) {
                super(v);
                productName = (TextView) v.findViewById(R.id.name);
            }
        }
    }

}