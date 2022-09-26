package com.coats.wbasubscriber.adapter;

import com.coats.wbasubscriber.repository.TimeZonesRepository;
import com.coats.wbasubscriber.repository.UserCategoriesRepository;
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
public class UserCategoriesAdapter {

    private static final Log LOGGER = LogFactory.getLog(UserCategoriesAdapter.class);

    @Autowired
    UserCategoriesRepository userCategoriesRepository;

    @Bean
    public PubSubInboundChannelAdapter UserCategoriesChannelAdapter(
            @Qualifier("userCategoriesChannel") MessageChannel inputUserCategoryChannel,
            PubSubTemplate pubSubTemplate) {
        PubSubInboundChannelAdapter adapter =
                new PubSubInboundChannelAdapter(pubSubTemplate, "usercategory");
        adapter.setOutputChannel(inputUserCategoryChannel);
        adapter.setAckMode(AckMode.MANUAL);
        return adapter;
    }

    @Bean
    public MessageChannel pubsubUserCategoryInputChannel() {
        return new DirectChannel();
    }

    @ServiceActivator(inputChannel = "userCategoriesChannel")
    public void messageUserCategoriesReceiver(String payload, @Header(GcpPubSubHeaders.ORIGINAL_MESSAGE) BasicAcknowledgeablePubsubMessage message) {
        LOGGER.info("Message arrived! userCategories Payload: " + payload);
    }
}
