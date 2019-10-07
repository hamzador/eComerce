package mobildev.iosm.com.ecom;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class AdminCategoryActivity extends AppCompatActivity {

    private ImageView tshirts, sports,femaleDresses,sweather;
    private ImageView glasses,purses_bags,hats,shoess;
    private  ImageView headphoness,laptops,watches,mobiles;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_category);


        tshirts=findViewById(R.id.tshorts);
        sports=findViewById(R.id.sports_tshorts);
        femaleDresses=findViewById(R.id.female_dresses);
        sweather=findViewById(R.id.sweather);
        glasses=findViewById(R.id.glasses);
        purses_bags=findViewById(R.id.purses_bags_wallets);
        hats=findViewById(R.id.hats_caps);
        shoess=findViewById(R.id.shoess);
        headphoness=findViewById(R.id.headphoness_handFree);
        laptops=findViewById(R.id.laptops_pc);
        watches=findViewById(R.id.watches);
        mobiles=findViewById(R.id.mobiles);



        tshirts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent =new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class);
                myIntent.putExtra("category","tshirts");
                startActivity(myIntent);
            }
        });
        sports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent =new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class);
                myIntent.putExtra("category","sports");
                startActivity(myIntent);
            }
        });
        femaleDresses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent =new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class);
                myIntent.putExtra("category","femaleDresses");
                startActivity(myIntent);
            }
        });
        sweather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent =new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class);
                myIntent.putExtra("category","sweather");
                startActivity(myIntent);
            }
        });
        glasses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent =new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class);
                myIntent.putExtra("category","glasses");
                startActivity(myIntent);
            }
        });
        purses_bags.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent =new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class);
                myIntent.putExtra("category","purses_bags");
                startActivity(myIntent);
            }
        });
        hats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent =new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class);
                myIntent.putExtra("category","hats");
                startActivity(myIntent);
            }
        });
        shoess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent =new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class);
                myIntent.putExtra("category","shoess");
                startActivity(myIntent);
            }
        });

        headphoness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent =new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class);
                myIntent.putExtra("category","headphoness");
                startActivity(myIntent);
            }
        });
        laptops.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent =new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class);
                myIntent.putExtra("category","laptops");
                startActivity(myIntent);
            }
        });
        watches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent =new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class);
                myIntent.putExtra("category","watches");
                startActivity(myIntent);
            }
        });
        mobiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent =new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class);
                myIntent.putExtra("category","mobiles");
                startActivity(myIntent);
            }
        });
    }
}
