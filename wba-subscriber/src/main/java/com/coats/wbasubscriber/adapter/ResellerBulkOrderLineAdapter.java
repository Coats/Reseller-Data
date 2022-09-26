package com.coats.wbasubscriber.adapter;

import com.coats.wbasubscriber.model.ResellerBulkOrderLine;
import com.coats.wbasubscriber.repository.ResellerBulkOrderLineRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONObject;
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

import java.time.LocalDate;
@Component
public class ResellerBulkOrderLineAdapter {

    private static final Log LOGGER = LogFactory.getLog(ResellerBulkOrderLineAdapter.class);

    @Autowired
    ResellerBulkOrderLineRepository resellerBulkOrderLineRepository;

    @Bean
    public PubSubInboundChannelAdapter bulkOrderLineChannelAdapter(
            @Qualifier("bulkOrderLineChannel") MessageChannel inputBulkOrderLineChannel,
            PubSubTemplate pubSubTemplate) {
        PubSubInboundChannelAdapter adapter =
                new PubSubInboundChannelAdapter(pubSubTemplate, "ax-bulkorderline");
        adapter.setOutputChannel(inputBulkOrderLineChannel);
        adapter.setAckMode(AckMode.MANUAL);
        return adapter;
    }

    @Bean
    public MessageChannel pubsubBulkOrderLineInputChannel() {
        return new DirectChannel();
    }

    @ServiceActivator(inputChannel = "bulkOrderLineChannel")
    public void messageBulkOrderLineReceiver(String payload, @Header(GcpPubSubHeaders.ORIGINAL_MESSAGE) BasicAcknowledgeablePubsubMessage message) {
        LOGGER.info("Message arrived! BulkOrderLine Payload: " + payload);

        JSONObject json = new JSONObject(payload);

        LocalDate d = LocalDate.ofEpochDay(Long.parseLong(json.getJSONObject("after").get("required_date").toString()));
        json.getJSONObject("after").put("required_date", d);

        Gson gsonb = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        ResellerBulkOrderLine data = gsonb.fromJson(String.valueOf(json.getJSONObject("after")), ResellerBulkOrderLine.class);

        resellerBulkOrderLineRepository.save(data);

        message.ack();
    }

}
