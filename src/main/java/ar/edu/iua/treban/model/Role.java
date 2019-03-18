package ar.edu.iua.treban.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "roles")
public class Role implements Serializable {

	private static final long serialVersionUID = 3941512108484193L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idRole;

	@Column(unique = true, nullable = false)
	private String name;

	@Column(nullable = false, length = 100)
	private String description;

	public int getIdRole() {
		return idRole;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public void setIdRole(int idRole) {
		this.idRole = idRole;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public boolean equals(Object obj) {
		return ((Role) obj).getIdRole() == getIdRole() || ((Role) obj).getName().equals(getName());
	}

	@Override
	public int hashCode() {
		return getIdRole();
	}

	@Override
	public String toString() {
		return String.format("Role: [%d] %s", getIdRole(), getName());
	}
}