package pl.zzpj.spacer.scheduler;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import pl.zzpj.spacer.exception.PictureException;
import pl.zzpj.spacer.model.Picture;
import pl.zzpj.spacer.service.interfaces.PictureService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Component

public class DailyApodScheduler {

    private static final Logger LOGGER = LoggerFactory.getLogger(DailyApodScheduler.class);
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    @Value("${nasa.api.key}")
    private String NASA_API_KEY;

    private PictureService pictureService;

    @Scheduled(cron = " 0 0 12 * * *")
    public void fetchDailyApod() throws PictureException {
        LOGGER.error("Apod Task :: Execution Time - {}", dateTimeFormatter.format(LocalDateTime.now()));
        String url = "https://api.nasa.gov/planetary/apod?api_key=" + NASA_API_KEY;

        RestTemplate apodTemplate = new RestTemplate();
        Picture result = apodTemplate.getForObject(url, Picture.class);



        pictureService.addPicture(result);
    }
}
