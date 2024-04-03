package me.vasylkov.discordvoicebot.amazonpolly.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.services.polly.PollyClient;
import software.amazon.awssdk.services.polly.model.OutputFormat;
import software.amazon.awssdk.services.polly.model.SynthesizeSpeechRequest;
import software.amazon.awssdk.services.polly.model.SynthesizeSpeechResponse;
import software.amazon.awssdk.services.polly.model.Voice;

import java.io.InputStream;

@Service
@RequiredArgsConstructor
public class PollySynthesizer
{
    private final PollyClient polly;
    private final Voice voice;

    public InputStream synthesize(String text)
    {
        SynthesizeSpeechRequest synthReq = SynthesizeSpeechRequest.builder().text(text).voiceId(voice.id()).outputFormat(OutputFormat.MP3).build();
        ResponseInputStream<SynthesizeSpeechResponse> synthRes = polly.synthesizeSpeech(synthReq);
        return synthRes;
    }
}
