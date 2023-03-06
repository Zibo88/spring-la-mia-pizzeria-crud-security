package com.corsojava.pizzeria.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.corsojava.pizzeria.model.Ingredient;
import com.corsojava.pizzeria.model.Pizza;
import com.corsojava.pizzeria.repository.IngredientRepository;
import com.corsojava.pizzeria.repository.PizzaRepository;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/pizza")
public class PizzaController {
	//repository
	@Autowired
	private  PizzaRepository pizzaRepository;
	
	@Autowired
	private IngredientRepository ingredientRepository;
	
	//index
	@GetMapping
	public String index(@RequestParam(name="keyword", required = false) String keyword, Model model) {
		List <Pizza> elencoPizze;
		if(keyword == null) {
			elencoPizze = pizzaRepository.findAll();
		}else {
			elencoPizze = pizzaRepository.findByNomeLike("%"+keyword+"%"); // cerca le pizze per nome
		}
		
		model.addAttribute("elencoPizze", elencoPizze);
		return "pizza/index";
	}
	
	//show
	@GetMapping("/{id}")
	public String show(@PathVariable("id") Integer id, Model model) {
		Pizza  singolaPizza = pizzaRepository.getReferenceById(id);
	
		model.addAttribute("pizza",singolaPizza);
	
		return "pizza/pizzaDetail";
	}
	
	//create
	@GetMapping("/create")
	public String create(Model model) {
		Pizza newPizza = new Pizza();
		List<Ingredient> allIngredient = ingredientRepository.findAll();
		model.addAttribute("allIngredients", allIngredient);
		model.addAttribute("pizza" ,newPizza);
		return "pizza/create";
	}
	
	@PostMapping("/create")
	public String strore(@Valid @ModelAttribute("pizza") Pizza formPizza, BindingResult bindingResult ,Model model) {
		
		if(bindingResult.hasErrors()) {
			return "pizza/create";
		}
		
		pizzaRepository.save(formPizza);
		
		return "redirect:/pizza";
	}
	
	//edit
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Integer id, Model model) {
		Pizza pizzaToUpdate = pizzaRepository.getReferenceById(id);
		List<Ingredient> allIngredient = ingredientRepository.findAll();
		model.addAttribute("allIngredients", allIngredient);
		model.addAttribute("pizzaToUpdate",pizzaToUpdate);
		return "pizza/edit";
	}
	
	@PostMapping("/edit/{id}")
	public String update(@Valid @ModelAttribute("pizzaToUpdate") Pizza pizzaEditForm,BindingResult bindingResult, Model model) {
		
		if(bindingResult.hasErrors()) {
			return "pizza/edit";
		}
		
		pizzaRepository.save(pizzaEditForm);
		
		return "redirect:/pizza/{id}";
	}
	
	//delete
	@PostMapping("/delete/{id}")
	public String delete(@PathVariable("id") Integer id) {
		
		pizzaRepository.deleteById(id);
		
		return "redirect:/pizza";
	}
	

}
