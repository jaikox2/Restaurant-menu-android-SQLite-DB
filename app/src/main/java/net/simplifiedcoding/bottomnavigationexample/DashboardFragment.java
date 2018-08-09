package net.simplifiedcoding.bottomnavigationexample;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Belal on 1/23/2018.
 */

public class DashboardFragment extends Fragment  {

    private static final int PICK_IMAGE = 100;
    EditText edtName,edtDetail,edtPrice,edtStamp;
    ImageView  imageView ;
    Button     button;
    FloatingActionButton actionButton;
    Uri imageUri;
    private SQLiteHelper sqLiteHelper = null ;
    public Food food;
    public DbBitmapUtility bitmapUtility;
    byte[] imageByte;

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
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageUri);
                imageByte = bitmapUtility.getBytes(bitmap);
                System.out.println("Add imageByte :"+imageByte);
                Bitmap image = DbBitmapUtility.getImage(imageByte);
                imageView.setImageBitmap(image);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_dashboard, null);


        edtName =(EditText) view.findViewById(R.id.edtName);
        edtDetail =(EditText) view.findViewById(R.id.edtDetail);
        edtPrice = (EditText) view.findViewById(R.id.edtPrice);
        edtStamp = (EditText) view.findViewById(R.id.edtStamp);
         imageView = (ImageView) view.findViewById(R.id.imageView2);
         button = (Button) view.findViewById(R.id.button);
        actionButton = (FloatingActionButton) view.findViewById(R.id.floatingActionButton);


        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveFood();
            }
        });

        return view;
}

    private void saveFood(){
        String name = edtName.getText().toString();
        String detail = edtDetail.getText().toString();
        String price = edtPrice.getText().toString();
        String stamp = edtStamp.getText().toString();
        byte [] image = imageByte;

        sqLiteHelper = new SQLiteHelper(getActivity());


        if(name.isEmpty()){
            //error name is empty
            Toast.makeText(getActivity(), "You must enter a name", Toast.LENGTH_SHORT).show();
        }else if(detail.isEmpty()){
            //error name is empty
            Toast.makeText(getActivity(), "You must enter detail", Toast.LENGTH_SHORT).show();
        }else if(price.isEmpty()){
            //error name is empty
            Toast.makeText(getActivity(), "You must enter an price", Toast.LENGTH_SHORT).show();
        } else if(stamp.isEmpty()){
            //error name is empty
            Toast.makeText(getActivity(), "You must enter an stamp", Toast.LENGTH_SHORT).show();
        }else if(image.equals("null")){
            //error name is empty
            Toast.makeText(getActivity(), "You must enter an image link", Toast.LENGTH_SHORT).show();
        }else {

            food = new Food(name, detail, price, stamp, image);
            boolean result = sqLiteHelper.saveNewFood(food);

            if (result == true) {
                Toast.makeText(getActivity(), "Saved", Toast.LENGTH_SHORT).show();
                edtName.setText("");
                edtDetail.setText("");
                edtPrice.setText("");
                edtStamp.setText("");
                imageView.setImageResource(0);

            } else {
                Toast.makeText(getActivity(), "Can't saved", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
