package com.morelap.menu;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class MenuActivity extends Activity {
    private MorelapMenu mMorelapMenu = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        mMorelapMenu = (MorelapMenu)findViewById(R.id.layout_menu);
        MenuItem menu = new MenuItem(this, null);
        menu.setImageResource(R.drawable.ic_camera);
        mMorelapMenu.addMenu(menu);

        menu = new MenuItem(this, null);
        menu.setImageResource(R.drawable.ic_location);
        mMorelapMenu.addMenu(menu);

        menu = new MenuItem(this, null);
        menu.setImageResource(R.drawable.ic_mail);
        mMorelapMenu.addMenu(menu);
        
        menu = new MenuItem(this, null);
        menu.setImageResource(R.drawable.ic_new);
        mMorelapMenu.addMenu(menu);
        
        menu = new MenuItem(this, null);
        menu.setImageResource(R.drawable.ic_setting);
        mMorelapMenu.addMenu(menu);
        
        menu = new MenuItem(this, null);
        menu.setImageResource(R.drawable.ic_video);
        mMorelapMenu.addMenu(menu);

        double radian = Math.asin(0.5);
        System.out.println(180*radian/Math.PI);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

}
