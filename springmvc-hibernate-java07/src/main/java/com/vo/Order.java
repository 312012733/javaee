package com.vo;

public class Order
{
    
    public static enum OrderType
    {
        ASC, DESC
    }
    
    private OrderType orderType;
    
    private String[] fields;
    
    public Order()
    {
    }
    
    public Order(OrderType orderType, String... fields)
    {
        this.orderType = orderType;
        this.fields = fields;
    }
    
    public OrderType getOrderType()
    {
        return orderType;
    }
    
    public void setOrderType(OrderType orderType)
    {
        this.orderType = orderType;
    }
    
    public String[] getFields()
    {
        return fields;
    }
    
    public void setFields(String[] fields)
    {
        this.fields = fields;
    }
    
    public boolean isAsc()
    {
        return OrderType.ASC == orderType;
    }
    
    public boolean isDesc()
    {
        return OrderType.DESC == orderType;
    }
    
}
