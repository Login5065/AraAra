package pl.zzpj.spacer.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.zzpj.spacer.exception.PictureException;
import pl.zzpj.spacer.model.Picture;
import pl.zzpj.spacer.repositories.PictureRepository;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PictureServiceImplTest {

    @Mock
    private PictureRepository pictureRepository;

    @Captor
    private ArgumentCaptor<Picture> pictureArgumentCaptor;

    @Captor
    private ArgumentCaptor<String> idArgumentCaptor;

    @InjectMocks
    private PictureServiceImpl pictureService;

    @Test
    void addPictureWithNoDuplicatesInDatabase() {
        //given
        Picture picture = new Picture("TestID", "www.testurl.it",
                "TestTitle",
                new HashSet<>(Collections.singletonList("test")),
                new Date());
        //when
        assertDoesNotThrow(() -> pictureService.addPicture(picture));
        verify(pictureRepository).save(pictureArgumentCaptor.capture());
        //then
        then(pictureRepository).should().findByTitle(pictureArgumentCaptor.getValue().getTitle());
        then(pictureRepository).should().save(pictureArgumentCaptor.getValue());
        then(pictureRepository).shouldHaveNoMoreInteractions();
        assertEquals(picture, pictureArgumentCaptor.getValue());
    }

    @Test
    void addPictureWithDuplicatesInDatabase() {
        //given
        Picture picture = new Picture("TestID", "www.testurl.it",
                "TestTitle",
                new HashSet<>(Collections.singletonList("test")),
                new Date());
        given(pictureRepository.findByTitle(any(String.class))).willReturn(java.util.Optional.of(picture));
        //when
        assertThrows(PictureException.class, () -> pictureService.addPicture(picture));
        //then
        then(pictureRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    void getPictureWithProperId() {
        //given
        Picture picture = new Picture("TestID", "www.testurl.it",
                "TestTitle",
                new HashSet<>(Collections.singletonList("test")),
                new Date());
        given(pictureRepository.findById(any(String.class))).willReturn(java.util.Optional.of(picture));
        //when
        assertDoesNotThrow(() -> pictureService.getPicture(picture.getId()));
        verify(pictureRepository).findById(idArgumentCaptor.capture());
        //then
        then(pictureRepository).should().findById(idArgumentCaptor.getValue());
        then(pictureRepository).shouldHaveNoMoreInteractions();
        assertEquals(picture.getId(), idArgumentCaptor.getValue());
    }

    @Test
    void getPictureWithBadId() {
        //given
        Picture picture = new Picture("TestID", "www.testurl.it",
                "TestTitle",
                new HashSet<>(Collections.singletonList("test")),
                new Date());
        given(pictureRepository.findById(any(String.class))).willReturn(Optional.empty());
        //when
        assertThrows(PictureException.class, () -> pictureService.getPicture(picture.getId()));
        //then
        then(pictureRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    void getAll() {
        //given
        Picture picture = new Picture("TestID", "www.testurl.it",
                "TestTitle",
                new HashSet<>(Collections.singletonList("test")),
                new Date());
        given(pictureRepository.findAll()).willReturn(List.of(picture));
        //when
        assertDoesNotThrow(() -> pictureService.getAll());
        //then
        then(pictureRepository).should().findAll();
        then(pictureRepository).shouldHaveNoMoreInteractions();

    }

    @Test
    void getAllBetweenDate() {
        //given
        Date date1 = new Date();
        Date date2 = new Date();

        Picture picture = new Picture("TestID", "www.testurl.it",
                "TestTitle",
                new HashSet<>(Collections.singletonList("test")),
                new Date());
        given(pictureRepository.findAllByCreationDateBetween(any(Date.class), any(Date.class)))
                .willReturn(List.of(picture));
        //when
        assertDoesNotThrow(() -> pictureService.getAllBetweenDate(date1, date2));
        //then
        then(pictureRepository).should().findAllByCreationDateBetween(date1, date2);
        then(pictureRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    void getAllByTag() {
        //given
        String tag = "test";

        Picture picture = new Picture("TestID", "www.testurl.it",
                "TestTitle",
                new HashSet<>(Collections.singletonList("test")),
                new Date());
        given(pictureRepository.findAllByTagsContaining(any(String.class)))
                .willReturn(List.of(picture));
        //when
        assertDoesNotThrow(() -> pictureService.getAllByTag(tag));
        //then
        then(pictureRepository).should().findAllByTagsContaining(tag);
        then(pictureRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    void addPictureTags() {
        //given
        String tag = "testTagToAdd";

        Picture picture = new Picture("TestID", "www.testurl.it",
                "TestTitle",
                new HashSet<>(Collections.singletonList("test")),
                new Date());
        given(pictureRepository.findById(any(String.class)))
                .willReturn(Optional.of(picture));
        //when
        assertDoesNotThrow(() -> pictureService.addPictureTags(picture.getId(), Collections.singleton(tag)));
        //then
        then(pictureRepository).should().findById(picture.getId());
        then(pictureRepository).should().save(picture);
        then(pictureRepository).shouldHaveNoMoreInteractions();


    }

    @Test
    void deletePictureTags() {
        //given
        String tag = "test";

        Picture picture = new Picture("TestID", "www.testurl.it",
                "TestTitle",
                new HashSet<>(Collections.singletonList("test")),
                new Date());
        given(pictureRepository.findById(any(String.class)))
                .willReturn(Optional.of(picture));
        //when
        assertDoesNotThrow(() -> pictureService.addPictureTags(picture.getId(), Collections.singleton(tag)));
        //then
        then(pictureRepository).should().findById(picture.getId());
        then(pictureRepository).should().save(picture);
        then(pictureRepository).shouldHaveNoMoreInteractions();
    }
}