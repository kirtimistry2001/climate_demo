package ca.kirti.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ca.kirti.demo.model.ClimateSummary;

@Repository
public interface ClimateSummaryRepository extends JpaRepository<ClimateSummary, Integer>{
}
