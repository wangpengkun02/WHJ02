package com.example.whj02.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.example.whj02.Adapter.MyGwcAdapter;
import com.example.whj02.Bean.DwcBean;
import com.example.whj02.R;

import com.example.whj02.demo.PayResult;
import com.example.whj02.demo.SignUtils;
import com.example.whj02.utils.OkHttp3Util_03;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class GouWuCheFragment extends Fragment   {
    SharedPreferences test;
    private SharedPreferences.Editor edit;
    private boolean pd;
    private int uid;
    private View view;
    private ImageView mBack;
    /**
     * 编辑
     */
    private TextView mEdit;
    private LinearLayout mTopBar;
    private RecyclerView mGwcrlv;
    /**
     * 全选
     */
    private CheckBox mAllChekbox;
    /**
     * 合计:
     */
    private TextView mTvTotalPrice;
    /**
     * 去结算(0)
     */
    private TextView mTvGoToPay;
    boolean isselect=false;
//易大师总结:这几行都是配置信息,以前在支付宝里是专门放到Keys配置类里,现在集成到此处.
    //实际把一下敏感信息放到这里(客户端)是极为不安全的,真实开发这些都是要放到服务器上,用到时在请求服务器,直接拿结果payInfo,字符串让服务器拼接即可.
    //上节课的怎么配置,实际在工作中不是我们的职责范围,应该由老板或者经理去弄,但是他们连什么是RSA都不知道,你就报最坏的打算,给老大一些指导当然要给老大面子,这个配置确实麻烦.

    // 商户PID
    public static final String PARTNER = "2088221871911835";
    // 商户收款账号
    public static final String SELLER = "zhuangshiyigou@163.com";

    // 商户私钥(你配置的东西)，pkcs8格式,注意复制到这里的公私钥信息要掐头去尾,没有回车,空格,就一行.
    public static final String RSA_PRIVATE ="MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBALmhW0c+ZO7sB3utNmkQu5hkpqnw+nAPLCdBoPEw+E+7qcOZBdXxwZsG05yr2BYp5j0MTE0FpKRu+uNTVTAX/MzhcInAColtnWkF1R8rsIdlLdleRoMcM3Lo4XesctdsyxLeummrFLKQfASMqS64kkTUoEFGb3tlYZs7iSFgpM1pAgMBAAECgYAGm6zhK2JycuqNR4xBTzwuX57jO9XeeVvMBfURwPmF9RtFAESJ6jJHL4YG9MMbfuBYWgC5WTMUO3Mo9oV40dHI9dPwANL5aPeKEIUawoQyuCyY/js84fOY3+TbEnXym8G6+Zxm+bGKQn3ZZqlSgR3CHk5f/CceoXvPWDLwl//RtQJBANzDaQTeckTUEtxVyZ9vM26Sv+TKULvqf8OHdrGm7WOWY6/CbChrxMKHxkdrNwZPNIhozNfTaOmnJaPqeMKo8SMCQQDXQmEPHj0h4Qjn3D6n6WiNOvUsNbzVpLP/TgwHFYkRLcz+GPRkbXXdvkSUKxNo7vwZr8vwTIquYA+K3CFTpr4DAkEAu3Ox2NCJdqgc27p8WUSzB1DUYBDqPKYBlqWPw4laSRWJz9Pmwuu/Ru7DDiGbt1/J24ohZaG9k6i57VVK9P8+wQJBAIgGtFrfWvY7xGrwbM+i2aTVqvTDCI9hQzWEVmlrnHA0pyOzFU0ZNrBneeK/zcYzry90PcWeOMy0e13eeVjpN40CQQCMBjVBeTdQ9afgGnBR6glIWrCtqTBAsUIr3gvNZYWdaznr0FmG2pjLwDLUsx0SUCpcrTQxhWu16HDEyQuCm7Ar";


    // 支付宝公钥,不是你本地生成
    public static final String RSA_PUBLIC = "";
    private static final int SDK_PAY_FLAG = 1;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((String) msg.obj);
                    /**
                     * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
                     * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
                     * docType=1) 建议商户依赖异步通知
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息

                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        Toast.makeText(getContext(), "支付成功", Toast.LENGTH_SHORT).show();
                    } else {
                        // 判断resultStatus 为非"9000"则代表可能支付失败
                        // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(getContext(), "支付结果确认中", Toast.LENGTH_SHORT).show();
                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            Toast.makeText(getContext(), "支付失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
                }
                default:
                    break;
            }
        };
    };
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gou_wu_che, container, false);
        initView(view);
        return view;
    }


    private void initView(View view) {
        mBack = (ImageView) view.findViewById(R.id.back);
        mEdit = (TextView) view.findViewById(R.id.edit);
        mTopBar = (LinearLayout) view.findViewById(R.id.top_bar);
        mGwcrlv = (RecyclerView) view.findViewById(R.id.gwcrlv);

        mAllChekbox = (CheckBox) view.findViewById(R.id.all_chekbox);
        mTvTotalPrice = (TextView) view.findViewById(R.id.tv_total_price);
        mTvGoToPay = (TextView) view.findViewById(R.id.tv_go_to_pay);

        mGwcrlv.setLayoutManager(new LinearLayoutManager(getContext(), OrientationHelper.VERTICAL, false));
        test = getActivity().getSharedPreferences("test", Context.MODE_PRIVATE);
        edit = test.edit();
        uid = test.getInt("uid", 0);
        pd = test.getBoolean("pd", false);
        if (pd) {
            Log.d("test", "-----" + uid);
            String gwcpath = "https://www.zhaoapi.cn/product/getCarts?uid=" + uid;
            getgwc(gwcpath);
        } else {
            Toast.makeText(getContext(), "你还未登录,请登录后查看", Toast.LENGTH_SHORT).show();
        }
        final String price = mTvTotalPrice.getText().toString();
        mTvGoToPay.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(),"-----------0",Toast.LENGTH_SHORT).show();
                    //对公私进行非空判断.
                    if (TextUtils.isEmpty(PARTNER) || TextUtils.isEmpty(RSA_PRIVATE) || TextUtils.isEmpty(SELLER)) {
                        //对话框
                        new AlertDialog.Builder(getContext()).setTitle("警告").setMessage("需要配置PARTNER | RSA_PRIVATE| SELLER")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialoginterface, int i) {
                                        getActivity().finish();
                                    }
                                }).show();
                        return;
                    }

                    //生成订单信息,拼接字符串,订单号,公司立马服务器接口(url)
                    String orderInfo = getOrderInfo("测试的商品", "该测试商品的详细描述", "0.01");

                    /**
                     * 特别注意，这里的签名逻辑需要放在服务端，切勿将私钥泄露在代码中！对订单信息用我们上面的私钥加密.
                     */
                    String sign = sign(orderInfo);
                    try {
                        /**
                         * 仅需对sign 做UTF-8编码,我们发送内容符合支付宝要求,无论是否是UTF编码,我们都转一下.
                         */
                        sign = URLEncoder.encode(sign, "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

                    /**
                     * 完整的符合支付宝参数规范的订单信息,完全就是为了符合支付宝的要求
                     */
                    final String payInfo = orderInfo + "&sign=\"" + sign + "\"&" + getSignType();

                    //在一个子线程中,进行处理,是一个同步操作.网络请求,由客户端,把数据直接传送给支付宝服务器
                    Runnable payRunnable = new Runnable() {
                        @Override
                        public void run() {
                            // 构造PayTask 对象
                            PayTask alipay = new PayTask(getActivity());
                            // 调用支付接口，获取支付结果,你要知道传的参数是什么,给你结果是什么,你拿去用就行.
                            String result = alipay.pay(payInfo, true);

                            //用handler把支付的结果更新到UI界面上.
                            Message msg = new Message();
                            msg.what = SDK_PAY_FLAG;
                            msg.obj = result;
                            mHandler.sendMessage(msg);
                        }
                    };
                    // 必须异步调用
                    Thread payThread = new Thread(payRunnable);
                    payThread.start();
                }

        });
    }



    private void getgwc(String gwcpath) {
        OkHttp3Util_03.doGet(gwcpath, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                final DwcBean dwcBean = new Gson().fromJson(string, DwcBean.class);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        final List<DwcBean.DataBean> data = dwcBean.getData();
                        final MyGwcAdapter adapter = new MyGwcAdapter(getContext(), data, uid);
                        mGwcrlv.setAdapter(adapter);
                        adapter.setOnzong(new MyGwcAdapter.onzong() {
                            @Override
                            public void onshangjia() {
                                boolean flag = true;
                                for (DwcBean.DataBean dataBean : data) {
                                    for (DwcBean.DataBean.ListBean listBean : dataBean.getList()) {
                                        if (listBean.getSelected() == 0) {
                                            flag = false;
                                        }
                                    }
                                }
                                mAllChekbox.setChecked(flag);
                            }

                            @Override
                            public void onshangpin() {
                                boolean flag = true;
                                for (DwcBean.DataBean dataBean : data) {
                                    for (DwcBean.DataBean.ListBean listBean : dataBean.getList()) {
                                        if (listBean.getSelected() == 0) {
                                            flag = false;
                                        }

                                    }
                                }
                                mAllChekbox.setChecked(flag);
                            }

                        });
                        mAllChekbox.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                float price = 0;
                                for (DwcBean.DataBean dataBean : data) {
                                    for (DwcBean.DataBean.ListBean listBean : dataBean.getList()) {
                                        price += listBean.getPrice() * listBean.getNum();
                                        if (mAllChekbox.isChecked()) {

                                            listBean.setSelected(1);
                                        } else {
                                            listBean.setSelected(0);
                                        }
                                    }
                                }
                                mTvTotalPrice.setText(price + "");
                                adapter.notifyDataSetChanged();

                            }
                        });
                        adapter.setGetSumprice(new MyGwcAdapter.getSumprice() {
                            @Override
                            public void onSumprice() {
                                float price=0;
                                for (DwcBean.DataBean dataBean : data) {
                                    for (DwcBean.DataBean.ListBean listBean : dataBean.getList()) {
                                        if(listBean.getSelected()==1)
                                        {
                                            price+=listBean.getPrice()*listBean.getNum();
                                        }

                                    }
                                }
                                mTvTotalPrice.setText(price+"");
                            }
                        });
                    }
                });
            }
        });
    }
    /**
     * create the order info. 创建订单信息,实际就是拼接一个大的字符串.
     */
    private String getOrderInfo(String subject, String body, String price) {

        // 签约合作者身份ID
        String orderInfo = "partner=" + "\"" + PARTNER + "\"";

        // 签约卖家支付宝账号
        orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

        // 商户网站唯一订单号,支付宝使用方法模拟了一个订单编号,实际开发中服务器会给我们一个订单编号,时间具备了唯一性
        orderInfo += "&out_trade_no=" + "\"" + getOutTradeNo() + "\"";

        // 商品名称
        orderInfo += "&subject=" + "\"" + subject + "\"";

        // 商品详情
        orderInfo += "&body=" + "\"" + body + "\"";

        // 商品金额
        orderInfo += "&total_fee=" + "\"" + price + "\"";

        // 服务器异步通知页面路径(就是自己服务器如果想知道支付到底有没有成功,当支付宝服务器支付完成后,会调用此链接进行通知,此链接由服务器开发人员设置)
        // 这个接口网址可以改成自己服务器的,不过我们没有,所以就不改了
        orderInfo += "&notify_url=" + "\"" + "http://notify.msp.hk/notify.htm" + "\"";

        // 服务接口名称， 固定值
        orderInfo += "&service=\"mobile.securitypay.pay\"";

        // 支付类型， 固定值
        orderInfo += "&payment_type=\"1\"";

        // 参数编码， 固定值
        orderInfo += "&_input_charset=\"utf-8\"";

        // 设置未付款交易的超时时间
        // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
        // 取值范围：1m～15d。
        // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
        // 该参数数值不接受小数点，如1.5h，可转换为90m。
        orderInfo += "&it_b_pay=\"30m\"";

        // extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
        // orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

        // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
        orderInfo += "&return_url=\"m.alipay.com\"";

        // 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
        // orderInfo += "&paymethod=\"expressGateway\"";

        return orderInfo;
    }
    /**
     * get the out_trade_no for an order. 生成商户订单号，该值在商户端应保持唯一（可自定义格式规范）
     * 使用到了时间戳,和产生随机数的类.来保证订单唯一性(时间戳+随机数),不是我们做的
     */
    private String getOutTradeNo() {
        SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss", Locale.getDefault());
        Date date = new Date();
        String key = format.format(date);

        Random r = new Random();
        key = key + r.nextInt();
        key = key.substring(0, 15);
        return key;
    }
    /**
     * sign the order info. 对订单信息进行签名
     *
     * @param content
     *            待签名订单信息
     */
    private String sign(String content) {
        //参数1.内容订单信息    参数2.私钥
        return SignUtils.sign(content, RSA_PRIVATE);
    }
    /**
     * get the sign type we use. 获取签名方式
     *
     */
    private String getSignType() {
        return "sign_type=\"RSA\"";
    }

}



