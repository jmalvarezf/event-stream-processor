package es.eci.elejandria.event.sender.beans;

public class EventBean {

    private enum EventType {
        CLICK, BUY
    }

    private enum Origin {
        WEB, SHOP, PHONE
    }

    private EventType eventType;

    private Integer quantity;

    private Origin origin;

    private Integer customerId;

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Origin getOrigin() {
        return origin;
    }

    public void setOrigin(Origin origin) {
        this.origin = origin;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("EventBean{");
        sb.append("eventType=").append(eventType);
        sb.append(", quantity=").append(quantity);
        sb.append(", origin=").append(origin);
        sb.append(", customerId=").append(customerId);
        sb.append('}');
        return sb.toString();
    }

}
