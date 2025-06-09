package demo.service;

import demo.dto.*;
import demo.errorhandling.ClientNotFoundException;
import demo.errorhandling.RealEstateNotFoundException;
import demo.errorhandling.RealEstateOwnerNotFoundException;
import jakarta.persistence.criteria.CriteriaBuilder;

import java.util.List;
import java.util.Set;

public interface RealEstateService {
    RealEstateDTO saveRealEstate(RealEstateDTO realEstateDTO) throws ClientNotFoundException;
    Iterable<RealEstateDTO> findAllRealEstates();
    RealEstateDTO findRealEstateById(Integer realEstateId) throws RealEstateNotFoundException;
    List<ReviewDTO> getReviewsForRealEstate(Integer realEstateId) throws RealEstateNotFoundException;
    Set<RealEstateDTO> getClientsFavoriteRealEstates(Integer clientId) throws ClientNotFoundException;
    int getNumberOfInterestedClients(Integer realEstateId) throws RealEstateNotFoundException;
    DisplayClientDTO getRealEstateOwner(Integer realEstateId) throws RealEstateOwnerNotFoundException, RealEstateNotFoundException;
    Set<RentalDTO> getRentals(Integer realEstateId) throws RealEstateNotFoundException;
    RealEstateDTO updateRealEstate (Integer id, RealEstateDTO realEstateDTO) throws RealEstateNotFoundException;
}
