package college.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {

	ADMIN, 
	USER, 
	PROFESSOR;

	@Override
	public String getAuthority() {
		return "ROLE_" + name();
	}
	
}

