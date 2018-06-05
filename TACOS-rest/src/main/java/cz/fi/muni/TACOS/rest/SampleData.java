package cz.fi.muni.TACOS.rest;

import cz.fi.muni.TACOS.dto.AttributeCategoryCreateDTO;
import cz.fi.muni.TACOS.dto.AttributeCreateDTO;
import cz.fi.muni.TACOS.dto.CreatedProductCreateDTO;
import cz.fi.muni.TACOS.dto.ProductCategoryCreateDTO;
import cz.fi.muni.TACOS.dto.ProductCreateDTO;
import cz.fi.muni.TACOS.dto.TemplateCreateDTO;
import cz.fi.muni.TACOS.dto.UserCreateDTO;
import cz.fi.muni.TACOS.dto.UserDTO;
import cz.fi.muni.TACOS.exceptions.InvalidRelationEntityIdException;
import cz.fi.muni.TACOS.facade.AttributeCategoryFacade;
import cz.fi.muni.TACOS.facade.AttributeFacade;
import cz.fi.muni.TACOS.facade.CreatedProductFacade;
import cz.fi.muni.TACOS.facade.OrderFacade;
import cz.fi.muni.TACOS.facade.ProductCategoryFacade;
import cz.fi.muni.TACOS.facade.ProductFacade;
import cz.fi.muni.TACOS.facade.TemplateFacade;
import cz.fi.muni.TACOS.facade.UserFacade;
import cz.fi.muni.TACOS.enums.ProductAttributeStatus;
import cz.fi.muni.TACOS.enums.UserRole;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

/**
 * @author Pavel Vyskocil <vyskocilpavel@muni.cz>
 */
@ApplicationScoped
public class SampleData {

	@Inject
	private OrderFacade orderFacade;

	@Inject
	private UserFacade userFacade;

	@Inject
	private CreatedProductFacade createdProductFacade;

	@Inject
	private TemplateFacade templateFacade;

	@Inject
	private ProductCategoryFacade productCategoryFacade;

	@Inject
	private ProductFacade productFacade;

	@Inject
	private AttributeFacade attributeFacade;

	@Inject
	private AttributeCategoryFacade attributeCategoryFacade;

	private Long userId1;
	private Long userId2;
	private Long userId3;
	private Long userId4;

	private Long cpId1;
	private Long cpId2;
	private Long cpId3;
	private Long cpId4;
	private Long templateWomenTshirtId;
	private Long templateMenTshirtId;
	private Long templateTrousersId;

	private Long productTshirtId1;
	private Long productTrousers2;
	private Long productShortsId3;
	private Long productTrainersId4;

	private Long pcTshirtsId;
	private Long pcTrousersId;
	private Long pcAccessoriesId;
	private Long pcShoesId;
	private Long pcClothesId;

	private Long attributeXSSizeId;
	private Long attributeSSizeId;
	private Long attributeMSizeId;
	private Long attributeLSizeId;
	private Long attributeGreenColorId;

	private Long acTshirtSizeId;
	private Long acTshirtColorId;
	private Long acTshirtMaterialId;
	private Long acId3;
	private Long attributeBlackColorId;
	private Long attributeWhiteColorId;
	private Long attributeCottonColorId;
	private Long attributePolyesterColorId;
	private Long attributeGrassColorId;
	private Long templateFemaleTrainers;
	private Long templatePaintedShorts;
	private Long acShoeSize;
	private Long acShoeColor;
	private Long acShortsColor;
	private Long acShortsMaterial;
	private Long acTrousersColor;
	private Long acTrousersMaterial;

	public void loadUserSampleData() {
		UserCreateDTO user = new UserCreateDTO();

		user.setName("Regular");
		user.setSurname("User");
		user.setPassword("password");
		user.setEmail("submitter@email.com");
		user.setRole(UserRole.SUBMITTER);
		userId1 = userFacade.create(user);

		user.setName("Name");
		user.setSurname("Surname");
		user.setPassword("password");
		user.setEmail("email@email.com");
		user.setRole(UserRole.SUPERADMIN);
		userId2 = userFacade.create(user);

		user.setName("Admin");
		user.setSurname("User");
		user.setPassword("password");
		user.setEmail("admin@email.com");
		user.setRole(UserRole.SUPERADMIN);
		userId3 = userFacade.create(user);

		user.setName("Practitioner");
		user.setSurname("User");
		user.setPassword("password");
		user.setEmail("practitioner@email.com");
		user.setRole(UserRole.PRACTITIONER);
		userId4 = userFacade.create(user);
	}

