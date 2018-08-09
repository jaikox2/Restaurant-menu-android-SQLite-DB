package net.simplifiedcoding.bottomnavigationexample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class showDetail extends AppCompatActivity {

    private long receivedFoodId;
    TextView edtName,edtDetail,edtPrice,edtStamp;
    ImageView imageView ;
    private SQLiteHelper sqLiteHelper = null ;
    public DbBitmapUtility bitmapUtility;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_detail);
        //add button back navigation
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        edtName =(TextView) findViewById(R.id.tName);
        edtDetail =(TextView) findViewById(R.id.tDetail);
        edtPrice = (TextView) findViewById(R.id.tPrice);
        edtStamp = (TextView) findViewById(R.id.tStamp);
        imageView = (ImageView) findViewById(R.id.imageView2);

        sqLiteHelper = new SQLiteHelper(this);

        try {
            //get intent to get person id
            receivedFoodId = getIntent().getLongExtra("USER_ID", 1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Food queriedFood = sqLiteHelper.getFood(receivedFoodId);

        edtName.setText("เมนู : "+queriedFood.getName());
        edtDetail.setText("รายละเอียด : "+queriedFood.getDetail());
        edtPrice.setText("ราคา : "+queriedFood.getPrice()+"  บาท");
        edtStamp.setText("แต้ม : "+queriedFood.getStamp()+"  แต้ม");
        bitmapUtility.getImageShow(imageView,queriedFood.getImage());



    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if (id == android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
