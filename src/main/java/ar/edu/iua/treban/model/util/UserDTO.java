package ar.edu.iua.treban.model.util;

public class UserDTO {

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}

	public void setAccountNonExpired(boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	public UserDTO(String username, String email, boolean accountNonExpired) {
		super();
		this.username = username;
		this.email = email;
		this.accountNonExpired = accountNonExpired;
	}

	private String username;
	private String email;
	private boolean accountNonExpired;

}
