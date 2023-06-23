package de.ait.gethelp.repositories;

import de.ait.gethelp.models.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubCategoriesRepository extends JpaRepository<SubCategory, Long> {

}
