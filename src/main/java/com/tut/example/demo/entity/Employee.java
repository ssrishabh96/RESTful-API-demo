package com.tut.example.demo.entity;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Entity  
@Getter 
@Setter  
@AllArgsConstructor 
@NoArgsConstructor
public class Employee 
{
	@Id @GeneratedValue
	private Long id; 

	private String name;

	private String role;

	public Employee(String name, String role)
	{
		this.role= role;
		this.name= name;
	}

	@Override
	public String toString()
	{
		return "Employee{" + "id=" + this.id + ", name='" + 
				this.name + '\'' + ", role='" + this.role + 
				'\'' + '}';
	}

	@Override
	public int hashCode() 
	{
		return Objects.hash(this.id, this.name, this.role);
	}

	@Override
	public boolean equals(Object o) 
	{
		if (this == o)
			return true;
		if (!(o instanceof Employee))
			return false;
		Employee employee = (Employee) o;
		return Objects.equals(this.id, employee.id) && Objects.equals(this.name, employee.name)
				&& Objects.equals(this.role, employee.role);
	}
}
