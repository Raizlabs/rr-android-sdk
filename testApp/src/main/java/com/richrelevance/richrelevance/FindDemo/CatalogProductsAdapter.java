package com.richrelevance.richrelevance.FindDemo;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.richrelevance.find.search.SearchResultProduct;
import com.richrelevance.richrelevance.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public abstract class CatalogProductsAdapter extends RecyclerView.Adapter<CatalogProductsAdapter.SearchProductViewHolder> {

    protected abstract void onNotifiedDataSetChanged(boolean hasProducts);

    private List<SearchResultProduct> products = new ArrayList<>();

    public abstract void onProductClicked(SearchResultProduct product);

    @Override
    public SearchProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(SearchProductViewHolder.LAYOUT_RESOURCE, parent, false);
        SearchProductViewHolder viewHolder = new SearchProductViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(SearchProductViewHolder holder, int position) {
        final SearchResultProduct product = products.get(position);
        holder.bind(product, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onProductClicked(product);
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public void setProducts(List<SearchResultProduct> products) {
        if(products == null) {
            products = new ArrayList<>();
        }
        this.products = products;
        notifyDataSetChanged();
        onNotifiedDataSetChanged(getItemCount() > 0);
    }

    public void addProducts(List<SearchResultProduct> products) {
        this.products.addAll(products);
        notifyDataSetChanged();
        onNotifiedDataSetChanged(getItemCount() > 0);
    }

    public static class SearchProductViewHolder extends RecyclerView.ViewHolder {

        public static final int LAYOUT_RESOURCE = R.layout.listing_search_product_card;

        private View view;
        private ImageView image;
        private TextView name;
        private TextView brand;
        private TextView price;

        public SearchProductViewHolder(View itemView) {
            super(itemView);

            view = itemView;
            image = (ImageView) itemView.findViewById(R.id.product_image);
            name = (TextView) itemView.findViewById(R.id.product_name);
            brand = (TextView) itemView.findViewById(R.id.product_brand);
            price = (TextView) itemView.findViewById(R.id.product_price);
        }

        public void bind(SearchResultProduct product, View.OnClickListener itemClickListener) {
            Picasso.with(view.getContext()).load(product.getImageId()).into(image);
            name.setText(product.getName());
            brand.setText(product.getBrand());

            //Todo: check with backend for return value -1
            if (product.getSalesPriceCents() != -1) {
                price.setText(String.format("$%s.%s", Integer.toString(product.getSalesPriceCents() / 100), String.format("%-2s", Integer.toString(product.getSalesPriceCents() % 100)).replace(" ", "0")));
            }

            view.setOnClickListener(itemClickListener);
        }
    }
}
