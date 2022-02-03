package com.assignment.fetchNumber.FetchNextNumberAPI.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.assignment.fetchNumber.FetchNextNumberAPI.entities.Category;

// Our DAO extends JpaRepo, so all available jpa repo funtions can be used for easy DB access
public interface CategoryDao extends JpaRepository<Category, Long>{

}
