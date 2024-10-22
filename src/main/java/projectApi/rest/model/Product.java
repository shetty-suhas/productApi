package projectApi.rest.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data 
@AllArgsConstructor
@JacksonXmlRootElement
public class Product {
	private long id; 
	private int price, qnt; 
	private String brand, description; 
	
	public void update(Product p) { 
		this.id = p.id; 
		this.brand = p.brand; 
		this.price = p.price; 
		this.description = p.description; 
		this.qnt = p.qnt;
		
	}

}
