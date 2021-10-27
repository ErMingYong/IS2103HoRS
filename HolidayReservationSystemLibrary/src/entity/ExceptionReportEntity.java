/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import util.enumeration.ExceptionReportTypeEnum;

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
    @NotNull
    @Column(nullable = false)
    private LocalDateTime generationDate;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull
    private ExceptionReportTypeEnum exceptionReportTypeEnum;
    
    @OneToOne(mappedBy = "exceptionReportEntity", fetch = FetchType.LAZY, optional = false)
    private ReservationEntity reservationEntity;
    
    public ExceptionReportEntity() {

    }

    public Long getExceptionReportId() {
        return exceptionReportId;
    }

    public void setExceptionReportId(Long exceptionReportId) {
        this.exceptionReportId = exceptionReportId;
    }

    /**
     * @return the generationDate
     */
    public LocalDateTime getGenerationDate() {
        return generationDate;
    }

    /**
     * @param generationDate the generationDate to set
     */
    public void setGenerationDate(LocalDateTime generationDate) {
        this.generationDate = generationDate;
    }

    /**
     * @return the exceptionReportTypeEnum
     */
    public ExceptionReportTypeEnum getExceptionReportTypeEnum() {
        return exceptionReportTypeEnum;
    }

    /**
     * @param exceptionReportTypeEnum the exceptionReportTypeEnum to set
     */
    public void setExceptionReportTypeEnum(ExceptionReportTypeEnum exceptionReportTypeEnum) {
        this.exceptionReportTypeEnum = exceptionReportTypeEnum;
    }
    
        /**
     * @return the reservationEntity
     */
    public ReservationEntity getReservationEntity() {
        return reservationEntity;
    }

    /**
     * @param reservationEntity the reservationEntity to set
     */
    public void setReservationEntity(ReservationEntity reservationEntity) {
        this.reservationEntity = reservationEntity;
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
