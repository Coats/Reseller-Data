package com.coats.resellersubscriber.adapter;

import com.coats.resellersubscriber.model.ResellerBulkOrder;
import com.coats.resellersubscriber.repository.ResellerBulkOrderRepository;
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

import java.text.SimpleDateFormat;
import java.util.TimeZone;

@Component
public class ResellerBulkOrderAdapter {

    private static final Log LOGGER = LogFactory.getLog(ResellerBulkOrderAdapter.class);

    @Autowired
    ResellerBulkOrderRepository resellerBulkOrderRepository;

    @Bean
    public PubSubInboundChannelAdapter bulkOrderChannelAdapter(
            @Qualifier("bulkOrderChannel") MessageChannel inputBulkOrderChannel,
            PubSubTemplate pubSubTemplate) {
        PubSubInboundChannelAdapter adapter =
                new PubSubInboundChannelAdapter(pubSubTemplate, "bulkorder");
        adapter.setOutputChannel(inputBulkOrderChannel);
        adapter.setAckMode(AckMode.MANUAL);
        return adapter;
    }

    @Bean
    public MessageChannel pubsubBulkOrderInputChannel() {
        return new DirectChannel();
    }

    @ServiceActivator(inputChannel = "bulkOrderChannel")
    public void messageBulkOrderReceiver(String payload, @Header(GcpPubSubHeaders.ORIGINAL_MESSAGE) BasicAcknowledgeablePubsubMessage message) {
        LOGGER.info("Message arrived! BulkOrder Payload: " + payload);

        JSONObject json = new JSONObject(payload);

        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

        json.getJSONObject("after").put("created", sdf.format(json.getJSONObject("after").get("created")));
        json.getJSONObject("after").put("updated", sdf.format(json.getJSONObject("after").get("updated")));

        Gson gsonb = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss.SSS").create();
        ResellerBulkOrder data = gsonb.fromJson(String.valueOf(json.getJSONObject("after")), ResellerBulkOrder.class);

        resellerBulkOrderRepository.save(data);
        message.ack();
    }
}
