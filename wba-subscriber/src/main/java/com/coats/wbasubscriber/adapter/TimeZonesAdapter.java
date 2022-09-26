package com.coats.wbasubscriber.adapter;

import com.coats.wbasubscriber.repository.TimeFormatsRepository;
import com.coats.wbasubscriber.repository.TimeZonesRepository;
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
public class TimeZonesAdapter {

    private static final Log LOGGER = LogFactory.getLog(TimeZonesAdapter.class);

    @Autowired
    TimeZonesRepository timeZonesRepository;

    @Bean
    public PubSubInboundChannelAdapter TimeZoneChannelAdapter(
            @Qualifier("timeZoneChannel") MessageChannel inputTimeZoneChannel,
            PubSubTemplate pubSubTemplate) {
        PubSubInboundChannelAdapter adapter =
                new PubSubInboundChannelAdapter(pubSubTemplate, "timezone");
        adapter.setOutputChannel(inputTimeZoneChannel);
        adapter.setAckMode(AckMode.MANUAL);
        return adapter;
    }

    @Bean
    public MessageChannel pubsubTimeZoneInputChannel() {
        return new DirectChannel();
    }

    @ServiceActivator(inputChannel = "timeZoneChannel")
    public void messageTimeZoneReceiver(String payload, @Header(GcpPubSubHeaders.ORIGINAL_MESSAGE) BasicAcknowledgeablePubsubMessage message) {
        LOGGER.info("Message arrived! TimeZone Payload: " + payload);
    }
}
