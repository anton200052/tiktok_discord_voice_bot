package me.vasylkov.discordvoicebot.amazonpolly.configuration;

import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.polly.PollyClient;
import software.amazon.awssdk.services.polly.model.DescribeVoicesRequest;
import software.amazon.awssdk.services.polly.model.DescribeVoicesResponse;
import software.amazon.awssdk.services.polly.model.Voice;

@Configuration
public class PollyConfiguration
{
    private PollyClient pollyClient;

    @Value("${polly_region}")
    private String region;

    @Value("${polly_voice}")
    private String voiceName;

    @Value("${polly_engine}")
    private String engine;

    @Bean
    public PollyClient pollyClient()
    {
        pollyClient = PollyClient.builder().region(Region.of(region)).build();
        return pollyClient;
    }

    @Bean
    public Voice pollyVoice()
    {
        DescribeVoicesRequest describeVoiceRequest = DescribeVoicesRequest.builder().engine(engine).build();
        DescribeVoicesResponse describeVoicesResult = pollyClient.describeVoices(describeVoiceRequest);
        return describeVoicesResult.voices().stream().filter(v -> v.name().equals(voiceName)).findFirst().orElseThrow(() -> new RuntimeException("Voice not found"));
    }

    @PreDestroy
    public void destroy()
    {
        if (pollyClient != null)
        {
            pollyClient.close();
        }
    }
}
