package com.tarunsmalviya.hackernews.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tarunsmalviya.hackernews.R;
import com.tarunsmalviya.hackernews.model.Item;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

public class ListAdapter extends RealmRecyclerViewAdapter<Item, ListAdapter.ItemHolder> {

    public ListAdapter(OrderedRealmCollection<Item> data) {
        super(data, true);
        setHasStableIds(true);
    }

    @Override
    public ItemHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ItemHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {
        final Item obj = getItem(position);
        holder.setData(obj);
        holder.invalidate();
    }

    @Override
    public long getItemId(int index) {
        return getItem(index).getId();
    }

    class ItemHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private Item data;

        private ItemHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title);
        }

        public Item getData() {
            return data;
        }

        public void setData(Item data) {
            this.data = data;
        }

        public void invalidate() {
            title.setText(data.getText());
        }
    }
}
