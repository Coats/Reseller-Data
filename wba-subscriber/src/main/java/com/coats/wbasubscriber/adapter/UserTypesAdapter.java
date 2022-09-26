package com.coats.wbasubscriber.adapter;

import com.coats.wbasubscriber.repository.UserTypesRepository;
import com.coats.wbasubscriber.repository.UsersSalesOrgsRepository;
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
public class UserTypesAdapter {
    private static final Log LOGGER = LogFactory.getLog(UserTypesAdapter.class);

    @Autowired
    UserTypesRepository userTypesRepository;

    @Bean
    public PubSubInboundChannelAdapter UserTypesChannelAdapter(
            @Qualifier("userTypeChannel") MessageChannel inputUserTypeChannel,
            PubSubTemplate pubSubTemplate) {
        PubSubInboundChannelAdapter adapter =
                new PubSubInboundChannelAdapter(pubSubTemplate, "usertype");
        adapter.setOutputChannel(inputUserTypeChannel);
        adapter.setAckMode(AckMode.MANUAL);
        return adapter;
    }

    @Bean
    public MessageChannel pubsubUserTypeInputChannel() {
        return new DirectChannel();
    }

    @ServiceActivator(inputChannel = "userTypeChannel")
    public void messageUserTypeReceiver(String payload, @Header(GcpPubSubHeaders.ORIGINAL_MESSAGE) BasicAcknowledgeablePubsubMessage message) {
        LOGGER.info("Message arrived! User Type Payload: " + payload);
    }
}
