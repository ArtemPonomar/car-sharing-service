package com.example.carsharingservice.service.impl;

import com.example.carsharingservice.model.Rental;
import com.example.carsharingservice.repository.RentalRepository;
import com.example.carsharingservice.service.MessagingService;
import com.example.carsharingservice.service.RentalService;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RentalServiceImpl implements RentalService {

    private final RentalRepository rentalRepository;
    private final MessagingService messagingService;

    @Override
    public Rental add(Rental rental) {
        return rentalRepository.save(rental);
    }

    @Override
    public Rental getById(Long id) {
        return rentalRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Can't find car by id: " + id));
    }

    @Override
    public Rental returnRental(Long id) {
        Rental rental = getById(id);
        rental.setActualReturnDate(LocalDateTime.now());
        return rentalRepository.save(rental);
    }

    @Override
    public List<Rental> getByUserIdAndActive(Long userId, Boolean isActive) {
        return rentalRepository.findAll().stream()
                .filter(x -> x.getId().equals(userId)
                        && isActive == (x.getActualReturnDate() == null))
                .collect(Collectors.toList());
    }

    @Scheduled(cron = "0 0 */1 * * *")
    public void checkOverdueRentals() {
        rentalRepository.findOverdueRentals().forEach(r ->
                messagingService.sendMessageToUser(
                        ("Your vehicle rent for %s %s is overdue since %s.\n "
                                + "See rental with id:%d for details.")
                                .formatted(r.getCar().getBrand(),
                                        r.getCar().getModel(),
                                        r.getReturnDate().format(
                                                DateTimeFormatter.ofPattern("dd-MM-yyyy")),
                                        r.getId()), r.getUser())
        );
    }
}
