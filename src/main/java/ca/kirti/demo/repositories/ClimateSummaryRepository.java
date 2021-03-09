package ca.kirti.demo.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ca.kirti.demo.model.ClimateSummary;
import ca.kirti.demo.model.ClimateSummaryIdentity;

@Repository
public interface ClimateSummaryRepository extends JpaRepository<ClimateSummary, ClimateSummaryIdentity> {

	@Query("select c from ClimateSummary c " +
	         "where c.climateDate between ?1 and ?2")
	List<ClimateSummary> findByDatesBetween(Date dateFrom, Date dateTo);
	
	@Query("select c from ClimateSummary c " +
	         "where c.climateDate between ?1 and ?2")
	Page<ClimateSummary> findByDatesBetween(Date dateFrom, Date dateTo, Pageable pageable);
}
