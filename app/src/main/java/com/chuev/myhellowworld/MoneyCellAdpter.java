package com.chuev.myhellowworld;

import android.content.Context;
import android.graphics.Color;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
public class MoneyCellAdpter extends RecyclerView.Adapter<MoneyCellAdpter.ItemViewHolder> {

    private List<MoneyItem> mItemsList = new ArrayList<MoneyItem>();
    private final int colorId;
    private ItemsAdapterListener mListener;

    private SparseBooleanArray mSelectedItems = new SparseBooleanArray();

    public void clearSelections() {
        mSelectedItems.clear();
        notifyDataSetChanged();
    }

    public void toggleItem(final int position) {
        mSelectedItems.put(position, !mSelectedItems.get(position));
        notifyDataSetChanged();
    }

    public void clearItem(final int position) {
        mSelectedItems.put(position, false);
        notifyDataSetChanged();
    }

    public int getSelectedSize() {
        int result = 0;
        for (int i = 0; i < mItemsList.size(); i++) {
            if (mSelectedItems.get(i)) {
                result++;
            }
        }
        return result;
    }

    public List<String> getSelectedItemIds() {
        List<String> result = new ArrayList<>();
        int i = 0;
        for (MoneyItem item: mItemsList) {
            if (mSelectedItems.get(i)) {
                result.add(item.getId());
            }
            i++;
        }
        return result;
    }

    public MoneyCellAdpter(final int colorId) {
        this.colorId = colorId;
    }

    public void setListener(final ItemsAdapterListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
        View itemView = View.inflate(parent.getContext(), R.layout.cell_money, null);

        return new ItemViewHolder(itemView, colorId);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemViewHolder holder, final int position) {
        holder.bindItem(mItemsList.get(position), mSelectedItems.get(position));
        holder.setListener(mListener, mItemsList.get(position), position);
    }

    public void addItem(MoneyItem item) {
        mItemsList.add(item);
        notifyDataSetChanged();
    }

    public void clearItems() {
        mItemsList.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mItemsList.size();
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {

        private View mItemView;
        private TextView mNameView;
        private TextView mPriceView;

        public ItemViewHolder(@NonNull final View itemView, final int colorId) {
            super(itemView);

            mItemView = itemView;
            mNameView = itemView.findViewById(R.id.cellmoneytitle);
            mPriceView = itemView.findViewById(R.id.cellmoneycost);

            final Context context = mPriceView.getContext();
            mPriceView.setTextColor(ContextCompat.getColor(context, colorId));
        }

        public void bindItem(final MoneyItem item, final boolean isSelected) {
            mItemView.setSelected(isSelected);
            mNameView.setText(item.getTitle());
            mPriceView.setText(
                    mPriceView.getContext().getResources().getString(R.string.price_with_currency, String.valueOf(item.getValue()))
            );
        }

        public void setListener(final ItemsAdapterListener listener, final MoneyItem item, final int position) {
            mItemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(final View view) {
                    listener.onItemClick(item, position);
                }
            });

            mItemView.setOnLongClickListener(new View.OnLongClickListener() {

                @Override
                public boolean onLongClick(final View view) {
                    listener.onItemLongClick(item, position);
                    return false;
                }
            });
        }
    }
}
