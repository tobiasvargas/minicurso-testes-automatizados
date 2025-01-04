package info.eati.response;

import java.math.BigDecimal;

public record ParcelaSimuladaResponse(int numeroParcela, BigDecimal valorParcela, BigDecimal amortizacao, BigDecimal juros, BigDecimal saldoDevedor) {
}
