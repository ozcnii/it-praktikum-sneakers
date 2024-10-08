package com.example.sneakers.features.supplier;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.sneakers.features.supplier.entities.RequestStatus;
import com.example.sneakers.features.supplier.entities.SupplierRequest;
import com.example.sneakers.features.user.Role;
import com.example.sneakers.features.user.UserAccount;
import com.example.sneakers.features.user.UserRepository;

@Service
public class SupplierRequestService {

  @Autowired
  private SupplierRequestRepository supplierRequestRepository;

  @Autowired
  private UserRepository userRepository;

  public void saveRequest(Long userId) {
    UserAccount user = userRepository.findById(userId)
        .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

    var existingRequest = supplierRequestRepository
        .findFirstByUserId(user.getId());

    if (existingRequest != null) {
      existingRequest.setStatus(RequestStatus.PENDING);
      supplierRequestRepository.save(existingRequest);
      return;
    }

    SupplierRequest request = new SupplierRequest();
    request.setUser(user);
    request.setStatus(RequestStatus.PENDING);
    supplierRequestRepository.save(request);
  }

  public SupplierRequest getRequest(Long userId) {

    UserAccount user = userRepository.findById(userId)
        .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

    SupplierRequest request = supplierRequestRepository.findFirstByUserId(user.getId());

    return request;
  }

  public List<SupplierRequest> getPendingRequests() {
    return supplierRequestRepository.findByStatus(RequestStatus.PENDING);
  }

  public void approveRequest(Long requestId) {
    var request = supplierRequestRepository.findById(requestId);

    if (request.isEmpty()) {
      throw new Error("Заявка не найдена");
    }

    var instance = request.get();
    instance.setStatus(RequestStatus.APPROVED);

    instance.getUser().setRole(Role.ROLE_SUPPLIER);

    supplierRequestRepository.save(instance);
  }

  public void rejectRequest(Long requestId) {
    var request = supplierRequestRepository.findById(requestId);

    if (request.isEmpty()) {
      throw new Error("Заявка не найдена");
    }

    var instance = request.get();
    instance.setStatus(RequestStatus.REJECTED);
    supplierRequestRepository.save(instance);
  }
}