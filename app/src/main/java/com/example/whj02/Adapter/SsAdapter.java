package com.example.whj02.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.whj02.Bean.SsBean;
import com.example.whj02.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by 浮生丶 on 2018/6/22 0022.
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

public class SsAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<SsBean.DataBean> datas;
    int TYPRONE;

    public SsAdapter(Context context, List<SsBean.DataBean> datas, int TYPRONE) {
        this.context = context;
        this.datas = datas;
        this.TYPRONE = TYPRONE;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (TYPRONE==1){
            View view = View.inflate(context, R.layout.ss_item, null);
            LMyViewHolder lvh=new LMyViewHolder(view);
            return lvh;
        }else{
            View view = View.inflate(context, R.layout.ss_item02, null);
            GMyViewHolder gvh=new GMyViewHolder(view);
            return gvh;
        }
     }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof LMyViewHolder){
            LMyViewHolder lvh= (LMyViewHolder) holder;
            lvh.ssitemname.setText(datas.get(position).getTitle());
            lvh.ssitemprice.setText(""+datas.get(position).getPrice());
            String[] split = datas.get(position).getImages().split("\\|");
            lvh.ssitemimg.setImageURI(split[0]);


        }else if(holder instanceof GMyViewHolder){
            GMyViewHolder gvh= (GMyViewHolder) holder;
            gvh.ssitemname.setText(datas.get(position).getTitle());
            gvh.ssitemprice.setText(""+datas.get(position).getPrice());
            String[] split = datas.get(position).getImages().split("\\|");
            gvh.ssitemimg.setImageURI(split[0]);


        }






    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    private class LMyViewHolder extends RecyclerView.ViewHolder {

        private final TextView ssitemname;
        private final TextView ssitemprice;
        private final SimpleDraweeView ssitemimg;
        public LMyViewHolder(View itemView) {
            super(itemView);
            ssitemname = itemView.findViewById(R.id.ssitemname);
            ssitemprice = itemView.findViewById(R.id.ssitemprice);
            ssitemimg = itemView.findViewById(R.id.ssitemimg);
        }
    }
    private class GMyViewHolder extends RecyclerView.ViewHolder {

        private final TextView ssitemname;
        private final TextView ssitemprice;
        private final SimpleDraweeView ssitemimg;
        public GMyViewHolder(View itemView) {
            super(itemView);
            ssitemname = itemView.findViewById(R.id.gssitemname);
            ssitemprice = itemView.findViewById(R.id.gssitemprice);
            ssitemimg = itemView.findViewById(R.id.gssitemimg);
        }
    }
}
