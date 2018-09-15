package es.eci.elejandria.event.sender.beans;

public class ProductBean {

    private String reference;

    private Integer quantity;

    private Float price;

    public enum Category {
        SPORTS, FOOD, COSMETICS, ELECTRONICS, FASHION
    }

    private Category category;

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ProductBean{");
        sb.append("reference='").append(reference).append('\'');
        sb.append(", category=").append(category);
        sb.append(", quantity=").append(quantity);
        sb.append(", price=").append(price);
        sb.append('}');
        return sb.toString();
    }
}
