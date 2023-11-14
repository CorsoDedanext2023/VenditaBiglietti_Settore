package it.dedagroup.settore.repository;

import it.dedagroup.settore.model.Settore;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SettoreRepository extends JpaRepository<Settore, Long> {
    List<Settore> findAllById(Long id);
    Optional<Settore> findByIdAndIsCancellatoFalse(long id);
    List<Settore> findAllByNomeAndIsCancellatoFalse(String nome);
    List<Settore> findAllByPosti(int posti);

}
