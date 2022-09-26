package com.coats.wbasubscriber.adapter;

import com.coats.wbasubscriber.repository.UsersRepository;
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
public class UsersSalesOrgsAdapter {

    private static final Log LOGGER = LogFactory.getLog(UsersSalesOrgsAdapter.class);

    @Autowired
    UsersSalesOrgsRepository usersSalesOrgsRepository;

    @Bean
    public PubSubInboundChannelAdapter UserSalesOrgsChannelAdapter(
            @Qualifier("userSalesOrgChannel") MessageChannel inputUserSalesOrgChannel,
            PubSubTemplate pubSubTemplate) {
        PubSubInboundChannelAdapter adapter =
                new PubSubInboundChannelAdapter(pubSubTemplate, "usersalesorg");
        adapter.setOutputChannel(inputUserSalesOrgChannel);
        adapter.setAckMode(AckMode.MANUAL);
        return adapter;
    }

    @Bean
    public MessageChannel pubsubUserSalesOrgInputChannel() {
        return new DirectChannel();
    }

    @ServiceActivator(inputChannel = "userSalesOrgChannel")
    public void messageUserSalesOrgReceiver(String payload, @Header(GcpPubSubHeaders.ORIGINAL_MESSAGE) BasicAcknowledgeablePubsubMessage message) {
        LOGGER.info("Message arrived! User SalesOrg Payload: " + payload);
    }
}
