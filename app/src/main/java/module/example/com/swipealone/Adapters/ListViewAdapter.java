package module.example.com.swipealone.Adapters;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import module.example.com.swipealone.R;

public class ListViewAdapter extends ArrayAdapter<String> {
	// Declare Variables
	Context context;
	LayoutInflater inflater;
	List<String> itemList;

	public ListViewAdapter(Context context, int resourceId,
						   List<String> itemList) {
		super(context, resourceId, itemList);
		this.context = context;
		this.itemList = itemList;
		inflater = LayoutInflater.from(context);
	}

	private class ViewHolder {
		TextView item;
		/* Here you can can add extra if you want */
	}

	public View getView(int position, View view, ViewGroup parent) {
		final ViewHolder holder;
		if (view == null) {
			holder = new ViewHolder();
			view = inflater.inflate(R.layout.list_view_item, null);
			holder.item = (TextView) view.findViewById(R.id.item);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		// Capture position and set to the TextViews
		holder.item.setText(itemList.get(position));
		return view;
	}
}