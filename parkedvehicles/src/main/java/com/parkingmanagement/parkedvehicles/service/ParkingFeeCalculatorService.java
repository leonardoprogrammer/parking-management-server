package com.parkingmanagement.parkedvehicles.service;

import com.parkingmanagement.parkedvehicles.model.entity.ParkingSettings;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
public class ParkingFeeCalculatorService {

    public BigDecimal calculatorFee(ParkingSettings settings, LocalDateTime checkin, LocalDateTime checkout) {
        if (checkin == null || checkout == null || checkout.isBefore(checkin)) {
            throw new IllegalArgumentException("Datas inválidas para o cálculo.");
        }

        // Tempo estacionado
        Duration totalTime = Duration.between(checkin, checkout);

        // Se não cobrar desde o check-in, verifica se ficou o tempo mínimo para cobrar
        if (!settings.isChargeFromCheckIn()) {
            if (settings.getMinimumTimeToCharge() != null) {
                Duration minimumTime = convertToDuration(settings.getMinimumTimeToCharge());
                if (totalTime.compareTo(minimumTime) < 0) {
                    return BigDecimal.ZERO; // Tempo menor que o mínimo, não cobra nada
                }
            }
        }

        return calculateCharge(totalTime, settings);
    }

    private BigDecimal calculateCharge(Duration totalTime, ParkingSettings settings) {
        Duration period = convertToDuration(settings.getPeriod());
        BigDecimal valuePerPeriod = settings.getValuePerPeriod();

        // Número de períodos a cobrar (arredondado para cima)
        long periodsToCharge = (long) Math.ceil((double) totalTime.toMinutes() / period.toMinutes());

        // Cálculo final
        return valuePerPeriod.multiply(BigDecimal.valueOf(periodsToCharge)).setScale(2, RoundingMode.HALF_UP);
    }

    private Duration convertToDuration(LocalTime time) {
        return Duration.ofHours(time.getHour()).plusMinutes(time.getMinute());
    }
}
