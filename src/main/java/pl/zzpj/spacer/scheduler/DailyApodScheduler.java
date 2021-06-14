package pl.zzpj.spacer.scheduler;


import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import pl.zzpj.spacer.exception.AccountException;
import pl.zzpj.spacer.exception.ApodSchedulerException;
import pl.zzpj.spacer.exception.PictureException;
import pl.zzpj.spacer.model.Picture;
import pl.zzpj.spacer.service.interfaces.EmailService;
import pl.zzpj.spacer.service.interfaces.PictureService;
import pl.zzpj.spacer.util.InitialTagsFromTitleConverter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;


@Component
public class DailyApodScheduler {

    private static final Logger LOGGER = LoggerFactory.getLogger(DailyApodScheduler.class);
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    @Value("${nasa.api.key}")
    @Setter
    private String NASA_API_KEY;

    private final PictureService pictureService;

    private final InitialTagsFromTitleConverter initialTagsFromTitleConverter;

    private final EmailService emailService;

    @Autowired
    public DailyApodScheduler( PictureService pictureService, InitialTagsFromTitleConverter initialTagsFromTitleConverter, EmailService emailService) {
        this.pictureService = pictureService;
        this.initialTagsFromTitleConverter = initialTagsFromTitleConverter;
        this.emailService = emailService;
    }

    @Scheduled(cron = "0 33 16 * * ?")
    public void fetchDailyApod() throws PictureException, ApodSchedulerException {
        LOGGER.error("Apod Task :: Execution Time - {}", dateTimeFormatter.format(LocalDateTime.now()));
        String url = "https://api.nasa.gov/planetary/apod?api_key=" + NASA_API_KEY;

        RestTemplate apodTemplate = new RestTemplate();
        Picture result = apodTemplate.getForObject(url, Picture.class);
        if (result != null) {
            result.getTags().addAll(initialTagsFromTitleConverter.convertTitleToTags(result.getTitle()));
            pictureService.addPicture(result);
        } else {
            throw ApodSchedulerException.operationResultIsNull();
        }
    }

    @Scheduled(cron = "0 0 12 * * *")
    public void dailyNotifications() throws AccountException {
        LOGGER.error("Notifications Task :: Execution Time - {}", dateTimeFormatter.format(LocalDateTime.now()));
        String subject = "Most popular tags on Spacer!";

        List<Picture> pictureList = pictureService.getAll();
        Map<String, Integer> pictureTagMap= new HashMap<>();
        for(Picture picture : pictureList) {
            for(String tag : picture.getTags()) {
                Integer n = pictureTagMap.get(tag);
                n = (n == null) ? 1 : ++n;
                pictureTagMap.put(tag, n);
            }
        }

      Map<String, Integer> sortedTagMap = pictureTagMap.entrySet()
              .stream()
              .sorted(Map.Entry.comparingByValue())
              .collect(Collectors.toMap(
                  Map.Entry::getKey,
                  Map.Entry::getValue,
                  (oldValue, newValue) -> oldValue, LinkedHashMap::new));

        int size = 0;
        StringBuilder textMessage = new StringBuilder();
        textMessage.append("Check out our most popular tags today \n");
        for (Map.Entry<String, Integer> pair : sortedTagMap.entrySet()) {
            textMessage.append(pair.getKey()).append(" ");
            if(++size > 3)
                break;
        }

        emailService.sendMessageToAllAccounts(subject, textMessage.toString());
    }
}
