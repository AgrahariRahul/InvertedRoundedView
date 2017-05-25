package rahul.agrahari.invertedroundedview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by rahul on 5/22/2017.
 */
public class GridAdapter extends ArrayAdapter<Integer> {
    Context context;
    Integer[] list;

    public GridAdapter(Context context, int resource, Integer[] list) {
        super(context, resource);
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.length;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.row_color, null);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.imgcolor);
        imageView.setBackgroundColor(context.getResources().getColor(list[position]));
        return convertView;
    }
}
