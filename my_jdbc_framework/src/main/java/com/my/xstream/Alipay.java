package com.my.xstream;

import java.util.List;

import com.alibaba.fastjson.annotation.JSONPOJOBuilder;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAliasType;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import com.thoughtworks.xstream.annotations.XStreamInclude;
import com.thoughtworks.xstream.converters.extended.ToAttributedValueConverter;

/*
 *  对应的xml里面的标签
 * @XStreamAlias("param") 
 * 
 * 解析标签的属性
 * @XStreamConverter(value = ToAttributedValueConverter.class, strings ={ "innerHtml" })
 *
 * 获取标签的属性
 * @XStreamAsAttribute
 *
 * 解析成集合或数字，元素对应的标签
 * @XStreamImplicit(itemFieldName = "param")
 * */
@XStreamAlias("alipay")
public class Alipay
{
    
    @XStreamAlias("param")
    private Param param;
    
    @XStreamAlias("is_success")
    private String isSuccess;
    
    @XStreamAlias("request")
    private Request request;
    
    @XStreamAlias("response")
    private Response response;
    
    @XStreamAsAttribute
    @XStreamAlias("error")
    private String error1;
    
   
    @XStreamAlias("error")
    private Error error;
    
    


    @Override
    public String toString()
    {
        return "Alipay [error1=" + "" + ", error2=" + error + "]";
    }
    
    @XStreamAlias("error")
    @XStreamConverter(value = ToAttributedValueConverter.class, strings ={ "innerHtml" })
    public static class Error
    {
        String innerHtml;

        public String getInnerHtml()
        {
            return innerHtml;
        }

        public void setInnerHtml(String innerHtml)
        {
            this.innerHtml = innerHtml;
        }

        @Override
        public String toString()
        {
            return "Error [innerHtml=" + innerHtml + "]";
        }
        
    }
    
    public static class Request
    {
        
        @XStreamImplicit(itemFieldName = "param")
        private List<Param> param;
        
        public List<Param> getParam()
        {
            return param;
        }
        
        public void setParam(List<Param> param)
        {
            this.param = param;
        }
        
    }
    
    @XStreamConverter(value = ToAttributedValueConverter.class, strings =
    { "innerHtml" })
    public static class Param
    {
        
        public Param(String name, String content)
        {
            super();
            this.name = name;
            this.innerHtml = content;
        }
        
        @XStreamAsAttribute
        private String type;
        
        @XStreamAsAttribute
        private String name;
        
        private String innerHtml;
        
        public String getName()
        {
            return name;
        }
        
        public void setName(String name)
        {
            this.name = name;
        }
        
        public String getInnerHtml()
        {
            return innerHtml;
        }
        
        public void setInnerHtml(String innerHtml)
        {
            this.innerHtml = innerHtml;
        }
        
        public String getType()
        {
            return type;
        }
        
        public void setType(String type)
        {
            this.type = type;
        }
        
        @Override
        public String toString()
        {
            return "Param [type=" + type + ", name=" + name + ", innerHtml=" + innerHtml + ", error1=" + "]";
        }
        
    }
    
    public static class Response
    {
        
        @XStreamAlias("trade")
        private Rrade rrade;
        
        public Rrade getRrade()
        {
            return rrade;
        }
        
        public void setRrade(Rrade rrade)
        {
            this.rrade = rrade;
        }
        
    }
    
    public static class Rrade
    {
        
        @XStreamAlias("body")
        private String body;
        @XStreamAlias("buyer_email")
        private String buyer_email;
        @XStreamAlias("buyer_id")
        private String buyer_id;
        @XStreamAlias("coupon_used_fee")
        private String coupon_used_fee;
        @XStreamAlias("discount")
        private String discount;
        @XStreamAlias("flag_trade_locked")
        private String flag_trade_locked;
        @XStreamAlias("gmt_create")
        private String gmt_create;
        @XStreamAlias("gmt_last_modified_time")
        private String gmt_last_modified_time;
        @XStreamAlias("gmt_payment")
        private String gmt_payment;
        @XStreamAlias("is_total_fee_adjust")
        private String is_total_fee_adjust;
        @XStreamAlias("operator_role")
        private String operator_role;
        @XStreamAlias("out_trade_no")
        private String out_trade_no;
        @XStreamAlias("payment_type")
        private String payment_type;
        @XStreamAlias("price")
        private String price;
        @XStreamAlias("quantity")
        private String quantity;
        @XStreamAlias("seller_email")
        private String seller_email;
        @XStreamAlias("seller_id")
        private String seller_id;
        @XStreamAlias("subject")
        private String subject;
        @XStreamAlias("to_buyer_fee")
        private String to_buyer_fee;
        @XStreamAlias("to_seller_fee")
        private String to_seller_fee;
        @XStreamAlias("total_fee")
        private String total_fee;
        @XStreamAlias("trade_no")
        private String trade_no;
        @XStreamAlias("trade_status")
        private String trade_status;
        @XStreamAlias("use_coupon")
        private String use_coupon;
        
        public String getBody()
        {
            return body;
        }
        
        public void setBody(String body)
        {
            this.body = body;
        }
        
        public String getBuyer_email()
        {
            return buyer_email;
        }
        
