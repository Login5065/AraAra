package pl.zzpj.spacer.scheduler;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.zzpj.spacer.exception.ApodSchedulerException;
import pl.zzpj.spacer.exception.PictureException;
import pl.zzpj.spacer.model.Picture;
import pl.zzpj.spacer.service.interfaces.EmailService;
import pl.zzpj.spacer.service.interfaces.PictureService;
import pl.zzpj.spacer.util.InitialTagsFromTitleConverter;

import java.util.Arrays;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DailyApodSchedulerTest {

    @Mock
    private PictureService pictureService;

    @Mock
    private InitialTagsFromTitleConverter initialTagsFromTitleConverter;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private DailyApodScheduler dailyApodScheduler;


    @Test
    void fetchDailyApodShouldReturnProperPicture() throws ApodSchedulerException, PictureException {
        //given
        dailyApodScheduler.setNASA_API_KEY("HRAR2IBdNgRaN0WDMOlCbRQwAfsPdoLIZ9S70qhy");
        ArgumentCaptor<Picture> pictureCaptor = ArgumentCaptor.forClass(Picture.class);
        doNothing().when(pictureService).addPicture(pictureCaptor.capture());
        when(initialTagsFromTitleConverter.convertTitleToTags(any(String.class)))
                .thenReturn(new HashSet<>(Arrays.asList("test", "test2")));
        //when
        dailyApodScheduler.fetchDailyApod();
        //then
        Picture picture = pictureCaptor.getValue();

        assertNotNull(picture);
        assertFalse(picture.getTitle().isEmpty());
        assertNull(picture.getId());
        assertFalse(picture.getTags().isEmpty());
        assertTrue(picture.getTags().contains("test"));

    }
}