package pl.zzpj.spacer;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.jglue.fluentjson.JsonBuilderFactory;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.zzpj.spacer.dto.CommentDto;
import pl.zzpj.spacer.dto.NewAccountDto;
import pl.zzpj.spacer.dto.PictureDto;
import pl.zzpj.spacer.exception.AppBaseException;

import java.util.*;
import java.util.stream.Collectors;

@AutoConfigureMockMvc
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CommentIntegrationTest {

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

    String newCommentJson(CommentDto cd) {
        return JsonBuilderFactory.buildObject()
                .add("owner", cd.getOwner())
                .add("pictureId", cd.getPictureId())
                .add("content", cd.getContent())
                .add("date", cd.getDate())
                .getJson().toString();
    }

    CommentDto newCommentFromJson(JsonObject jsonObject) {
        return CommentDto.builder()
                .owner(jsonObject.get("owner").getAsString())
                .pictureId(jsonObject.get("pictureId").getAsString())
                .content(jsonObject.get("content").getAsString())
                .date(jsonObject.get("date").getAsString())
                .build();
    }

    List<CommentDto> newCommentsFromJson(JsonElement jsonElement) {
        ArrayList<CommentDto> list = new ArrayList<>();
        Iterator<JsonElement> jsonIter = jsonElement.getAsJsonArray().iterator();

        jsonIter.forEachRemaining((JsonElement element) -> list.add(newCommentFromJson(element.getAsJsonObject())));

        return list;
    }

    @Autowired
    MockMvc mvc;

    static String tokenUsero;
    static String tokenHenry;
    static String testPictureId;
    static String testPictureId2;

    @Order(1)
    @Test
    void CreateTestData() throws Exception {
        // create test user account
        NewAccountDto newAccount = NewAccountDto.builder()
                .password("pasuworudo")
                .username("useroIntegrated")
                .build();

        String newAccJson = newAccToJson(newAccount);

        mvc.perform(MockMvcRequestBuilders.post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newAccJson)
        ).andExpect(MockMvcResultMatchers.status().isCreated());

        NewAccountDto newAccount2 = NewAccountDto.builder()
                .password("worudopasu")
                .username("henryIntegrated")
                .build();

        String newAccJson2 = newAccToJson(newAccount2);

        mvc.perform(MockMvcRequestBuilders.post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newAccJson2)
        ).andExpect(MockMvcResultMatchers.status().isCreated());

        // user login
        String newLogin = newLoginJson("useroIntegrated", "pasuworudo");

        MvcResult res = mvc.perform(MockMvcRequestBuilders.post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newLogin)
        ).andExpect(MockMvcResultMatchers.status().is2xxSuccessful()).andReturn();

        tokenUsero = res.getResponse().getContentAsString();

        newLogin = newLoginJson("henryIntegrated", "worudopasu");

        res = mvc.perform(MockMvcRequestBuilders.post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newLogin)
        ).andExpect(MockMvcResultMatchers.status().is2xxSuccessful()).andReturn();

        tokenHenry = res.getResponse().getContentAsString();

        // create/post picture
        PictureDto newPicture = PictureDto.builder()
                .title("Test picture posted Integrated")
                .url("https://picsum.photos/400")
                .id(UUID.randomUUID().toString())
                .build();

        String newPictureJson = newPictureJson(newPicture);

        mvc.perform(MockMvcRequestBuilders.post("/picture/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newPictureJson)
        ).andExpect(MockMvcResultMatchers.status().isCreated());

        newPicture = PictureDto.builder()
                .title("Another test picture posted Integrated")
                .url("https://picsum.photos/500")
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

    private void MvcCommentAdd(CommentDto commentDto, String token) throws Exception {
        String commentDtoJson = newCommentJson(commentDto);

        mvc.perform(MockMvcRequestBuilders.post("/comment")
                .contentType(MediaType.APPLICATION_JSON)
                .content(commentDtoJson)
                .header("Authorization", "Bearer " + token)
        ).andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Order(2)
    @Test
    void AddComment() throws Exception {
        CommentDto commentDto = CommentDto.builder()
                .owner("useroIntegrated")
                .pictureId(testPictureId)
                .content("I don't know, seems kinda gae to me")
                .build();

        MvcCommentAdd(commentDto, tokenUsero);

        commentDto = CommentDto.builder()
                .owner("henryIntegrated")
                .pictureId(testPictureId)
                .content("Yeah, the guy above is kinda right")
                .build();

        MvcCommentAdd(commentDto, tokenUsero);

        commentDto = CommentDto.builder()
                .owner("useroIntegrated")
                .pictureId(testPictureId2)
                .content("I'm sub")
                .build();

        MvcCommentAdd(commentDto, tokenHenry);
    }

    private List<CommentDto> MvcGetCommentByPictureId(String pictureId, String token) throws Exception {
        String requestPath = "/comments/picture/" + pictureId;

        MvcResult res = mvc.perform(MockMvcRequestBuilders.get(requestPath)
                .header("Authorization", "Bearer " + token))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        List<CommentDto> commentsDto = newCommentsFromJson(
                JsonParser.parseString(res.getResponse().getContentAsString())
        );

        return commentsDto;
    }

    @Order(3)
    @Test
    void FindCommentByPictureId() throws Exception {
        List<CommentDto> comments = MvcGetCommentByPictureId(testPictureId, tokenUsero);

        comments = comments.stream().filter((CommentDto comment)
                -> comment.getOwner().equals("henryIntegrated")
                || comment.getOwner().equals("useroIntegrated")
        ).collect(Collectors.toList());

        Assertions.assertEquals(2, comments.size());
    }

    private List<CommentDto> MvcGetCommentByUsername(String username, String token) throws Exception {
        String requestPath = "/comments/username/" + username;

        MvcResult res = mvc.perform(MockMvcRequestBuilders.get(requestPath)
                .header("Authorization", "Bearer " + token))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        List<CommentDto> commentsDto = newCommentsFromJson(
                JsonParser.parseString(res.getResponse().getContentAsString())
        );

        return commentsDto;
    }

    @Order(3)
    @Test
    void FindCommentByUsername() throws Exception {
        List<CommentDto> comments = MvcGetCommentByUsername("henryIntegrated", tokenHenry);

        Assertions.assertEquals(1, comments.size());
    }

    @Order(4)
    @Test
    void EditComment() throws Exception {
        List<CommentDto> commentDtos = MvcGetCommentByUsername("henryIntegrated", tokenHenry);

        CommentDto commentDto = commentDtos.get(0);
        String editedContent = "This comment was edited by comment edit gang.";
        commentDto.setContent(editedContent);

        mvc.perform(MockMvcRequestBuilders.put("/comment/edit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newCommentJson(commentDto))
                .header("Authorization", "Bearer " + tokenHenry)
        ).andExpect(MockMvcResultMatchers.status().isOk());

        commentDtos = MvcGetCommentByUsername("henryIntegrated", tokenHenry);
        commentDto = commentDtos.get(0);
        Assertions.assertEquals(editedContent, commentDto.getContent());
    }
}
