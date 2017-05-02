package com.fips.huashun.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.fips.huashun.R;
import com.fips.huashun.widgets.MyGridView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FollowListViewAdapter extends BaseExpandableListAdapter implements AdapterView.OnItemClickListener {
	public static final int ItemHeight = 48;// 每项的高度
	private static final int GROUP_TEXTVIEW = 999;
	private static final int GROUP_IMAGEVIEW = 998;

	private MyGridView toolbarGrid;

	private List<TreeNode> treeNodes = new ArrayList<TreeNode>();

	private Context parentContext;

	private LayoutInflater layoutInflater;

	private SimpleAdapter simperAdapter;

	static public class TreeNode {
	public 	Object parent;
		public	Integer parentImg;
		public	List<Map<String, Object>> childs = new ArrayList<Map<String, Object>>();
	}

	public FollowListViewAdapter(Context view) {
		parentContext = view;
	}

	/**
	 * 获取treeNodes
	 *
	 * @return
	 */
	public List<TreeNode> GetTreeNode() {
		return treeNodes;
	}

	/**
	 * 更新treeNodes中的数据
	 *
	 * @param nodes
	 */
	public void UpdateTreeNode(List<TreeNode> nodes) {
		treeNodes = nodes;
	}

	/**
	 * 清除treeNodes中的数据
	 */
	public void RemoveAll() {
		treeNodes.clear();
	}

	/**
	 * 获取组员名
	 */
	public Object getChild(int groupPosition, int childPosition) {
		return treeNodes.get(groupPosition).childs.get(childPosition);
	}

	/**
	 * 返回值必须为1，否则会重复数据
	 */
	public int getChildrenCount(int groupPosition) {
		return 1;
	}

	/**
	 * 新建TextView
	 *
	 * @param context
	 * @return
	 */
	static public LinearLayout getGroupLayout(Context context) {
		LinearLayout layout = new LinearLayout(context);
		layout.setLayoutParams(new AbsListView.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ItemHeight));
		ImageView imageView = new ImageView(context);
//		imageView.setId(GROUP_IMAGEVIEW);
		imageView.setLayoutParams(new AbsListView.LayoutParams(ItemHeight,ItemHeight));
		layout.addView(imageView);

		TextView textView = new TextView(context);
		textView.setLayoutParams(new AbsListView.LayoutParams(ItemHeight, ItemHeight));
//		textView.setId(GROUP_TEXTVIEW);
		textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
		layout.addView(textView);
		return layout;
	}

	/**
	 * 自定义组员
	 */
	public View getChildView(int groupPosition, int childPosition,
							 boolean isLastChild, View convertView, ViewGroup parent) {
		layoutInflater = (LayoutInflater) parentContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		convertView = layoutInflater.inflate(R.layout.child_items, null);
		toolbarGrid = (MyGridView) convertView.findViewById(R.id.child_view);
		toolbarGrid.setSelector(new ColorDrawable(Color.TRANSPARENT));
		toolbarGrid.setAdapter(getMenuAdapter(groupPosition));// 设置菜单Adapter
		toolbarGrid.setOnItemClickListener(this);
		return convertView;
	}

	/**
	 * 自定义组
	 */
	public View getGroupView(int groupPosition, boolean isExpanded,
							 View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = LayoutInflater.from(parentContext).inflate(R.layout.group_items, null);
			holder = new ViewHolder();
			holder.textView = (TextView) convertView.findViewById(R.id.tv_text);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		return convertView;
	}

	/**
	 * 获取组员id
	 */
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	/**
	 * 获取组名
	 */
	public Object getGroup(int groupPosition) {
		return treeNodes.get(groupPosition).parent;
	}

	public Integer getGroupImg(int groupPosition) {
		return treeNodes.get(groupPosition).parentImg;
	}

	/**
	 * 统计组的长度
	 */
	public int getGroupCount() {
		return treeNodes.size();
	}

	/**
	 * 获取组id
	 */
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	/**
	 * 定义组员是否可选
	 */
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

	public boolean hasStableIds() {
		return true;
	}

	/**
	 * 构造SimpleAdapter
	 *
	 */
	private SimpleAdapter getMenuAdapter(int groupPosition) {
		ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
		int len = treeNodes.get(groupPosition).childs.size();
		for (int i = 0; i < len; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("itemText",	treeNodes.get(groupPosition).childs.get(i).get("name"));
			map.put("itemId",treeNodes.get(groupPosition).childs.get(i).get("id"));
			map.put("itemImg",treeNodes.get(groupPosition).childs.get(i).get("iv"));
			map.put("itemImg2",treeNodes.get(groupPosition).childs.get(i).get("iv2"));
			data.add(map);
		}
		simperAdapter = new SimpleAdapter(parentContext, data,
				R.layout.gridview_child_item,
				new String[] { "itemText", },
				new int[] {  R.id.tv_child });
		return simperAdapter;
	}

	/**
	 * 点击监听器
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
							long id) {
		Map<String, Object> map = (Map<String, Object>) parent
				.getItemAtPosition(position);
//		if (!FollowActivity.gcategory.contains(map.get("itemId"))) {
//			FollowActivity.gcategory.add((Integer) map.get("itemId"));
//			map.put("itemImg2", R.drawable.checkbox_selected);
//			FollowActivity.follow_tv_01.setText("（"
//					+ FollowActivity.gcategory.size() + "）");
//			simperAdapter.notifyDataSetChanged();
//		} else {
//			Toast.makeText(parentContext, "该项已经被添加", Toast.LENGTH_SHORT).show();
//		}
	}
	class ViewHolder {
		TextView textView;
	}
}