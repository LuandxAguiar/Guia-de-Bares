package br.luan.sp.bares.guidebar.model;

import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
@Entity
public class Avaliacao {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	private Bar bar;
	private double nota; 
	private String comentario ; 
	//apresentar  e ler a data
	@JsonFormat(pattern = "dd-MM-yyyy")
	private Calendar datavisita ;
	@ManyToOne
	private Usuario usuario ; 
	
}
