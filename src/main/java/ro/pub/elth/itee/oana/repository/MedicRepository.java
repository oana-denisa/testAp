package ro.pub.elth.itee.oana.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import ro.pub.elth.itee.oana.domain.Medic;

/**
 * Spring Data SQL repository for the Medic entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MedicRepository extends JpaRepository<Medic, Long> {}
