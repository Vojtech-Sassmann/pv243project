package cz.fi.muni.TACOS.service.Impl;

import cz.fi.muni.TACOS.persistence.dao.CreatedProductDao;
import cz.fi.muni.TACOS.persistence.entity.Attribute;
import cz.fi.muni.TACOS.persistence.entity.CreatedProduct;
import cz.fi.muni.TACOS.service.CreatedProductService;

import javax.inject.Inject;
import java.util.List;

/**
 * Service layer for CreatedProduct Entity
 *
 * @author Pavel Vyskocil <vyskocilpavel@muni.cz>
 */
public class CreatedProductServiceImpl implements CreatedProductService {

	private final CreatedProductDao createdProductDao;

	@Inject
	public CreatedProductServiceImpl(CreatedProductDao createdProductDao) {
		this.createdProductDao = createdProductDao;
	}

	@Override
	public void create(CreatedProduct entity) {
		createdProductDao.create(entity);
	}

	@Override
	public void delete(CreatedProduct entity) {
		createdProductDao.delete(entity);
	}

	@Override
	public CreatedProduct findById(Long id) {
		return createdProductDao.findById(id);
	}

	@Override
	public List<CreatedProduct> getAll() {
		return createdProductDao.getAll();
	}

	@Override
	public void addAttribute(CreatedProduct createdProduct, Attribute attribute) {
		if (createdProduct.getAttributes().contains(attribute)) {
			throw new IllegalArgumentException("Attribute is already in list of product attributes");
		}
		createdProduct.addAttribute(attribute);
	}

	@Override
	public void removeAttribute(CreatedProduct createdProduct, Attribute attribute) {
		if (!createdProduct.getAttributes().contains(attribute)) {
			throw new IllegalArgumentException("Attribute is not in list of product attributes");
		}
		createdProduct.removeAttribute(attribute);
	}

}
