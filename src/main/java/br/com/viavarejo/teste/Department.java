package br.com.viavarejo.teste;

import java.util.ArrayList;
import java.util.List;

public class Department {
  private final List<Employee> employees = new ArrayList<>();
  private Integer id;

  private String name;

  private Integer organizationId;

  public Department(final Integer id, final String name, final Integer organizationId) {
    super();
    this.id = id;
    this.name = name;
    this.organizationId = organizationId;
  }

  public Department addEmployee(final Employee employee) {
    employees.add(employee);
    return this;
  }

  public Department addEmployee(final List<Employee> employees) {
    this.employees.addAll(employees);
    return this;
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

  public Integer getOrganizationId() {
    return organizationId;
  }

  public void setId(final Integer id) {
    this.id = id;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public void setOrganizationId(final Integer organizationId) {
    this.organizationId = organizationId;
  }

  @Override
  public String toString() {
    return "Department [id=" + id + ", name=" + name + ", organizationId=" + organizationId + "]";
  }
}
