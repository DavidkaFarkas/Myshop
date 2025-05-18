package com.example.myshop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myshop.MobilePackage;

import java.util.List;

public class PackageAdapter extends RecyclerView.Adapter<PackageAdapter.ViewHolder> {
    private List<MobilePackage> packageList;
    private Context context;

    public PackageAdapter(Context context, List<MobilePackage> packages) {
        this.context = context;
        this.packageList = packages;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, price, description;
        Button addToCart;

        public ViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.packageName);
            price = view.findViewById(R.id.packagePrice);
            description = view.findViewById(R.id.packageDescription);
            addToCart = view.findViewById(R.id.addToCartButton);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MobilePackage pack = packageList.get(position);
        holder.name.setText(pack.getName());
        holder.price.setText(pack.getPrice() + " Ft");
        holder.description.setText(pack.getDescription());
        holder.addToCart.setOnClickListener(v -> {
            CartManager.getInstance().addToCart(pack);
            Toast.makeText(context, pack.getName() + " kosárba rakva!", Toast.LENGTH_SHORT).show();
        });
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.item_animation_fall_down);
        holder.itemView.startAnimation(animation);
    }

    @Override
    public int getItemCount() {
        return packageList.size();
    }
}
