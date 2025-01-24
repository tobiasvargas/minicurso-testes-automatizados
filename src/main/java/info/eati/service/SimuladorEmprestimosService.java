package info.eati.service;

import info.eati.entity.Emprestimo;
import info.eati.entity.Parcela;
import info.eati.repository.EmprestimoRepository;
import info.eati.repository.ParcelaRepository;
import info.eati.request.SimulacaoEmprestimoRequest;
import info.eati.response.ParcelaSimuladaResponse;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class SimuladorEmprestimosService {

    @Inject
    EmprestimoRepository emprestimoRepository;

    @Inject
    ParcelaRepository parcelaRepository;

    @Transactional
    public List<ParcelaSimuladaResponse> simular(SimulacaoEmprestimoRequest request) {
        List<ParcelaSimuladaResponse> parcelas = new ArrayList<>();

        BigDecimal amortizacaoDeCadaParcela = request.valor().divide(BigDecimal.valueOf(request.numeroMeses()), 2, RoundingMode.UNNECESSARY);
        BigDecimal saldoDevedor = request.valor();
        for (int numeroParcela = 1; numeroParcela <= request.numeroMeses(); numeroParcela++) {
            BigDecimal juros = saldoDevedor.multiply(request.taxaJurosMensal().divide(BigDecimal.valueOf(100), 2, RoundingMode.UNNECESSARY));
            saldoDevedor = saldoDevedor.subtract(amortizacaoDeCadaParcela);
            BigDecimal valorParcela = juros.add(amortizacaoDeCadaParcela);
            var parcela = new ParcelaSimuladaResponse(numeroParcela,
                    valorParcela.setScale(2, RoundingMode.UNNECESSARY),
                    amortizacaoDeCadaParcela.setScale(2, RoundingMode.UNNECESSARY),
                    juros.setScale(2, RoundingMode.UNNECESSARY),
                    saldoDevedor.setScale(2, RoundingMode.UNNECESSARY));
            parcelas.add(parcela);
        }

        salvarSimulacao(request, parcelas);
        
        return parcelas;
    }

    private void salvarSimulacao(SimulacaoEmprestimoRequest request, List<ParcelaSimuladaResponse> parcelasSimuladas) {
        var emprestimo = new Emprestimo(request.valor(), request.taxaJurosMensal(), request.numeroMeses());
        emprestimoRepository.persist(emprestimo);

        for (ParcelaSimuladaResponse parcelaSimulada : parcelasSimuladas) {
            var parcela = new Parcela(parcelaSimulada.numeroParcela(), parcelaSimulada.valorParcela(), emprestimo);
            parcelaRepository.persist(parcela);
        }
    }

}
