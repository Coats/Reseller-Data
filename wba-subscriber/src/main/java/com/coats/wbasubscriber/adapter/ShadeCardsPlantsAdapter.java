package com.coats.wbasubscriber.adapter;

import com.coats.wbasubscriber.repository.ShadeCardsPlantsRepository;
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
public class ShadeCardsPlantsAdapter {

    private static final Log LOGGER = LogFactory.getLog(ShadeCardsPlantsAdapter.class);

    @Autowired
    ShadeCardsPlantsRepository shadeCardsPlantsRepository;

    @Bean
    public PubSubInboundChannelAdapter ShadeCardPlantChannelAdapter(
            @Qualifier("shadeCardPlantChannel") MessageChannel inputShadeCardPlantChannel,
            PubSubTemplate pubSubTemplate) {
        PubSubInboundChannelAdapter adapter =
                new PubSubInboundChannelAdapter(pubSubTemplate, "shadecardplant");
        adapter.setOutputChannel(inputShadeCardPlantChannel);
        adapter.setAckMode(AckMode.MANUAL);
        return adapter;
    }

    @Bean
    public MessageChannel pubsubShadeCardPlantInputChannel() {
        return new DirectChannel();
    }

    @ServiceActivator(inputChannel = "shadeCardPlantChannel")
    public void messageShadeCardPlantReceiver(String payload, @Header(GcpPubSubHeaders.ORIGINAL_MESSAGE) BasicAcknowledgeablePubsubMessage message) {
        LOGGER.info("Message arrived! Shade Card Plant Payload: " + payload);
    }
}
