package com.example.schedule_utc_bot1.telegram;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendAnimation;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@Component
public class TelegramBot extends TelegramLongPollingBot {

  @Value("${app.config.telegram.bot-token}")
  public String botToken;

  @Override
  public String getBotUsername() {
    return "@lptech_bot";
  }

  @Override
  public String getBotToken() {
    return "5275209542:AAHG7NjD-7A8l_rsbsnXFVuvvqfMRwHa75k";
  }

  @Override
  public void onUpdateReceived(Update update) {
    //        String message = update.getMessage().getText();
    //        sendMsg(update.getMessage().getChatId().toString(), message);
    //        if (update.hasMessage()) {
    //            String message = "Xin chào! Bạn cần gì?";
    //            sendMsg(update.getMessage().getChatId().toString(), message);
    //            switch (update.getMessage().getText()){
    //                case "Sinh nhật nhân viên":
    //                    log.info("đến rồi nha!!!!");
    //                    break;
    //                case "Tôi cần hỗ trợ.":
    //                    log.info("vào case 2");
    //                    break;
    //                case "Phản hồi.":
    //                    log.info("Gọi quản lý");
    //                    break;
    //                default:
    //                    log.info("lỗi");
    //                    break;
    //            }
    //        } else if (update.hasCallbackQuery()) {
    //             log.info("chưa có gì");
    //        }
  }

  public synchronized void sendMsg(String chatId, String s) {
    SendMessage sendMessage = new SendMessage();
    sendMessage.enableMarkdown(true);
    sendMessage.setChatId(chatId);
    sendMessage.setText(s);
    //        setButton(sendMessage);
    try {
      execute(sendMessage);
    } catch (TelegramApiException e) {
      log.error(String.valueOf(e));
    }
  }

  public synchronized void sendMsg(String chatId, String s, String parseMode) {
    SendMessage sendMessage = new SendMessage();
    sendMessage.enableMarkdown(true);
    sendMessage.setChatId(chatId);
    sendMessage.setText(s);
    sendMessage.setParseMode(parseMode);
    try {
      execute(sendMessage);
    } catch (TelegramApiException e) {
      log.error(String.valueOf(e));
    }
  }

  public synchronized void sendAnimation(String chatId, InputFile annimation) {
    SendAnimation sendAnimation = new SendAnimation();
    sendAnimation.setChatId(chatId);
    sendAnimation.setAnimation(annimation);
    try {
      execute(sendAnimation);
    } catch (TelegramApiException e) {
      log.error(String.valueOf(e));
    }
  }

  public synchronized void sendPhoto(String chatId, InputFile photo) {
    SendPhoto sendPhoto = new SendPhoto();
    sendPhoto.setChatId(chatId);
    sendPhoto.setPhoto(photo);
    try {
      execute(sendPhoto);
    } catch (TelegramApiException e) {
      log.error(String.valueOf(e));
    }
  }
}
