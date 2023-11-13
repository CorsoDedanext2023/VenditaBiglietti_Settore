package it.dedagroup.settore.repository;

import it.dedagroup.settore.model.Settore;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SettoreRepository extends JpaRepository<Settore, Long> {
    List<Settore> findAllById(Long id);
    List<Settore> findAllByNome(String nome);
    List<Settore> findAllByPosti(int posti);
}
