package es.eci.elejandria.event.sender.beans;

import java.util.Date;
import java.util.List;

public class EventBean {

    private enum EventType {
        CLICK, BUY, RECOMMEND, RETURN
    }

    private enum Origin {
        WEB, SHOP, PHONE
    }

    private EventType eventType;

    private List<ProductBean> products;

    private Origin origin;

    private CustomerBean customer;

    private Date timestamp;

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public Origin getOrigin() {
        return origin;
    }

    public void setOrigin(Origin origin) {
        this.origin = origin;
    }

    public List<ProductBean> getProducts() {
        return products;
    }

    public void setProducts(List<ProductBean> products) {
        this.products = products;
    }

    public CustomerBean getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerBean customer) {
        this.customer = customer;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("EventBean{");
        sb.append("eventType=").append(eventType);
        sb.append(", products=").append(products);
        sb.append(", origin=").append(origin);
        sb.append(", customer=").append(customer);
        sb.append(", timestamp=").append(timestamp);
        sb.append('}');
        return sb.toString();
    }
}
