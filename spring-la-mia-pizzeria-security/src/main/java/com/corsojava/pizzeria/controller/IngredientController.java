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

import com.corsojava.pizzeria.model.Ingredient;
import com.corsojava.pizzeria.repository.IngredientRepository;

import jakarta.servlet.annotation.HttpMethodConstraint;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/ingredients")
public class IngredientController {
	
	@Autowired
	IngredientRepository ingredientRepository;
	
	
	@GetMapping
	public String index(Model model) {
		
		List <Ingredient>  allIngredient = ingredientRepository.findAll();
		model.addAttribute( "allIngredients",allIngredient);
		return "ingredients/index";
	}
	
	@GetMapping("/create")
	public String create(Model model) {
		Ingredient newIngredient = new Ingredient();
		model.addAttribute("newIngredient",newIngredient);
		return "ingredients/create";
	}
	
	@PostMapping("/create")
	public String store(@Valid @HttpMethodConstraint("newIngredient") Ingredient formIngredient, BindingResult bindingResult, Model model ) {
		
		if(bindingResult.hasErrors()) {
			return "ingredients/create";
		}
		
		ingredientRepository.save(formIngredient);
		
		return "redirect:/ingredients";
	}
	
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Integer id, Model model) {
		
		Ingredient ingredientToUpdate;
		ingredientToUpdate = ingredientRepository.getReferenceById(id);
		
		model.addAttribute("ingredientToUpdate",ingredientToUpdate);

		return "ingredients/edit";
	}
	
	
	@PostMapping("/edit/{id}")
	public String update( @Valid @ModelAttribute("ingredientToUpdate") Ingredient updateForm,  BindingResult bindingResult, Model model) {
		
		if(bindingResult.hasErrors()) {
			return "/ingredients/edit";
		}
		ingredientRepository.save(updateForm);
		return "redirect:/ingredients";
	}
	
	@PostMapping("delete/{id}")
	public String delete(@PathVariable("id") Integer id) {
		ingredientRepository.deleteById(id);
		
		return "redirect:/ingredients";
	}
	
}
