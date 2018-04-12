package cz.fi.muni.TACOS.service.Impl;

import cz.fi.muni.TACOS.persistence.dao.CreatedProductDao;
import cz.fi.muni.TACOS.persistence.entity.Attribute;
import cz.fi.muni.TACOS.persistence.entity.CreatedProduct;
import cz.fi.muni.TACOS.service.AbstractEntityService;
import cz.fi.muni.TACOS.service.CreatedProductService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

/**
 * EntityService layer for CreatedProduct Entity
 *
 * @author Pavel Vyskocil <vyskocilpavel@muni.cz>
 */
@ApplicationScoped
public class CreatedProductServiceImpl extends AbstractEntityService<CreatedProduct> implements CreatedProductService {

	private final CreatedProductDao createdProductDao;

	@Inject
	public CreatedProductServiceImpl(CreatedProductDao createdProductDao) {
		super(createdProductDao);

		this.createdProductDao = createdProductDao;
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
