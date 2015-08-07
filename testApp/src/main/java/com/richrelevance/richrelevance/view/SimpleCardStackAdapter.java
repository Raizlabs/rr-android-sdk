package com.richrelevance.richrelevance.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.richrelevance.richrelevance.R;
import com.richrelevance.richrelevance.model.CardModel;
import com.squareup.picasso.Picasso;

import java.util.Collection;

public final class SimpleCardStackAdapter extends CardStackAdapter {

	public SimpleCardStackAdapter(Context mContext) {
		super(mContext);
	}

    public SimpleCardStackAdapter(Context mContext, Collection<? extends CardModel> items) {
        super(mContext, items);
    }

	@Override
	public View getCardView(int position, CardModel product, View convertView, ViewGroup parent) {
		if(convertView == null) {
			LayoutInflater inflater = LayoutInflater.from(getContext());
			convertView = inflater.inflate(R.layout.std_card_inner, parent, false);
			assert convertView != null;
		}

		Picasso.with(getContext())
				.load(product.getImgUrl())
				.placeholder(R.drawable.picture1)
				.error(R.drawable.picture1)
				.into((ImageView) convertView.findViewById(R.id.image_content));
		((TextView) convertView.findViewById(R.id.title)).setText(product.getTitle());
		((TextView) convertView.findViewById(R.id.brand)).setText(product.getBrand());
		((TextView) convertView.findViewById(R.id.price)).setText(product.getPrice());

		return convertView;
	}
}
