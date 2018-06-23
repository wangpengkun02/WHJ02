package com.example.whj02.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.whj02.Bean.DwcBean;
import com.example.whj02.R;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by 浮生丶 on 2018/6/18 0018.
 * <p>
 * ------------------_ooOoo_
 * -----------------o8888888o
 * ----------------88" . "88
 * ----------------(| -_- |)
 * ----------------O\  =  /O
 * -------------____/`---'\____
 * ----------- .'  \\|     |//  `.
 * ----------/  \\|||  :  |||//  \
 * ---------/  _||||| -:- |||||-  \
 * ---------|   | \\\  -  /// |   |
 * ----------| \_|  ''\---/''  |   |
 * -----------\  .-\__  `-`  ___/-. /
 * --------___`. .'  /--.--\  `. . __
 * -----."" '<  `.___\_<|>_/___.'  >'"".
 * -----| | :  `- \`.;`\ _ /`;.`/ - ` : | |
 * ----\  \ `-.   \_ __\ /__ _/   .-` /  /
 * ======`-.____`-.___\_____/___.-`____.-'======
 * -----------------`=---='
 * ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
 * -------------佛祖保佑       永无BUG
 */

public class MyGwcAdapter extends RecyclerView.Adapter {
     private Context context;
     private List<DwcBean.DataBean> data;
 private  int uid;

    private List<DwcBean.DataBean.ListBean> list;
    private onzong onzong;
    private getSumprice getSumprice;
    public MyGwcAdapter(Context context, List<DwcBean.DataBean> data, int uid) {
        this.context = context;
        this.data = data;
        this.uid = uid;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = View.inflate(context, R.layout.gwc_item, null);
        MyGwcViewholder vh=new MyGwcViewholder(inflate);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        final MyGwcViewholder vh= (MyGwcViewholder) holder;
        vh.sjname.setText(data.get(position).getSellerName());
        list = data.get(position).getList();
        vh.gwczirlv.setLayoutManager(new LinearLayoutManager(context, OrientationHelper.VERTICAL,false));
            if (list.size()==0){
                data.remove(position);

                notifyDataSetChanged();
            }
        final MyGwcZiAdapter  adapter=new MyGwcZiAdapter(context,  list,uid);
        vh.gwczirlv.setAdapter(adapter);
          /*vh.sjche_box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
              @Override
              public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                   if (vh.sjche_box.isChecked()){
                       vh.sjche_box.setChecked(true);

                          for (int j = 0; j < list.size(); j++) {
                              data.get(j).getList().get(j).setSelected(1);
                              adapter.notifyDataSetChanged();
                          }



                   }else {
                       vh.sjche_box.setChecked(false);
                       for (int i = 0; i < list.size(); i++) {
                           data.get(i).getList().get(i).setSelected(0);
                           adapter.notifyDataSetChanged();
                       }

                   }
              }
          });*/
//点击商家商品全选
        vh.sjche_box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (DwcBean.DataBean.ListBean listBean : data.get(position).getList()) {
                    if(vh.sjche_box.isChecked())
                    {
                        listBean.setSelected(1);
                    }
                    else
                    {
                        listBean.setSelected(0);
                    }
                }
                adapter.notifyDataSetChanged();
                onzong.onshangjia();
                getSumprice.onSumprice();
            }
        });


        //调用小adapter的接口实现商品全选 商家选中
        adapter.setOngeshu(new MyGwcZiAdapter.ongeshu() {
            @Override
            public void geshu() {
                boolean  flag=true;
                for (DwcBean.DataBean.ListBean listBean : data.get(position).getList()) {
                    if(listBean.getSelected()==0)
                    {
                        flag=false;
                    }
                }
                vh.sjche_box.setChecked(flag);
                onzong.onshangpin();
                getSumprice.onSumprice();
            }
        });
        adapter.setGetsumprice(new MyGwcZiAdapter.getsumprice() {
            @Override
            public void onsumprice() {
                getSumprice.onSumprice();
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    private class MyGwcViewholder extends  RecyclerView.ViewHolder {

        private final TextView sjname;
        private final RecyclerView gwczirlv;
        private final CheckBox sjche_box;
        public MyGwcViewholder(View itemView) {
            super(itemView);
            sjname = itemView.findViewById(R.id.sjname);
            gwczirlv = itemView.findViewById(R.id.gwczirlv);
            sjche_box = itemView.findViewById(R.id.sjche_box);
        }
    }
    public void setOnzong(MyGwcAdapter.onzong onzong) {
        this.onzong = onzong;
    }

    public interface onzong{
        void onshangjia();
        void onshangpin();
    }


    public void setGetSumprice(MyGwcAdapter.getSumprice getSumprice) {
        this.getSumprice = getSumprice;
    }

    public interface  getSumprice{
        void onSumprice();
    }


}
