package com.assignment.fetchNumber.FetchNextNumberAPI.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.assignment.fetchNumber.FetchNextNumberAPI.dao.CategoryDao;
import com.assignment.fetchNumber.FetchNextNumberAPI.entities.Category;

@Service
public class CategoryServiceImpl implements CategoryService{
	
	@Autowired
	CategoryDao categoryDao;

	// Function to get all the categories present in category table
	@Override
	public List<Category> getCategories() {
		// jpa repository funtion to get all the data from the table as list of objects (Category in this case)
		return categoryDao.findAll();
	}
	// Function to update or insert data in category table
	@Override
	public Category insertUpdateCategory(Category category) {
		// jpa repository funtion to insert/update(if present) in the table
		categoryDao.save(category);
		return category;
	}
	
	// Function to get a particular category using category id
	@Override
	public Category getCategory(long categoryId) {
		try {
			Category category = categoryDao.findById(categoryId).get(); // Returns an object matching to the id, if found
			return category;
		}catch(Exception e) {
			// IllegalArgumentException is raised if no data found so wrapped in try catch block and null is returned instead
			return null;
		}
	}
	
	// Function to delete all the categories from category table
	@Override
	public String deleteAllCategory() {
		try {
			//jpa repo function to delete all records from a table
			categoryDao.deleteAll();
			return "Success";
		}catch(Exception e) {
			return "No Data in Category Table";
		}
		
	}

	// Function to delete a particular category from category table
	@Override
	public String deleteCategory(long categoryId) {
		try {
			//Finds if data of particular id present or not, if not exception is raised by the function
			Category category = categoryDao.findById(categoryId).get();
			//If found deleting that entity
			categoryDao.delete(category);
			
			return "Success";
		}catch(Exception e) {
			//IllegalArgumentException is raised if no data found so wrapped in try catch block and a message is returned instead
			return "No Such Id in Category Table";
		}
	}

	// Function to fetch the next available id as per the requirement (digit sum = 1)
	@Override
	public long fetchNextNumber(long id) {
		// The algorithm used here works in O(1) time complexity
		// Plus extra time needed to find the next available id (depends on the time to find that id)
		
		long oldValue = id;
		long nextValue;
		
		// calculating the remainder
		long remainder = (oldValue % 9);
		
		
		if(remainder == 0) {
			// if id multiple of 9 then next number i.e id+1 will have digit sum == 1
			// e.g. 18 % 9 = 0, so 19 = 1 + 9 = 10, 1 + 0 = 1, so satisfies the condition
			nextValue = oldValue + 1;
		}
		else {
			// if not multiple of 9, find the next nearest multiple of 9, then add one to it for that following code is written
			nextValue = oldValue + (9 - (remainder - 1));
			// e.g. 13 % 9 = 4,  so next number that satisfies the condition : 13 + (9-(4-1)) = 13 + 6 = 19, 1 + 9 = 10, 1 + 0 = 1 
		}
		
		// Now we need to find the next available id that satifies didgitsum = 1
		while(this.categoryDao.existsById(nextValue)) {
			//if a number's digit sum = 1, then every (number + 9*m)'s digit sum is 1 (m >= 1)
			nextValue += 9;
		}
		
		//returns the next available id present in database
		return nextValue;
	}

}
