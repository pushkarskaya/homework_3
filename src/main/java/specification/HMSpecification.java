package specification;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static common.Env.BASE_URL;

// HW - HomeWork RequestSpecification.
public class HMSpecification {
    // Спецификация у нас на все тесты одна. Логично будет ее вынести в отдельный файл-класс.
    // SOLID - S.
    // Так же, лучше не создавать переменные, которые не будет переиспользоваться и не захламлять память.
    public static RequestSpecification getRequestSpecification() {
        return new RequestSpecBuilder().setBaseUri(BASE_URL)
                .setContentType(ContentType.JSON)
                .build();
    }

    public static ResponseSpecification getResponseSpecification(int statusCode) {
        return new ResponseSpecBuilder()
                .expectStatusCode(statusCode)
                .build();
    }
}
