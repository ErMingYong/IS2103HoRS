/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

/**
 *
 * @author mingy
 */
@Entity
public class ExceptionReportEntity implements Serializable {

    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long exceptionReportId;
    
    @OneToMany(mappedBy = "exceptionReportEntity", fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private List<ReservationEntity> firstTypeExceptionReservations;
    
    @OneToMany(mappedBy = "exceptionReportEntity", fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private List<ReservationEntity> secondTypeExceptionReservations;

    public Long getExceptionReportId() {
        return exceptionReportId;
    }

    public void setExceptionReportId(Long exceptionReportId) {
        this.exceptionReportId = exceptionReportId;
    }
    
    /**
     * @return the firstTypeExceptionReservations
     */
    public List<ReservationEntity> getFirstTypeExceptionReservations() {
        return firstTypeExceptionReservations;
    }

    /**
     * @param firstTypeExceptionReservations the firstTypeExceptionReservations to set
     */
    public void setFirstTypeExceptionReservations(List<ReservationEntity> firstTypeExceptionReservations) {
        this.firstTypeExceptionReservations = firstTypeExceptionReservations;
    }

    /**
     * @return the secondTypeExceptionReservations
     */
    public List<ReservationEntity> getSecondTypeExceptionReservations() {
        return secondTypeExceptionReservations;
    }

    /**
     * @param secondTypeExceptionReservations the secondTypeExceptionReservations to set
     */
    public void setSecondTypeExceptionReservations(List<ReservationEntity> secondTypeExceptionReservations) {
        this.secondTypeExceptionReservations = secondTypeExceptionReservations;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (exceptionReportId != null ? exceptionReportId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the exceptionReportId fields are not set
        if (!(object instanceof ExceptionReportEntity)) {
            return false;
        }
        ExceptionReportEntity other = (ExceptionReportEntity) object;
        if ((this.exceptionReportId == null && other.exceptionReportId != null) || (this.exceptionReportId != null && !this.exceptionReportId.equals(other.exceptionReportId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.ExceptionReportEntity[ id=" + exceptionReportId + " ]";
    }
    
}
