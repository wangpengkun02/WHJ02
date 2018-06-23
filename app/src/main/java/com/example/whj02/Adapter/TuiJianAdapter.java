package com.example.whj02.Adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.whj02.Bean.ShouYeBean;
import com.example.whj02.R;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;

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

public class TuiJianAdapter extends RecyclerView.Adapter  {
    private Context context;
    List<ShouYeBean.TuijianBean.ListBean> tuijians;
    private  OnItemClickListener onItemClickListener;

    public TuiJianAdapter(Context context, List<ShouYeBean.TuijianBean.ListBean> tuijians) {
        this.context = context;
        this.tuijians = tuijians;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LinearLayout.inflate(context, R.layout.tuijianitem, null);
        MyViewHolder vh = new MyViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        MyViewHolder vh= (MyViewHolder) holder;
        vh.tuititle.setText(tuijians.get(position).getTitle());

        String[] split = tuijians.get(position).getImages().split("\\|");
        Uri uri =  Uri.parse(split[0]);
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(uri)
                .setAutoPlayAnimations(true)
                .build();
        vh.tuitu .setController(controller);
        vh.itemView.setOnClickListener(new View.OnClickListener() {
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
        return tuijians.size();
    }

    private class MyViewHolder extends  RecyclerView.ViewHolder {

        private final SimpleDraweeView tuitu;
        private final TextView tuititle;

        public MyViewHolder(View itemView) {
            super(itemView);
            tuitu = itemView.findViewById(R.id.tuitu);
            tuititle = itemView.findViewById(R.id.tuititle);
        }
    }
    public   interface OnItemClickListener {
        void onItemClick(int position);

    }
    public void SetOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener=onItemClickListener;
    }
}
