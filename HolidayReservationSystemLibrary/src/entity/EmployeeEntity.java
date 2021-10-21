/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Entity;
import util.enumeration.EmployeeAccessRightEnum;

/**
 *
 * @author Koh Wen Jie
 */
@Entity

public class EmployeeEntity extends UserEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private EmployeeAccessRightEnum employeeAccessRightEnum;
    
    /**
     * @return the employeeAccessRightEnum
     */
    public EmployeeAccessRightEnum getEmployeeAccessRightEnum() {
        return employeeAccessRightEnum;
    }

    /**
     * @param employeeAccessRightEnum the employeeAccessRightEnum to set
     */
    public void setEmployeeAccessRightEnum(EmployeeAccessRightEnum employeeAccessRightEnum) {
        this.employeeAccessRightEnum = employeeAccessRightEnum;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (this.getUserEntityId() != null ? this.getUserEntityId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the employeeEntitiyId fields are not set
        if (!(object instanceof EmployeeEntity)) {
            return false;
        }
        EmployeeEntity other = (EmployeeEntity) object;
        if ((this.getUserEntityId() == null && other.getUserEntityId() != null) || (this.getUserEntityId() != null && !this.getUserEntityId().equals(other.getUserEntityId()))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.EmployeeEntity[ id=" + this.getUserEntityId() + " ]";
    }
    
}
