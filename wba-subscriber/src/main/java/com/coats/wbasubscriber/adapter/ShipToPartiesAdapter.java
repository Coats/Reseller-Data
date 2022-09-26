package com.coats.wbasubscriber.adapter;

import com.coats.wbasubscriber.repository.ShadesRepository;
import com.coats.wbasubscriber.repository.ShipToPartiesRepository;
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
public class ShipToPartiesAdapter {

    private static final Log LOGGER = LogFactory.getLog(ShipToPartiesAdapter.class);

    @Autowired
    ShipToPartiesRepository shipToPartiesRepository;

    @Bean
    public PubSubInboundChannelAdapter ShipToPartiesChannelAdapter(
            @Qualifier("shipToChannel") MessageChannel inputShipToChannel,
            PubSubTemplate pubSubTemplate) {
        PubSubInboundChannelAdapter adapter =
                new PubSubInboundChannelAdapter(pubSubTemplate, "shiptoparties");
        adapter.setOutputChannel(inputShipToChannel);
        adapter.setAckMode(AckMode.MANUAL);
        return adapter;
    }

    @Bean
    public MessageChannel pubsubShipToInputChannel() {
        return new DirectChannel();
    }

    @ServiceActivator(inputChannel = "shipToChannel")
    public void messageShipToReceiver(String payload, @Header(GcpPubSubHeaders.ORIGINAL_MESSAGE) BasicAcknowledgeablePubsubMessage message) {
        LOGGER.info("Message arrived! ShipTo Payload: " + payload);
    }
}
