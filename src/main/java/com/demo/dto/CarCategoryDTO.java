package com.demo.dto;

import java.time.LocalDateTime;

/**
 * Typical and uninteresting DTO object
 */
public class CarCategoryDTO {
    private String creationDate;
    private String description;
    private UserDTO.Role visibleTo;

    public CarCategoryDTO(){}

    public CarCategoryDTO(String description, UserDTO.Role visibleTo, String creationDate) {
        this.description = description;
        this.visibleTo = visibleTo;
        this.creationDate = creationDate;
    }

    public String getDescription() {
        return description;
    }


    public UserDTO.Role getVisibleTo() {
        return visibleTo;
    }

    public String getCreationDate() {
        return creationDate;
    }

    /**
     * Constructed date time parser example to showcase missing safety checks.
     * Throws hidden DateTimeParseException.
     * Called directly by {@link com.demo.handler.CarCategoryHandler#createCategory(CarCategoryDTO)}.
     * Called ultimately by {@link com.demo.Controller.CarCategoryController#createCategory(String, CarCategoryDTO)}.
     * @return if it is possible to parse a date time object from the saved string
     */
    public boolean verifyCreationDate(){
        if (this.creationDate == null) {
            return false;
        } else {
            return LocalDateTime.parse(this.creationDate).isBefore(LocalDateTime.now());
        }
    }
}
