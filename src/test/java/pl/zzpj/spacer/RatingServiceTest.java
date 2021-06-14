package pl.zzpj.spacer;

import com.google.gson.JsonParser;
import org.jglue.fluentjson.JsonBuilderFactory;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.zzpj.spacer.dto.RatingDto;
import pl.zzpj.spacer.dto.CommentDto;
import pl.zzpj.spacer.dto.NewAccountDto;
import pl.zzpj.spacer.dto.PictureDto;
import pl.zzpj.spacer.dto.mapper.RatingMapper;
import pl.zzpj.spacer.dto.mapper.RatingMapperImpl;
import pl.zzpj.spacer.exception.AppBaseException;
import pl.zzpj.spacer.model.Comment;
import pl.zzpj.spacer.model.Rating;
import pl.zzpj.spacer.service.RatingServiceImpl;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@AutoConfigureMockMvc
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RatingServiceTest {
    RatingMapper ratingMapper = new RatingMapperImpl();

    @Autowired
    RatingServiceImpl ratingService;
    @Autowired
    MockMvc mvc;

    String newAccToJson(NewAccountDto nad) {
        return JsonBuilderFactory.buildObject()
                .add("username", nad.getUsername())
                .add("password", nad.getPassword())
                .add("firstName", nad.getFirstName())
                .add("lastName", nad.getLastName())
                .getJson().toString();
    }

    String newLoginJson(String username, String password) {
        return JsonBuilderFactory.buildObject()
                .add("username", username)
                .add("password", password)
                .getJson().toString();
    }

    String newPictureJson(PictureDto pd) {
        return JsonBuilderFactory.buildObject()
                .add("id", pd.getId().toString())
                .add("url", pd.getUrl())
                .add("title", pd.getTitle())
                .getJson().toString();
    }

    static String UserToken;
    static String testPictureId;
    static String testPictureId2;

    @Order(1)
    @Test
    void CreateTestData() throws Exception {
        // create test user account
        NewAccountDto newAccount = NewAccountDto.builder()
                .password("abc123")
                .username("bolek")
                .build();

        String newAccJson = newAccToJson(newAccount);

        mvc.perform(MockMvcRequestBuilders.post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newAccJson)
        ).andExpect(MockMvcResultMatchers.status().isCreated());

        NewAccountDto newAccount2 = NewAccountDto.builder()
                .password("123abc")
                .username("lolek")
                .build();

        String newAccJson2 = newAccToJson(newAccount2);

        mvc.perform(MockMvcRequestBuilders.post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newAccJson2)
        ).andExpect(MockMvcResultMatchers.status().isCreated());

        // user login
        String newLogin = newLoginJson("bolek", "abc123");

        MvcResult res = mvc.perform(MockMvcRequestBuilders.post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newLogin)
        ).andExpect(MockMvcResultMatchers.status().is2xxSuccessful()).andReturn();

        UserToken = res.getResponse().getContentAsString();

        // create/post picture
        PictureDto newPicture = PictureDto.builder()
                .title("Test picture posted")
                .url("https://picsum.photos/404")
                .id(UUID.randomUUID().toString())
                .build();

        String newPictureJson = newPictureJson(newPicture);

        mvc.perform(MockMvcRequestBuilders.post("/picture/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newPictureJson)
        ).andExpect(MockMvcResultMatchers.status().isCreated());

        newPicture = PictureDto.builder()
                .title("Another test picture posted")
                .url("https://picsum.photos/440")
                .id(UUID.randomUUID().toString())
                .build();

        newPictureJson = newPictureJson(newPicture);

        mvc.perform(MockMvcRequestBuilders.post("/picture/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newPictureJson)
        ).andExpect(MockMvcResultMatchers.status().isCreated());

        res = mvc.perform(MockMvcRequestBuilders.get("/pictures")
        ).andExpect(MockMvcResultMatchers.status().is2xxSuccessful()).andReturn();

        String resString = res.getResponse().getContentAsString();
        testPictureId = JsonParser.parseString(resString)
                .getAsJsonArray().get(0)
                .getAsJsonObject().get("id")
                .getAsString();
        testPictureId2 = JsonParser.parseString(resString)
                .getAsJsonArray().get(1)
                .getAsJsonObject().get("id")
                .getAsString();
    }

    @Order(2)
    @Test
    void AddRating() throws AppBaseException {
        RatingDto ratingDto = RatingDto.builder()
                .owner("bolek")
                .pictureId(testPictureId)
                .rating(3)
                .build();

        ratingService.addRating(ratingMapper.ratingDtoToRating(ratingDto));

         ratingDto = RatingDto.builder()
                .owner("bolek")
                .pictureId(testPictureId2)
                .rating(1)
                .build();

        ratingService.addRating(ratingMapper.ratingDtoToRating(ratingDto));

        ratingDto = RatingDto.builder()
                .owner("lolek")
                .pictureId(testPictureId)
                .rating(5)
                .build();

        ratingService.addRating(ratingMapper.ratingDtoToRating(ratingDto));
    }

    @Order(3)
    @Test
    void FindCommentsByUsername() throws Exception {
        List<Rating> ratings = ratingService.getRatingsByUsername("bolek");

        ratings = ratings.stream().filter((Rating rating)
                -> rating.getOwner().equals("bolek")
                || rating.getOwner().equals("lolek")
        ).collect(Collectors.toList());

        Assertions.assertEquals(2, ratings.size());
    }

    @Order(4)
    @Test
    void FindCommentsByPictureId() throws Exception {
        List<Rating> ratings = ratingService.getRatingsByPictureId(testPictureId);

        ratings = ratings.stream().filter((Rating rating)
                -> rating.getOwner().equals("bolek")
                || rating.getOwner().equals("lolek")
        ).collect(Collectors.toList());

        Assertions.assertEquals(2, ratings.size());
    }

    @Order(5)
    @Test
    void EditRating() throws Exception {
        List<Rating> ratings = ratingService.getRatingsByUsername("bolek");

        Rating ratingNew = ratings.get(0);
        ratingNew.setRating(5);
        ratingService.editRating(ratingNew);

        ratings = ratingService.getRatingsByUsername("bolek");
        Rating rate = ratings.get(0);

        if (!rate.getRating().equals(ratingNew.getRating())) {
            throw new Exception();
        }
    }

}
