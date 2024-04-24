package com.demo.dto;

/**
 * Typical and uninteresting DTO object
 */
public class CarCategoryDTO {

    public enum TrunkSize{
        ONE_CASE,
        TWO_CASES,
        THREE_CASES,
        FOUR_CASES;

        public static TrunkSize fromString(String enumString) {
            if (enumString.equalsIgnoreCase("TWO_CASES")) {
                return TWO_CASES;
            } else if (enumString.equalsIgnoreCase("THREE_CASES")) {
                return THREE_CASES;
            } else if (enumString.equalsIgnoreCase("FOUR_CASES")) {
                return FOUR_CASES;
            }
            return ONE_CASE;
        }
    }
    private String description;
    private TrunkSize trunkSize;
    private int seats;
    private String exampleCar;
    private UserDTO.Role visibleTo;
    private String creationDate;

    public CarCategoryDTO(){}

    public CarCategoryDTO(String description,
                          TrunkSize trunkSize,
                          int seats,
                          String exampleCar,
                          UserDTO.Role visibleTo) {
        this.description = description;
        this.trunkSize = trunkSize;
        this.seats = seats;
        this.exampleCar = exampleCar;
        this.visibleTo = visibleTo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TrunkSize getTrunkSize() {
        return trunkSize;
    }

    public void setTrunkSize(TrunkSize trunkSize) {
        this.trunkSize = trunkSize;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public String getExampleCar() {
        return exampleCar;
    }

    public void setExampleCar(String exampleCar) {
        this.exampleCar = exampleCar;
    }

    public UserDTO.Role getVisibleTo() {
        return visibleTo;
    }

    public void setVisibleTo(UserDTO.Role visibleTo) {
        this.visibleTo = visibleTo;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }
}
