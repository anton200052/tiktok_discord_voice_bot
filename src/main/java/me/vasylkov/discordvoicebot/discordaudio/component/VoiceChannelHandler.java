package me.vasylkov.discordvoicebot.discordaudio.component;

import lombok.RequiredArgsConstructor;
import me.vasylkov.discordvoicebot.jda.ApplicationLifecycleMarkers;
import me.vasylkov.discordvoicebot.jda.service.MessagesSender;
import me.vasylkov.discordvoicebot.tiktok.service.TiktokConnector;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceUpdateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VoiceChannelHandler extends ListenerAdapter
{
    private final MessagesSender messagesSender;
    private final TiktokConnector tiktokConnector;
    private final ApplicationLifecycleMarkers applicationLifecycleMarkers;

    @Override
    public void onGuildVoiceUpdate(GuildVoiceUpdateEvent event)
    {
        if (event.getMember().getUser().equals(event.getJDA().getSelfUser()))
        {
            if (event.getChannelLeft() != null)
            {
                messagesSender.sendMessageToLastChannel("Бот покинул канал, отключаем тикток, если было подключение!");
                if (applicationLifecycleMarkers.isConnectedToTickTok())
                    tiktokConnector.disconnect();
            }
        }
    }
}
