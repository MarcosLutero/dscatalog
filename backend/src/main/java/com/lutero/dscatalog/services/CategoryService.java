package com.lutero.dscatalog.services;


import java.util.Optional;


import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lutero.dscatalog.dto.CategoryDTO;
import com.lutero.dscatalog.entities.Category;
import com.lutero.dscatalog.repositories.CategoryRepository;
import com.lutero.dscatalog.services.exceptions.DatabaseException;
import com.lutero.dscatalog.services.exceptions.ResourceNotFoundException;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository repository;

	@Transactional(readOnly = true)
	public Page<CategoryDTO> findAllPaged(PageRequest pageRequest) {
		Page<Category> list = repository.findAll(pageRequest);
		return list.map(x -> new CategoryDTO(x));
	}
	
	
	/*public List<CategoryDTO> findAll() {
		List<Category> list = repository.findAll();
		return list.stream().map(x -> new CategoryDTO(x)).collect(Collectors.toList());
*/
		/*
		 * List <CategoryDTO> listDto = new ArrayList<>(); for (Category cat: list){
		 * listDto.add(new Category(cat)) } return listDto;
		 *
		 

	}
	*/

	@Transactional(readOnly = true)
	public CategoryDTO findById(Long id) {
		Optional<Category> obj = repository.findById(id); // pego o objeto do optional
		Category entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not Found")); // agora sim e a
																									// entidade
		return new CategoryDTO(entity); // agora ela vai retornar no dto
	}

	@Transactional
	public CategoryDTO insert(CategoryDTO dto) {
		Category entity = new Category();
		entity.setName(dto.getName());
		entity = repository.save(entity);
		return new CategoryDTO(entity);

	}

	@Transactional
	public CategoryDTO update(Long id, CategoryDTO dto) {
		try {
			Category entity = repository.getOne(id);
			entity.setName(dto.getName());
			entity = repository.save(entity);
			return new CategoryDTO(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id Not Found" + id);
		}
	}

	public void delete(Long id) {
		try {
		repository.deleteById(id);
		}catch (EmptyResultDataAccessException e){
			throw new ResourceNotFoundException("Id Not Foud" + id);
		}catch(DataIntegrityViolationException e) {
			throw new DatabaseException("Integrety violation");
			
			
		}
		
	}

}
