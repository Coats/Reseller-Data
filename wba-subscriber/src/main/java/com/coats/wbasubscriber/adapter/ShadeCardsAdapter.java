package com.coats.wbasubscriber.adapter;

import com.coats.wbasubscriber.repository.SalesOrgsRepository;
import com.coats.wbasubscriber.repository.ShadeCardsRepository;
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
public class ShadeCardsAdapter {

    private static final Log LOGGER = LogFactory.getLog(ShadeCardsAdapter.class);

    @Autowired
    ShadeCardsRepository shadeCardsRepository;

    @Bean
    public PubSubInboundChannelAdapter ShadeCardChannelAdapter(
            @Qualifier("shadeCardChannel") MessageChannel inputShadeCardChannel,
            PubSubTemplate pubSubTemplate) {
        PubSubInboundChannelAdapter adapter =
                new PubSubInboundChannelAdapter(pubSubTemplate, "shadecard");
        adapter.setOutputChannel(inputShadeCardChannel);
        adapter.setAckMode(AckMode.MANUAL);
        return adapter;
    }

    @Bean
    public MessageChannel pubsubShadeCardInputChannel() {
        return new DirectChannel();
    }

    @ServiceActivator(inputChannel = "shadeCardChannel")
    public void messageShadeCardReceiver(String payload, @Header(GcpPubSubHeaders.ORIGINAL_MESSAGE) BasicAcknowledgeablePubsubMessage message) {
        LOGGER.info("Message arrived! Shade Card Payload: " + payload);
    }
}
