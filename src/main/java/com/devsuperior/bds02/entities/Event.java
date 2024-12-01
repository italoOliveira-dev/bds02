package com.devsuperior.bds02.entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;



@Entity
@Table(name = "tb_event")
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@ToString
public class Event {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private LocalDate date;
	private String url;
	
	@ManyToOne
	@JoinColumn(name = "city_id")
	private City city;
	
	public Event() {
	}

	public Event(Long id, String name, LocalDate date, String url, City city) {
		this.id = id;
		this.name = name;
		this.date = date;
		this.url = url;
		this.city = city;
	}
}
