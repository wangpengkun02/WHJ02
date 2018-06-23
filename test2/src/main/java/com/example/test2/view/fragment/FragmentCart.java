package com.example.test2.view.fragment;

import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test2.R;
import com.example.test2.model.bean.CartBean;
import com.example.test2.model.bean.CreateOrderBean;
import com.example.test2.model.bean.DefaultAddrBean;
import com.example.test2.model.bean.DeleteCartBean;
import com.example.test2.model.bean.MessageEvent;
import com.example.test2.presenter.CartPresenter;
import com.example.test2.utils.CalcUtils;
import com.example.test2.view.activity.OrdersActivity;
import com.example.test2.view.adapter.CartsAdapter;
import com.example.test2.view.interfaces.ICartView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;

/**
 * author:Created by WangZhiQiang on 2018/4/26.
 */
public class FragmentCart extends BaseFragment<CartPresenter> implements ICartView, CartsAdapter.UpdateView, View.OnClickListener, CartsAdapter.DeleteView {

    private CartBean cartBean=new CartBean();
    private DeleteCartBean deleteCartBean=new DeleteCartBean();
    private CreateOrderBean createOrderBean=new CreateOrderBean();
    private DefaultAddrBean defaultAddrBean=new DefaultAddrBean();
    private String uid;
    private String token;
    private double price;
    private SharedPreferences sharedPreferences;

    private ExpandableListView elv_cart;
    private CheckBox cb_cart_select_all;
    private TextView tv_cart_all_money;
    private TextView tv_cart_transport;
    private TextView tv_cart_addr;
    private Button btn_cart_settlement;
    private CartsAdapter adapter;

