package com.huong.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor 
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name ="product")
public class Product {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(length = 5000)
	private String name;

	@Column(length = 5000)
	private String description;
	
	@ManyToOne
	@JoinColumn(name ="category_id")
    private Category category;
    private Double price;
    private int stock;
    private String img;
    private int discount;
    private Double discountPrice;
    
    @Column(name = "is_active")
    private Boolean isActive;
    
}
