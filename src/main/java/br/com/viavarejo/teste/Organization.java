package br.com.viavarejo.teste;

import java.util.ArrayList;
import java.util.List;

public class Organization {
  private final List<Department> departments = new ArrayList<>();

  private final List<Employee> employees = new ArrayList<>();

  private Integer id;

  private String name;

  public Organization(final Integer id, final String name) {
    this.id = id;
    this.name = name;
  }

  public Organization addEmployee(final List<Employee> employees) {
    try {
      Thread.sleep(1000);
    } catch (final InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    this.employees.addAll(employees);
    return this;
  }

  public List<Department> getDepartments() {
    return departments;
  }

  public List<Employee> getEmployees() {
    return employees;
  }

  public Integer getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setId(final Integer id) {
    this.id = id;
  }

  public void setName(final String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return "Organization [id=" + id + ", name=" + name + "]";
  }
}
