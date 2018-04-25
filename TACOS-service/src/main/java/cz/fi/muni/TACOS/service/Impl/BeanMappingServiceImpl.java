package cz.fi.muni.TACOS.service.Impl;

import cz.fi.muni.TACOS.dto.AttributeDTO;
import cz.fi.muni.TACOS.dto.OrderDTO;
import cz.fi.muni.TACOS.persistence.entity.Attribute;
import cz.fi.muni.TACOS.persistence.entity.Order;
import cz.fi.muni.TACOS.service.BeanMappingService;
import cz.fi.muni.TACOS.service.annotations.TacosMapper;
import org.dozer.Mapper;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Pavel Vyskocil <vyskocilpavel@muni.cz>
 */
@ApplicationScoped
public class BeanMappingServiceImpl implements BeanMappingService {

	private Mapper dozer;

	@Inject
	public BeanMappingServiceImpl(@TacosMapper Mapper dozer) {
		this.dozer = dozer;
	}

	@Override
	public <T> List<T> mapTo(Collection<?> objects, Class<T> mapToClass) {
		List<T> mappedCollection = new ArrayList<>();
		for (Object object : objects) {
			T mappedObject = dozer.map(object, mapToClass);
			mappedCollection.add(mappedObject);
		}
		return mappedCollection;
	}

	@Override
	public <T> T mapTo(Object o, Class<T> mapToClass) {
		if (o == null) {
			return null;
		}
		return dozer.map(o, mapToClass);
	}

	@Override
	public Mapper getMapper() {
		return dozer;
	}
}
