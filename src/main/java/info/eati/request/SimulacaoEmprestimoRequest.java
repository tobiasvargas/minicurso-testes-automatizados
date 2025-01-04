package info.eati.request;

import java.math.BigDecimal;

public record SimulacaoEmprestimoRequest(BigDecimal valor, BigDecimal taxaJurosMensal, int numeroMeses) {
}
