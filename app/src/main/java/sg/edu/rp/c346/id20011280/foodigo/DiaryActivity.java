package sg.edu.rp.c346.id20011280.foodigo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;

public class DiaryActivity extends AppCompatActivity {

    EditText etName,etDesc,etPrice;

    ArrayList<Food> FoodList;

    ArrayAdapter adapter;

    Button btnAdd;

    ToggleButton btnDel,btnUp;

    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);

        etName = findViewById(R.id.name);
        etDesc = findViewById(R.id.desc);
        etPrice = findViewById(R.id.price);
        btnAdd = findViewById(R.id.Add);
        btnDel = findViewById(R.id.Delete);
        btnUp = findViewById(R.id.Update);
        lv = findViewById(R.id.LV);

        FoodList = new ArrayList<>();
        DBHelper dbh = new DBHelper(DiaryActivity.this);
        FoodList = dbh.getAllFood();
        adapter = new CustomAdapter(DiaryActivity.this,R.layout.row,FoodList);
        lv.setAdapter(adapter);
        dbh.close();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                DBHelper dbh = new DBHelper(DiaryActivity.this);

                if(etName.getText().toString().isEmpty() || etDesc.getText().toString().isEmpty() || etPrice.getText().toString().isEmpty())
                {
                    Toast.makeText(DiaryActivity.this, "Empty Inputs pls fill in everything", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    String name = etName.getText().toString().trim();
                    String desc = etDesc.getText().toString().trim();
                    double price = Double.parseDouble(etPrice.getText().toString());

                    int count = 0;

                    for(int i = 0 ; i < FoodList.size();i++)
                    {
                        count = i;

                    }

                    dbh.insertfood(name,desc,price);

                    FoodList.add(new Food(count+1,name,desc,price));

                    adapter.notifyDataSetChanged();

                    dbh.close();


                    Toast.makeText(DiaryActivity.this, "Successfully inserted into database", Toast.LENGTH_SHORT).show();

                }

            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                if(btnDel.isChecked() && !btnUp.isChecked())
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(DiaryActivity.this)
                            .setMessage("Do you want to delete this food item?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which)
                                {

                                    DBHelper dbh = new DBHelper(DiaryActivity.this);

                                    dbh.deleteFood(FoodList.get(position).getId());

                                    FoodList.remove(position);

                                    adapter.notifyDataSetChanged();
                                    dbh.close();
                                    Toast.makeText(DiaryActivity.this,"food item removed",Toast.LENGTH_SHORT).show();
                                }
                            })
                            .setNegativeButton("No",null);
                    AlertDialog alert = builder.create();
                    alert.show();
                }
                else if(!btnDel.isChecked() && btnUp.isChecked())
                {
                    LayoutInflater inflater = LayoutInflater.from(DiaryActivity.this);
                    View DialogMenu = inflater.inflate(R.layout.dialog_update, null);

                    EditText FoodName = DialogMenu.findViewById(R.id.foodName);
                    EditText FoodDesc = DialogMenu.findViewById(R.id.foodDesc);
                    EditText FoodPrice = DialogMenu.findViewById(R.id.Foodprice);

                    AlertDialog.Builder alert = new AlertDialog.Builder(DiaryActivity.this);

                    alert.setView(DialogMenu).setPositiveButton("Save your details?", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            if(FoodName.getText().toString().isEmpty() || FoodDesc.getText().toString().isEmpty() || FoodPrice.getText().toString().isEmpty())
                            {
                                Toast.makeText(DiaryActivity.this,"Empty fields pls fill up everything",Toast.LENGTH_SHORT).show();

                            }
                            else
                            {
                                AlertDialog.Builder confirmation = new AlertDialog.Builder(DiaryActivity.this)
                                        .setMessage("Are you sure ? ")
                                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which)
                                            {
                                                Food newFood = new Food(FoodList.get(position).getId(),FoodName.getText().toString(),FoodDesc.getText().toString(),Double.parseDouble(FoodPrice.getText().toString()));
                                                DBHelper dbh = new DBHelper(DiaryActivity.this);
                                                dbh.updateFood(FoodList.get(position),FoodName.getText().toString(),FoodDesc.getText().toString(),Double.parseDouble(FoodPrice.getText().toString()));
                                                FoodList.get(position).setName("Name " + FoodName.getText().toString());
                                                FoodList.get(position).setDescription("Description " + FoodDesc.getText().toString());
                                                FoodList.get(position).setPrice(Double.parseDouble(FoodPrice.getText().toString()));
                                                adapter.notifyDataSetChanged();
                                                dbh.close();
                                            }
                                        })
                                        .setNegativeButton("No", null);
                                confirmation.show();
                            }

                        }
                    })
                            .setNegativeButton("No",null);
                    alert.show();


                }
            }

        });



    }
}