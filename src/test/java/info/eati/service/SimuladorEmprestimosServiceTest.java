package info.eati.service;

import info.eati.entity.Emprestimo;
import info.eati.entity.Parcela;
import info.eati.repository.EmprestimoRepository;
import info.eati.repository.ParcelaRepository;
import info.eati.request.SimulacaoEmprestimoRequest;
import info.eati.response.ParcelaSimuladaResponse;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@QuarkusTest
public class SimuladorEmprestimosServiceTest {
    @Inject
    SimuladorEmprestimosService simuladorEmprestimosService;

    @InjectMocks
    SimuladorEmprestimosService simuladorEmprestimosServiceMock;

    @Mock
    EmprestimoRepository emprestimoRepository;

    @Mock
    ParcelaRepository parcelaRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void simular_emprestimo30Mil24Meses2PorCentoJuros_deveGerar24Parcelas() {
        var request = new SimulacaoEmprestimoRequest(BigDecimal.valueOf(30000), BigDecimal.valueOf(2), 24);

        var parcelas = simuladorEmprestimosService.simular(request);

        assertEquals(24, parcelas.size());
    }

    @Test
    void simular_emprestimo30Mil24Meses2PorCentoJuros_deveGerar24ParcelasEPersistirNoBancoDeDados() {

        var request = new SimulacaoEmprestimoRequest(BigDecimal.valueOf(30000), BigDecimal.valueOf(2), 24);

        var parcelas = simuladorEmprestimosServiceMock.simular(request);

        assertEquals(24, parcelas.size());

        verify(emprestimoRepository, times(1)).persist((Emprestimo) any());
        verify(parcelaRepository, times(24)).persist((Parcela) any());
    }

    @Test
    void simular_emprestimo30Mil24Meses2PorCentoJuros_deveGerar24ParcelasComAmortizacaoIgualA1250() {
        var request = new SimulacaoEmprestimoRequest(BigDecimal.valueOf(30000), BigDecimal.valueOf(2), 24);

        var parcelas = simuladorEmprestimosService.simular(request);

        for (ParcelaSimuladaResponse parcela : parcelas) {
            assertEquals(BigDecimal.valueOf(1250).setScale(2, RoundingMode.UNNECESSARY), parcela.amortizacao());
        }
    }

    @Test
    void simular_emprestimo10Mil5Meses2PorCentoJuros_deveGerar5ParcelasComNumerosSequenciais() {
        var request = new SimulacaoEmprestimoRequest(BigDecimal.valueOf(10000), BigDecimal.valueOf(2), 5);

        var parcelas = simuladorEmprestimosService.simular(request);

        assertEquals(5, parcelas.size());

        var parcela1 = parcelas.get(0);
        var parcela2 = parcelas.get(1);
        var parcela3 = parcelas.get(2);
        var parcela4 = parcelas.get(3);
        var parcela5 = parcelas.get(4);

        assertEquals(1, parcela1.numeroParcela());
        assertEquals(2, parcela2.numeroParcela());
        assertEquals(3, parcela3.numeroParcela());
        assertEquals(4, parcela4.numeroParcela());
        assertEquals(5, parcela5.numeroParcela());
    }

    @Test
    void simular_emprestimo10Mil5Meses2PorCentoJuros_deveGerar5ParcelasComValoresCompativeis() {
        var request = new SimulacaoEmprestimoRequest(BigDecimal.valueOf(10000), BigDecimal.valueOf(2), 5);

        var parcelas = simuladorEmprestimosService.simular(request);

        assertEquals(5, parcelas.size());

        var parcela1 = parcelas.get(0);
        var parcela2 = parcelas.get(1);
        var parcela3 = parcelas.get(2);
        var parcela4 = parcelas.get(3);
        var parcela5 = parcelas.get(4);

        assertEquals(BigDecimal.valueOf(2200).setScale(2, RoundingMode.UNNECESSARY), parcela1.valorParcela());
        assertEquals(BigDecimal.valueOf(2160).setScale(2, RoundingMode.UNNECESSARY), parcela2.valorParcela());
        assertEquals(BigDecimal.valueOf(2120).setScale(2, RoundingMode.UNNECESSARY), parcela3.valorParcela());
        assertEquals(BigDecimal.valueOf(2080).setScale(2, RoundingMode.UNNECESSARY), parcela4.valorParcela());
        assertEquals(BigDecimal.valueOf(2040).setScale(2, RoundingMode.UNNECESSARY), parcela5.valorParcela());
    }
}
