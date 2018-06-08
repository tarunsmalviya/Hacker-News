package com.tarunsmalviya.hackernews.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tarunsmalviya.hackernews.R;
import com.tarunsmalviya.hackernews.model.Item;
import com.tarunsmalviya.hackernews.util.CommonFunction;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

public class ListAdapter extends RealmRecyclerViewAdapter<Item, ListAdapter.ItemHolder> {

    private Context context;

    public ListAdapter(Context context, OrderedRealmCollection<Item> data) {
        super(data, true);
        this.context = context;
        setHasStableIds(true);
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ItemHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {
        final Item obj = getItem(position);
        holder.setData(obj);
        if (holder.getData().getSynced() == 0) {
            holder.invalidate(true);
            CommonFunction.fetchItem(context, holder.getData().getId());
        } else
            holder.invalidate(false);
    }

        @Override
    public long getItemId(int index) {
        return getItem(index).getId();
    }

    class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private View card;
        private TextView byTxt, timestampTxt, titleTxt, commentTxt, scoreTxt;
        private Item data;

        private ItemHolder(View view) {
            super(view);
            card = view.findViewById(R.id.item_card);
            byTxt = view.findViewById(R.id.by_txt);
            timestampTxt = view.findViewById(R.id.timestamp_txt);
            titleTxt = view.findViewById(R.id.title_txt);
            commentTxt = view.findViewById(R.id.comment_txt);
            scoreTxt = view.findViewById(R.id.score_txt);

            card.setOnClickListener(this);
        }

        public Item getData() {
            return data;
        }

        public void setData(Item data) {
            this.data = data;
        }

        public void invalidate(boolean reset) {
            if (reset) {
                byTxt.setText("...");
                timestampTxt.setText("...");
                titleTxt.setText("...");
                commentTxt.setText("...");
                scoreTxt.setText("...");
            } else {
                byTxt.setText("by " + data.getBy());
                timestampTxt.setText(String.valueOf(DateUtils.getRelativeTimeSpanString(data.getTimestamp() * 1000, System.currentTimeMillis(), DateUtils.FORMAT_ABBREV_ALL)));
                titleTxt.setText(data.getTitle());
                commentTxt.setText(data.getKids().size() + " comments");
                scoreTxt.setText(data.getScore() + " pts");
            }
        }

        @Override
        public void onClick(View v) {

        }
    }
}
