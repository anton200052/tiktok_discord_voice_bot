package me.vasylkov.discordvoicebot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@SpringBootApplication
public class DiscordvoicebotApplication
{

    public static void main(String[] args) throws IOException
    {
        SpringApplication.run(DiscordvoicebotApplication.class, args);
    }

}
