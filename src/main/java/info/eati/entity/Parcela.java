package info.eati.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(schema = "simulador", name = "parcelas")
public class Parcela {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false, unique = true)
    private Long id;

    @Column(name = "numero_parcela", nullable = false)
    private int numeroParcela;

    @Column(name = "valor_parcela", nullable = false)
    private BigDecimal valorParcela;

    @JoinColumn(name = "emprestimo_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Emprestimo emprestimo;

    public Parcela() {
    }

    public Parcela(int numeroParcela, BigDecimal valorParcela, Emprestimo emprestimo) {
        this.numeroParcela = numeroParcela;
        this.valorParcela = valorParcela;
        this.emprestimo = emprestimo;
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getValorParcela() {
        return valorParcela;
    }

    public Emprestimo getEmprestimo() {
        return emprestimo;
    }
}
