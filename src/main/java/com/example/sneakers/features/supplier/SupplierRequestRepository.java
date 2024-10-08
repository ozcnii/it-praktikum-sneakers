package com.example.sneakers.features.supplier;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.sneakers.features.supplier.entities.RequestStatus;
import com.example.sneakers.features.supplier.entities.SupplierRequest;

@Repository
public interface SupplierRequestRepository extends JpaRepository<SupplierRequest, Long> {
  SupplierRequest findFirstByUserId(Long userId);

  List<SupplierRequest> findByStatus(RequestStatus status);
}