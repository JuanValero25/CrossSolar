package com.crossover.techtrial.dto;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DailyElectricity class will hold sum, average,minimum and maximum electricity
 * for a given day.
 * 
 * @author Crossover
 *
 */

public class DailyElectricity implements Serializable {

	private static final long serialVersionUID = 3605549122072628877L;

	private LocalDate date;

	private Long sum;

	private Double average;

	private Long min;

	private Long max;

	public LocalDate getDate() {
		return date;
	}

	public DailyElectricity(Long sum, Long min,Long max , Double average, LocalDate date) {
		super();
		this.date = date;
		this.sum = sum;
		this.average = average;
		this.min = min;
		this.max = max;
	}

	public DailyElectricity() {
		super();
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public Long getSum() {
		return sum;
	}

	public void setSum(Long sum) {
		this.sum = sum;
	}

	public Double getAverage() {
		return average;
	}

	public void setAverage(Double average) {
		this.average = average;
	}

	public Long getMin() {
		return min;
	}

	public void setMin(Long min) {
		this.min = min;
	}

	public Long getMax() {
		return max;
	}

	public void setMax(Long max) {
		this.max = max;
	}
	
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((average == null) ? 0 : average.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((max == null) ? 0 : max.hashCode());
		result = prime * result + ((min == null) ? 0 : min.hashCode());
		result = prime * result + ((sum == null) ? 0 : sum.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DailyElectricity other = (DailyElectricity) obj;
		if (average == null) {
			if (other.average != null)
				return false;
		} else if (!average.equals(other.average))
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (max == null) {
			if (other.max != null)
				return false;
		} else if (!max.equals(other.max))
			return false;
		if (min == null) {
			if (other.min != null)
				return false;
		} else if (!min.equals(other.min))
			return false;
		if (sum == null) {
			if (other.sum != null)
				return false;
		} else if (!sum.equals(other.sum))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DailyElectricity [date=" + date + ", sum=" + sum + ", average=" + average + ", min=" + min + ", max="
				+ max + "]";
	}

}
