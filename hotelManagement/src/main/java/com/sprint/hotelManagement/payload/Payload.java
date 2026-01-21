package com.sprint.hotelManagement.payload;

import lombok.Data;

import java.util.Set;

public class Payload {
    @Data
    public static class LoginRequest {
        private String username;
        private String password;
        
		public String getUsername() {
			return username;
		}
		public void setUsername(String username) {
			this.username = username;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
        
        
    }

    @Data
    public static class SignupRequest {
        private String username;
        private String email;
        private String password;
        private String mobile;
        private String address;
        private Set<String> role;
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
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		public String getMobile() {
			return mobile;
		}
		public void setMobile(String mobile) {
			this.mobile = mobile;
		}
		public String getAddress() {
			return address;
		}
		public void setAddress(String address) {
			this.address = address;
		}
		public Set<String> getRole() {
			return role;
		}
		public void setRole(Set<String> role) {
			this.role = role;
		}
        
        
    }

    @Data
    public static class JwtResponse {
        private String token;
        private Long id;
        private String username;
        private String email;
        private Set<String> roles;

        public JwtResponse(String accessToken, Long id, String username, String email, Set<String> roles) {
            this.token = accessToken;
            this.id = id;
            this.username = username;
            this.email = email;
            this.roles = roles;
        }

		public String getToken() {
			return token;
		}

		public void setToken(String token) {
			this.token = token;
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

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

		public Set<String> getRoles() {
			return roles;
		}

		public void setRoles(Set<String> roles) {
			this.roles = roles;
		}
        
        
    }

    @Data
    public static class MessageResponse {
        private String message;

        public MessageResponse(String message) {
            this.message = message;
        }

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}
        
        
    }
}
