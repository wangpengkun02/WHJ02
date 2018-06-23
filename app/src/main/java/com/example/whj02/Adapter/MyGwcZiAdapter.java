package com.example.whj02.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.whj02.Bean.DwcBean;
import com.example.whj02.Bean.GxGwcBean;
import com.example.whj02.R;
import com.example.whj02.activity.DlActivity;
import com.example.whj02.activity.ZcActivity;
import com.example.whj02.utils.OkHttp3Util_03;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

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

public class MyGwcZiAdapter extends RecyclerView.Adapter  {
    private Context context;
    List<DwcBean.DataBean.ListBean> list;
    private  int uid;
    private int gxnum;

    private ongeshu ongeshu;
    private getsumprice getsumprice;
    private int number;
    int bj=0;
    public MyGwcZiAdapter(Context context, List<DwcBean.DataBean.ListBean> list, int uid) {
        this.context = context;
        this.list = list;
        this.uid = uid;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View  view = View.inflate(context, R.layout.gwc_zi_item, null);
        MyGwcZiViewHolder vh=new MyGwcZiViewHolder(view);
        return vh;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
          final MyGwcZiViewHolder vh= (MyGwcZiViewHolder) holder;
          vh.spname.setText(list.get(position).getTitle());
          vh.spprice.setText( ""+list.get(position).getPrice()*list.get(position).getNum());
          vh.spnum.setText(""+list.get(position).getNum());

        String[] split = list.get(position).getImages().split("\\|");
            vh.spimg.setImageURI(split[0]);
        final int sellerid = list.get(position).getSellerid();
        final int pid = list.get(position).getPid();
        final int selected = list.get(position).getSelected();
        Log.d("test","----"+selected);
        if (selected==0){
            vh.spche_box.setChecked(false);
        }else {
            vh.spche_box.setChecked(true);
            ongeshu.geshu();
            getsumprice.onsumprice();
        }
        Log.d("test","----"+uid+"----"+pid);
        vh.spche_box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                 number=Integer.valueOf(vh.spnum.getText().toString());
                int selected1;
                 if (vh.spche_box.isChecked()){
                     list.get(position).setSelected(1);
                    selected1 = list.get(position).getSelected();

                 }else {
                     list.get(position).setSelected(0);
                       selected1 = list.get(position).getSelected();
                }
                ongeshu.geshu();
                getsumprice.onsumprice();
              getgwcspq(uid,pid,sellerid,selected1,number);
            }
        });


        vh.jian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number = Integer.valueOf(vh.spnum.getText().toString());
                if(number >1){
                    number--;
                    //list.get(position).setQuantity(number);
                   // EventBus.getDefault().post(new UpdataButton(mallShopCartAdapter2.getAllPrice()));
                    getgwcspq(uid,pid,sellerid,selected, number);
                    vh.spnum.setText(number +"");
                    vh.spprice.setText( ""+list.get(position).getPrice()* number);

                }else {
                    Toast.makeText(context, "已经是最少数量!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        vh.jia.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                 number=Integer.valueOf(vh.spnum.getText().toString());
                if(number<999){
                    number++;
                     // mDatas.get(position).setQuantity(number);
                    // EventBus.getDefault().post(new UpdataButton(mallShopCartAdapter2.getAllPrice()));
                    getgwcspq(uid,pid,sellerid,selected,number);
                    vh.spnum.setText(number+"");
                    vh.spprice.setText( ""+list.get(position).getPrice()*number);
                }else {
                    Toast.makeText(context, "已经是最大库存量!", Toast.LENGTH_SHORT).show();
                }

            }
        });
        vh.spsc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //https://www.zhaoapi.cn/product/deleteCart?uid=72&pid=1

                 AlertDialog.Builder builder=new AlertDialog.Builder(  context);
                builder.setMessage( "是否删除该商品");
                builder.setPositiveButton("删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        list.remove(position);
                        notifyDataSetChanged();
                        bj=1;
                        getdel(uid,pid);
                    }
                });

                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.create().show();

            }
        });
    }

    private void getdel(int uid, int pid) {
        String del="https://www.zhaoapi.cn/product/deleteCart?uid="+uid+"&pid="+pid;
        OkHttp3Util_03.doGet(del, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                GxGwcBean gxGwcBean = new Gson().fromJson(string, GxGwcBean.class);
                String code = gxGwcBean.getCode();
                if (code.length()==0){
                    Log.d("test","----成功了");
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }




    private class MyGwcZiViewHolder extends  RecyclerView.ViewHolder {
        private final TextView spname;
        private final TextView spprice;
        private final TextView jian;
        private final TextView jia;
        private final SimpleDraweeView spimg;
        private final EditText spnum;
        private final CheckBox spche_box;
        private final ImageView spsc;
        public MyGwcZiViewHolder(View itemView) {
            super(itemView);
            spimg = itemView.findViewById(R.id.spimg);
            spname = itemView.findViewById(R.id.spname);
            spprice = itemView.findViewById(R.id.spprice);
            jian = itemView.findViewById(R.id.jian);
            jia = itemView.findViewById(R.id.jia);
            spche_box = itemView.findViewById(R.id.spche_box);
            spnum = itemView.findViewById(R.id.spnum);
            spsc = itemView.findViewById(R.id.spsc);
        }
    }
    private void getgwcspq(int uid,int pid, int sellerid, int selected, int number) {
        String path="https://www.zhaoapi.cn/product/updateCarts?uid="+uid+"&sellerid="+sellerid+"&pid="+pid+"&selected="+selected+"&num="+number;

        OkHttp3Util_03.doGet(path, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                GxGwcBean gxGwcBean = new Gson().fromJson(string, GxGwcBean.class);
                String code = gxGwcBean.getCode();
                if (code.length()==0){
                    Log.d("test","----成功了");
                }
            }
        });
    }
    public interface ongeshu{
        void geshu();
    } public void setOngeshu(MyGwcZiAdapter.ongeshu ongeshu) {
        this.ongeshu = ongeshu;
    }
    public void setGetsumprice(MyGwcZiAdapter.getsumprice getsumprice) {
        this.getsumprice = getsumprice;
    }

    public interface getsumprice{
        void onsumprice();
    } 
}
