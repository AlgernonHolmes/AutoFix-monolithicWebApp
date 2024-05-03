package com.example.autofix.autofix.services;

import com.example.autofix.autofix.entities.ReceiptEntity;
import com.example.autofix.autofix.entities.RepairEntity;
import com.example.autofix.autofix.entities.VehicleEntity;
import com.example.autofix.autofix.repositories.ReceiptRepository;
import com.example.autofix.autofix.repositories.RepairRepository;
import com.example.autofix.autofix.repositories.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class ReceiptService {

    /* Repository layer methods */

    @Autowired
    ReceiptRepository receiptRepository;

    @Autowired
    VehicleRepository vehicleRepository;

    @Autowired
    RepairRepository repairRepository;


    /* GET OPERATIONS */

    /*--------------------------------------------------------------------------------------------------------
     * getReceipts: method to find all receipts in the DB;
     *
     *
     * @return - a list with all receipts;
     --------------------------------------------------------------------------------------------------------*/
    /* FUNCTION USED FOR R1 */
    public List<ReceiptEntity> getReceipts() {
        return (List<ReceiptEntity>) receiptRepository.findAll();
    }

    /* POST OPERATIONS */

    /*--------------------------------------------------------------------------------------------------------
     * createReceipt: method to create a new receipt entity and save it in the database;
     *
     * @param plate - the vehicle plate associated with the receipt;
     * @return - the newly created receipt entity;
     --------------------------------------------------------------------------------------------------------*/
    public ReceiptEntity createReceipt(String plate){
        ReceiptEntity receipt = new ReceiptEntity();
        receipt.setVehiclePlate(plate);
        double totalPayment = receiptTotalPayment(plate);
        receipt.setTotalPayment(totalPayment);
        applySurAndDis(receipt);
        /* we stablish actual date and time */
        LocalDate currentDate = LocalDate.now();
        receipt.setReceiptDate(currentDate);
        LocalTime currentTime = LocalTime.now();
        receipt.setReceiptTime(currentTime);
        /* we save the receipt */
        receiptRepository.save(receipt);
        return receipt;
    }



    /* Business layer methods */

    /* RECEIPT TOTAL AMOUNT */

    /*--------------------------------------------------------------------------------------------------------
     * receiptTotalPayment: method to calculate the total payment for all repairs associated with a specific vehicle plate;
     *
     * @param plate - the vehicle plate for which the total payment is to be calculated;
     * @return - the total payment amount for all repairs associated with the specified vehicle plate;
     --------------------------------------------------------------------------------------------------------*/
    public double receiptTotalPayment(String plate){
        List<RepairEntity> repairs = repairRepository.findAllByVehiclePlate(plate);
        double totalPayment = 0.0;
        for(RepairEntity repair : repairs){
            totalPayment = totalPayment + repair.getTotalCost();
        }
        return totalPayment;
    }




    /* SURCHARGES */

    /*--------------------------------------------------------------------------------------------------------
     * delaySurcharge: method to calculate antiquity surcharge;
     *
     * @param price - total price;
     * @param plate - vehicle plate;
     * @return - the amount to be surcharged;
     --------------------------------------------------------------------------------------------------------*/
    public double delaySurcharge(double price, String plate) {
        List<RepairEntity> repairs = repairRepository.findAllByVehiclePlate(plate);
        double maxDelaySurcharge = 0.0;
        for ( RepairEntity repair : repairs){

            /* in case required data is null */
            if (repair.getExitVDate() == null || repair.getExitCDate() == null) {
                continue;
            }

            long diffInDays = ChronoUnit.DAYS.between(repair.getExitVDate(), repair.getExitCDate());
            if(diffInDays > 0){
                double delaySurchargePercentage = 0.05;
                double delaySurcharge = price * diffInDays * delaySurchargePercentage;
                if(delaySurcharge > maxDelaySurcharge){
                    maxDelaySurcharge = delaySurcharge;
                }
            }


        }

        return maxDelaySurcharge;
    }


    /*--------------------------------------------------------------------------------------------------------
     * antiquitySurcharge: method to calculate antiquity surcharge;
     *
     * @param price - total price;
     * @param fabricationYear - fabrication year;
     * @param type - vehicle type;
     * @return - the amount to be surcharged;
     --------------------------------------------------------------------------------------------------------*/

    public double antiquitySurcharge(double price, String plate) {
        VehicleEntity vehicle = vehicleRepository.findByRegistrationPlate(plate);
        int fabricationYear = vehicle.getFabricationYear();
        String type = vehicle.getType().toLowerCase();
        int antiquity = 2024 - fabricationYear;
        double surchargePercentage = 0.0;

        if (antiquity >= 6 && antiquity <= 10) {
            if (type.equals("sedan") || type.equals("hatchback")) {
                surchargePercentage = 0.05;
            } else {
                surchargePercentage = 0.07;
            }
        } else if (antiquity >= 11 && antiquity <= 15) {
            if (type.equals("sedan") || type.equals("hatchback")) {
                surchargePercentage = 0.09;
            } else {
                surchargePercentage = 0.11;
            }
        } else if (antiquity >= 16) {
            if (type.equals("sedan") || type.equals("hatchback")) {
                surchargePercentage = 0.15;
            } else {
                surchargePercentage = 0.20;
            }
        }

        double surchargeValue = price * surchargePercentage;


        return surchargeValue;

    }


    /*--------------------------------------------------------------------------------------------------------
    * mileageSurcharge: method to calculate mileage surcharge;
    *
    * @param price - total price;
    * @param mileage - mileage of the vehicle;
    * @param type - type of the vehicle;
    * @return - the amount to be surcharged;
    --------------------------------------------------------------------------------------------------------*/
    public double mileageSurcharge(double price, String plate) {
        VehicleEntity vehicle = vehicleRepository.findByRegistrationPlate(plate);
        int mileage = vehicle.getMilage();
        String type = vehicle.getType().toLowerCase();
        double surchargePercentage = 0.0;

        if (mileage >= 5001 && mileage <= 12000) {
            if (type.equals("sedan") || type.equals("hatchback")) {
                surchargePercentage = 0.03;
            } else {
                surchargePercentage = 0.05;
            }
        } else if (mileage >= 12001 && mileage <= 25000) {
            if (type.equals("sedan") || type.equals("hatchback")) {
                surchargePercentage = 0.07;
            } else {
                surchargePercentage = 0.09;
            }
        } else if (mileage >= 25001 && mileage <= 40000) {
            surchargePercentage = 0.12;
        } else {
            surchargePercentage = 0.20;
        }

        double surchargeValue = price * surchargePercentage;
        return surchargeValue;
    }


    /* DISCOUNTS */

    /*--------------------------------------------------------------------------------------------------------
     * dayDiscount: method to calculate day discount;
     *
     * @param price      - total price;
     * @param entryVDate - date of vehicle entry to the workshop;
     * @param entryVTime - time of vehicle entry to the workshop;
     * @return - the amount to be discounted;
     --------------------------------------------------------------------------------------------------------*/

    public double dayDiscount(double price, String plate) {
        List<RepairEntity> repairs = repairRepository.findAllByVehiclePlate(plate);
        boolean inRange = true;

        for (RepairEntity repair : repairs) {
            if (repair.getEntryVDate() == null || repair.getEntryVTime() == null) {
                return -1.0;
            } else if (!(repair.getEntryVDate().getDayOfWeek() == DayOfWeek.MONDAY || repair.getEntryVDate().getDayOfWeek() == DayOfWeek.THURSDAY)
                    || !(repair.getEntryVTime().isAfter(LocalTime.of(9, 0)) && repair.getEntryVTime().isBefore(LocalTime.of(12, 0)))) {
                inRange = false;
            }
        }
        if (inRange) {
            return price * 0.1;
        } else {
            return 0.0;
        }
    }


    /*--------------------------------------------------------------------------------------------------------
     * repAmountDiscount: method to calculate repair amount discount;
     *
     * @param plate      - vehicle plate number;
     * @return - the discount percentage applied based on the repair quantity and engine type;
     --------------------------------------------------------------------------------------------------------*/
    public double repAmountDiscount(String plate) {
        VehicleEntity vehicle = vehicleRepository.findByRegistrationPlate(plate);
        String engineType = vehicle.getEngineType().toLowerCase();
        List<RepairEntity> repairs = repairRepository.findAllByVehiclePlate(plate);
        LocalDate currentDate = LocalDate.now();
        int repairQuantity = 0;
        for (RepairEntity repair : repairs) {
            if (ChronoUnit.MONTHS.between(repair.getEntryVDate(), currentDate) <= 12) {
                repairQuantity = repairQuantity + 1;
            }
        }

        double discount = 0;

        if (repairQuantity >= 1 && repairQuantity <= 2) {
            if (engineType.equalsIgnoreCase("gasolina")) {
                discount = 0.05;
            } else if (engineType.equalsIgnoreCase("diesel")) {
                discount = 0.07;
            } else if (engineType.equalsIgnoreCase("hibrido")) {
                discount = 0.10;
            } else if (engineType.equalsIgnoreCase("electrico")) {
                discount = 0.08;
            }
        } else if (repairQuantity >= 3 && repairQuantity <= 5) {
            if (engineType.equalsIgnoreCase("gasolina")) {
                discount = 0.10;
            } else if (engineType.equalsIgnoreCase("diesel")) {
                discount = 0.12;
            } else if (engineType.equalsIgnoreCase("hibrido")) {
                discount = 0.15;
            } else if (engineType.equalsIgnoreCase("electrico")) {
                discount = 0.13;
            }
        } else if (repairQuantity >= 6 && repairQuantity <= 9) {
            if (engineType.equalsIgnoreCase("gasolina")) {
                discount = 0.15;
            } else if (engineType.equalsIgnoreCase("diesel")) {
                discount = 0.17;
            } else if (engineType.equalsIgnoreCase("hibrido")) {
                discount = 0.20;
            } else if (engineType.equalsIgnoreCase("electrico")) {
                discount = 0.18;
            }
        } else if (repairQuantity >= 10) {
            if (engineType.equalsIgnoreCase("gasolina")) {
                discount = 0.20;
            } else if (engineType.equalsIgnoreCase("diesel")) {
                discount = 0.22;
            } else if (engineType.equalsIgnoreCase("hibrido")) {
                discount = 0.25;
            } else if (engineType.equalsIgnoreCase("electrico")) {
                discount = 0.23;
            }
        }
        return discount;
    }


    /* SURCHARGE APPLICATION */

    /*--------------------------------------------------------------------------------------------------------
     * surchargeTotal: method to apply all surcharges;
     *
     * @param receipt - the receipt to apply all surcharges;
     * @return - null;
     --------------------------------------------------------------------------------------------------------*/
    public void surchargeTotal(ReceiptEntity receipt){
        double price = receipt.getTotalPayment();
        String plate = receipt.getVehiclePlate();
        double antSur = antiquitySurcharge(price, plate);
        double delSur = delaySurcharge(price, plate);
        double milSur = mileageSurcharge(price, plate);
        double totalSurcharge = antSur + delSur + milSur;
        receipt.setTotalSurcharge(totalSurcharge);
        receiptRepository.save(receipt);
    }


    /* DISCOUNT APPLICATION */

    /*--------------------------------------------------------------------------------------------------------
     * innerDiscount: method to calculate the total internal discount for a repair;
     *                (assuming autofix internal discounts only)
     *
     * @param repair - the repair entity for which the internal discount is being calculated;
     * @return - the total internal discount amount for the repair;
     --------------------------------------------------------------------------------------------------------*/
    public void innerDiscount(ReceiptEntity receipt){
        double price = receipt.getTotalPayment();
        String plate = receipt.getVehiclePlate();
        double dayDis = dayDiscount(price, plate);
        double repDisPercentage = repAmountDiscount(plate);
        double repDis = repDisPercentage * price;
        double inDiscount = dayDis + repDis;
        receipt.setTotalDiscount(inDiscount);
        receiptRepository.save(receipt);
    }


    /* APPLY SURCHARGES AND DISCOUNTS */

    /*--------------------------------------------------------------------------------------------------------
     * applySurAndDis: method to apply surcharges and inner discounts to a receipt;
     *
     * @param receipt - the receipt to modify;
     --------------------------------------------------------------------------------------------------------*/
    public void applySurAndDis(ReceiptEntity receipt){
        surchargeTotal(receipt);
        innerDiscount(receipt);
    }

    /* COUPON DISCOUNT */

    /*--------------------------------------------------------------------------------------------------------
     * couponIndex: method to retrieve the index of a brand in a coupon list;
     *
     * @param brand - the brand of the vehicle;
     * @return - the index of the brand in the coupon list, or -1 if the brand is not found;
     --------------------------------------------------------------------------------------------------------*/
    public int couponIndex(String brand) {
        int index = -1;
        switch (brand.toLowerCase()) {
            case "toyota":
                index = 0;
                break;
            case "ford":
                index = 1;
                break;
            case "hyundai":
                index = 2;
                break;
            case "honda":
                index = 3;
                break;
        }
        return index;
    }

    /*--------------------------------------------------------------------------------------------------------
     * applyCoupont: method to update the quantity of coupons for a specific brand and return the updated list;
     *
     * @param plate - plate of the vehicle;
     * @param coupon - a list of lists containing coupon values;
     * @return - the updated list of coupon values after decrementing the quantity of coupons for the specified brand;
     --------------------------------------------------------------------------------------------------------*/
    public List<List<Double>> applyCoupon(String plate, List<List<Double>> coupons){
        ReceiptEntity receipt = receiptRepository.findByVehiclePlate(plate);
        VehicleEntity vehicle = vehicleRepository.findByRegistrationPlate(plate);
        String brand = vehicle.getBrand();
        int index = couponIndex(brand);
        double coupon = 0.0;
        double quantity = coupons.get(1).get(index);
        if(quantity != 0){
            coupon = coupons.get(0).get(index);
            coupons.get(1).set(index, quantity - 1.0);
            receipt.setCouponAssigned(coupon);
            receiptRepository.save(receipt);
        }
        return coupons;
    }


    /* REAL PAYMENT */

    /*--------------------------------------------------------------------------------------------------------
     * applyRealPayment: method to calculate and apply the real payment for a receipt associated with a specific vehicle plate;
     *
     * @param plate - the vehicle plate for which the real payment is to be applied;
     * @return - the amount of calculated real payment applied to the receipt associated with the specified vehicle plate;
     --------------------------------------------------------------------------------------------------------*/
    public ReceiptEntity applyRealPayment(String plate){
        ReceiptEntity receipt = receiptRepository.findByVehiclePlate(plate);
        double realPayment = (receipt.getTotalPayment() + receipt.getTotalSurcharge() -
                             receipt.getTotalDiscount());
        realPayment = realPayment + (realPayment * 0.19);
        realPayment = realPayment - receipt.getCouponAssigned();
        receipt.setRealPayment(realPayment);
        receiptRepository.save(receipt);
        return receipt;
    }


    /* REPORTS */

    /* R1 */
    /* getReceipts OPERATION USED TO GET R1 NEEDED VALUES */


}
