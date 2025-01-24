package info.eati.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "emprestimos")
public class Emprestimo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false, updatable = false)
    private Long id;

    @Column(name = "valor", nullable = false)
    private BigDecimal valor;

    @Column(name = "taxa_juros_mensal", nullable = false)
    private BigDecimal taxaJurosMensal;

    @Column(name = "numero_meses", nullable = false)
    private int numeroMeses;

    public Emprestimo() {
    }

    public Emprestimo(BigDecimal valor, BigDecimal taxaJurosMensal, int numeroMeses) {
        this.valor = valor;
        this.taxaJurosMensal = taxaJurosMensal;
        this.numeroMeses = numeroMeses;
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public BigDecimal getTaxaJurosMensal() {
        return taxaJurosMensal;
    }

    public int getNumeroMeses() {
        return numeroMeses;
    }
}
