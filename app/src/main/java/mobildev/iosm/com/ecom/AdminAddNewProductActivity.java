package mobildev.iosm.com.ecom;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.style.QuoteSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class AdminAddNewProductActivity extends AppCompatActivity {
    private String categoryName,description,pName,price,saveCurrentDate,saveCurrentTime;
    private Button AddNewProductButton;
    private EditText inputProductName,inputProductDescreption,inputProductPrice;
    private ImageView inputProductImage;
    private static final  int GalloryPick=1;
    private Uri imageUri;
    private String productRandomKey,dawnloadImageUrl;
    //dossier pour stoquer les images
    private StorageReference ProductImageReference;
    private DatabaseReference ProductRef;
    ProgressDialog LoadingBar;
    private static final int PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_new_product);

        categoryName = getIntent().getExtras().get("category").toString();

        LoadingBar = new ProgressDialog(this);

       ProductImageReference = FirebaseStorage.getInstance().getReference().child("Product Images");
       ProductRef = FirebaseDatabase.getInstance().getReference().child("Products");

        Toast.makeText(this, categoryName, Toast.LENGTH_SHORT).show();

        AddNewProductButton=findViewById(R.id.add_new_product);
        inputProductImage=findViewById(R.id.select_product_image);
        inputProductDescreption=findViewById(R.id.product_descryption);
        inputProductPrice=findViewById(R.id.product_price);
        inputProductName=findViewById(R.id.product_name);

        inputProductImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oppenGallory();
            }
        });
        AddNewProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateProductData();
            }
        });

    }

    private void oppenGallory() {
        Intent galloryIntent = new Intent();
        galloryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galloryIntent.setType("image/*");
        startActivityForResult(galloryIntent,GalloryPick);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==GalloryPick  && resultCode==RESULT_OK && data != null){
            imageUri =data.getData();
            inputProductImage.setImageURI(imageUri);
        }
    }
    private void validateProductData(){
        description=inputProductDescreption.getText().toString();
        price=inputProductPrice.getText().toString();
        pName=inputProductName.getText().toString();
        if(imageUri==null){
            Toast.makeText(this, "Product image is mandatory ...", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(description)){
            Toast.makeText(this, "Please write the Product Description ...", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(price)){
            Toast.makeText(this, "Please write the Product Price ...", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(pName)){
            Toast.makeText(this, "Please write the Product Name ...", Toast.LENGTH_SHORT).show();
        }
        else{
            storeImageInformation();
        }



    }

    private void storeImageInformation() {
        LoadingBar.setTitle("Adding New product");
        LoadingBar.setMessage("Dear Admin, Please Wait , While We Are Adding The New Product.");
        LoadingBar.setCanceledOnTouchOutside(false);
        LoadingBar.show();

        Calendar calendar=Calendar.getInstance();
        SimpleDateFormat currentDate=new SimpleDateFormat("mm,dd,yyyy");
        saveCurrentDate=currentDate.format(calendar.getTime());
        SimpleDateFormat currentTime=new SimpleDateFormat("hh:mm:ss a");

        saveCurrentTime=currentTime.format(calendar.getTime());
        productRandomKey = saveCurrentDate+saveCurrentTime;

//linck of product image  //on peut enlevé getLAstPhathSegent
        final StorageReference filePath=ProductImageReference.child(imageUri.getLastPathSegment()+productRandomKey +".jpg");
        final UploadTask uploadTask = filePath.putFile(imageUri);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                String message =e.toString();
                Toast.makeText(AdminAddNewProductActivity.this, "Error"+message, Toast.LENGTH_SHORT).show();
                LoadingBar.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(AdminAddNewProductActivity.this, "Product Image Uploaded Succeflly", Toast.LENGTH_SHORT).show();
                //Storaged the link in database
                Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if(!task.isSuccessful()){
                            throw  task.getException();
                        }

                        dawnloadImageUrl=filePath.getDownloadUrl().toString();

                        return filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task)
                    {
                        if(task.isSuccessful())
                        {
                           // requestPermission();
                            dawnloadImageUrl=task.getResult().toString();
                            Toast.makeText(AdminAddNewProductActivity.this, "got Product Image Url Successfully", Toast.LENGTH_SHORT).show();
                            saveProductInfoToDatabase();
                        }
                    }
                });
            }
        });
    }
   /* private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(AdminAddNewProductActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(AdminAddNewProductActivity .this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(AdminAddNewProductActivity.this, "Write External Storage permission allows us to do store images. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(AdminAddNewProductActivity.this,
                    new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }*/
    private void saveProductInfoToDatabase()
    {
        HashMap<String, Object>  productMap= new HashMap<>();
        productMap.put("pid", productRandomKey);//pid = product ID
        productMap.put("date", saveCurrentDate);
        productMap.put("time", saveCurrentTime);
        productMap.put("Description", description);
        productMap.put("category", categoryName);
        productMap.put("price", price);
        productMap.put("image", dawnloadImageUrl);
        productMap.put("name", pName);

        ProductRef.child(productRandomKey).updateChildren(productMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        if(task.isSuccessful())
                        {
//dans le viedo les 2 sont inversé


                            Intent myIntent =new Intent(AdminAddNewProductActivity.this,AdminCategoryActivity.class);
                            startActivity(myIntent);
                            LoadingBar.dismiss();
                            Toast.makeText(AdminAddNewProductActivity.this, "Product Is Added successfully", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            LoadingBar.dismiss();
                            String message=task.getException().toString();
                            Toast.makeText(AdminAddNewProductActivity.this, "Error"+message, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}
