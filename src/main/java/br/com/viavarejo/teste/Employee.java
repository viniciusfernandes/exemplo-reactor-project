package br.com.viavarejo.teste;

public class Employee {

  private Integer departmentId;

  private Integer id;
  private String name;

  private Integer organizationId;

  public Employee() {}

  public Employee(final Integer departmentId, final Integer id, final String name, final Integer organizationId) {
    this.departmentId = departmentId;
    this.id = id;
    this.name = name;
    this.organizationId = organizationId;
  }

  public Integer getDepartmentId() {
    return departmentId;
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

  public void setDepartmentId(final Integer departmentId) {
    this.departmentId = departmentId;
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
    return "Employee [departmentId=" + departmentId + ", id=" + id + ", name=" + name + ", organizationId=" + organizationId + "]";
  }
}
