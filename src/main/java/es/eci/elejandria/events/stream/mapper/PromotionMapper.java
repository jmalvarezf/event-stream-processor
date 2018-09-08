package es.eci.elejandria.events.stream.mapper;

import es.eci.elejandria.event.sender.beans.EventBean;
import es.eci.elejandria.event.sender.beans.ProductBean;
import es.eci.elejandria.event.sender.beans.Promotion;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.KeyValueMapper;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class PromotionMapper implements KeyValueMapper<String, EventBean, KeyValue<String, EventBean>> {

    private static Random random = new Random();

    @Override
    public KeyValue<String, EventBean> apply(String s, EventBean eventBean) {
        if (eventBean.getCustomerAllowed()) {
            float total = 0;
            for (ProductBean product : eventBean.getProducts()) {
                total += product.getPrice();
            }
            if (total > 100 && eventBean.getEventType().equals(EventBean.EventType.BUY) || eventBean.getEventType().equals(EventBean.EventType.RETURN)) {
                //apply a promotion
                Promotion promo = new Promotion();
                int low = 5;
                int high = 20;
                promo.setBenefit(random.nextInt(high - low) + low);
                promo.setBenefitType(Promotion.BENEFIT_TYPE.DISCOUNT);
                promo.setDescription("Promoción asociada a compra");
                promo.setName("Order_Promotion");
                eventBean.setPromotion(promo);
            }
        }
        return new KeyValue<String, EventBean>(s, eventBean);

    }
}
