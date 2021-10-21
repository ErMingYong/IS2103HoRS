/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author Koh Wen Jie
 */
@Entity
public class TestEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long testEntitiyId;

    public Long getTestEntitiyId() {
        return testEntitiyId;
    }

    public void setTestEntitiyId(Long testEntitiyId) {
        this.testEntitiyId = testEntitiyId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (testEntitiyId != null ? testEntitiyId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the testEntitiyId fields are not set
        if (!(object instanceof TestEntity)) {
            return false;
        }
        TestEntity other = (TestEntity) object;
        if ((this.testEntitiyId == null && other.testEntitiyId != null) || (this.testEntitiyId != null && !this.testEntitiyId.equals(other.testEntitiyId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.TestEntity[ id=" + testEntitiyId + " ]";
    }
    
}
