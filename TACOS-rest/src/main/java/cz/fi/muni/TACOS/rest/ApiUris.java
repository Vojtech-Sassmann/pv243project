package cz.fi.muni.TACOS.rest;

/**
 * @author Pavel Vyskocil <vyskocilpavel@muni.cz>
 */
public class ApiUris {
	public static final String REST_ROOT = "/api/v1";
	public static final String URI_AUTH = "/auth";
	public static final String URI_ORDERS = URI_AUTH + "/orders";
	public static final String URI_USERS = URI_AUTH + "/users";
	public static final String URI_ATTRIBUTES = URI_AUTH + "/attributes";
	public static final String URI_ATTRIBUTE_CATEGORIES = URI_AUTH + "/attributeCategories";
	public static final String URI_TEMPLATES = URI_AUTH + "/templates";
	public static final String URI_CREATED_PRODUCTS = URI_AUTH + "/createdProducts";
	public static final String URI_PRODUCTS = URI_AUTH + "/products";
	public static final String URI_PRODUCT_CATEGORIES = URI_AUTH + "/productCategories";
}
