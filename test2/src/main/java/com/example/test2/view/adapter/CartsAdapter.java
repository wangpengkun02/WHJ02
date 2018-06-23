package com.example.test2.view.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.test2.R;
import com.example.test2.model.bean.CartBean;
import com.example.test2.utils.CalcUtils;
import com.example.test2.utils.FrescoUtil;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * author:Created by WangZhiQiang on 2018/5/17.
 */
public class CartsAdapter extends BaseExpandableListAdapter{
    private Context mContext;
    private UpdateView updateViewListener;
    private DeleteView deleteView;
    private CartBean cartBean;

    public CartsAdapter(Context context) {
        this.mContext = context;
    }

    public void setData(CartBean cartBean){
        this.cartBean=cartBean;
    }

    public void setUpdateViewListener(UpdateView listener) {
        if (updateViewListener == null) {
            this.updateViewListener = listener;
        }
    }

    public void setDeleteViewListener(DeleteView deleteView){
        if (this.deleteView == null) {
            this.deleteView = deleteView;
        }
    }

    @Override
    public int getGroupCount() {
        if (cartBean==null){
            return 0;
        }
        return cartBean.getData().size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if (cartBean==null){
            return 0;
        }
        return cartBean.getData().get(groupPosition).getList().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return cartBean.getData().get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return cartBean.getData().get(groupPosition).getList().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        updateViewListener.update(cartBean.isAllSelect(), cartBean.getAllCount(), cartBean.getAllMoney());
        final GroupViewHolder holder;
        if (convertView==null){
            convertView = View.inflate(mContext, R.layout.item_shopingcart_group, null);
            holder=new GroupViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder= (GroupViewHolder) convertView.getTag();
        }
        holder.cbGroupItem.setTag(groupPosition);
        holder.tvPosition.setText(cartBean.getData().get(groupPosition).getSellerName());
        //根据获取的状态设置是否被选中
        holder.cbGroupItem.setChecked(cartBean.getData().get(groupPosition).isSelected());
        //选中店铺，商品全选
        holder.cbGroupItem.setOnClickListener(new View.OnClickListener() {
            int allCount = cartBean.getAllCount();//被选中的item数量
            double allMoney = cartBean.getAllMoney();
            int childSize = cartBean.getData().get(groupPosition).getList().size();
            @Override
            public void onClick(View v) {
                cartBean.getData().get(groupPosition).setSelected(holder.cbGroupItem.isChecked());
                Log.e("--groupisSelected--",cartBean.getData().get(groupPosition).isSelected()+"");
                if (holder.cbGroupItem.isChecked()) {
                    for (int i = 0; i < childSize; i++) {
                        if (!cartBean.getData().get(groupPosition).getList().get(i).isSelected()) {
                            allCount++;
                            cartBean.getData().get(groupPosition).getList().get(i).setSelected(holder.cbGroupItem.isChecked());
                            allMoney=CalcUtils.add(allMoney,
                                    CalcUtils.multiply((double) cartBean.getData().get(groupPosition).getList().get(i).getNum(),
                                    cartBean.getData().get(groupPosition).getList().get(i).getBargainPrice()));
                        }
                    }
                } else {
                    allCount -= childSize;
                    for (int i = 0; i < childSize; i++) {
                        cartBean.getData().get(groupPosition).getList().get(i).setSelected(holder.cbGroupItem.isChecked());
                        allMoney=CalcUtils.sub(allMoney,
                                CalcUtils.multiply((double) cartBean.getData().get(groupPosition).getList().get(i).getNum(),
                                cartBean.getData().get(groupPosition).getList().get(i).getBargainPrice()));
                    }
                }
                //父item选中的数量
                int fCount = 0;
                //判断是否所有的父item都被选中，决定全选按钮状态
                for (int i = 0; i < cartBean.getData().size(); i++) {
                    if (cartBean.getData().get(i).isSelected()) {
                        fCount++;
                    }
                }
                if (fCount == cartBean.getData().size()) {
                    cartBean.setAllSelect(true);
                } else {
                    cartBean.setAllSelect(false);
                }
                cartBean.setAllCount(allCount);
                cartBean.setAllMoney(allMoney);
                notifyDataSetChanged();
                updateViewListener.update(cartBean.isAllSelect(), allCount, allMoney);
            }
        });
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final ChildViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(mContext,R.layout.item_shopingcart_child, null);
            holder = new ChildViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ChildViewHolder) convertView.getTag();
        }
        String images = cartBean.getData().get(groupPosition).getList().get(childPosition).getImages();
        String s=images;
        int i = images.indexOf("|");
        if (i!=-1){
            s = images.substring(0, i);
        }
        FrescoUtil.setJianJin(s,holder.imgIcon);
        holder.tvPrice.setText("¥" + cartBean.getData().get(groupPosition).getList().get(childPosition).getBargainPrice());
        holder.tvGoodName.setText(cartBean.getData().get(groupPosition).getList().get(childPosition).getTitle());
        holder.etCount.setText(String.valueOf(cartBean.getData().get(groupPosition).getList().get(childPosition).getNum()));
        //根据获取的状态设置是否被选中
        holder.cbItem.setChecked(cartBean.getData().get(groupPosition).getList().get(childPosition).isSelected());
        //添加商品数量
        holder.tvAdd.setOnClickListener(new View.OnClickListener() {
            int allCount = cartBean.getAllCount();
            double allMoney = cartBean.getAllMoney();
            @Override
            public void onClick(View v) {
                int goodCount = cartBean.getData().get(groupPosition).getList().get(childPosition).getNum();
                cartBean.getData().get(groupPosition).getList().get(childPosition).setNum(addCount(goodCount));
                if (cartBean.getData().get(groupPosition).getList().get(childPosition).isSelected()) {
                    allMoney=CalcUtils.add(allMoney,cartBean.getData().get(groupPosition).getList().get(childPosition).getBargainPrice());
                    updateViewListener.update(cartBean.isAllSelect(), allCount, allMoney);
                }
                cartBean.setAllMoney(allMoney);
                notifyDataSetChanged();
            }
        });
        //减少商品数量
        holder.tvReduce.setOnClickListener(new View.OnClickListener() {
            int allCount = cartBean.getAllCount();
            double allMoney = cartBean.getAllMoney();
            @Override
            public void onClick(View v) {
                int goodCount = cartBean.getData().get(groupPosition).getList().get(childPosition).getNum();
                if (Integer.valueOf(goodCount) > 1) {
                    cartBean.getData().get(groupPosition).getList().get(childPosition).setNum(reduceCount(goodCount));
                    if (cartBean.getData().get(groupPosition).getList().get(childPosition).isSelected()) {
                        allMoney=CalcUtils.sub(allMoney,cartBean.getData().get(groupPosition).getList().get(childPosition).getBargainPrice());
                        updateViewListener.update(cartBean.isAllSelect(), allCount, allMoney);
                    }
                    cartBean.setAllMoney(allMoney);
                    notifyDataSetChanged();
                }
            }
        });
        //商品选中
        holder.cbItem.setOnClickListener(new View.OnClickListener() {
            int allCount = cartBean.getAllCount();
            double allMoney = cartBean.getAllMoney();
            @Override
            public void onClick(View v) {
                int cCount = 0;//子item被选中的数量
                int fcCount = 0;//父item被选中的数量
                if(holder.cbItem.isChecked()){
                    cartBean.getData().get(groupPosition).getList().get(childPosition).setNum(Integer.valueOf(holder.etCount.getText().toString()));
                }
                cartBean.getData().get(groupPosition).getList().get(childPosition).setSelected(holder.cbItem.isChecked());
                //遍历父item所有数据，统计被选中的item数量
                for (int i = 0; i < cartBean.getData().get(groupPosition).getList().size(); i++) {
                    if (cartBean.getData().get(groupPosition).getList().get(i).isSelected()) {
                        cCount++;
                    }
                }
                //判断是否所有的子item都被选中，决定父item状态
                if (cCount == cartBean.getData().get(groupPosition).getList().size()) {
                    cartBean.getData().get(groupPosition).setSelected(true);
                } else {
                    cartBean.getData().get(groupPosition).setSelected(false);
                }
                //判断是否所有的父item都被选中，决定全选按钮状态
                for (int i = 0; i < cartBean.getData().size(); i++) {
                    if (cartBean.getData().get(i).isSelected()) {
                        fcCount++;
                    }
                }
                if (fcCount == cartBean.getData().size()) {
                    cartBean.setAllSelect(true);
                } else {
                    cartBean.setAllSelect(false);
                }
                //判断子item状态，更新结算总商品数和合计Money
                if (holder.cbItem.isChecked()) {
                    allCount++;
                    allMoney=CalcUtils.add(allMoney,
                            CalcUtils.multiply((double) cartBean.getData().get(groupPosition).getList().get(childPosition).getNum(),
                            cartBean.getData().get(groupPosition).getList().get(childPosition).getBargainPrice()));
                } else {
                    allCount--;
                    allMoney=CalcUtils.sub(allMoney,
                            CalcUtils.multiply((double) cartBean.getData().get(groupPosition).getList().get(childPosition).getNum(),
                            cartBean.getData().get(groupPosition).getList().get(childPosition).getBargainPrice()));
                    cartBean.getData().get(groupPosition).getList().get(childPosition).setNum(Integer.valueOf(holder.etCount.getText().toString()));
                }
//                Log.e("adapter", "allMoney: "+allMoney);
                cartBean.setAllCount(allCount);
                cartBean.setAllMoney(allMoney);
                notifyDataSetChanged();
                updateViewListener.update(cartBean.isAllSelect(), allCount, allMoney);
            }
        });
        deleteView.delete(updateViewListener,holder.cbItem,holder.etCount,holder.imgDelete,groupPosition,childPosition);
        return convertView;
    }
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    private int addCount(int var) {
        Integer integer = Integer.valueOf(var);
        integer++;
        return integer;
    }

    private int reduceCount(int var) {
        Integer integer = Integer.valueOf(var);
        if (integer > 1) {
            integer--;
        }
        return integer;
    }

    class GroupViewHolder {

        CheckBox cbGroupItem;
        TextView tvPosition;

        public GroupViewHolder(View view) {
            cbGroupItem = view.findViewById(R.id.cb_cart_item_group);
            tvPosition = view.findViewById(R.id.tv_cart_item_group);
        }
    }
    class ChildViewHolder {
        CheckBox cbItem;
        TextView tvPrice;
        TextView tvGoodName;
        EditText etCount;
        TextView tvReduce;
        TextView tvAdd;
        ImageView imgDelete;
        SimpleDraweeView imgIcon;

        public ChildViewHolder(View view) {
            cbItem = view.findViewById(R.id.cb_cart_item_child);
            tvPrice = view.findViewById(R.id.tv_cart_item_child_price);
            tvGoodName = view.findViewById(R.id.tv_cart_item_child_name);
            etCount = view.findViewById(R.id.et_cart_item_child_count);
            tvReduce = view.findViewById(R.id.tv_cart_item_child_reduce);
            tvAdd = view.findViewById(R.id.tv_cart_item_child_add);
            imgDelete = view.findViewById(R.id.img_cart_item_child_delete);
            imgIcon = view.findViewById(R.id.sdv_cart_item_child);
        }
    }
    public interface UpdateView {
        void update(boolean isAllSelected, int count, double price);
    }
    public interface DeleteView {
        void delete(UpdateView updateViewListener, CheckBox cbItem, EditText etCount, ImageView imgDelete, int groupPosition, int childPosition);
    }
}
