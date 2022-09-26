package com.coats.wbasubscriber.adapter;

import com.coats.wbasubscriber.repository.MultiSoldToUsersRepository;
import com.coats.wbasubscriber.repository.PlantsRepository;
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
public class PlantsAdapter {

    private static final Log LOGGER = LogFactory.getLog(PlantsAdapter.class);

    @Autowired
    PlantsRepository plantsRepository;

    @Bean
    public PubSubInboundChannelAdapter PlantChannelAdapter(
            @Qualifier("plantChannel") MessageChannel inputPlantChannel,
            PubSubTemplate pubSubTemplate) {
        PubSubInboundChannelAdapter adapter =
                new PubSubInboundChannelAdapter(pubSubTemplate, "plant");
        adapter.setOutputChannel(inputPlantChannel);
        adapter.setAckMode(AckMode.MANUAL);
        return adapter;
    }

    @Bean
    public MessageChannel pubsubPlantInputChannel() {
        return new DirectChannel();
    }

    @ServiceActivator(inputChannel = "plantChannel")
    public void messagePlantReceiver(String payload, @Header(GcpPubSubHeaders.ORIGINAL_MESSAGE) BasicAcknowledgeablePubsubMessage message) {
        LOGGER.info("Message arrived! Plant Payload: " + payload);
    }
}
