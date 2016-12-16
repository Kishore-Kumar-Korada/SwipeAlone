package module.example.com.swipealone;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import module.example.com.swipealone.Adapters.ListViewAdapter;
import module.example.com.swipealone.SwipeUtils.SwipeActionAdapter;
import module.example.com.swipealone.SwipeUtils.SwipeDirection;

public class MainActivity extends Activity implements SwipeActionAdapter.SwipeActionListener, AdapterView.OnItemClickListener {

    // Declare Variables
    private ListView listView;
    private ListViewAdapter listViewAdapter;

    /* Swipe Related */
    private SwipeActionAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listView);

        List<String> itemList = new ArrayList<String>();
        for (int i = 0; i < 10; i++) {
            itemList.add("Item " + i);
        }

        // Here you have to pass R.layout.list_view_item as a constructor parameter of LisTViewAdapter since ListViewAdapter is an ArrayAdapter which itself internally doesn't have default constructor and it has 3parameter constructor
        listViewAdapter = new ListViewAdapter(this, R.layout.list_view_item, itemList);

        mAdapter = new SwipeActionAdapter(listViewAdapter);
        mAdapter.setSwipeActionListener(this)
                .setDimBackgrounds(true)
                .setListView(listView);
        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(this);

        mAdapter.addBackground(SwipeDirection.DIRECTION_FAR_LEFT,R.layout.row_left_far)
                .addBackground(SwipeDirection.DIRECTION_NORMAL_LEFT, R.layout.row_left)
                .addBackground(SwipeDirection.DIRECTION_FAR_RIGHT, R.layout.row_right_far)
                .addBackground(SwipeDirection.DIRECTION_NORMAL_RIGHT, R.layout.row_right);
        //Till now you can't see any swiping functionality yet and for that hasActions will take a role
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        System.err.println("Inside onItemClick");
        String itemName = listViewAdapter.getItem(position);
        Toast.makeText(MainActivity.this, "You've clicked: "+itemName, Toast.LENGTH_SHORT).show();
    }

    /* Following are swipe related override methods */
    /* This hasActions is a functions which does the work on each swipe event */
    @Override
    public boolean hasActions(int position, SwipeDirection direction) {
        System.err.println("Inside hasActions");
        if(direction.isLeft()) {
            System.err.println("Inside hasActions isLeft condition");
            return true;
        }
        if(direction.isRight()) {
            System.err.println("Inside hasActions isRight condition");
            return true;
        }
        return false;
    }

    @Override
    public boolean shouldDismiss(int position, SwipeDirection direction) {
        System.err.println("Inside shouldDismiss");
        return direction == SwipeDirection.DIRECTION_NORMAL_LEFT;   //delete only when the swipe direction towards normal right
    }

    @Override
    public void onSwipe(int[] positions, SwipeDirection[] directions) {
        System.err.println("Inside onSwipe()");

        for(int i=0;i<positions.length;i++) {
            SwipeDirection direction = directions[i];
            int position = positions[i];
            String dir = "";

            switch (direction) {
                case DIRECTION_FAR_LEFT:
                    dir = "Far left";
                    break;
                case DIRECTION_NORMAL_LEFT:
                    dir = "Left";
                    break;
                case DIRECTION_FAR_RIGHT:
                    dir = "Far right";
                    break;
                case DIRECTION_NORMAL_RIGHT:
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Test Dialog").setMessage("You swiped right").create().show();
                    dir = "Right";
                    break;
            }
            Toast.makeText(
                    this,
                    dir + " swipe Action triggered on " + mAdapter.getItem(position),
                    Toast.LENGTH_SHORT
            ).show();
            mAdapter.notifyDataSetChanged();
        }
    }
}