package com.coats.wbasubscriber.adapter;

import com.coats.wbasubscriber.repository.LengthsRepository;
import com.coats.wbasubscriber.repository.MultiSoldToUsersRepository;
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
public class MultiSoldToUsersAdapter {

    private static final Log LOGGER = LogFactory.getLog(MultiSoldToUsersAdapter.class);

    @Autowired
    MultiSoldToUsersRepository multiSoldToUsersRepository;

    @Bean
    public PubSubInboundChannelAdapter MultiSoldToUserChannelAdapter(
            @Qualifier("multiSoldToChannel") MessageChannel inputMultiSoldToUserChannel,
            PubSubTemplate pubSubTemplate) {
        PubSubInboundChannelAdapter adapter =
                new PubSubInboundChannelAdapter(pubSubTemplate, "multisoldtouser");
        adapter.setOutputChannel(inputMultiSoldToUserChannel);
        adapter.setAckMode(AckMode.MANUAL);
        return adapter;
    }

    @Bean
    public MessageChannel pubsubMultiSoldToInputChannel() {
        return new DirectChannel();
    }

    @ServiceActivator(inputChannel = "multiSoldToChannel")
    public void messageMultiSoldToReceiver(String payload, @Header(GcpPubSubHeaders.ORIGINAL_MESSAGE) BasicAcknowledgeablePubsubMessage message) {
        LOGGER.info("Message arrived! MultiSoldTo Payload: " + payload);
    }
}
