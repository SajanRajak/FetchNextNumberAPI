package com.assignment.fetchNumber.FetchNextNumberAPI;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.assignment.fetchNumber.FetchNextNumberAPI.dao.CategoryDao;
import com.assignment.fetchNumber.FetchNextNumberAPI.entities.Category;

@SpringBootTest
class FetchNextNumberApiApplicationTests {

	@Autowired
	CategoryDao categoryDao;
	
	//Test to check if properly categories are getting added or not
	@Test
	public void testAddCategory() {
		Category category = new Category();
		category.setId(0L);
		category.setName("JUnit Test Category");
		
		categoryDao.save(category);
		assertNotNull(categoryDao.findById(0L).get());
	}
	
	//test to check deleting of a particular category is working 
	@Test
	public void testDeleteCategory() {
		Category category = new Category();
		category.setId(1L);
		category.setName("JUnit Test Category");
		
		categoryDao.delete(category);
		try {
			category = categoryDao.findById(1L).get();
			assert(false);
		}catch(Exception e) {
			assert(true);
		}
	}
	
	//Testing the fetchNextNumber
	@Test
	public void testFetchNextNumber() {
		Category category = new Category();
		long id = 1L;
		category.setId(id);
		category.setName("JUnit Test Category");
		
		categoryDao.save(category);
		
		long oldValue = category.getId();
		long nextValue;
		
		// described in CategoryServiceImpl
		int remainder = (int) (oldValue % 9);
		if(remainder == 0)
			nextValue = oldValue + 1;
		else
			nextValue = oldValue + (9 - (remainder - 1));
		
		while(this.categoryDao.existsById(nextValue)) {
			nextValue += 9;
		}
		long newValue = nextValue;
		
		this.categoryDao.deleteById(oldValue);
		category.setId(newValue);
		this.categoryDao.save(category);
		assertNotNull(categoryDao.findById(newValue).get());
	}
	
}