        public void setBuyer_email(String buyer_email)
        {
            this.buyer_email = buyer_email;
        }
        
        public String getBuyer_id()
        {
            return buyer_id;
        }
        
        public void setBuyer_id(String buyer_id)
        {
            this.buyer_id = buyer_id;
        }
        
        public String getCoupon_used_fee()
        {
            return coupon_used_fee;
        }
        
        public void setCoupon_used_fee(String coupon_used_fee)
        {
            this.coupon_used_fee = coupon_used_fee;
        }
        
        public String getDiscount()
        {
            return discount;
        }
        
        public void setDiscount(String discount)
        {
            this.discount = discount;
        }
        
        public String getFlag_trade_locked()
        {
            return flag_trade_locked;
        }
        
        public void setFlag_trade_locked(String flag_trade_locked)
        {
            this.flag_trade_locked = flag_trade_locked;
        }
        
        public String getGmt_create()
        {
            return gmt_create;
        }
        
        public void setGmt_create(String gmt_create)
        {
            this.gmt_create = gmt_create;
        }
        
        public String getGmt_last_modified_time()
        {
            return gmt_last_modified_time;
        }
        
        public void setGmt_last_modified_time(String gmt_last_modified_time)
        {
            this.gmt_last_modified_time = gmt_last_modified_time;
        }
        
        public String getGmt_payment()
        {
            return gmt_payment;
        }
        
        public void setGmt_payment(String gmt_payment)
        {
            this.gmt_payment = gmt_payment;
        }
        
        public String getIs_total_fee_adjust()
        {
            return is_total_fee_adjust;
        }
        
        public void setIs_total_fee_adjust(String is_total_fee_adjust)
        {
            this.is_total_fee_adjust = is_total_fee_adjust;
        }
        
        public String getOperator_role()
        {
            return operator_role;
        }
        
        public void setOperator_role(String operator_role)
        {
            this.operator_role = operator_role;
        }
        
        public String getOut_trade_no()
        {
            return out_trade_no;
        }
        
        public void setOut_trade_no(String out_trade_no)
        {
            this.out_trade_no = out_trade_no;
        }
        
        public String getPayment_type()
        {
            return payment_type;
        }
        
        public void setPayment_type(String payment_type)
        {
            this.payment_type = payment_type;
        }
        
        public String getPrice()
        {
            return price;
        }
        
        public void setPrice(String price)
        {
            this.price = price;
        }
        
        public String getQuantity()
        {
            return quantity;
        }
        
        public void setQuantity(String quantity)
        {
            this.quantity = quantity;
        }
        
        public String getSeller_email()
        {
            return seller_email;
        }
        
        public void setSeller_email(String seller_email)
        {
            this.seller_email = seller_email;
        }
        
        public String getSeller_id()
        {
            return seller_id;
        }
        
        public void setSeller_id(String seller_id)
        {
            this.seller_id = seller_id;
        }
        
        public String getSubject()
        {
            return subject;
        }
        
        public void setSubject(String subject)
        {
            this.subject = subject;
        }
        
        public String getTo_buyer_fee()
        {
            return to_buyer_fee;
        }
        
        public void setTo_buyer_fee(String to_buyer_fee)
        {
            this.to_buyer_fee = to_buyer_fee;
        }
        
        public String getTo_seller_fee()
        {
            return to_seller_fee;
        }
        
        public void setTo_seller_fee(String to_seller_fee)
        {
            this.to_seller_fee = to_seller_fee;
        }
        
        public String getTotal_fee()
        {
            return total_fee;
        }
        
        public void setTotal_fee(String total_fee)
        {
            this.total_fee = total_fee;
        }
        
        public String getTrade_no()
        {
            return trade_no;
        }
        
        public void setTrade_no(String trade_no)
        {
            this.trade_no = trade_no;
        }
        
        public String getTrade_status()
        {
            return trade_status;
        }
        
        public void setTrade_status(String trade_status)
        {
            this.trade_status = trade_status;
        }
        
        public String getUse_coupon()
        {
            return use_coupon;
        }
        
        public void setUse_coupon(String use_coupon)
        {
            this.use_coupon = use_coupon;
        }
        
    }
    
    @XStreamAlias("sign")
    private String sign;
    
    @XStreamAlias("sign_type")
    private String sign_type;
    
    public String getIsSuccess()
    {
        return isSuccess;
    }
    
    public void setIsSuccess(String isSuccess)
    {
        this.isSuccess = isSuccess;
    }
    
    public String getSign()
    {
        return sign;
    }
    
    public void setSign(String sign)
    {
        this.sign = sign;
    }
    
    public String getSign_type()
    {
        return sign_type;
    }
    
    public void setSign_type(String sign_type)
    {
        this.sign_type = sign_type;
    }
    
    public Request getRequest()
    {
        return request;
    }
    
    public void setRequest(Request request)
    {
        this.request = request;
    }
    
    public Response getResponse()
    {
        return response;
    }
    
    public void setResponse(Response response)
    {
        this.response = response;
    }
    
    // public Integer getError()
    // {
    // return error;
    // }
    //
    // public void setError(Integer error)
    // {
    // this.error = error;
    // }
    
    public Param getParam()
    {
        return param;
    }
    
    public void setParam(Param param)
    {
        this.param = param;
    }
    
}