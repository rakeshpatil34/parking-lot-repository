package com.parkinglot.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Schedule {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private Long userId;
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss") //yyyy-MM-dd'T'HH:mm:ss.Z
	private Date startTime;
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date endTime;
	
	public Schedule() {}
	
	public Schedule(Date startTime, Date endTime) {
		super();
		this.startTime = startTime;
		this.endTime = endTime;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	public boolean isOverLapping(Schedule reservationSchedule) {
		Date rsTime = reservationSchedule.getStartTime();
		Date reTime = reservationSchedule.getEndTime();
		
		if(rsTime.after(endTime))
			return false;
		if(reTime.before(startTime))
			return false;
		
		return true;
	}
	
	@JsonIgnore
	public long getTotalReservationMinutes() {
		long secs = (this.endTime.getTime() - this.startTime.getTime()) / 1000;
		long mins = secs / 60;
		return mins;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((endTime == null) ? 0 : endTime.hashCode());
		result = prime * result + ((startTime == null) ? 0 : startTime.hashCode());
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
		Schedule other = (Schedule) obj;
		if (endTime == null) {
			if (other.endTime != null)
				return false;
		} else if (!endTime.equals(other.endTime))
			return false;
		if (startTime == null) {
			if (other.startTime != null)
				return false;
		} else if (!startTime.equals(other.startTime))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Schedule [startTime=" + startTime + ", endTime=" + endTime + "]";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	
	
	
}
