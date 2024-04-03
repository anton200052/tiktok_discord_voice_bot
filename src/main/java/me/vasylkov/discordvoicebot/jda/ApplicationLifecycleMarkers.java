package me.vasylkov.discordvoicebot.jda;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Getter
@Setter
public class ApplicationLifecycleMarkers
{
    private boolean connectedToVoice;
    private boolean connectedToTickTok;
}
