package hanium.where2go.domain;

import java.math.BigDecimal;

public class Restaurant {

    public Long restaurantId;
    public Owner owner;
    public Customer customer;
    public String restaurantName;
    public String address;
    public String description;
    public String tel;
    public String business_registration;
    public int seat;
    public BigDecimal longitude;
    public BigDecimal latitude;
}