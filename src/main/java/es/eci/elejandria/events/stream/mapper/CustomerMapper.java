package es.eci.elejandria.events.stream.mapper;

import es.eci.elejandria.event.sender.beans.EventBean;
import es.eci.elejandria.events.stream.beans.ResponseBean;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.KeyValueMapper;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class CustomerMapper implements KeyValueMapper<String, EventBean, KeyValue<String, EventBean>> {

    @Value("${customer.service-url}")
    private String url;

    private static Logger log = LoggerFactory.getLogger(CustomerMapper.class);

    @Override
    public KeyValue<String, EventBean> apply(String key, EventBean eventBean) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseBean response
                = restTemplate.getForObject(url + "/" + eventBean.getCustomer().getCustomerId(), ResponseBean.class);
        if (response.getIsallowedToUseData()) {
            //allowed to use data
            eventBean.setCustomerAllowed(true);
            return new KeyValue<String, EventBean>(key, eventBean);
        } else {
            //not allowed
            eventBean.setCustomerAllowed(false);
            eventBean.getCustomer().setCustomerId(0);
            eventBean.getCustomer().setName("XXXXX");
            eventBean.getCustomer().setPhoneNumber("000000000");
            eventBean.getCustomer().setAddress("NONE");
            return new KeyValue<String, EventBean>(key, eventBean);
        }
    }
}
