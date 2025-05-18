package com.example.myshop;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CartActivity extends AppCompatActivity implements CartAdapter.OnItemDeleteListener {

    private RecyclerView cartRecyclerView;
    private CartAdapter cartAdapter;
    private List<CartItem> cartItems;
    private TextView cartTotalTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    "default", "Kosár Emlékeztető", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        cartRecyclerView = findViewById(R.id.cartRecyclerView);
        cartTotalTextView = findViewById(R.id.cartTotalTextView);
        cartRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        cartItems = CartManager.getInstance().getCartItems();
        cartAdapter = new CartAdapter(this, cartItems,this);
        cartRecyclerView.setAdapter(cartAdapter);

        updateCartTotal();

        Button backButton = findViewById(R.id.backToShopButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // visszalépés a főoldalra
            }
        });
        Button notifyButton = findViewById(R.id.notifyButton);
        notifyButton.setOnClickListener(v -> sendNotification());
    }
    private void sendNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "default")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Kosár emlékeztető")
                .setContentText("Ne felejtsd el befejezni a vásárlást!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        notificationManager.notify(1, builder.build());
    }
    private void updateCartTotal() {
        int total = 0;
        for (CartItem item : cartItems) {
            int pricePerItem = item.getMobilePackage().getPrice();
            int quantity = item.getQuantity();
            total += pricePerItem * quantity;
        }
        cartTotalTextView.setText("Összesen: " + total + " Ft");
    }

    @Override
    public void onItemDelete(int position) {
        cartItems.remove(position);
        cartAdapter.notifyItemRemoved(position);
        updateCartTotal();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateCartTotal();
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        Toast.makeText(this, "Üdv újra a kosárban!", Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText(this, "Kiléptél a kosár nézetből", Toast.LENGTH_SHORT).show();
    }

}