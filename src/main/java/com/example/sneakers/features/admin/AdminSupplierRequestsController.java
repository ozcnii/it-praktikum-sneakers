package com.example.sneakers.features.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import com.example.sneakers.features.supplier.SupplierRequestService;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/admin/supplier-requests")
public class AdminSupplierRequestsController {

  @Autowired
  private SupplierRequestService supplierRequestService;

  @GetMapping
  public String index(Model model) {
    var requests = supplierRequestService.getPendingRequests();

    model.addAttribute("requests", requests);

    return "admin/supplier-requests/index";
  }

  @PostMapping("/approve/{requestId}")
  public String approveRequest(@PathVariable Long requestId) {
    supplierRequestService.approveRequest(requestId);
    return "redirect:/admin/supplier-requests";
  }

  @PostMapping("/reject/{requestId}")
  public String rejectRequest(@PathVariable Long requestId) {
    supplierRequestService.rejectRequest(requestId);
    return "redirect:/admin/supplier-requests";
  }
}
