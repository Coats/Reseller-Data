package com.coats.wbasubscriber.adapter;

import com.coats.wbasubscriber.repository.BusinessPrincipalsRepository;
import com.coats.wbasubscriber.repository.CustomerBusinessPrincipalsRepository;
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
public class CustomerBusinessPrincipalsAdapter {
    private static final Log LOGGER = LogFactory.getLog(CustomerBusinessPrincipalsAdapter.class);

    @Autowired
    CustomerBusinessPrincipalsRepository customerBusinessPrincipalsRepository;

    @Bean
    public PubSubInboundChannelAdapter CustBusPrincipalChannelAdapter(
            @Qualifier("custBusinessPrincipalsChannel") MessageChannel inputCustBusPrincipalChannel,
            PubSubTemplate pubSubTemplate) {
        PubSubInboundChannelAdapter adapter =
                new PubSubInboundChannelAdapter(pubSubTemplate, "customerbusinessprincipal");
        adapter.setOutputChannel(inputCustBusPrincipalChannel);
        adapter.setAckMode(AckMode.MANUAL);
        return adapter;
    }

    @Bean
    public MessageChannel pubsubCustBusinessPrincipalInputChannel() {
        return new DirectChannel();
    }

    @ServiceActivator(inputChannel = "custBusinessPrincipalsChannel")
    public void messageCustBusinessPrincipalsReceiver(String payload, @Header(GcpPubSubHeaders.ORIGINAL_MESSAGE) BasicAcknowledgeablePubsubMessage message) {
        LOGGER.info("Message arrived! CustomerBusinessPrincipal Payload: " + payload);
    }
}