	public void loadCreatedProductSampleData() throws InvalidRelationEntityIdException {
		UserCreateDTO user = new UserCreateDTO();

		user.setName("RegularDDDD");
		user.setSurname("UserDDD");
		user.setPassword("passwordDDD");
		user.setEmail("v.sassmann@gmail.com");
		user.setRole(UserRole.SUBMITTER);
		userFacade.create(user);

		UserDTO userDto = userFacade.findByEmail(user.getEmail());
		CreatedProductCreateDTO createdProduct = new CreatedProductCreateDTO();

		createdProduct.setAttributeIds(new HashSet<>(Arrays.asList(attributeXSSizeId, attributeGreenColorId)));
		createdProduct.setCount(10L);
		createdProduct.setDescription("createdProduct1desc");
		createdProduct.setProductId(productTshirtId1);
		cpId1 = createdProductFacade.create(createdProduct, userDto.getId());

		createdProduct.setAttributeIds(new HashSet<>(Arrays.asList(attributeSSizeId, attributeGreenColorId)));
		createdProduct.setCount(5L);
		createdProduct.setDescription("createdProduct 2 Description");
		createdProduct.setProductId(productTshirtId1);
		cpId2 = createdProductFacade.create(createdProduct, userDto.getId());

		createdProduct.setAttributeIds(new HashSet<>(Arrays.asList(attributeMSizeId, attributeGreenColorId)));
		createdProduct.setCount(3L);
		createdProduct.setDescription("createdProduct3desc");
		createdProduct.setProductId(productTshirtId1);
		cpId3 = createdProductFacade.create(createdProduct, userDto.getId());

		createdProduct.setAttributeIds(Collections.singleton(attributeGreenColorId));
		createdProduct.setCount(8L);
		createdProduct.setDescription("createdProduct4desc");
		createdProduct.setProductId(productTshirtId1);
		cpId4 = createdProductFacade.create(createdProduct, userDto.getId());

		Long basketId = userFacade.getBasket(userDto.getId()).getId();
		orderFacade.submitOrder(basketId);

		createdProduct.setAttributeIds(new HashSet<>(Arrays.asList(attributeSSizeId, attributeGreenColorId)));
		createdProduct.setCount(2L);
		createdProduct.setDescription("createdProduct 88888 Description");
		createdProduct.setProductId(productTshirtId1);
		cpId2 = createdProductFacade.create(createdProduct, userDto.getId());

		createdProduct.setAttributeIds(new HashSet<>(Arrays.asList(attributeMSizeId, attributeGreenColorId)));
		createdProduct.setCount(8L);
		createdProduct.setDescription("createdProduct4545 3desc");
		createdProduct.setProductId(productTshirtId1);
		cpId3 = createdProductFacade.create(createdProduct, userDto.getId());

		basketId = userFacade.getBasket(userDto.getId()).getId();
		orderFacade.submitOrder(basketId);
		orderFacade.processOrder(basketId);

		createdProduct.setAttributeIds(new HashSet<>(Arrays.asList(attributeSSizeId, attributeGreenColorId)));
		createdProduct.setCount(2L);
		createdProduct.setDescription("createdProduct 88888 Description");
		createdProduct.setProductId(productTshirtId1);
		cpId2 = createdProductFacade.create(createdProduct, userDto.getId());

		createdProduct.setAttributeIds(new HashSet<>(Arrays.asList(attributeMSizeId, attributeGreenColorId)));
		createdProduct.setCount(8L);
		createdProduct.setDescription("createdProduct4545 3desc");
		createdProduct.setProductId(productTshirtId1);
		cpId3 = createdProductFacade.create(createdProduct, userDto.getId());

		basketId = userFacade.getBasket(userDto.getId()).getId();
		orderFacade.submitOrder(basketId);
		orderFacade.finishOrder(basketId);
	}

	public void loadTemplateSampleData() throws InvalidRelationEntityIdException {
		TemplateCreateDTO template = new TemplateCreateDTO();

		template.setName("Women t-shirt");
		template.setProductIds(Collections.singleton(productTshirtId1));
		templateWomenTshirtId = templateFacade.create(template);

		template.setProductIds(Collections.singleton(productTshirtId1));
		template.setName("Men t-shirt");
		templateMenTshirtId = templateFacade.create(template);

		template.setProductIds(Collections.singleton(productTrousers2));
		template.setName("Trousers");
		templateTrousersId = templateFacade.create(template);

		template.setProductIds(Collections.singleton(productTrainersId4));
		template.setName("Female trainers");
		templateFemaleTrainers = templateFacade.create(template);

		template.setProductIds(Collections.singleton(productShortsId3));
		template.setName("Painted shorts");
		templatePaintedShorts = templateFacade.create(template);
	}

