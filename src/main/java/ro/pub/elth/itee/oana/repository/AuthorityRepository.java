package ro.pub.elth.itee.oana.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.pub.elth.itee.oana.domain.Authority;

/**
 * Spring Data JPA repository for the {@link Authority} entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {}
