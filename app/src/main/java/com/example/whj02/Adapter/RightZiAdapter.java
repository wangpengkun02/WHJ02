package com.example.whj02.Adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.whj02.Bean.JiuGongGeBean;
import com.example.whj02.Bean.RightBean;
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

public class RightZiAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<RightBean.DataBean.ListBean> rightzilist;

    public RightZiAdapter(Context context, List<RightBean.DataBean.ListBean> rightzilist) {
        this.context = context;
        this.rightzilist = rightzilist;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


            View view = LayoutInflater.from(context).inflate(R.layout.rightzi, null,false);
            MyViewHloder viewHolderA = new MyViewHloder(view);
            return viewHolderA;

    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        MyViewHloder vh= (MyViewHloder) holder;
        vh.t01.setText(rightzilist.get(position).getName());
        Uri uri =  Uri.parse(rightzilist.get(position).getIcon());
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(uri)
                .setAutoPlayAnimations(true)
                .build();
        vh.img01 .setController(controller);

        }
    @Override
    public int getItemCount() {
        return rightzilist.size();
    }
      class  MyViewHloder extends RecyclerView.ViewHolder{
        private final TextView t01;
        private final SimpleDraweeView img01;

        public MyViewHloder(View itemView) {
            super(itemView);
            img01 = itemView.findViewById(R.id.zimg01 );
            t01 = itemView.findViewById(R.id.zt01);
        }
    }


}