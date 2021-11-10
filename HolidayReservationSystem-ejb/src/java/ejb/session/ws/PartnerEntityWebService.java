/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.ws;

import ejb.session.stateless.AllocationReportSessionBeanLocal;
import ejb.session.stateless.PartnerEntitySessionBeanLocal;
import ejb.session.stateless.ReservationEntitySessionBeanLocal;
import entity.PartnerEntity;
import entity.ReservationEntity;
import entity.RoomTypeEntity;
import entity.UserEntity;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.util.Pair;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBContext;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import util.exception.CreateNewReservationException;
import util.exception.InputDataValidationException;
import util.exception.InsufficientRoomsAvailableException;
import util.exception.InvalidLoginCredentialException;
import util.exception.PartnerNotFoundException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author mingy
 */
@WebService(serviceName = "PartnerEntityWebService")
@Stateless()
public class PartnerEntityWebService {

    @EJB
    private AllocationReportSessionBeanLocal allocationReportSessionBeanLocal;

    @EJB
    private ReservationEntitySessionBeanLocal reservationEntitySessionBeanLocal;

    @PersistenceContext(unitName = "HolidayReservationSystem-ejbPU")
    private EntityManager em;

    @EJB
    private PartnerEntitySessionBeanLocal partnerEntitySessionBeanLocal;

    @Resource
    private EJBContext eJBContext;

//    public class MyHash {
//
//        private HashMap<RoomTypeEntity, HashMap<String, BigDecimal>> map;
//
//        public MyHash(HashMap<RoomTypeEntity, HashMap<String, BigDecimal>> map) {
//            this.map = map;
//        }
//
//        /**
//         * @return the map
//         */
//        public HashMap<RoomTypeEntity, HashMap<String, BigDecimal>> getMap() {
//            return map;
//        }
//
//        /**
//         * @param map the map to set
//         */
//        public void setMap(HashMap<RoomTypeEntity, HashMap<String, BigDecimal>> map) {
//            this.map = map;
//        }
//
//    }
    @WebMethod(operationName = "partnerLogin")
    public PartnerEntity partnerLogin(@WebParam(name = "partnerUsername") String partnerUsername,
            @WebParam(name = "partnerPassword") String partnerPassword) throws PartnerNotFoundException, InvalidLoginCredentialException {

        PartnerEntity partner = null;
        partner = partnerEntitySessionBeanLocal.retrievePartnerByUsername(partnerUsername);

        if (partner == null) {
            throw new PartnerNotFoundException("Partner with username " + partnerUsername + " does not exist");
        } else if (!partner.getPassword().equals(partnerPassword)) {
            throw new InvalidLoginCredentialException("Wrong password");
        }

        return partner;
    }

    @WebMethod(operationName = "retrieveAllPartnerReservations")
    public List<ReservationEntity> retrieveAllPartnerReservations(@WebParam(name = "partnerId") Long partnerId) throws PartnerNotFoundException {

        try {
            PartnerEntity partner = partnerEntitySessionBeanLocal.retrievePartnerByPartnerId(partnerId);
            List<ReservationEntity> partnerReservations = partner.getReservationEntities();

            return partnerReservations;
        } catch (PartnerNotFoundException ex) {
            throw new PartnerNotFoundException("Partner does not exist");
        }
    }