	public void loadProductSampleData() throws InvalidRelationEntityIdException {
		ProductCreateDTO product = new ProductCreateDTO();

		product.setDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras eleifend mi velit, nec dapibus elit maximus at. Aenean venenatis imperdiet auctor. Vestibulum id lectus enim. Aenean nec arcu enim. Sed at imperdiet lectus. Duis consequat mollis lacus sit amet hendrerit. Fusce gravida semper imperdiet. Sed elit lorem, auctor euismod viverra ut, vulputate id tellus. ");
		product.setName("T-shirt");
		product.setProductCategoryIds(Collections.singleton(pcTshirtsId));
		productTshirtId1 = productFacade.create(product);

		product.setDescription("Donec est ligula, dignissim vitae mattis nec, facilisis et sem. Aliquam eleifend massa vel nisi placerat, vitae lobortis diam auctor. In finibus consectetur dapibus. Ut ut ex cursus, luctus metus sed, elementum leo. Mauris sollicitudin condimentum nisl ac venenatis. Donec laoreet, quam a elementum bibendum, velit sapien efficitur libero, in euismod neque est quis libero.");
		product.setName("Trousers");
		product.setProductCategoryIds(Collections.singleton(pcTrousersId));
		productTrousers2 = productFacade.create(product);

		product.setDescription("Donec est ligula, dignissim vitae mattis nec, facilisis et sem. Aliquam eleifend massa vel nisi placerat, vitae lobortis diam auctor. In finibus consectetur dapibus. Ut ut ex cursus, luctus metus sed, elementum leo. Mauris sollicitudin condimentum nisl ac venenatis. Donec laoreet, quam a elementum bibendum, velit sapien efficitur libero, in euismod neque est quis libero.");
		product.setName("Necklace");
		product.setProductCategoryIds(Collections.singleton(pcAccessoriesId));
		productTrousers2 = productFacade.create(product);

		product.setDescription("Phasellus eu ullamcorper nulla. Nulla augue lectus, dictum ut est sed, ultrices auctor ipsum. Mauris semper eu nibh a porttitor. Donec magna leo, gravida sed tincidunt vitae, sodales volutpat metus. Aliquam tempus efficitur augue et fringilla. Aliquam euismod ultrices nunc. Proin erat ligula, vulputate ac odio ac, rhoncus tempor nunc. Mauris nibh eros, tincidunt eget fringilla eget, semper ut ex.");
		product.setName("Shorts");
		product.setProductCategoryIds(Collections.singleton(pcTrousersId));
		productShortsId3 = productFacade.create(product);

		product.setDescription("Morbi eu mi turpis. Cras auctor risus a ligula feugiat bibendum. Duis eu pulvinar nisi, consectetur efficitur urna. Etiam ac euismod diam, eget consequat quam. Donec luctus, dui id blandit interdum, turpis elit sodales nibh, a porta elit elit non augue. Morbi iaculis dictum diam in lobortis. Sed id sem massa. Donec quis aliquam est, vel maximus eros. Pellentesque a maximus lacus.");
		product.setName("Trainers");
		product.setProductCategoryIds(Collections.singleton(pcShoesId));
		productTrainersId4 = productFacade.create(product);
	}


	public void loadProductCategorySampleData() throws InvalidRelationEntityIdException {
		ProductCategoryCreateDTO category = new ProductCategoryCreateDTO();

		category.setName("Clothes");
		pcClothesId = productCategoryFacade.create(category);

		category.setName("Accessories");
		pcAccessoriesId = productCategoryFacade.create(category);

		category.setName("Shoes");
		pcShoesId = productCategoryFacade.create(category);

		category.setName("T shirts");
		category.setParentCategoryId(pcClothesId);
		pcTshirtsId = productCategoryFacade.create(category);

		category.setName("Trousers");
		category.setParentCategoryId(pcClothesId);
		pcTrousersId = productCategoryFacade.create(category);
	}

