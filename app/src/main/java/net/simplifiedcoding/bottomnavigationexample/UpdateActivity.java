package net.simplifiedcoding.bottomnavigationexample;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class UpdateActivity extends AppCompatActivity {

    private long receivedFoodId;
    private static final int PICK_IMAGE = 100;
    EditText edtName,edtDetail,edtPrice,edtStamp;
    ImageView  imageView ;
    Button     button;
    FloatingActionButton actionButton;
    Uri imageUri;
    private SQLiteHelper sqLiteHelper = null ;
    public Food UpdateFood;
    public DbBitmapUtility bitmapUtility;
    byte[] imageByte ;


    public void openGallery(){
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery,PICK_IMAGE);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            imageUri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), imageUri);
                imageByte = bitmapUtility.getBytes(bitmap);
                System.out.println("Update 1 imageByte"+imageByte);
                Bitmap image = DbBitmapUtility.getImage(imageByte);
                imageView.setImageBitmap(image);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        edtName =(EditText) findViewById(R.id.edtName);
        edtDetail =(EditText) findViewById(R.id.edtDetail);
        edtPrice = (EditText) findViewById(R.id.edtPrice);
        edtStamp = (EditText) findViewById(R.id.edtStamp);
        imageView = (ImageView) findViewById(R.id.imageView2);
        button = (Button) findViewById(R.id.button);
        actionButton = (FloatingActionButton) findViewById(R.id.floatingActionButton);

        sqLiteHelper = new SQLiteHelper(this);

        try {
            //get intent to get person id
            receivedFoodId = getIntent().getLongExtra("USER_ID", 1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        /***populate user data before update***/
        Food queriedFood = sqLiteHelper.getFood(receivedFoodId);
        //set field to this user data
        edtName.setText(queriedFood.getName());
        edtDetail.setText(queriedFood.getDetail());
        edtPrice.setText(queriedFood.getPrice());
        edtStamp.setText(queriedFood.getStamp());
        bitmapUtility.getImageShow(imageView,queriedFood.getImage());

        imageByte = queriedFood.getImage();

        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });

        //listen to add button click to update
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateFood();
            }
        });





    }

    private void updateFood(){
        String name = edtName.getText().toString();
        String detail = edtDetail.getText().toString();
        String price = edtPrice.getText().toString();
        String stamp = edtStamp.getText().toString();

        byte [] image = imageByte;

        sqLiteHelper = new SQLiteHelper(this);

        if(name.isEmpty()){
            //error name is empty
            Toast.makeText(this, "You must enter a name", Toast.LENGTH_SHORT).show();
        }else if(detail.isEmpty()){
            //error name is empty
            Toast.makeText(this, "You must enter detail", Toast.LENGTH_SHORT).show();
        }else if(price.isEmpty()){
            //error name is empty
            Toast.makeText(this, "You must enter an price", Toast.LENGTH_SHORT).show();
        } else if(stamp.isEmpty()){
            //error name is empty
            Toast.makeText(this, "You must enter an stamp", Toast.LENGTH_SHORT).show();
        }else if(image.equals("null")){
            //error name is empty
            Toast.makeText(this, "You must enter an image link", Toast.LENGTH_SHORT).show();
        }else {

            System.out.println("Update imageByte"+image);

            UpdateFood = new Food(name,detail,price,stamp,image);
           sqLiteHelper.updateFoodRecord(receivedFoodId, this, UpdateFood);
            goBackHome();
        }


    }

    private void goBackHome(){
        startActivity(new Intent(this, MainActivity.class));
    }


}
