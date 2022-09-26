package com.coats.wbasubscriber.adapter;

import com.coats.wbasubscriber.repository.ShadeCardsPlantsBrandsRepository;
import com.coats.wbasubscriber.repository.ShadesRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
public class ShadesAdapter {

    private static final Log LOGGER = LogFactory.getLog(ShadesAdapter.class);

    @Autowired
    ShadesRepository shadesRepository;

    @Bean
    public PubSubInboundChannelAdapter ShadeChannelAdapter(
            @Qualifier("shadeChannel") MessageChannel inputShadeChannel,
            PubSubTemplate pubSubTemplate) {
        PubSubInboundChannelAdapter adapter =
                new PubSubInboundChannelAdapter(pubSubTemplate, "shade");
        adapter.setOutputChannel(inputShadeChannel);
        adapter.setAckMode(AckMode.MANUAL);
        return adapter;
    }

    @Bean
    public MessageChannel pubsubShadeInputChannel() {
        return new DirectChannel();
    }

    @ServiceActivator(inputChannel = "shadeChannel")
    public void messageShadeReceiver(String payload, @Header(GcpPubSubHeaders.ORIGINAL_MESSAGE) BasicAcknowledgeablePubsubMessage message) {
        LOGGER.info("Message arrived! Shade Payload: " + payload);
    }
}
