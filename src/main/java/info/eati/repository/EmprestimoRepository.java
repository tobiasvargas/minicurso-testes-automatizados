package info.eati.repository;

import info.eati.entity.Emprestimo;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class EmprestimoRepository implements PanacheRepositoryBase<Emprestimo, Long> {
}
