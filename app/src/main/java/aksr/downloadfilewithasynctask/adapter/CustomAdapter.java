package aksr.downloadfilewithasynctask.adapter;



import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import aksr.downloadfilewithasynctask.MainActivity;
import aksr.downloadfilewithasynctask.R;

public class CustomAdapter extends BaseAdapter{
    String [] result;
    String[] imageId;
    private Activity ctx;
    private static LayoutInflater inflater=null;
    public CustomAdapter(MainActivity mainActivity, String[] prgmNameList, String[] prgmImages) {
        // TODO Auto-generated constructor stub
        result=prgmNameList;
        ctx=mainActivity;
        imageId=prgmImages;
        inflater = ( LayoutInflater )ctx.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return result.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder
    {
        TextView tv;
        ImageView img;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Holder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(ctx).inflate(R.layout.lista, null);
            viewHolder = new Holder();

            viewHolder.tv =  (TextView)convertView.findViewById(R.id.Itemname);
            viewHolder.img=  (ImageView)convertView.findViewById(R.id.icon);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (Holder) convertView.getTag();
        }

        viewHolder.tv.setText(result[position]);
        Bitmap bitmap = BitmapFactory.decodeFile(String.valueOf(imageId[position]));
        viewHolder.img.setImageBitmap(bitmap);

        convertView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(ctx, ""+result[position], Toast.LENGTH_LONG).show();
            }
        });
        return convertView;

    }

}
