package com.lutero.dscatalog.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lutero.dscatalog.dto.CategoryDTO;
import com.lutero.dscatalog.entities.Category;
import com.lutero.dscatalog.repositories.CategoryRepository;
import com.lutero.dscatalog.services.exceptions.EntityNotFoundException;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository repository;
	
	@Transactional(readOnly = true)
	public List<CategoryDTO> findAll(){	
		List<Category> list = repository.findAll();
		return list.stream().
				map(x -> new CategoryDTO(x)).collect(Collectors.toList());
		
		
		
		/*
		 * List <CategoryDTO> listDto = new ArrayList<>();
		 * for (Category cat: list){
		 * 		listDto.add(new Category(cat))
		 * } 
		 * return listDto;
		 *
		 * */
		
	}

	@Transactional(readOnly = true)
	public CategoryDTO findById(Long id) {
		Optional<Category> obj = repository.findById(id); // pego o objeto do optional
		Category entity = obj.orElseThrow(() -> new EntityNotFoundException("Entity not Found"));	// agora sim e a entidade
		return new CategoryDTO(entity); // agora ela vai retornar no dto
	}
	
}
