package info.eati.repository;

import info.eati.entity.Parcela;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ParcelaRepository implements PanacheRepositoryBase<Parcela, Long> {
}
