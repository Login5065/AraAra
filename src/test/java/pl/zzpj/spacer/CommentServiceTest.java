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
import pl.zzpj.spacer.dto.CommentDto;
import pl.zzpj.spacer.dto.NewAccountDto;
import pl.zzpj.spacer.dto.PictureDto;
import pl.zzpj.spacer.dto.mapper.CommentMapper;
import pl.zzpj.spacer.dto.mapper.CommentMapperImpl;
import pl.zzpj.spacer.exception.AppBaseException;
import pl.zzpj.spacer.model.Comment;
import pl.zzpj.spacer.service.CommentServiceImpl;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@AutoConfigureMockMvc
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CommentServiceTest {

    CommentMapper commentMapper = new CommentMapperImpl();

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

    @Autowired
    CommentServiceImpl commentService;

    @Autowired
    MockMvc mvc;

    static String tokenUsero;
    static String testPictureId;
    static String testPictureId2;

    @Order(1)
    @Test
    void CreateTestData() throws Exception {
        // create test user account
        NewAccountDto newAccount = NewAccountDto.builder()
                .password("pasuworudo")
                .username("usero")
                .build();

        String newAccJson = newAccToJson(newAccount);

        mvc.perform(MockMvcRequestBuilders.post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newAccJson)
        ).andExpect(MockMvcResultMatchers.status().isCreated());

        NewAccountDto newAccount2 = NewAccountDto.builder()
                .password("worudopasu")
                .username("henry")
                .build();

        String newAccJson2 = newAccToJson(newAccount2);

        mvc.perform(MockMvcRequestBuilders.post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newAccJson2)
        ).andExpect(MockMvcResultMatchers.status().isCreated());

        // user login
        String newLogin = newLoginJson("usero", "pasuworudo");

        MvcResult res = mvc.perform(MockMvcRequestBuilders.post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newLogin)
        ).andExpect(MockMvcResultMatchers.status().is2xxSuccessful()).andReturn();

        tokenUsero = res.getResponse().getContentAsString();

        // create/post picture
        PictureDto newPicture = PictureDto.builder()
                .title("Test picture posted in service test")
                .url("https://picsum.photos/205")
                .id(UUID.randomUUID().toString())
                .build();

        String newPictureJson = newPictureJson(newPicture);

        mvc.perform(MockMvcRequestBuilders.post("/picture/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newPictureJson)
        ).andExpect(MockMvcResultMatchers.status().isCreated());

        newPicture = PictureDto.builder()
                .title("Another test picture posted in service test")
                .url("https://picsum.photos/305")
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
    void AddComment() throws AppBaseException {
        CommentDto commentDto = CommentDto.builder()
                .owner("usero")
                .pictureId(testPictureId)
                .content("I don't know, seems kinda gae to me")
                .build();

        commentService.addComment(commentMapper.commentDtoToComment(commentDto));

        commentDto = CommentDto.builder()
                .owner("henry")
                .pictureId(testPictureId)
                .content("Yeah, the guy above is kinda right")
                .build();

        commentService.addComment(commentMapper.commentDtoToComment(commentDto));

        commentDto = CommentDto.builder()
                .owner("usero")
                .pictureId(testPictureId2)
                .content("I'm sub")
                .build();

        commentService.addComment(commentMapper.commentDtoToComment(commentDto));
    }

    @Order(3)
    @Test
    void FindCommentByPictureId() throws Exception {
        List<Comment> comments = commentService.getCommentsByPictureId(testPictureId);

        comments = comments.stream().filter((Comment comment)
                -> comment.getOwner().equals("henry")
                || comment.getOwner().equals("usero")
        ).collect(Collectors.toList());

        Assertions.assertEquals(2, comments.size());
    }

    @Order(3)
    @Test
    void FindCommentByUsername() throws Exception {
        List<Comment> comments = commentService.getCommentsByUsername("henry");

        Assertions.assertEquals(1, comments.size());
    }

    @Order(4)
    @Test
    void EditComment() throws Exception {
        List<Comment> comments = commentService.getCommentsByUsername("henry");

        Comment edited = comments.get(0);
        String editedContent = "This comment was edited by comment edit gang.";
        edited.setContent(editedContent);
        commentService.editComment(edited);

        comments = commentService.getCommentsByUsername("henry");
        edited = comments.get(0);

        if (!edited.getContent().equals(editedContent)) {
            throw new Exception();
        }
    }


}
