package com.leanlogistics.squotem.service.manager;

import java.util.List;

import com.leanlogistics.squotem.model.BusinessSector;
import com.leanlogistics.squotem.model.Country;
import com.leanlogistics.squotem.model.ProductCategory;
import com.leanlogistics.squotem.model.State;
import com.leanlogistics.squotem.service.dao.BusinessSectorDao;
import com.leanlogistics.squotem.service.dao.CountryDao;
import com.leanlogistics.squotem.service.dao.ProductCategoryDao;
import com.leanlogistics.squotem.service.dao.StateDao;

/**
 * Manager class for all Catalogs (Master Data)
 *
 */
public class CatalogsManager extends BaseManager {
    private ProductCategoryDao productCategoryDao;
    private CountryDao countryCodeDao;
    private StateDao countryCodeStateDao;
    private BusinessSectorDao businessSectorDao;
    

    public CatalogsManager() {
        productCategoryDao = new ProductCategoryDao();
        countryCodeDao = new CountryDao();
        countryCodeStateDao = new StateDao();  
        businessSectorDao = new BusinessSectorDao();
    }
    
    public List<ProductCategory> getProductCategories() {
        return productCategoryDao.getProductCategories(hibernateSession);
    }        
    
    public List<Country> getCountries() {
        return countryCodeDao.getCountries(hibernateSession);
    }
    
    public List<State> getCountryStates(Country country){
        return countryCodeStateDao.getCountryStates(hibernateSession, country.getCountryCode());
    }

    public List<State> getStates(){
        return countryCodeStateDao.getStates(hibernateSession);
    }
    
    public List<BusinessSector> getBusinessSectors() {
        return businessSectorDao.betBusinessSectors(hibernateSession);
    }

}
