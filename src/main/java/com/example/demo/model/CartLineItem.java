package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.databind.annotation.EnumNaming;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Embeddable
//@Entity
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//class ProductCartKey {
//	@Column(name = "card_id")
//	private Long cardId;
//	
//	@Column(name = "variant_product_id")
//	private Long variantProductId;
//	
//}

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartLineItem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Boolean deleted;
	private Integer quantity;
//	@EmbeddedId
//	private ProductCartKey id;
	@ManyToOne
	@JoinColumn(name = "card_id")
	private Cart cart;

	@ManyToOne
	@JoinColumn(name = "variant_product_id")
	@JsonBackReference
	private VariantProduct variantProduct;
}
