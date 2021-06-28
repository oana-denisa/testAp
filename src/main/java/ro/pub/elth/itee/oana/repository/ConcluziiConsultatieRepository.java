package ro.pub.elth.itee.oana.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import ro.pub.elth.itee.oana.domain.ConcluziiConsultatie;

/**
 * Spring Data SQL repository for the ConcluziiConsultatie entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConcluziiConsultatieRepository extends JpaRepository<ConcluziiConsultatie, Long> {}
