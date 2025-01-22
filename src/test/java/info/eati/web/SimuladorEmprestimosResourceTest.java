package info.eati.web;

import info.eati.request.SimulacaoEmprestimoRequest;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class SimuladorEmprestimosResourceTest {
    @Test
    void simular_emprestimo30Mil24Meses2PorCentoJuros_deveRetornarOk() {
        var request = new SimulacaoEmprestimoRequest(BigDecimal.valueOf(30000), BigDecimal.TWO, 24);
        given()
            .when()
            .contentType("application/json")
            .body(request)
            .post("/api/simulador-emprestimos")
                .then()
                .statusCode(200);
    }
}
