package com.assignment.fetchNumber.FetchNextNumberAPI.controllers;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.fetchNumber.FetchNextNumberAPI.entities.Category;
import com.assignment.fetchNumber.FetchNextNumberAPI.services.CategoryService;
import com.assignment.fetchNumber.FetchNextNumberAPI.utility.ChangePair;

@RestController
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;

	//Controller function to return a list of all Catergories present in category table of our database
	@GetMapping("/categories")
	public List<Category> getAllCategories() {
		// returns a list of Category objects or null is no Data in the table
		return this.categoryService.getCategories();
	}
	
	//Controller function to return a category with the same id as passed in our path variable from category table of our database
	@GetMapping("/category/{id}")
	public Category getCategory(@PathVariable String id) {
		// returns null if no such entry found
		try {
			long parsed_id = Long.parseLong(id);
			// As mentioned in question, can't have any category value as 0, so returning null instead of inserting
			if(parsed_id != 0)
				return this.categoryService.getCategory(Long.parseLong(id));
			else
				return null;
		}catch(Exception e) {
			// if wrong format id is passed which is not parsable, returning null instead of any error
			// this can be replaced with any http error message, or if not wrapped in try-catch
			// automatically returns 500:Internal Server Error
			return null; 
		}
		
	}
	
	//Controller function to add a category, the data is sent in JSON, if successful in adding a category, returns the same category
	//If Same Id is present in database, this function updates the category name attribute.
	@PostMapping("/category")
	public Category inserUpdateCategory(@RequestBody Category category){
		return this.categoryService.insertUpdateCategory(category);
		
	}
	
	//Function to delete all data from the category table
	@DeleteMapping("/categories")
	public String deleteAllCategory() {
		// returns a success message when execution is completed
		return this.categoryService.deleteAllCategory();
	}
	
	//Function to delete particular category as per id from the category table
	@DeleteMapping("/category/{id}")
	public String deleteCategory(@PathVariable String id){
		
		// returns null if no such entry found
		try {
			return this.categoryService.deleteCategory(Long.parseLong(id));
		}catch(Exception e) {
			// if wrong format id is passed which is not parsable, returning null instead of any error
			// this can be replaced with any http error message, or if not wrapped in try-catch
			// automatically returns 500:Internal Server Error
			return null; 
		}
	}
	
	//Controller to FetchNextNumber and update the id of that particular category with the new number as described in assignment
	//Returns a pair of OldValue and NewValue which are member of ChangePair Class(taken as utility), so this function returns a ChangePair Object
	@GetMapping("/FetchNextNumber")
	public ChangePair fetchNextNumber(){
		// initializing variables for no data present case
		long oldValue = 0;
		long newValue = 1;
		
		//As not mentioned which category id to choose from table of multiple categories
		//I resolved it using random selection
		Random random = new Random();  
		
		//Getting all the categories
		List<Category> categories = this.categoryService.getCategories();
		
		//If null, i.e no data in table, using 0 as oldValue, automatically 1 is the next smallest number whose digits add up to 1
		if(categories == null) {
			ChangePair pair = new ChangePair(oldValue,newValue);
			return pair;
		}
		
		int listSize = categories.size();
		//Selecting a random index
		int index = random.nextInt(listSize);
		
		Category category= categories.get(index);
		//Storing the old value of the selected category
		oldValue = category.getId();
		
		//calling fextNextNumber from service layer, which gives us the next smallest number not present in database and also
		//its digits add up to 1
		newValue = categoryService.fetchNextNumber(oldValue);
		
		//deleting the old record
		this.categoryService.deleteCategory(oldValue);
		
		//changing the id, keeping other values intact(in this case Category Name)
		category.setId(newValue);
		
		//inserting the data with new id in our data base
		this.categoryService.insertUpdateCategory(category);
		
		//Preparing the change pair object to meet the return requirements
		ChangePair pair = new ChangePair(oldValue,newValue);
		return pair;
	}
	
	//Controller to FetchNextNumber and update the id of that particular category(passed as path variable) with the new number as described in assignment
	//Returns a pair of OldValue and NewValue which are member of ChangePair Class(taken as utility), so this function returns a ChangePair Object
	//Here Id needs to be passed
	@GetMapping("/FetchNextNumber/{id}")
	public ChangePair fetchNextNumber(@PathVariable String id){
		// initializing variables for no data for provided id present
		long oldValue = 0;
		long newValue = 1;
		Category category;
		
		try {
			category = this.categoryService.getCategory(Long.parseLong(id));
		}catch(Exception e){
			// if wrong format id is passed which is not parsable, returning null instead of any error
			// this can be replaced with any http error message, or if not wrapped in try-catch
			// automatically returns 500:Internal Server Error
			return null;
		}
		//if data of the particular id not present default oldValue = 0, but other data may be present so checking the next
		//available required number
		if(category == null) {
			newValue = categoryService.fetchNextNumber(oldValue);
			//creating a new category object, as 0 id object is not present in database (as mentioned)
			category = new Category();
			category.setId(newValue);
			category.setName("Created By Default Fetch Number");
			
			//adding the the database
			this.categoryService.insertUpdateCategory(category);
			
			//Preparing the change pair object to meet the return requirements
			ChangePair pair = new ChangePair(oldValue,newValue);
			return pair;
		}
		
		oldValue = category.getId();
		newValue = categoryService.fetchNextNumber(oldValue);
		
		//deleting the old record
		this.categoryService.deleteCategory(oldValue);
		
		//changing the id, keeping other values intact(in this case Category Name)
		category.setId(newValue);
		
		//inserting the data with new id in our data base
		this.categoryService.insertUpdateCategory(category);
		
		//Preparing the change pair object to meet the return requirements
		ChangePair pair = new ChangePair(oldValue,newValue);
		return pair;
	}
}
