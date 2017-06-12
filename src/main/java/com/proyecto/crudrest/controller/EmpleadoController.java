package com.proyecto.crudrest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.crudrest.model.Empleado;
import com.proyecto.crudrest.model.ErrorRest;
import com.proyecto.crudrest.repo.EmpleadoRepository;

@RestController
@RequestMapping("/api")
public class EmpleadoController {
	
	@Autowired
	EmpleadoRepository empleadoRepository;
	
	@GetMapping("/empleados")
	public List<Empleado> list(){
		List<Empleado> result = empleadoRepository.findAll();
		if(result != null)
			return result;
		else
			throw new EmpleadoNotFoundException();
	}
	
	@GetMapping("/empleado/{id}")
	public Empleado getEmpleado(@PathVariable Long id){
		Empleado result = empleadoRepository.findOne(id);
		if(result != null)
			return result;
		else
			throw new EmpleadoNotFoundException(id);
	}
	
	/*@PostMapping("/empleado")
	public Empleado createEmpleado(@RequestBody Empleado empleado, HttpServletResponse response){
		Empleado nuevo = new Empleado(empleado.getNombre(), empleado.getApellidos(), empleado.getFechaNacimiento());
		response.setStatus(201);
		return empleadoRepository.save(nuevo);
	}*/
	
	/* Metodo no recomendable, trabaja con dos sistemas json y caracteres
	@PostMapping("/empleado")
	public ResponseEntity<?> createEmpleado(RequestEntity<Empleado> reqEmpleado){
		Empleado empleado = reqEmpleado.getBody();
		
		if(empleadoRepository.findOne(empleado.getId())!= null){
			return new ResponseEntity<String>("El empleado con el ID: "+ empleado.getId()+" ya existe", HttpStatus.CONFLICT);
		}else{
			return new ResponseEntity<Empleado>(empleadoRepository.save(empleado), HttpStatus.CREATED);
		}
	}*/
	
	
	@PostMapping("/empleado")
	public ResponseEntity<?> createEmpleado(RequestEntity<Empleado> reqEmpleado){
		
		if(reqEmpleado.getBody() == null){
			return new ResponseEntity<ErrorRest>(new ErrorRest("Formato de petición incorrecto. Debe enviar los datos del empleado a dar de alta"), HttpStatus.BAD_REQUEST);
		}
		
		Empleado empleado = reqEmpleado.getBody();
		
		if(empleadoRepository.findOne(empleado.getId()) != null){
			return new ResponseEntity<ErrorRest>(new ErrorRest("El empleado con la id: "+empleado.getId()+" ya existe"), HttpStatus.CONFLICT);
		} else{
			return new ResponseEntity<Empleado>(empleadoRepository.save(empleado), HttpStatus.CREATED);
		}
			
	}
	
	
	@PutMapping("/empleado/{id}")
	public Empleado updateEmpleado(){
		return null;
	}
	
	@DeleteMapping("/empleado/{id}")
	public Empleado deleteEmpleado(){
		return null;
	}
	
	
	@ResponseStatus(value=HttpStatus.NOT_FOUND)
	private class EmpleadoNotFoundException extends RuntimeException{

		private static final long serialVersionUID = 1L;
		
		public EmpleadoNotFoundException(){
			super("No existe ningún empleado");
		}
		
		public EmpleadoNotFoundException(Long id){
			super(String.format("No existe ningún empleado con el id = %d", id));
		}
		
	}
}
