package rahul.agrahari.invertedroundedview;

import android.graphics.Color;
import android.graphics.Path;
import android.graphics.drawable.ScaleDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    private Integer[] strokeArray;
    private Integer[] bgArray;


    private GridAdapter strokeAdapter;
    private GridAdapter bgAdapter;
    private GridView strokeGridView;
    private GridView bgGridView;
    private Spinner spincornerradius;
    private Spinner spinstrokewidth;
    private InvertedLinearLayout invertedLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        strokeArray = new Integer[]{R.color.color1, R.color.color2,
                R.color.color3, R.color.color4};

        bgArray = new Integer[]{R.color.bg1, R.color.bg2, R.color.bg3,
                R.color.bg4};
        strokeGridView = (GridView) findViewById(R.id.gridStroke);
        bgGridView = (GridView) findViewById(R.id.gridBg);
        spinstrokewidth = (Spinner) findViewById(R.id.spinstrokewidth);
        spincornerradius = (Spinner) findViewById(R.id.spincornerradius);
        invertedLinearLayout = (InvertedLinearLayout) findViewById(R.id.invertedLinearLayout);
        setAdapter();
        setItemClick();
        getSpinnerSelectedItem();
    }

    private void setAdapter() {
        strokeAdapter = new GridAdapter(this, R.layout.row_color, strokeArray);
        bgAdapter = new GridAdapter(this, R.layout.row_color, bgArray);
        strokeGridView.setAdapter(strokeAdapter);
        bgGridView.setAdapter(bgAdapter);
    }

    private void setItemClick() {
        strokeGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int color = strokeArray[position];
                invertedLinearLayout.setStrokeColor(getResources().getColor(color));
            }
        });

        bgGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int color = bgArray[position];
                //invertedLinearLayout.setBackground(R.mipmap.st_edt);
                invertedLinearLayout.setBackground(color);
            }
        });
    }

    private void getSpinnerSelectedItem() {
        spincornerradius.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int width = R.dimen.space_thirty;
                switch (position) {
                    case 0:
                        width = R.dimen.space_twentyfive;
                        break;
                    case 1:
                        width = R.dimen.space_thirty;
                        break;
                    case 2:
                        width = R.dimen.space_thirtyfive;
                        break;
                    case 3:
                        width = R.dimen.space_fourty;
                        break;
                }
                invertedLinearLayout.setRoundRadious(getResources().getDimensionPixelSize(width));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        spinstrokewidth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int width = R.dimen.space_ten;
                switch (position) {
                    case 0:
                        width = R.dimen.space_five;
                        break;
                    case 1:
                        width = R.dimen.space_ten;
                        break;
                    case 2:
                        width = R.dimen.space_fifteen;
                        break;
                    case 3:
                        width = R.dimen.space_twenty;
                        break;
                }
                invertedLinearLayout.setStrokewidth(getResources().getDimensionPixelSize(width));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


}
