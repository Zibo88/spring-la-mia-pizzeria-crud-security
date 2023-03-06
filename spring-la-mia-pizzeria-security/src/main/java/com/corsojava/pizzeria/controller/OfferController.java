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

import com.corsojava.pizzeria.model.Offer;
import com.corsojava.pizzeria.model.Pizza;
import com.corsojava.pizzeria.repository.OfferRepository;
import com.corsojava.pizzeria.repository.PizzaRepository;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/offer")
public class OfferController {
	
	@Autowired
	OfferRepository offerRepository;
	
	@Autowired
	private  PizzaRepository pizzaRepository;
	
	@GetMapping
	public String index(Model model) {
		
		List <Offer> offers  = offerRepository.findAll();
		
		model.addAttribute("offers",offers);
		return "offer/index";
	}
	
	@GetMapping("/create")
	public String create(@RequestParam(name = "pizzaId", required = true) Integer pizzaId, Model model  ) {
		
		// creo una nuova istanza di pizza
		Offer newOffer = new Offer();
		// le filtro per id
		Pizza pizza = pizzaRepository.getReferenceById(pizzaId);
		
		// assegno all'offerta una pizza
		newOffer.setPizza(pizza);
		
		model.addAttribute("newOffer",newOffer);
		
		return "offer/create";
	}
	
	@PostMapping("/create")
	public String store(@Valid @ModelAttribute("newOffer") Offer offerForm, BindingResult bindingResult, Model model) {
		
		if(bindingResult.hasErrors()) {
			return "offer/create";
		}
		
		
		offerRepository.save(offerForm);
		return "redirect:/pizza";
	}
	
	//edit
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Integer id, Model model) {
		Offer offerToUpdate = offerRepository.getReferenceById(id);
		model.addAttribute("offerToUpdate",offerToUpdate);
		return "offer/edit";
	}
	
	@PostMapping("/edit/{id}")
	public String update(@Valid @ModelAttribute("offerToUpdate") Offer offerForm,BindingResult bindingResult, Model model) {
		
		if(bindingResult.hasErrors()) {
			return "offer/edit";
		}
		
		Pizza pizza = offerForm.getPizza();
		
		offerRepository.save(offerForm);
		
		return "redirect:/pizza/" + pizza.getId();
	}
	
	//delete
		@PostMapping("/delete/{id}")
		public String delete(@PathVariable("id") Integer id) {
			
			offerRepository.deleteById(id);
			
			return "redirect:/pizza";
		}

}
