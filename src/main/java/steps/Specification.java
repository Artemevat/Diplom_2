package steps;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.logging.Log;

import static constants.BasicDate.URL;

public class Specification {
    protected RequestSpecification getSpec() {
        return new RequestSpecBuilder()
                .setRelaxedHTTPSValidation()
                .setContentType(ContentType.JSON)
                .setBaseUri(URL)
                .log(LogDetail.ALL)
                .build();
    }
}
