package de.ait.gethelp.repositories;

import de.ait.gethelp.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TasksRepository extends JpaRepository<Task, Long> {
    List<Task> findAllByUser_Id(Long userId);

}
