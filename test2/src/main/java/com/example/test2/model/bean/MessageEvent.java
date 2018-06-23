package com.example.test2.model.bean;

/**
 * author:Created by WangZhiQiang on 2018/5/15.
 */
public class MessageEvent {
    private boolean successHome;
    private boolean successClassify;
    private boolean successDiscover;
    private boolean successCart;
    private boolean successMine;
    private boolean clickHome;
    private boolean clickClassify;
    private boolean clickDiscover;
    private boolean clickCart;
    private boolean clickMine;
    private boolean loginOk;
    private boolean successAll;
    private int page;

    public boolean isLoginOk() {
        return loginOk;
    }

    public void setLoginOk(boolean loginOk) {
        this.loginOk = loginOk;
    }

    public boolean isClickHome() {
        return clickHome;
    }

    public void setClickHome(boolean clickHome) {
        this.clickHome = clickHome;
    }

    public boolean isClickClassify() {
        return clickClassify;
    }

    public void setClickClassify(boolean clickClassify) {
        this.clickClassify = clickClassify;
    }

    public boolean isClickDiscover() {
        return clickDiscover;
    }

    public void setClickDiscover(boolean clickDiscover) {
        this.clickDiscover = clickDiscover;
    }

    public boolean isClickCart() {
        return clickCart;
    }

    public void setClickCart(boolean clickCart) {
        this.clickCart = clickCart;
    }

    public boolean isClickMine() {
        return clickMine;
    }

    public void setClickMine(boolean clickMine) {
        this.clickMine = clickMine;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public boolean isSuccessHome() {
        return successHome;
    }

    public void setSuccessHome(boolean successHome) {
        this.successHome = successHome;
    }

    public boolean isSuccessClassify() {
        return successClassify;
    }

    public void setSuccessClassify(boolean successClassify) {
        this.successClassify = successClassify;
    }

    public boolean isSuccessDiscover() {
        return successDiscover;
    }

    public void setSuccessDiscover(boolean successDiscover) {
        this.successDiscover = successDiscover;
    }

    public boolean isSuccessCart() {
        return successCart;
    }

    public void setSuccessCart(boolean successCart) {
        this.successCart = successCart;
    }

    public boolean isSuccessMine() {
        return successMine;
    }

    public void setSuccessMine(boolean successMine) {
        this.successMine = successMine;
    }

    public boolean isSuccessAll() {
        return successAll;
    }

    public void setSuccessAll(boolean successAll) {
        this.successAll = successAll;
    }
}
