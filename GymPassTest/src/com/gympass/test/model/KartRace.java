/******************************************************************************** 
 * Criado em(Created on): 14/08/2018
 * Autor(Author)        : Bruno Lima Neves
 *******************************************************************************/
package com.gympass.test.model;

import java.io.Serializable;
import java.time.LocalTime;

/**
 * @author bruno.lima.neves
 */
public class KartRace implements Serializable{
	
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * time.
	 */
	private LocalTime time;

	/**
	 * id.
	 */
	private Integer id;
	
	/**
	 * name.
	 */
	private String name;
	
	/**
	 * lapNumber.
	 */
	private Integer lapNumber;
	
	/**
	 * lapTime.
	 */
	private LocalTime lapTime;
	
	/**
	 * lapAverage.
	 */
	private Double lapAverage;

	/**
	 * @return the time.
	 */
	public LocalTime getTime() {
		return time;
	}

	/**
	 * @param time the time to set.
	 */
	public void setTime(final LocalTime time) {
		this.time = time;
	}

	/**
	 * @return the id.
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set.
	 */
	public void setId(final Integer id) {
		this.id = id;
	}

	/**
	 * @return the name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set.
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * @return the lapNumber.
	 */
	public Integer getLapNumber() {
		return lapNumber;
	}

	/**
	 * @param lapNumber the lapNumber to set.
	 */
	public void setLapNumber(final Integer lapNumber) {
		this.lapNumber = lapNumber;
	}

	/**
	 * @return the lapTime.
	 */
	public LocalTime getLapTime() {
		return lapTime;
	}

	/**
	 * @param lapTime the lapTime to set.
	 */
	public void setLapTime(final LocalTime lapTime) {
		this.lapTime = lapTime;
	}
	
	/**
	 * @return the lapAverage.
	 */
	public Double getLapAverage() {
		return lapAverage;
	}
	
	/**
	 * @param lapAverage the lapAverage to set. 
	 */
	public void setLapAverage(final Double lapAverage) {
		this.lapAverage = lapAverage;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		KartRace other = (KartRace) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}