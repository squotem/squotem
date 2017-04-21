package com.leanlogistics.squotem.service.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.leanlogistics.squotem.co.MatrixStatusCO;
import com.leanlogistics.squotem.co.MetricGroupCO;
import com.leanlogistics.squotem.model.Matrix;
import com.leanlogistics.squotem.model.MatrixCostAdjustment;
import com.leanlogistics.squotem.model.MatrixCostItem;
import com.leanlogistics.squotem.model.MatrixMetric;
import com.leanlogistics.squotem.model.MatrixRiskAnalysis;
import com.leanlogistics.squotem.model.ProductCategory;

public class MatrixDao {
    
    /** Get all customers */
    @SuppressWarnings("unchecked")
    public List<Matrix> getMatrices(Session ses, boolean forAdmin) {
        Query q = null;
        if (forAdmin) {
            // Only leave out disabled matrices
            q = ses.createQuery("from Matrix m where m.status <> :disabledStatus "
            		+ "order by m.createDate desc "
            		)
                    .setParameter("disabledStatus", MatrixStatusCO.DISABLED);                        
        }
        else {
            // Only include matrices available for all users
            q = ses.createQuery("from Matrix m where m.status = :allUsersStatus "
            		+ "order by m.createDate desc "
            		)
                    .setParameter("allUsersStatus", MatrixStatusCO.ALL_USERS);                                    
        }
        return (List<Matrix>) q.list();        
    }
    
    public Matrix getMatrix(Session ses, long id) {
        // Only include matrices available for all users
        Query q = ses.createQuery("from Matrix m where m.id = :id")
                .setParameter("id", id);                                    

        Matrix m = (Matrix) q.uniqueResult();
        return m;
    }
    
    public Matrix getCurrentMatrix(Session ses) {
        Query q =  ses.createQuery("from Matrix m where m.current = :current")
                .setParameter("current", true);
        Matrix m = (Matrix) q.uniqueResult();
        return m;
    }
    
    @SuppressWarnings("unchecked")
    public List<MatrixCostItem> getMatrixCostItems(Session ses, Matrix m, ProductCategory pc) {
        Query q = ses.createQuery("select mci " +
                                  "from MatrixCostItem mci " +
                                  "     left join fetch mci.costItem as ci " +
                                  "     left join fetch ci.itemCategory as cic " +
                                  "     left outer join fetch mci.costModel as cm " +
                                  "where mci.matrix = :m and ci.productCategory = :pc " +
                                  "order by mci.costItem.sortOrder, mci.costItem.description, mci.feeCategory "                                  
                                  )
                   .setParameter("m", m).setParameter("pc", pc);               
        return (List<MatrixCostItem>) q.list();
    }
    
    @SuppressWarnings("unchecked")
    public List<MatrixMetric> getMatrixMetrics(Session ses, Matrix m) {
        Query q = ses.createQuery("select mm " +
                "from MatrixMetric mm " +
                "     left join fetch mm.metric as m " +
                "     left join fetch m.category as mc " +
                "where mm.matrix = :matrix " +
                "order by m.category.sortOrder, m.category.description, m.sortOrder ")
                .setParameter("matrix", m);
        return (List<MatrixMetric>) q.list();        
    }
    
    @SuppressWarnings("unchecked")
    public List<MatrixMetric> getMatrixMetrics(Session ses, Matrix m, MetricGroupCO group) {
        Query q = ses.createQuery("select mm " +
                "from MatrixMetric mm " +
                "     left join fetch mm.metric as m " +
                "     left join fetch m.category as mc " +
                "where mm.matrix = :matrix and mc.metricGroup = :group " +
                "order by m.category.sortOrder, m.category.description, m.sortOrder ")
                .setParameter("matrix", m).setParameter("group", group);
        return (List<MatrixMetric>) q.list();                
    }

    @SuppressWarnings("unchecked")
    public List<MatrixCostAdjustment> getMatrixCostAdjustments(Session ses, Matrix m, ProductCategory pc) {
        Query q = ses.createQuery("select mca " +
                                    "from MatrixCostAdjustment mca " +
                                    "     left join fetch mca.costAdjustment as ca " +
                                    "where mca.matrix = :m and ca.productCategory = :pc " +
                                    "order by mca.costAdjustment.description,  mca.costAdjustment.feeCategory "                                  
                                    )
                .setParameter("m", m).setParameter("pc", pc);
        return (List<MatrixCostAdjustment>) q.list();
    }
    
    @SuppressWarnings("unchecked")
    public List<MatrixRiskAnalysis> getMatrixRiskAnalysisItems(Session ses, Matrix m) {
        Query q = ses.createQuery("select mra " +
                "from MatrixRiskAnalysis mra " +
                "     left join fetch mra.riskAnalysis as ra " +
                "     left join fetch ra.riskCategory as rc " +
                "where mra.matrix = :matrix and ra.active = :active " +
                "order by ra.riskCategory.sortOrder, ra.riskCategory.description, ra.sortOrder ")
                .setParameter("matrix", m).setParameter("active", Boolean.TRUE);
        return (List<MatrixRiskAnalysis>) q.list();        
    }
}
