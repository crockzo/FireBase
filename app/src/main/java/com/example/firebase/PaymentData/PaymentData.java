package com.example.firebase.PaymentData;

import androidx.annotation.Nullable;

public class PaymentData {
    String uId;
    String amount ;
    String name;
    String date;
    String month;

    public PaymentData(){  }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if(obj instanceof PaymentData){
            PaymentData pd = (PaymentData) obj;
            return this.uId.equals(pd.getuId());
        }else{
            return false;
        }
    }
}
