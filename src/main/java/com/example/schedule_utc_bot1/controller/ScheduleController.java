package com.example.schedule_utc_bot1.controller;

import com.example.schedule_utc_bot1.job.NotifySchedule;
import com.example.schedule_utc_bot1.model.Lesson.LessonDTO;
import com.example.schedule_utc_bot1.model.Schedule.ScheduleDTO;
import com.example.schedule_utc_bot1.services.AccountService;
import com.example.schedule_utc_bot1.services.LessonService;
import com.example.schedule_utc_bot1.services.ScheduleService;
import com.example.schedule_utc_bot1.services.StudentService;
import com.example.schedule_utc_bot1.telegram.TelegramBot;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/schedule")
@RestController
public class ScheduleController {

  final AccountService accountService;
  final ScheduleService scheduleService;
  final LessonService lessonService;
  final StudentService studentService;

  @GetMapping("/scanAndNotify")
  public ResponseEntity<Object> scanAndNotify() {
    notifySchedule();
    return new ResponseEntity<>( HttpStatus.OK);
  }

  public void notifySchedule() {
    log.info(":: Start notify Schedule");
    // Lấy thời gian hiện tại theo zone
    // tránh chạy server tại zone khác VietNam
    ZoneId zoneHCM = ZoneId.of("Asia/Ho_Chi_Minh");
    LocalDateTime today = LocalDateTime.now();
    ZonedDateTime hcmDateTime = ZonedDateTime.of(today, zoneHCM);
    Instant instant = hcmDateTime.toInstant();
    Date dateInVietNam = Date.from(instant);
    String stringDateFormat = "dd/MM/yyyy";
    TelegramBot bot = new TelegramBot();
//    bot.sendMsg(String.valueOf(1692652674),"Server is scanning schedules......" );
    boolean scheduleStatus = true;
    log.info(
            ":: notify Schedule in date: " + convertDateToString("dd-MM-yyyy HH:mm:ss", dateInVietNam));
    List<ScheduleDTO> scheduleDTOS =
            scheduleService.findByDateAndActive(dateInVietNam, scheduleStatus);
    // kiểm tra số lượng schedule trong ngày
    if (CollectionUtils.isEmpty(scheduleDTOS)) return;
    String dateTitle =
            getDayOfWeek(getDayNumberOld(dateInVietNam))
                    + ", ngày "
                    + convertDateToString(stringDateFormat, dateInVietNam)
                    + " :";
    Long chatID = null;
    String pareMode = "html";

    int n = scheduleDTOS.size();
    for (int k = 0; k < n; k++) {

      String scheduleString = "";
      List<LessonDTO> lessonDTOS = lessonService.findAllBySchduleID(scheduleDTOS.get(k).getId());
      for (int i = 0; i < lessonDTOS.size(); i++) {
        scheduleString +=
                String.format(
                        "\nTiết %s môn %s tại %s.",
                        lessonDTOS.get(i).getLessonNum(),
                        lessonDTOS.get(i).getSubjectName(),
                        lessonDTOS.get(i).getAddress());
      }
      chatID = accountService.findAccountCodeByStudentCode(scheduleDTOS.get(k).getStudentCode());
      if (chatID == null) {
        String studentName =
                studentService.findByCode(scheduleDTOS.get(k).getStudentCode()).getName();
        log.info(":: [ERROR] notify Schedule error send to Student Name: " + studentName);
        continue;
      }

      String textMsg = "<code><b>" + dateTitle + scheduleString + "</b></code>";
      bot.sendMsg(chatID.toString(), textMsg, pareMode);
      scheduleService.updateStatusById(scheduleDTOS.get(k).getId(), false);
      log.info("::notifySchedule > send Message to chatID: " + chatID);
    }
  }


  public String convertDateToString(String stringFormat, Date date) {
    Format formatter = new SimpleDateFormat(stringFormat);
    return formatter.format(date);
  }

  public int getDayNumberOld(Date date) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    return cal.get(Calendar.DAY_OF_WEEK);
  }

  public String getDayOfWeek(int numberOfWeek) {
    switch (numberOfWeek) {
      case 2:
        return "Thứ Hai";
      case 3:
        return "Thứ Ba";
      case 4:
        return "Thứ Tư";
      case 5:
        return "Thứ Năm";
      case 6:
        return "Thứ Sáu";
      case 7:
        return "Thứ Bảy";
      case 8:
        return "Chủ Nhật";
      default:
        return "";
    }
  }
}
