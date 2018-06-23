package com.example.whj02.Adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.whj02.Bean.JiuGongGeBean;
import com.example.whj02.R;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 浮生丶 on 2018/6/11 0011.
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

public class LeftAdapter extends RecyclerView.Adapter {
    private Context context;
    private  OnItemClickListener onItemClickListener;
    private int defItem;//声明默认选中的项

    List<JiuGongGeBean.DataBean> jiugongdata =new ArrayList<>();

    public LeftAdapter(Context context, List<JiuGongGeBean.DataBean> jiugongdata) {
        this.context = context;
        this.jiugongdata = jiugongdata;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


            View view = LayoutInflater.from(context).inflate(R.layout.left, null,false);
            MyViewHloder viewHolderA = new MyViewHloder(view);
            return viewHolderA;

    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        MyViewHloder vh= (MyViewHloder) holder;
        vh.lefttext.setText(jiugongdata.get(position).getName());
        vh.lefttext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener!=null){
                    onItemClickListener.onItemClick(position);


                }
            }
        });
        }
    @Override
    public int getItemCount() {
        return jiugongdata.size();
    }
      class  MyViewHloder extends RecyclerView.ViewHolder{
        private final TextView lefttext;


        public MyViewHloder(View itemView) {
            super(itemView);

            lefttext = itemView.findViewById(R.id.lefttext);

        }
    }
    public   interface OnItemClickListener {
        void onItemClick(int position);

    }
    public void SetOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener=onItemClickListener;
    }


}