	public void loadAttributeSampleData() throws InvalidRelationEntityIdException {
		AttributeCreateDTO attribute = new AttributeCreateDTO();

		attribute.setAttributeCategoryId(acTshirtSizeId);
		attribute.setName("XS");
		attribute.setDescription("Random description(to be honest, not so random)");
		attribute.setPrice(BigDecimal.TEN);
		attribute.setStatus(ProductAttributeStatus.IN_STOCK);
		attributeXSSizeId = attributeFacade.create(attribute);

		attribute.setAttributeCategoryId(acTshirtSizeId);
		attribute.setName("S");
		attribute.setDescription("Random description(to be honest, not so random)");
		attribute.setPrice(BigDecimal.TEN);
		attribute.setStatus(ProductAttributeStatus.IN_STOCK);
		attributeSSizeId = attributeFacade.create(attribute);

		attribute.setAttributeCategoryId(acTshirtSizeId);
		attribute.setName("M");
		attribute.setDescription("Random description(to be honest, not so random)");
		attribute.setPrice(BigDecimal.TEN);
		attribute.setStatus(ProductAttributeStatus.IN_STOCK);
		attributeMSizeId = attributeFacade.create(attribute);

		attribute.setAttributeCategoryId(acTshirtSizeId);
		attribute.setName("L");
		attribute.setDescription("Random description(to be honest, not so random)");
		attribute.setPrice(BigDecimal.TEN);
		attribute.setStatus(ProductAttributeStatus.IN_STOCK);
		attributeLSizeId = attributeFacade.create(attribute);

		attribute.setAttributeCategoryId(acTshirtColorId);
		attribute.setName("Black");
		attribute.setDescription("Random description(to be honest, not so random)");
		attribute.setPrice(BigDecimal.TEN);
		attribute.setStatus(ProductAttributeStatus.IN_STOCK);
		attributeBlackColorId = attributeFacade.create(attribute);

		attribute.setAttributeCategoryId(acTshirtColorId);
		attribute.setName("Green");
		attribute.setDescription("Random description(to be honest, not so random)");
		attribute.setPrice(BigDecimal.TEN);
		attribute.setStatus(ProductAttributeStatus.IN_STOCK);
		attributeGreenColorId = attributeFacade.create(attribute);

		attribute.setAttributeCategoryId(acTshirtColorId);
		attribute.setName("White");
		attribute.setDescription("Random description(to be honest, not so random)");
		attribute.setPrice(BigDecimal.TEN);
		attribute.setStatus(ProductAttributeStatus.IN_STOCK);
		attributeWhiteColorId = attributeFacade.create(attribute);

		attribute.setAttributeCategoryId(acTshirtMaterialId);
		attribute.setName("Cotton");
		attribute.setDescription("Random description(to be honest, not so random)");
		attribute.setPrice(BigDecimal.valueOf(28L));
		attribute.setStatus(ProductAttributeStatus.IN_STOCK);
		attributeCottonColorId = attributeFacade.create(attribute);

		attribute.setAttributeCategoryId(acTshirtMaterialId);
		attribute.setName("Polyester");
		attribute.setDescription("Random description(to be honest, not so random)");
		attribute.setPrice(BigDecimal.valueOf(22L));
		attribute.setStatus(ProductAttributeStatus.IN_STOCK);
		attributePolyesterColorId = attributeFacade.create(attribute);

		attribute.setAttributeCategoryId(acTshirtMaterialId);
		attribute.setName("Grass");
		attribute.setDescription("Random description(to be honest, not so random)");
		attribute.setPrice(BigDecimal.valueOf(15L));
		attribute.setStatus(ProductAttributeStatus.IN_STOCK);
		attributeGrassColorId = attributeFacade.create(attribute);

		attribute.setAttributeCategoryId(acShoeColor);
		attribute.setName("Green");
		attribute.setDescription("Random description(to be honest, not so random)");
		attribute.setPrice(BigDecimal.valueOf(18L));
		attribute.setStatus(ProductAttributeStatus.IN_STOCK);
		attributeGrassColorId = attributeFacade.create(attribute);

		attribute.setAttributeCategoryId(acShoeColor);
		attribute.setName("Red");
		attribute.setDescription("Random description(to be honest, not so random)");
		attribute.setPrice(BigDecimal.valueOf(19L));
		attribute.setStatus(ProductAttributeStatus.IN_STOCK);
		attributeGrassColorId = attributeFacade.create(attribute);

		attribute.setAttributeCategoryId(acShoeColor);
		attribute.setName("Black");
		attribute.setDescription("Random description(to be honest, not so random)");
		attribute.setPrice(BigDecimal.valueOf(11L));
		attribute.setStatus(ProductAttributeStatus.IN_STOCK);
		attributeGrassColorId = attributeFacade.create(attribute);

		attribute.setAttributeCategoryId(acShoeColor);
		attribute.setName("Brown");
		attribute.setDescription("Random description(to be honest, not so random)");
		attribute.setPrice(BigDecimal.valueOf(12L));
		attribute.setStatus(ProductAttributeStatus.IN_STOCK);
		attributeGrassColorId = attributeFacade.create(attribute);

		attribute.setAttributeCategoryId(acShoeSize);
		attribute.setName("38");
		attribute.setDescription("Random description(to be honest, not so random)");
		attribute.setPrice(BigDecimal.valueOf(12L));
		attribute.setStatus(ProductAttributeStatus.IN_STOCK);
		attributeGrassColorId = attributeFacade.create(attribute);

		attribute.setAttributeCategoryId(acShoeSize);
		attribute.setName("39");
		attribute.setDescription("Random description(to be honest, not so random)");
		attribute.setPrice(BigDecimal.valueOf(12L));
		attribute.setStatus(ProductAttributeStatus.IN_STOCK);
		attributeGrassColorId = attributeFacade.create(attribute);

		attribute.setAttributeCategoryId(acShoeSize);
		attribute.setName("40");
		attribute.setDescription("Random description(to be honest, not so random)");
		attribute.setPrice(BigDecimal.valueOf(12L));
		attribute.setStatus(ProductAttributeStatus.IN_STOCK);
		attributeGrassColorId = attributeFacade.create(attribute);

		attribute.setAttributeCategoryId(acShoeSize);
		attribute.setName("41");
		attribute.setDescription("Random description(to be honest, not so random)");
		attribute.setPrice(BigDecimal.valueOf(12L));
		attribute.setStatus(ProductAttributeStatus.IN_STOCK);
		attributeGrassColorId = attributeFacade.create(attribute);

		attribute.setAttributeCategoryId(acShoeSize);
		attribute.setName("42");
		attribute.setDescription("Random description(to be honest, not so random)");
		attribute.setPrice(BigDecimal.valueOf(12L));
		attribute.setStatus(ProductAttributeStatus.IN_STOCK);
		attributeGrassColorId = attributeFacade.create(attribute);

		attribute.setAttributeCategoryId(acShoeSize);
		attribute.setName("43");
		attribute.setDescription("Random description(to be honest, not so random)");
		attribute.setPrice(BigDecimal.valueOf(12L));
		attribute.setStatus(ProductAttributeStatus.IN_STOCK);
		attributeGrassColorId = attributeFacade.create(attribute);

		attribute.setAttributeCategoryId(acShortsColor);
		attribute.setName("Red");
		attribute.setDescription("Random description(to be honest, not so random)");
		attribute.setPrice(BigDecimal.valueOf(11L));
		attribute.setStatus(ProductAttributeStatus.IN_STOCK);
		attributeGrassColorId = attributeFacade.create(attribute);

		attribute.setAttributeCategoryId(acShortsColor);
		attribute.setName("Brown");
		attribute.setDescription("Random description(to be honest, not so random)");
		attribute.setPrice(BigDecimal.valueOf(10L));
		attribute.setStatus(ProductAttributeStatus.IN_STOCK);
		attributeGrassColorId = attributeFacade.create(attribute);

		attribute.setAttributeCategoryId(acShortsColor);
		attribute.setName("Yellow");
		attribute.setDescription("Random description(to be honest, not so random)");
		attribute.setPrice(BigDecimal.valueOf(8L));
		attribute.setStatus(ProductAttributeStatus.IN_STOCK);
		attributeGrassColorId = attributeFacade.create(attribute);

		attribute.setAttributeCategoryId(acShortsMaterial);
		attribute.setName("Wool");
		attribute.setDescription("Random description(to be honest, not so random)");
		attribute.setPrice(BigDecimal.valueOf(20L));
		attribute.setStatus(ProductAttributeStatus.IN_STOCK);
		attributeGrassColorId = attributeFacade.create(attribute);

		attribute.setAttributeCategoryId(acShortsMaterial);
		attribute.setName("Polyester");
		attribute.setDescription("Random description(to be honest, not so random)");
		attribute.setPrice(BigDecimal.valueOf(30L));
		attribute.setStatus(ProductAttributeStatus.IN_STOCK);
		attributeGrassColorId = attributeFacade.create(attribute);

		attribute.setAttributeCategoryId(acShortsMaterial);
		attribute.setName("Cotton");
		attribute.setDescription("Random description(to be honest, not so random)");
		attribute.setPrice(BigDecimal.valueOf(40L));
		attribute.setStatus(ProductAttributeStatus.IN_STOCK);
		attributeGrassColorId = attributeFacade.create(attribute);

		attribute.setAttributeCategoryId(acTrousersColor);
		attribute.setName("Red");
		attribute.setDescription("Random description(to be honest, not so random)");
		attribute.setPrice(BigDecimal.valueOf(11L));
		attribute.setStatus(ProductAttributeStatus.IN_STOCK);
		attributeGrassColorId = attributeFacade.create(attribute);

		attribute.setAttributeCategoryId(acTrousersColor);
		attribute.setName("Brown");
		attribute.setDescription("Random description(to be honest, not so random)");
		attribute.setPrice(BigDecimal.valueOf(10L));
		attribute.setStatus(ProductAttributeStatus.IN_STOCK);
		attributeGrassColorId = attributeFacade.create(attribute);

		attribute.setAttributeCategoryId(acTrousersColor);
		attribute.setName("Yellow");
		attribute.setDescription("Random description(to be honest, not so random)");
		attribute.setPrice(BigDecimal.valueOf(5L));
		attribute.setStatus(ProductAttributeStatus.IN_STOCK);
		attributeGrassColorId = attributeFacade.create(attribute);

		attribute.setAttributeCategoryId(acTrousersMaterial);
		attribute.setName("Wool");
		attribute.setDescription("Random description(to be honest, not so random)");
		attribute.setPrice(BigDecimal.valueOf(20L));
		attribute.setStatus(ProductAttributeStatus.IN_STOCK);
		attributeGrassColorId = attributeFacade.create(attribute);

		attribute.setAttributeCategoryId(acTrousersMaterial);
		attribute.setName("Polyester");
		attribute.setDescription("Random description(to be honest, not so random)");
		attribute.setPrice(BigDecimal.valueOf(30L));
		attribute.setStatus(ProductAttributeStatus.IN_STOCK);
		attributeGrassColorId = attributeFacade.create(attribute);

		attribute.setAttributeCategoryId(acTrousersMaterial);
		attribute.setName("Cotton");
		attribute.setDescription("Random description(to be honest, not so random)");
		attribute.setPrice(BigDecimal.valueOf(40L));
		attribute.setStatus(ProductAttributeStatus.IN_STOCK);
		attributeGrassColorId = attributeFacade.create(attribute);
	}

