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
                if (eventBean.getEventType().equals(EventBean.EventType.RETURN)) {
                    promo.setDescription("Sentimos su devolución. Para hacer mas fáciles sus compras en El Corte Inglés a futuro, le regalamos un descuento de un " + promo.getBenefit() + "% en su proxima compra");
                } else {
                    promo.setDescription("Gracias por su compra. Para premiar su fidelidad, le regalamos un descuento de un " + promo.getBenefit() + "% en su proxima compra");
                }
                promo.setName("Order_Promotion");
                eventBean.setPromotion(promo);
            }
            if (eventBean.getEventType().equals(EventBean.EventType.BUY) && eventBean.getPromotion() == null) {
                boolean isSports = false;
                for (ProductBean product : eventBean.getProducts()) {
                    if (product.getCategory().equals(ProductBean.Category.SPORTS)) {
                        Promotion promo = new Promotion();
                        promo.setBenefitType(Promotion.BENEFIT_TYPE.MULTIPLE);
                        promo.setBenefit(10);
                        promo.setName("Ball");
                        promo.setDescription("Regalamos un balón valorado en 10€ por la compra de cualquier articulo de Deportes.");
                        eventBean.setPromotion(promo);
                        break;
                    }
                }
            }
        }
        return new KeyValue<String, EventBean>(s, eventBean);

    }
}
