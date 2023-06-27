package de.ait.gethelp.repositories;

import de.ait.gethelp.models.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardsRepository extends JpaRepository<Card, Long> {

    List<Card> findAllByUser_Id(Long userId);

}
