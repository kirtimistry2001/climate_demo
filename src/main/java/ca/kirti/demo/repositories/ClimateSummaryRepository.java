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

	/**
	 * Returns data for given date range.
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	@Query("select c from ClimateSummary c " +
	         "where c.climateDate between ?1 and ?2")
	List<ClimateSummary> findByDatesBetween(Date dateFrom, Date dateTo);
	
	/**
	 * Returns a {@link Page}for given date range and 
	 * 		meeting the paging restriction provided in the {@code Pageable} object
	 * @param dateFrom
	 * @param dateTo
	 * @param pageable
	 * 	the paging restriction provided in the {@code Pageable} object
	 * @return
	 */
	@Query("select c from ClimateSummary c " +
	         "where c.climateDate between ?1 and ?2")
	Page<ClimateSummary> findByDatesBetween(Date dateFrom, Date dateTo, Pageable pageable);
}
