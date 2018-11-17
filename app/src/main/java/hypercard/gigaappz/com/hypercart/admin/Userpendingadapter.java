package hypercard.gigaappz.com.hypercart.admin;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import hypercard.gigaappz.com.hypercart.R;
import hypercard.gigaappz.com.hypercart.model_class.Shop;

public class Userpendingadapter extends RecyclerView.Adapter<Userpendingadapter.MyViewHolder> {

    private List<Shop> shopList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public EditText mobile,shopname,shopplace,shopcontact;
        Button accept,reject;

        public MyViewHolder(View view) {
            super(view);
            mobile = (EditText) view.findViewById(R.id.mobile_text);
            shopname = (EditText) view.findViewById(R.id.shopname);
            shopplace = (EditText) view.findViewById(R.id.shopplace);
            shopcontact = (EditText) view.findViewById(R.id.contact);
            accept = (Button) view.findViewById(R.id.accept);
            reject = (Button) view.findViewById(R.id.reject);
        }
    }


    public Userpendingadapter(List<Shop> shopList) {
        this.shopList = shopList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_card_view, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Shop shop = shopList.get(position);

        holder.mobile.setText(shop.mobile);
        holder.shopname.setText(shop.name);
        holder.shopplace.setText(shop.place);
        holder.shopcontact.setText(shop.contact);
    }

    @Override
    public int getItemCount() {
        return shopList.size();
    }
}