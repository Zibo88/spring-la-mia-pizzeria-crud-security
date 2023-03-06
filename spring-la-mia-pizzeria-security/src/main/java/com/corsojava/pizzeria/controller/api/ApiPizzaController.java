package com.corsojava.pizzeria.controller.api;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.corsojava.pizzeria.model.Pizza;
import com.corsojava.pizzeria.repository.PizzaRepository;

@RestController
@CrossOrigin
@RequestMapping("/api/pizza")
public class ApiPizzaController {

	@Autowired
	PizzaRepository pizzaRepository;
	
	@GetMapping
	public List<Pizza> index(@RequestParam(name="nome", required=false) String nome, Model model){
		List <Pizza> elencoPizze;
		if(nome == null) {
			return	elencoPizze = pizzaRepository.findAll();
		}else {
			return elencoPizze = pizzaRepository.findByNomeLike("%"+nome+"%"); // cerca le pizze per nome
		}
		
	}
	
	@GetMapping("{id}")
	public ResponseEntity<Pizza> show(@PathVariable("id") Integer id){
	Optional<Pizza> pizzaSingola=pizzaRepository.findById(id);
	if(pizzaSingola.isPresent()) {
	return new ResponseEntity<Pizza>(pizzaSingola.get(), HttpStatus.OK);
	}else {
	return new ResponseEntity<Pizza>(HttpStatus.NOT_FOUND);
	}
	}
	
	@PostMapping("/create")
	public Pizza create(@RequestBody Pizza pizza){
	return pizzaRepository.save(pizza);
	}
	
	@PutMapping("/edit/{id}")
	public Pizza edit(@RequestBody Pizza pizza, @PathVariable("id")Integer id) {
		
		Pizza pizzaToUpdate = pizzaRepository.getReferenceById(id);
		pizzaToUpdate.setNome(pizza.getNome());
		pizzaToUpdate.setDescrizione(pizza.getDescrizione());
		pizzaToUpdate.setFoto(pizza.getFoto());
		pizzaToUpdate.setPrezzo(pizza.getPrezzo());
	
		return pizzaRepository.save(pizzaToUpdate);
	}
	
	@DeleteMapping("/{id}")	
	public void delete(
			@PathVariable("id") Integer id) {
		pizzaRepository.deleteById(id);
	}
	
	
	
}
