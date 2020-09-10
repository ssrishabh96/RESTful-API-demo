package com.tut.example.demo.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tut.example.demo.assembler.EmployeeModelAssembler;
import com.tut.example.demo.entity.Employee;
import com.tut.example.demo.exception.EmployeeNotFoundException;
import com.tut.example.demo.repository.EmployeeRepository;

@RestController
@RequestMapping("/api")
public class DemoController 
{
	@Autowired
	private final EmployeeRepository repository;

	private final EmployeeModelAssembler assembler;


	public DemoController(EmployeeRepository employeeRepository, 
			EmployeeModelAssembler assembler) 
	{
		super();
		this.repository = employeeRepository;
		this.assembler= assembler;
	}

	@GetMapping("/hello")
	public String welcome()
	{
		return "Welcome to the demo App";
	}

	@GetMapping("/employees1")
	List<Employee> all1() {
		return repository.findAll();
	}

	@PostMapping("/employees")
	Employee newEmployee(@RequestBody Employee newEmployee) {
		return repository.save(newEmployee);
	}

	@GetMapping("/employees1/{id}")
	Employee one1(@PathVariable Long id) {

		return repository.findById(id)
				.orElseThrow(() -> new EmployeeNotFoundException(id));
	}

	@PutMapping("/employees/{id}")
	Employee replaceEmployee(
			@RequestBody Employee newEmployee, @PathVariable Long id) 
	{
		return repository.findById(id)
				.map(employee -> {
					employee.setName(newEmployee.getName());
					employee.setRole(newEmployee.getRole());
					return repository.save(employee);
				})
				.orElseGet(() -> {
					newEmployee.setId(id);
					return repository.save(newEmployee);
				});
	}

	@DeleteMapping("/employees/{id}")
	void deleteEmployee(@PathVariable Long id) {
		repository.deleteById(id);
	}

	@GetMapping("/employees/{id}")
	public
	EntityModel<Employee> one(@PathVariable Long id) {

		Employee employee = repository.findById(id) //
				.orElseThrow(() -> new EmployeeNotFoundException(id));

		return assembler.toModel(employee);
	}

	@GetMapping("/employees")
	public CollectionModel<EntityModel<Employee>> all() {

		List<EntityModel<Employee>> employees = repository.findAll().stream()
				.map(assembler :: toModel)
				.collect(Collectors.toList());

		return CollectionModel.of(employees, 
				linkTo(methodOn(DemoController.class).all()).withSelfRel());
	}

}