    @Override
    void initView(View view) {
        elv_cart = view.findViewById(R.id.elv_cart);
        cb_cart_select_all = view.findViewById(R.id.cb_cart_select_all);
        tv_cart_all_money = view.findViewById(R.id.tv_cart_all_money);
        tv_cart_transport = view.findViewById(R.id.tv_cart_transport);
        tv_cart_addr = view.findViewById(R.id.tv_cart_addr);
        btn_cart_settlement = view.findViewById(R.id.btn_cart_settlement);
        sharedPreferences = getActivity().getSharedPreferences("user", getActivity().MODE_PRIVATE);
        getUidAndToken();
        //去掉ExpandableListView 默认的箭头
        elv_cart.setGroupIndicator(null);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onMessageEvent(MessageEvent event) {
        if (event.isSuccessClassify()||event.isClickCart()){
            Log.e("myMessage" , "isSuccessClassify:"+event.isSuccessClassify());
            Log.e("myMessage" , "点击购物车:"+event.isClickCart());
            getData();
            EventBus.getDefault().removeStickyEvent(event);
        }
        if (event.isLoginOk()){
            Log.e("myMessage" , "isLoginOk:"+event.isLoginOk());
            getUidAndToken();
        }
    }

    private void getUidAndToken() {
        uid=sharedPreferences.getString("uid",null);
        token=sharedPreferences.getString("token",null);
    }

    private void getData(){
        getUidAndToken();
        if (!(TextUtils.isEmpty(uid)||TextUtils.isEmpty(token))){
//            Log.e("myMessage" , "查询购物车uid:"+uid+"--token:"+token);
            HashMap<String, String> map = new HashMap<>();
            map.put("source","android");
            map.put("uid",uid);
            map.put("token",token);
            getPresenter().getDataFromServer(map,1);
        }
    }

    private void createOrder(){
        if (!(TextUtils.isEmpty(uid)||TextUtils.isEmpty(token))){
            if (price>0){
                HashMap<String, String> map = new HashMap<>();
                map.put("uid",uid);
                map.put("price",""+price);
                getPresenter().getDataFromServer(map,3);
            }else {
                Toast.makeText(getActivity(), "请选择商品！", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(getActivity(), "请先登录！", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    void initData() {
        adapter = new CartsAdapter(getActivity());
        elv_cart.setAdapter(adapter);
        adapter.setUpdateViewListener(this);
        adapter.setDeleteViewListener(this);
        EventBus.getDefault().register(this);
        //全选按钮点击事件
        cb_cart_select_all.setOnClickListener(this);
        //结算创建订单
        btn_cart_settlement.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cb_cart_select_all:
                if(cartBean!=null){
                    selectAll();
                }
                break;
            case R.id.btn_cart_settlement:
                createOrder();
                break;
        }

    }
    private boolean one=true;
    @Override
    public void onSuccess(Object success, int flag) {
        switch (flag){
            case 1:
                cartBean = (CartBean) success;
                adapter.setData(cartBean);
                adapter.notifyDataSetChanged();
                //展开所有的分组
                if (cartBean!=null){
                    for (int i = 0; i < cartBean.getData().size(); i++) {
                        elv_cart.expandGroup(i);
                    }
                }
                if (one){
                    MessageEvent messageEvent = new MessageEvent();
                    messageEvent.setSuccessCart(true);
                    EventBus.getDefault().postSticky(messageEvent);
                    one=false;
                }
                getPresenter().defaultAddr(uid);
                break;
            case 2:
                deleteCartBean = (DeleteCartBean) success;
                Toast.makeText(getActivity(), deleteCartBean.getMsg(), Toast.LENGTH_SHORT).show();
                break;
            case 3:
                createOrderBean= (CreateOrderBean) success;
                Toast.makeText(getActivity(), createOrderBean.getMsg(), Toast.LENGTH_SHORT).show();
                if (createOrderBean.getCode().equals("0")){
                    OrdersActivity.start(getActivity());
                }
                break;
            case 4:
                defaultAddrBean= (DefaultAddrBean) success;
                if (null!=defaultAddrBean){
                    tv_cart_addr.setVisibility(View.VISIBLE);
                    tv_cart_addr.setText("默认收货地址:"+defaultAddrBean.getData().getAddr());
                }
                break;
        }
    }

    @Override
    public void update(boolean isAllSelected, int count, double price) {
        this.price=price;
        btn_cart_settlement.setText("结算(" + count + ")");
        tv_cart_all_money.setText("¥" + price);
        cb_cart_select_all.setChecked(isAllSelected);
    }

    private void selectAll() {
        int allCount = cartBean.getAllCount();
        double allMoney = cartBean.getAllMoney();
        if (cb_cart_select_all.isChecked()) {
            cartBean.setAllSelect(true);
            for (int i = 0; i < cartBean.getData().size(); i++) {
                cartBean.getData().get(i).setSelected(true);
                for (int n = 0; n < cartBean.getData().get(i).getList().size(); n++) {
                    if (!cartBean.getData().get(i).getList().get(n).isSelected()) {
                        allCount++;
                        allMoney=CalcUtils.add(allMoney,
                                CalcUtils.multiply((double) cartBean.getData().get(i).getList().get(n).getNum(),
                                        cartBean.getData().get(i).getList().get(n).getBargainPrice()));
                        cartBean.getData().get(i).getList().get(n).setSelected(true);
                    }
                }
            }
        } else {
            cartBean.setAllSelect(false);
            for (int i = 0; i < cartBean.getData().size(); i++) {
                cartBean.getData().get(i).setSelected(false);
                for (int n = 0; n < cartBean.getData().get(i).getList().size(); n++) {
                    cartBean.getData().get(i).getList().get(n).setSelected(false);
                }
                allCount = 0;
                allMoney = 0;
            }
        }
        cartBean.setAllMoney(allMoney);
        cartBean.setAllCount(allCount);
        update(cartBean.isAllSelect(), allCount, allMoney);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void delete(final CartsAdapter.UpdateView updateViewListener, final CheckBox cbItem, final EditText etCount, ImageView imgDelete, final int groupPosition, final int childPosition) {
        //删除
        imgDelete.setOnClickListener(new View.OnClickListener() {
            int allCount = cartBean.getAllCount();
            double allMoney = cartBean.getAllMoney();
            @Override
            public void onClick(View v) {
                HashMap<String, String> map = new HashMap<>();
                map.put("uid",uid);
                map.put("token",token);
                map.put("pid",cartBean.getData().get(groupPosition).getList().get(childPosition).getPid()+"");
                getPresenter().getDataFromServer(map,2);

                if(cbItem.isChecked()){
                    allCount--;
                    allMoney=CalcUtils.sub(allMoney,
                            CalcUtils.multiply((double) cartBean.getData().get(groupPosition).getList().get(childPosition).getNum(),
                                    cartBean.getData().get(groupPosition).getList().get(childPosition).getBargainPrice()));
                    cartBean.getData().get(groupPosition).getList().get(childPosition).setNum(Integer.valueOf(etCount.getText().toString()));
                }
                cartBean.getData().get(groupPosition).getList().remove(childPosition);
                if (cartBean.getData().get(groupPosition).getList().size() == 0) {
                    cartBean.getData().remove(groupPosition);
                }
                cartBean.setAllCount(allCount);
                cartBean.setAllMoney(allMoney);
                adapter.notifyDataSetChanged();
                updateViewListener.update(cartBean.isAllSelect(), allCount, allMoney);
            }
        });
    }

    @Override
    CartPresenter initPresenter() {
        return new CartPresenter();
    }

    @Override
    int setChildContentView() {
        return R.layout.fragment_cart;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        getPresenter().detachView();
    }
}
