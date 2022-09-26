package com.coats.wbasubscriber.adapter;

import com.coats.wbasubscriber.model.Brands;
import com.coats.wbasubscriber.repository.BrandsRepository;
import com.google.gson.Gson;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.gcp.pubsub.core.PubSubTemplate;
import org.springframework.cloud.gcp.pubsub.integration.AckMode;
import org.springframework.cloud.gcp.pubsub.integration.inbound.PubSubInboundChannelAdapter;
import org.springframework.cloud.gcp.pubsub.support.BasicAcknowledgeablePubsubMessage;
import org.springframework.cloud.gcp.pubsub.support.GcpPubSubHeaders;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Component
public class BrandsAdapter {

    private static final Log LOGGER = LogFactory.getLog(BrandsAdapter.class);

    @Autowired
    BrandsRepository brandsRepository;

    @Bean
    public PubSubInboundChannelAdapter brandChannelAdapter(
            @Qualifier("brandChannel") MessageChannel inputBrandChannel,
            PubSubTemplate pubSubTemplate) {
        PubSubInboundChannelAdapter adapter =
                new PubSubInboundChannelAdapter(pubSubTemplate, "brands");
        adapter.setOutputChannel(inputBrandChannel);
        adapter.setAckMode(AckMode.MANUAL);
        return adapter;
    }

    @Bean
    public MessageChannel pubsubBrandInputChannel() {
        return new DirectChannel();
    }

    @ServiceActivator(inputChannel = "brandChannel")
    public void messageBrandReceiver(String payload, @Header(GcpPubSubHeaders.ORIGINAL_MESSAGE) BasicAcknowledgeablePubsubMessage message) {
        LOGGER.info("Message arrived! Brand Payload: " + payload);

        JSONObject json = new JSONObject(payload);

        Gson gsonb = new Gson();
        Brands data = gsonb.fromJson(String.valueOf(json.getJSONObject("after")), Brands.class);

        System.out.println(data);

        try{
            brandsRepository.save(data);
        }catch(Exception e){
            e.printStackTrace();
        }
        message.ack();
    }

}
