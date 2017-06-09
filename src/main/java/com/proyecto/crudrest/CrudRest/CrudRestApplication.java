package com.proyecto.crudrest.CrudRest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.proyecto.crudrest.model.Empleado;
import com.proyecto.crudrest.repo.EmpleadoRepository;

@SpringBootApplication
public class CrudRestApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrudRestApplication.class, args);
	}
	
	
	/**
	 * Inicializar los datos justo despues de lanzar la aplicacion
	 * @param empleadoRepository repositorio del empleado
	 * @return codigo que guarda los datos
	 */
	@Bean
	CommandLineRunner init(EmpleadoRepository empleadoRepository){
		String[][] data = {
				{"José","García Martinez", "01/01/1975"},
				{"Manuel","Pérez Díaz", "10/10/1978"},
				{"Luis Miguel", "López Magaña", "18/09/1982"},
				{"Alberto", "Jiménez Sarmiento", "03/03/1973"},
				{"Carlos", "Ruiz Santos", "02/12/1978"},
				{"Martín", "Lópex Alfaro", "04/05/1976"},
				{"María", "Martínez Sánchez", "14/07/1983"},
				{"Luisa", "Milán Llanes", "28/08/1978"}
		};
		
		final DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		
		return (evt) -> Arrays.asList(data)
				.forEach(a -> {
				try{
					empleadoRepository.save(new Empleado(a[0], a[1], df.parse(a[2])));
				} catch(Exception e){
					e.printStackTrace();
				}
		});
	}
}
