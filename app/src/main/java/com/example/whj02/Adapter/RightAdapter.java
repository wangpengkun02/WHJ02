package com.example.whj02.Adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.whj02.Bean.JiuGongGeBean;
import com.example.whj02.Bean.RightBean;
import com.example.whj02.R;

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

public class RightAdapter extends RecyclerView.Adapter {
    private Context context;
    RightZiAdapter rightZiAdapter;

    List<RightBean.DataBean> righttuidata  =new ArrayList<>();
    private List<RightBean.DataBean.ListBean> rightzilist;

    public RightAdapter(Context context, List<RightBean.DataBean> righttuidata) {
        this.context = context;
        this.righttuidata = righttuidata;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


            View view = LayoutInflater.from(context).inflate(R.layout.right, null,false);
            MyViewHloder viewHolderA = new MyViewHloder(view);
            return viewHolderA;

    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        MyViewHloder vh= (MyViewHloder) holder;
        Log.d("TAG", "或" + righttuidata.get(position).getName() );
        rightzilist = righttuidata.get(position).getList();

        vh.rightzifen.setLayoutManager(new GridLayoutManager(context,3,GridLayoutManager.VERTICAL,false));
        rightZiAdapter = new RightZiAdapter (context,rightzilist);
        vh.rightzifen.setAdapter(rightZiAdapter);
        vh.righttou.setText(righttuidata.get(position).getName());
        }
    @Override
    public int getItemCount() {
        return righttuidata.size();
    }
      class  MyViewHloder extends RecyclerView.ViewHolder{
        private  RecyclerView rightzifen;
          private  TextView righttou;
        public MyViewHloder(View itemView) {
            super(itemView);

            righttou = itemView.findViewById(R.id.righttou);
            rightzifen = itemView.findViewById(R.id.rightzifen);
        }
    }



}