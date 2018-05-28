package cz.fi.muni.TACOS.dto;


import cz.fi.muni.TACOS.enums.UserRole;

import java.util.Objects;

public class UserCreateDTO {

	private String name;

	private String surname;

	private String email;

	private UserRole role;

	private String password;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		UserCreateDTO that = (UserCreateDTO) o;
		return Objects.equals(name, that.name) &&
				Objects.equals(surname, that.surname) &&
				Objects.equals(email, that.email) &&
				role == that.role &&
				Objects.equals(password, that.password);
	}

	@Override
	public int hashCode() {

		return Objects.hash(name, surname, email, password);
	}

    @Override
    public String toString() {
        return "UserCreateDTO{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                ", password='" + password + '\'' +
                '}';
    }
}
