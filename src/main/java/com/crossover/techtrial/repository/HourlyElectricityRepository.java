package com.crossover.techtrial.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

import com.crossover.techtrial.dto.DailyElectricity;
import com.crossover.techtrial.model.HourlyElectricity;

/**
 * HourlyElectricity Repository is for all operations for HourlyElectricity.
 * 
 * @author Crossover
 */


@RestResource(exported = false)
public interface HourlyElectricityRepository extends PagingAndSortingRepository<HourlyElectricity, Long> {

	Page<HourlyElectricity> findAllByPanelIdOrderByReadingAtDesc(Long panelId, Pageable pageable);

	//@Query(name="HourlyElectricity.findAllDataFromSerialPanelByDay", nativeQuery=true)
	@Query(value="select sum(generated_electricity) as 'sum',\n" + 
			"		  min(generated_electricity) as 'min', \n" + 
			"		  max(generated_electricity) as 'max',\n" + 
			"		  avg(generated_electricity) as 'average', \n" + 
			"		  reading_at as 'date' \n" + 
			"		  from crosssolar.hourly_electricity inner join crosssolar.panel\n" + 
			"		  where hourly_electricity.panel_id=panel.id and panel.serial=:panelSerial and reading_at<:currentDate group by DAY(reading_at) , MONTH(reading_at), YEAR(reading_at)", nativeQuery=true)
	List<DailyElectricity> getElectrycityByDay(@Param("panelSerial")Long panelSerial, @Param("currentDate")LocalDate currentDate);

}
