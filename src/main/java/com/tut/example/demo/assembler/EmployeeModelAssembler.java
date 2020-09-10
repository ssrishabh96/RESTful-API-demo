package com.tut.example.demo.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.tut.example.demo.controller.DemoController;
import com.tut.example.demo.entity.Employee;

@Component
public class EmployeeModelAssembler implements 
RepresentationModelAssembler<Employee, EntityModel<Employee>>
{

	@Override
	public EntityModel<Employee> toModel(Employee employee) 
	{
		return EntityModel.of(employee, //
				linkTo(methodOn(DemoController.class).one(employee.getId())).withSelfRel(),
				linkTo(methodOn(DemoController.class).all()).withRel("employees"));
	}

}
