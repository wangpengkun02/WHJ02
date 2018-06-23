package com.example.test2.model.callback;

import com.example.test2.model.bean.AddAddrBean;
import com.example.test2.model.bean.AddCartBean;
import com.example.test2.model.bean.AddrsBean;
import com.example.test2.model.bean.CartBean;
import com.example.test2.model.bean.ClassifyBean;
import com.example.test2.model.bean.ClassifyFenBean;
import com.example.test2.model.bean.CreateOrderBean;
import com.example.test2.model.bean.DefaultAddrBean;
import com.example.test2.model.bean.DeleteCartBean;
import com.example.test2.model.bean.HomeBean;
import com.example.test2.model.bean.HomeJiuBean;
import com.example.test2.model.bean.LoginBean;
import com.example.test2.model.bean.NickNameBean;
import com.example.test2.model.bean.OrdersBean;
import com.example.test2.model.bean.PicturesBean;
import com.example.test2.model.bean.ProductDetailsBean;
import com.example.test2.model.bean.ProductListBean;
import com.example.test2.model.bean.RegisterBean;
import com.example.test2.model.bean.SetAddrBean;
import com.example.test2.model.bean.UpdateAddrBean;
import com.example.test2.model.bean.UpdateOrderBean;
import com.example.test2.model.bean.UserInfoBean;

import java.util.HashMap;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * author:Created by WangZhiQiang on 2018/5/18.
 */
public interface RetrofitInterface {
    //https://www.zhaoapi.cn/ad/getAd
    @GET("ad/getAd")
    Observable<HomeBean> getHomeBean();
    //https://www.zhaoapi.cn/product/getCatagory
    @GET("product/getCatagory")
    Observable<HomeJiuBean> getHomeJiuBean();
    //https://www.zhaoapi.cn/product/getCatagory
    @GET("product/getCatagory")
    Observable<ClassifyBean> getClassifyBean();
    //https://www.zhaoapi.cn/product/getProductCatagory
    @GET("product/getProductCatagory")
    Observable<ClassifyFenBean> getClassifyFenBean(@Query("cid")String cid);
    //https://www.zhaoapi.cn/product/getCarts
    @POST("product/getCarts")
    @FormUrlEncoded
    Observable<CartBean> getCartBean(@FieldMap HashMap<String, String> map);
    //https://www.zhaoapi.cn/product/deleteCart
    @POST("product/deleteCart")
    @FormUrlEncoded
    Observable<DeleteCartBean> getDeleteCartBean(@FieldMap HashMap<String, String> map);
    //https://www.zhaoapi.cn/user/getUserInfo
    @POST("user/getUserInfo")
    @FormUrlEncoded
    Observable<UserInfoBean> getUserInfoBean(@FieldMap HashMap<String, String> map);
    //https://www.zhaoapi.cn/user/login
    @POST("user/login")
    @FormUrlEncoded
    Observable<LoginBean> getLoginBean(@FieldMap HashMap<String, String> map);
    //https://www.zhaoapi.cn/user/reg
    @POST("user/reg")
    @FormUrlEncoded
    Observable<RegisterBean> getRegisterBean(@FieldMap HashMap<String, String> map);
    //https://www.zhaoapi.cn/product/getProducts
    @GET("product/getProducts")
    Observable<ProductListBean> getProductListBean(@Query("pscid")String pscid);
    //https://www.zhaoapi.cn/product/getProductDetail
    @GET("product/getProductDetail")
    Observable<ProductDetailsBean> getProductDetailsBean(@QueryMap HashMap<String, String> map);
    //https://www.zhaoapi.cn/product/addCart
    @POST("product/addCart")
    @FormUrlEncoded
    Observable<AddCartBean> getAddCartBean(@FieldMap HashMap<String, String> map);
    //https://www.zhaoapi.cn/user/updateNickName
    @POST("user/updateNickName")
    @FormUrlEncoded
    Observable<NickNameBean> getNickNameBean(@FieldMap HashMap<String, String> map);
    //https://www.zhaoapi.cn/file/upload
    @Multipart
    @POST("file/upload")
    Observable<PicturesBean> getPicturesBean(@Part("uid") RequestBody uid,@Part MultipartBody.Part file);
    //https://www.zhaoapi.cn/product/createOrder?uid=14944&price=100
    @POST("product/createOrder")
    @FormUrlEncoded
    Observable<CreateOrderBean> getCreateOrderBean(@FieldMap HashMap<String, String> map);
    //https://www.zhaoapi.cn/product/getOrders?uid=14944
    @POST("product/getOrders")
    @FormUrlEncoded
    Observable<OrdersBean> getOrdersBean(@Field("uid") String uid);
    //https://www.zhaoapi.cn/product/updateOrder?uid=14944&status=2&orderId=10717
    @POST("product/updateOrder")
    @FormUrlEncoded
    Observable<UpdateOrderBean> getUpdateOrderBean(@FieldMap HashMap<String, String> map);
    //https://www.zhaoapi.cn/user/getAddrs?uid=14944
    @POST("user/getAddrs")
    @FormUrlEncoded
    Observable<AddrsBean> getAddrsBean(@Field("uid") String uid);
    //https://www.zhaoapi.cn/user/getDefaultAddr?uid=14944
    @POST("user/getDefaultAddr")
    @FormUrlEncoded
    Observable<DefaultAddrBean> getDefaultAddrBean(@Field("uid") String uid);
    //https://www.zhaoapi.cn/user/setAddr?uid=14944&addrid=2022&status=0
    @POST("user/setAddr")
    @FormUrlEncoded
    Observable<SetAddrBean> getSetAddrBean(@FieldMap HashMap<String, String> map);
    //https://www.zhaoapi.cn/user/updateAddr?uid=14944&addrid=2022&addr=%E6%B3%95%E6%8C%89&name=%E4%B9%8B%E8%A8%80
    @POST("user/updateAddr")
    @FormUrlEncoded
    Observable<UpdateAddrBean> getUpdateAddrBean(@FieldMap HashMap<String, String> map);
    //https://www.zhaoapi.cn/user/addAddr?uid=14944&addr=%E8%A5%BF%E4%BA%8C%E6%97%97&mobile=18611111119&name=%E5%AE%9E%E9%AA%8C1
    @POST("user/addAddr")
    @FormUrlEncoded
    Observable<AddAddrBean> getAddAddrBean(@FieldMap HashMap<String, String> map);
}