    @WebMethod(operationName = "retrieveRoomTypeAvailabilities")
    public List<String> retrieveRoomTypeAvailabilities(@WebParam(name = "reservationStartDateDay") Integer reservationStartDateDay, @WebParam(name = "reservationStartDateMonth") Integer reservationStartDateMonth, @WebParam(name = "reservationStartDateYear") Integer reservationStartDateYear,
            @WebParam(name = "reservationEndDateDay") Integer reservationEndDateDay, @WebParam(name = "reservationEndDateMonth") Integer reservationEndDateMonth, @WebParam(name = "reservationEndDateYear") Integer reservationEndDateYear,
            @WebParam(name = "numRooms") Integer numRooms, @WebParam(name = "isWalkIn") Boolean isWalkIn) throws InsufficientRoomsAvailableException {

        try {
            LocalDateTime reservationStartDate = LocalDateTime.of(reservationStartDateYear, reservationStartDateMonth, reservationStartDateDay, 0, 0);
            LocalDateTime reservationEndDate = LocalDateTime.of(reservationEndDateYear, reservationEndDateMonth, reservationEndDateDay, 0, 0);
            HashMap<RoomTypeEntity, HashMap<String, BigDecimal>> map = reservationEntitySessionBeanLocal.retrieveRoomTypeAvailabilities(reservationStartDate, reservationEndDate, numRooms, false);
            List<String> listOfResults = new ArrayList<>();
            List<RoomTypeEntity> listOfKeys = new ArrayList<>(map.keySet());
            listOfKeys.sort((RoomTypeEntity x, RoomTypeEntity y) -> {
                return x.getRanking() - y.getRanking();
            });
            for (RoomTypeEntity roomType : listOfKeys) {
                String msg = "";
                msg += roomType.getRoomTypeName() + ",";
                msg += map.get(roomType).get("numRoomType") + ",";
                msg += map.get(roomType).get("bestPrice") + ",";
                List<String> listOfStrings = new ArrayList<>(map.get(roomType).keySet());
                for (String word : listOfStrings) {
                    if (word.equals("numRoomType") || word.equals("bestPrice")) {
                        continue;
                    } else {
                        msg += word + ",";
                    }
                }
                listOfResults.add(msg);
            }
            System.out.println("HEREHERERE");
            System.out.println(listOfResults.toString());
            //format "roomTypeName, numRoomType, bestPrice, roomRateName, roomRateName ....
            return listOfResults;
        } catch (InsufficientRoomsAvailableException ex) {
            throw new InsufficientRoomsAvailableException();
        }
    }

//    public void () throws CreateNewReservationException, UnknownPersistenceException, InputDataValidationException {
//        try {
//            reservationEntitySessionBeanLocal.createNewReservations(listOfNewReservationPairs);
//            List<ReservationEntity> partnerReservations = partner.getReservationEntities();
//
//            return partnerReservations;
//        } catch (PartnerNotFoundException ex) {
//            throw new PartnerNotFoundException("Partner does not exist");
//        } catch (CreateNewReservationException ex) {
//            Logger.getLogger(PartnerEntityWebService.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (UnknownPersistenceException ex) {
//            Logger.getLogger(PartnerEntityWebService.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (InputDataValidationException ex) {
//            Logger.getLogger(PartnerEntityWebService.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    @WebMethod(operationName = "createNewReservationsForPartner")
    public void createNewReservationsForPartner(@WebParam(name = "listOfNewReservation") List<ReservationEntity> listOfNewReservation, @WebParam(name = "listOfNewReservationsStringOfRoomRateNames") List<String> listOfNewReservationsStringOfRoomRateNames, @WebParam(name = "startDay") Integer startDay, @WebParam(name = "startMonth") Integer startMonth, @WebParam(name = "startYear") Integer startYear, @WebParam(name = "endDay") Integer endDay, @WebParam(name = "endMonth") Integer endMonth, @WebParam(name = "endYear") Integer endYear, @WebParam(name = "partner") PartnerEntity partner) throws CreateNewReservationException, UnknownPersistenceException, InputDataValidationException {
        List<List<String>> listOfNewReservationsListOfRoomRateNames = new ArrayList<>();
        for (String roomRateNames : listOfNewReservationsStringOfRoomRateNames) {
            String[] arr = roomRateNames.split(",");
            List<String> roomRateNameList = Arrays.asList(arr);
            System.out.println(roomRateNameList);
            listOfNewReservationsListOfRoomRateNames.add(roomRateNameList);
        }
        System.out.println("THERE");
        for (List<String> list : listOfNewReservationsListOfRoomRateNames) {
            System.out.println(list.toString());
        }
        LocalDateTime startDate = LocalDateTime.of(startYear, startMonth, startDay, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(endYear, endMonth, endDay, 0, 0);
        List<Pair<ReservationEntity, List<String>>> list = new ArrayList<>();
        for (int i = 0; i < listOfNewReservation.size(); i++) {
            ReservationEntity res = listOfNewReservation.get(i);
            res.setReservationStartDate(startDate);
            res.setReservationEndDate(endDate);
            Pair pair = new Pair(res, listOfNewReservationsListOfRoomRateNames.get(i));
            list.add(pair);
        }
        reservationEntitySessionBeanLocal.createNewReservationsForPartner(list, partner);
        ReservationEntity res = em.find(ReservationEntity.class, list.get(0).getKey().getReservationEntityId());
        LocalDateTime currDateTime = LocalDateTime.now();
        LocalDateTime dateTime2Am = LocalDateTime.of(LocalDate.now(), LocalTime.of(2,0));
        if (currDateTime.isAfter(dateTime2Am) && res.getReservationStartDate().isEqual(currDateTime)) {
            allocationReportSessionBeanLocal.allocationReportCheckTimerManual();
        }

    }
    //createNewReservationsForPartner(List<Pair<ReservationEntity, List<String>>> list, PartnerEntity partner)

    @WebMethod(operationName = "allocationReportCheckTimerManual")
    public void allocationReportCheckTimerManual() throws UnknownPersistenceException {

        allocationReportSessionBeanLocal.allocationReportCheckTimerManual();

    }
}
