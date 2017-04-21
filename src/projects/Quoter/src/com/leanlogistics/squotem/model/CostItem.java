package com.leanlogistics.squotem.model;

public class CostItem {
    
    private Long id;
    private ProductCategory productCategory;
    private CostItemCategory itemCategory;
    private String description;
    private String descriptionDetail;
	private Integer sortOrder;
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public ProductCategory getProductCategory() {
        return productCategory;
    }
    public void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
    }
    public CostItemCategory getItemCategory() {
        return itemCategory;
    }
    public void setItemCategory(CostItemCategory itemCategory) {
        this.itemCategory = itemCategory;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Integer getSortOrder() {
        return sortOrder;
    }
    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }
    public String getDescriptionDetail() {
		return descriptionDetail;
	}
	public void setDescriptionDetail(String descriptionDetail) {
		this.descriptionDetail = descriptionDetail;
	}
}
