package com.example.sneakers.features.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.sneakers.features.supplier.SupplierRequestService;

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
}