	public void loadAttributeCategorySampleData() throws InvalidRelationEntityIdException {
		AttributeCategoryCreateDTO category = new AttributeCategoryCreateDTO();

		category.setTemplateIds(new HashSet<>(Arrays.asList(templateWomenTshirtId, templateMenTshirtId)));
		category.setName("t-shirt size");
		acTshirtSizeId = attributeCategoryFacade.create(category);

		category.setTemplateIds(new HashSet<>(Arrays.asList(templateWomenTshirtId, templateMenTshirtId)));
		category.setName("t-shirt color");
		acTshirtColorId = attributeCategoryFacade.create(category);

		category.setTemplateIds(Collections.singleton(templateWomenTshirtId));
		category.setName("t-shirt material");
		acTshirtMaterialId = attributeCategoryFacade.create(category);

		category.setTemplateIds(Collections.singleton(templateFemaleTrainers));
		category.setName("Shoe size");
		acShoeSize = attributeCategoryFacade.create(category);

		category.setTemplateIds(Collections.singleton(templateFemaleTrainers));
		category.setName("Color");
		acShoeColor = attributeCategoryFacade.create(category);

		category.setTemplateIds(Collections.singleton(templatePaintedShorts));
		category.setName("Shorts material");
		acShortsMaterial = attributeCategoryFacade.create(category);

		category.setTemplateIds(Collections.singleton(templatePaintedShorts));
		category.setName("Shorts color");
		acShortsColor = attributeCategoryFacade.create(category);

		category.setTemplateIds(Collections.singleton(templateTrousersId));
		category.setName("Trousers material");
		acTrousersMaterial = attributeCategoryFacade.create(category);

		category.setTemplateIds(Collections.singleton(templateTrousersId));
		category.setName("Trousers color");
		acTrousersColor = attributeCategoryFacade.create(category);
	}

	public void loadSampleData() throws InvalidRelationEntityIdException {
		loadProductCategorySampleData();
		loadProductSampleData();
		loadTemplateSampleData();
		loadAttributeCategorySampleData();
		loadAttributeSampleData();
		loadUserSampleData();
		loadCreatedProductSampleData();
	}
}
