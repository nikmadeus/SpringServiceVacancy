package com.nikmadeus.vacancies.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="users")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Email
	private String username;
	
	@Size(min=2, max=255)
	private String firstName;
	
	@Size(min=2, max=255)
	private String lastName;

	@Size(min=6, max=255)
	private String phone;
	
	private String state;
	
	@Size(min=8, max=255)
	private String password;
	
	@OneToMany(mappedBy="host", cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private List<Vacancy> vacanciesCreated;
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(
			name = "vacancies_attendees",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "vacancy_id")
	)
	private List<Vacancy> vacanciesAttending;
	
	@OneToMany(mappedBy="author", cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private List<Comment> comments;
	
	@DateTimeFormat(pattern = "MM/dd/yyy HH:mm:ss")
	private Date createdAt;
	
	@DateTimeFormat(pattern = "MM/dd/yyy HH:mm:ss")
	private Date updatedAt;
	
	public User() {}
	
	public User(String username, String firstName, String lastName, String phone, String state, String password) {
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phone = phone;
		this.state = state;
		this.password = password;
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

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Vacancy> getVacanciesCreated() {
		return vacanciesCreated;
	}

	public void setVacanciesCreated(List<Vacancy> vacanciesCreated) {
		this.vacanciesCreated = vacanciesCreated;
	}

	public List<Vacancy> getVacanciesAttending() {
		return vacanciesAttending;
	}

	public void setVacanciesAttending(List<Vacancy> vacanciesAttending) {
		this.vacanciesAttending = vacanciesAttending;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	@PrePersist
    protected void onCreate(){
		this.setCreatedAt(new Date());
    }

    @PreUpdate
    protected void onUpdate(){
    		this.setUpdatedAt(new Date());
    }

}
