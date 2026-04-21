package com.myinnovate.demo.controller;

import com.myinnovate.demo.entity.Orders;
import com.myinnovate.demo.repo.OrdersRepo;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/orders")
public class OrdersController {

    private static final Path ORDER_UPLOAD_ROOT = Paths.get("uploads", "orders");

    @Autowired
    private OrdersRepo ordersRepo;

    @GetMapping("")
    public List<Orders> getOrders() {
        return ordersRepo.findByInvalidOrderByNumber(false);
    }

    @PostMapping("/delete/{number}")
    public void deleteOrders(@PathVariable int number) {
        Orders order = ordersRepo.findByNumber(number);
        if (order != null) {
            order.setInvalid(true);
            ordersRepo.save(order);
        }
    }

    @PostMapping("/add")
    public Orders addOrders(@RequestBody Orders order) {
        return ordersRepo.save(order);
    }

    @GetMapping("/{number}")
    public Orders findOrder(@PathVariable int number) {
        return ordersRepo.findByNumber(number);
    }

    @PostMapping("/{number}/upload")
    public ResponseEntity<Map<String, String>> uploadFile(
        @PathVariable int number,
        @RequestParam("file") MultipartFile file
    ) throws IOException {
        Orders order = ordersRepo.findByNumber(number);
        if (order == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "order not found"));
        }

        if (file == null || file.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("message", "file is empty"));
        }

        String originalName = file.getOriginalFilename() == null ? "upload.bin" : file.getOriginalFilename();
        String safeFileName = Path.of(originalName).getFileName().toString();
        Path orderDir = ORDER_UPLOAD_ROOT.resolve(String.valueOf(number)).normalize();
        Files.createDirectories(orderDir);

        // Keep only one latest file per order: delete old files before saving the new one.
        try (var stream = Files.list(orderDir)) {
            stream
                .filter(Files::isRegularFile)
                .sorted(Comparator.reverseOrder())
                .forEach(path -> {
                    try {
                        Files.deleteIfExists(path);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                });
        } catch (RuntimeException ex) {
            if (ex.getCause() instanceof IOException ioEx) {
                throw ioEx;
            }
            throw ex;
        }

        //String savedFileName = System.currentTimeMillis() + "_" + safeFileName;
        Path target = orderDir.resolve(safeFileName).normalize();
        if (!target.startsWith(orderDir)) {
            return ResponseEntity.badRequest().body(Map.of("message", "invalid file name"));
        }

        Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);

        Map<String, String> result = new HashMap<>();
        result.put("fileName", safeFileName);
        result.put("downloadUrl", "/orders/" + number + "/download/" + safeFileName);
        result.put("message", "upload success");
        Orders updatedOrder = ordersRepo.findByNumber(number);
        updatedOrder.setAttachment(safeFileName);
        ordersRepo.save(updatedOrder);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/{number}/download/{fileName}")
    public ResponseEntity<Resource> downloadFile(
        @PathVariable int number,
        @PathVariable String fileName
    ) throws IOException {
        Orders order = ordersRepo.findByNumber(number);
        if (order == null) {
            return ResponseEntity.notFound().build();
        }

        String safeFileName = Path.of(fileName).getFileName().toString();
        Path orderDir = ORDER_UPLOAD_ROOT.resolve(String.valueOf(number)).normalize();
        Path file = orderDir.resolve(safeFileName).normalize();
        if (!file.startsWith(orderDir) || !Files.exists(file) || Files.isDirectory(file)) {
            return ResponseEntity.notFound().build();
        }

        Resource resource = toResource(file);
        String contentType = Files.probeContentType(file);
        if (contentType == null) {
            contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
        }

        return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType(contentType))
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + safeFileName + "\"")
            .body(resource);
    }

    private Resource toResource(Path path) throws MalformedURLException {
        return new UrlResource(path.toUri());
    }
}
