package ru.rosbank.javaschool.finalprojectback;

import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.context.WebApplicationContext;
import ru.rosbank.javaschool.finalprojectback.dto.PostSaveRequestDto;
import ru.rosbank.javaschool.finalprojectback.dto.UserSaveRequestDto;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.Matchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;


@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class FinalProjectBackApplicationTests {
    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.webAppContextSetup(context);
    }

    @Test
    void shouldBeAbleCreateUserWithoutAuth() {
        given()
                .contentType(ContentType.JSON)
                .body(new UserSaveRequestDto(0, "Name", "Username", "Password", "email@mail.ru", null))
                .when()
                .post("/api/users")
                .then()
                .statusCode(200)
                .body("id", not(equalTo(0)));
    }

    @Test
    void shouldBeAbleSeePostWithAuth() {
        given()
                .auth().with(httpBasic("masha", "secret"))
                .when()
                .get("/api/posts?lastPost=0&step=5")
                .then()
                .statusCode(200)
                .body("$", not(emptyArray()))
                .body("$", hasSize(5));
    }

    @Test
    void shouldNotSeePostsWithoutAuth() {
        given()
                .when()
                .get("/api/posts?lastPost=0&step=5")
                .then()
                .statusCode(401);
    }

    @Test
    void shouldBeAbleCreatePostWithAuth() {
        given()
                .auth().with(httpBasic("masha", "secret"))
                .contentType(ContentType.JSON)
                .body(new PostSaveRequestDto(0, null, "Last", null))
                .when()
                .post("/api/posts")
                .then()
                .statusCode(200)
                .body("id", not(equalTo(0)));
    }

}

