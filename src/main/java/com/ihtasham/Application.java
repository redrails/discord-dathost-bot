package com.ihtasham;

import com.ihtasham.Listeners.MessageListener;
import com.ihtasham.model.Constants;
import javax.security.auth.login.LoginException;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;

@Slf4j
public class Application {

  public static void main(String[] args) {
    try {
      JDABuilder.createDefault(System.getenv(Constants.TOKEN))
          .setActivity(Activity.playing(Constants.PLAYING))
          .addEventListeners(new MessageListener())
          .build();

    } catch (LoginException e) {
      log.error("Login to the Discord API failed.");
    } catch (Exception e) {
      log.error("Something went wrong: {}", e.getMessage());
      System.exit(1);
    }
  }
}
