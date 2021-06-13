package pl.zzpj.spacer.scheduler;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import pl.zzpj.spacer.exception.ApodSchedulerException;
import pl.zzpj.spacer.exception.PictureException;
import pl.zzpj.spacer.model.Picture;
import pl.zzpj.spacer.service.interfaces.PictureService;
import pl.zzpj.spacer.util.InitialTagsFromTitleConverter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Component
public class DailyApodScheduler {

    private static final Logger LOGGER = LoggerFactory.getLogger(DailyApodScheduler.class);
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    @Value("${nasa.api.key}")
    private String NASA_API_KEY;

    private final PictureService pictureService;

    private final InitialTagsFromTitleConverter initialTagsFromTitleConverter;

    @Autowired
    public DailyApodScheduler(PictureService pictureService, InitialTagsFromTitleConverter initialTagsFromTitleConverter) {
        this.pictureService = pictureService;
        this.initialTagsFromTitleConverter = initialTagsFromTitleConverter;
    }

    @Scheduled(cron = "0 0 12 * * *")
    public void fetchDailyApod() throws PictureException, ApodSchedulerException {
        LOGGER.error("Apod Task :: Execution Time - {}", dateTimeFormatter.format(LocalDateTime.now()));
        String url = "https://api.nasa.gov/planetary/apod?api_key=" + NASA_API_KEY;

        RestTemplate apodTemplate = new RestTemplate();
        Picture result = apodTemplate.getForObject(url, Picture.class);
        System.out.println(result);
        if (result != null) {
            result.getTags().addAll(initialTagsFromTitleConverter.convertTitleToTags(result.getTitle()));
            pictureService.addPicture(result);
        } else {
            throw ApodSchedulerException.operationResultIsNull();
        }
    }
}
