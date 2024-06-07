package com.example.systemdesign2024.control.students;

public class CreateStudentInput {
  private final String id;
  private final String name;

  public CreateStudentInput(String id, String name) {
    this.id = id;
    this.name = name;
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }
}
