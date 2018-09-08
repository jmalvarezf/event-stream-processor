package es.eci.elejandria.event.sender.beans;

public class Promotion {

    public enum BENEFIT_TYPE {
        PERCENTAGE, DISCOUNT, MULTIPLE
    }

    private String name;

    private String description;

    private float benefit;

    private BENEFIT_TYPE benefitType;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getBenefit() {
        return benefit;
    }

    public void setBenefit(float benefit) {
        this.benefit = benefit;
    }

    public BENEFIT_TYPE getBenefitType() {
        return benefitType;
    }

    public void setBenefitType(BENEFIT_TYPE benefitType) {
        this.benefitType = benefitType;
    }
}
