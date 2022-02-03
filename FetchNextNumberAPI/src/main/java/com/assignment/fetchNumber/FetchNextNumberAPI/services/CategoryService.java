package com.assignment.fetchNumber.FetchNextNumberAPI.services;

import java.util.List;

import com.assignment.fetchNumber.FetchNextNumberAPI.entities.Category;

public interface CategoryService {
	public List<Category> getCategories();	// to get all the categories present in category table
	public Category insertUpdateCategory(Category category); // to update or insert data in category table
	public Category getCategory(long categoryId);	// to get a particular category using category id
	public String deleteAllCategory();	// to delete all the categories from category table
	public String deleteCategory(long categoryId);	// to delete a particular category from category table
	public long fetchNextNumber(long id);	// to fetch the next available id as per the requirement (digit sum = 1)
}
