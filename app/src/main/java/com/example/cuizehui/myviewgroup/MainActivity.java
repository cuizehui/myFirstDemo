package com.example.cuizehui.myviewgroup;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    private myviewgroup mv;
    ListView lv;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mv= (myviewgroup) findViewById(R.id.myviewgroup);
        List<String> mlist=new ArrayList<String>();
        for(int i=0;i<40;i++) {
            mlist.add(i+"");
        }
        lv=new ListView(this);
        lv.setAdapter(new lvadapter(this, mlist));


        int pic[] =new int[]{R.mipmap.a1,R.mipmap.a2,R.mipmap.a3,R.mipmap.a4,R.mipmap.a5,R.mipmap.a6};
        for (int i=0;i<pic.length;i++){
            ImageView imageView=new ImageView(this);
            imageView.setBackgroundResource(pic[i]);

            mv.addView(imageView);
        }
        mv.addView(lv);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
