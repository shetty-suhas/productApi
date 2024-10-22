package projectApi.rest.controller;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import projectApi.rest.model.Product;

@RestController 
@RequestMapping("/product/api")
public class ProductController {  
	
	List<Product> productList = new ArrayList<>();
	
	@GetMapping("/get")
	public String sendStatus() { 
		return "Running";
	} 
	
	@GetMapping("/multiply/{number}")
	 public int multiply(@PathVariable int number) { 
		return number * 5;
	} 
	
	@GetMapping("/sum/{array}")
	public int sum(@PathVariable String array) { 
		String[] arr = array.split(","); 
		int sum = 0;
		for(String s: arr) sum += Integer.parseInt(s); 
		return sum;
	} 
	
	@GetMapping("/sub") 
	public int sub(@RequestParam(value = "a", required = false, defaultValue = "100") int a, @RequestParam("b") int b) { 
		return a - b;
	} 
	
	{
		productList.add(new Product(100, 13000, 3, "PUMA", "GOOD QUALITY SHOES")); 
		productList.add(new Product(200, 34200, 1, "ADIDAS", "EXPENSIVE SHOES")); 
		productList.add(new Product(300, 43555, 2, "NIKE", "AIR JORDAN"));
	}
	
	@GetMapping(value="/productInfo", produces = MediaType.APPLICATION_JSON_VALUE) 
	public List<Product> viewListOfProducts(){ 
		return productList;
		
	}
	
	@GetMapping(value="/productInfoEntity", produces = MediaType.APPLICATION_XML_VALUE) 
	public ResponseEntity<List<Product>> entityView(){ 
		return ResponseEntity.ok(productList);
	}
	
	@GetMapping(value="getProduct/{id}", 
			produces = MediaType.APPLICATION_JSON_VALUE) 
	public ResponseEntity<Product> getProductById(@PathVariable Long id){ 
		Optional<Product> opProd = productList.stream().filter(product -> product.getId() == id).findAny();
		
		if(opProd.isPresent()) return ResponseEntity.ok(opProd.get()); 
		return ResponseEntity.noContent().build();
		
	}
	
	@GetMapping(
			value="getProductByBrand/{brand}", 
			produces=MediaType.APPLICATION_JSON_VALUE) 
	public ResponseEntity<Product> getProductByBrand(@PathVariable String brand){ 
		Optional<Product> opProd = productList.stream().filter(product -> product.getBrand().equals(brand.toUpperCase())).findAny(); 
		
		if(opProd.isPresent()) return ResponseEntity.ok(opProd.get()); 
		return ResponseEntity.noContent().build();
	} 
	
	@PostMapping(
			value="addNewProduct", 
			consumes = MediaType.APPLICATION_JSON_VALUE, 
			produces=MediaType.APPLICATION_JSON_VALUE) 
	public ResponseEntity<Product> addProduct(@RequestBody Product product){  
		if(product != null) { 
			productList.add(product); 
			return ResponseEntity.ok(product);
		} 
		
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping(value="modify", 
			consumes = MediaType.APPLICATION_JSON_VALUE, 
			produces = MediaType.APPLICATION_XML_VALUE) 
	public ResponseEntity<Product> updateProduct(@RequestBody Product product){  
		Optional<Product> oldProduct = productList.stream().
									filter(p -> p.getId() == product.getId()).
									findAny(); 
//		for(Product p: productList) { 
//			if(p.getProductId() == product.getProductId()) p.update(product); 
//		} 
//		for(Product p: productList) { 
//			if(p.getProductId() == product.getProductId()) System.out.println(System.identityHashCode(p));
//		}
		
		Product p = oldProduct.get();  
		p.setBrand(product.getBrand());
		p.setDescription(product.getDescription());
		p.setPrice(product.getPrice());
		p.setQnt(product.getQnt()); 
//		System.out.println(System.identityHashCode(p));
		return ResponseEntity.accepted().body(null);
	
	}  
	
	@DeleteMapping(value="/delete/{id}")
	public void removeProduct(@PathVariable long id) { 
		Optional<Product> prod = productList.stream(). 
											filter(p -> p.getId() == id).
											findAny(); 
		productList.remove(prod.get()); 
	}
	
	
	
}
