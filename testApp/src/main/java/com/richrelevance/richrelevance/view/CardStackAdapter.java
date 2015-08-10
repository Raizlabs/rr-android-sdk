package com.richrelevance.richrelevance.view;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;

import com.richrelevance.richrelevance.R;
import com.richrelevance.richrelevance.model.CardModel;

import java.util.ArrayList;
import java.util.Collection;

public abstract class CardStackAdapter extends BaseAdapter {
	private final Context mContext;

	/**
	 * Lock used to modify the content of {@link #mData}. Any write operation
	 * performed on the deque should be synchronized on this lock.
	 */
	private final Object mLock = new Object();
	private ArrayList<CardModel> mData;

	public CardStackAdapter(Context context) {
		mContext = context;
		mData = new ArrayList<CardModel>();
	}

	public CardStackAdapter(Context context, Collection<? extends CardModel> items) {
		mContext = context;
		mData = new ArrayList<CardModel>(items);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		FrameLayout innerWrapper;
		View cardView;
		innerWrapper = new FrameLayout(mContext);
		innerWrapper.setBackgroundColor(Color.TRANSPARENT);
		cardView = getCardView(position, getCardModel(position), null, parent);
		innerWrapper.addView(cardView);

		return cardView;
	}

	protected abstract View getCardView(int position, CardModel model, View convertView, ViewGroup parent);

	public boolean shouldFillCardBackground() {
		return true;
	}

	public void add(CardModel item) {
		synchronized (mLock) {
			mData.add(item);
		}
		notifyDataSetChanged();
	}

	public CardModel pop() {
		CardModel model;
		synchronized (mLock) {
			model = mData.remove(mData.size() - 1);
		}
		notifyDataSetChanged();
		return model;
	}

	@Override
	public Object getItem(int position) {
		return getCardModel(position);
	}

	public CardModel getCardModel(int position) {
		synchronized (mLock) {
			return mData.get(mData.size() - 1 - position);
		}
	}

	@Override
	public int getCount() {
		return mData.size();
	}

	@Override
	public long getItemId(int position) {
		return getItem(position).hashCode();
	}

	public Context getContext() {
		return mContext;
	}
}
