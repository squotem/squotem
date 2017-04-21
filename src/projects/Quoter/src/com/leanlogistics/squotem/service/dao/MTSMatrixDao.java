package com.leanlogistics.squotem.service.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.leanlogistics.squotem.model.MTSMatrix;
import com.leanlogistics.squotem.model.MTSMatrixRoleCost;
import com.leanlogistics.squotem.model.MTSMatrixRoleRelation;
import com.leanlogistics.squotem.model.MTSMatrixScopeQuestion;

public class MTSMatrixDao {
    
    public MTSMatrix getMTSMatrix(Session ses, long id) {
        return (MTSMatrix) ses.get(MTSMatrix.class, id);
    }
    
    
    @SuppressWarnings("unchecked")
    public List<MTSMatrixScopeQuestion> getMTSMatrixScopeQuestions(Session ses, Long mtsMatrixId) {
        Query q = ses.createQuery("select msq " +
                "from MTSMatrixScopeQuestion msq " +
                "     left join fetch msq.category as sqcat " +
                "     left join msq.mtsMatrix as mtsMat " +
                "where mtsMat.id = :mtsMatId " +
                "order by sqcat.sortOrder, msq.sortOrder "                                  
                ).setParameter("mtsMatId", mtsMatrixId);               
        return (List<MTSMatrixScopeQuestion>) q.list();        
    }
    
    @SuppressWarnings("unchecked")    
    public List<MTSMatrixRoleCost> getMTSMatrixRoleCosts(Session ses, Long mtsMatrixId) {
        Query q = ses.createQuery("select mrc " +
                "from MTSMatrixRoleCost mrc " +
                "     left join fetch mrc.mtsRole as role " +
                "     left join mrc.mtsMatrix as mtsMat " +
                "where mtsMat.id = :mtsMatId " +
                "order by role.id, role.description "                                  
                ).setParameter("mtsMatId", mtsMatrixId);               
        return (List<MTSMatrixRoleCost>) q.list();        
        
    }
    
    @SuppressWarnings("unchecked")    
    public List<MTSMatrixRoleRelation> getMTSMatrixRoleRelations(Session ses, Long mtsMatrixId) {
        Query q = ses.createQuery("select mrr " +
                "from MTSMatrixRoleRelation mrr " +
                "     left join fetch mrr.mtsRole as role " +
                "     left join mrr.mtsMatrix as mtsMat " +
                "where mtsMat.id = :mtsMatId " +
                "order by role.id, mrr.rangeStart "                                  
                ).setParameter("mtsMatId", mtsMatrixId);               
        return (List<MTSMatrixRoleRelation>) q.list();        
        
    }    
    
}
