package mobildev.iosm.com.ecom.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import mobildev.iosm.com.ecom.R;
import mobildev.iosm.com.ecom.interfaces.ItemClickListner;

public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public  TextView txtProductName,txtProductDescreption,txtProductPrice;
    public ImageView imageView;
    public ItemClickListner listner;

    public ProductViewHolder(View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.product_image);
        txtProductName = itemView.findViewById(R.id.product_name);
        txtProductDescreption = itemView.findViewById(R.id.product_description);
        txtProductPrice = itemView.findViewById(R.id.product_price);
    }
    public void setItemClickListner (ItemClickListner listner){
        this.listner = listner;
    }

    @Override
    public void onClick(View v) {
        listner.onClick(v, getAdapterPosition(), false);
    }
}
