package com.example.myshop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    private Context context;
    private List<CartItem> cartItems;
    private OnItemDeleteListener deleteListener;

    public interface OnItemDeleteListener {
        void onItemDelete(int position);
    }

    public CartAdapter(Context context, List<CartItem> cartItems,OnItemDeleteListener listener) {
        this.context = context;
        this.cartItems = cartItems;
        this.deleteListener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, quantity, price;
        Button deleteButton;

        public ViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.cartItemName);
            quantity = view.findViewById(R.id.cartItemQuantity);
            price = view.findViewById(R.id.cartItemPrice);
            deleteButton = view.findViewById(R.id.buttonDelete);
        }
    }

    @NonNull
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cart_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.ViewHolder holder, int position) {
        CartItem item = cartItems.get(position);
        holder.name.setText(item.getMobilePackage().getName());
        holder.quantity.setText("Mennyiség: " + item.getQuantity());
        int totalPrice = item.getQuantity() * item.getMobilePackage().getPrice();
        holder.price.setText("Összesen: " + totalPrice + " Ft");

        holder.deleteButton.setOnClickListener(v -> {
            if (deleteListener != null) {
                deleteListener.onItemDelete(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }
}
