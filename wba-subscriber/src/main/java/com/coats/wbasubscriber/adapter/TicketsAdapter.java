package com.coats.wbasubscriber.adapter;

import com.coats.wbasubscriber.repository.ShipToPartiesRepository;
import com.coats.wbasubscriber.repository.TicketsRepository;
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
public class TicketsAdapter {

    private static final Log LOGGER = LogFactory.getLog(TicketsAdapter.class);

    @Autowired
    TicketsRepository ticketsRepository;

    @Bean
    public PubSubInboundChannelAdapter TicketsChannelAdapter(
            @Qualifier("ticketChannel") MessageChannel inputTicketChannel,
            PubSubTemplate pubSubTemplate) {
        PubSubInboundChannelAdapter adapter =
                new PubSubInboundChannelAdapter(pubSubTemplate, "ticket");
        adapter.setOutputChannel(inputTicketChannel);
        adapter.setAckMode(AckMode.MANUAL);
        return adapter;
    }

    @Bean
    public MessageChannel pubsubTicketInputChannel() {
        return new DirectChannel();
    }

    @ServiceActivator(inputChannel = "ticketChannel")
    public void messageTicketReceiver(String payload, @Header(GcpPubSubHeaders.ORIGINAL_MESSAGE) BasicAcknowledgeablePubsubMessage message) {
        LOGGER.info("Message arrived! Ticket Payload: " + payload);
    }
}
