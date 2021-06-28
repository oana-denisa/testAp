package ro.pub.elth.itee.oana.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import ro.pub.elth.itee.oana.domain.Consultatie;

/**
 * Spring Data SQL repository for the Consultatie entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConsultatieRepository extends JpaRepository<Consultatie, Long> {}
