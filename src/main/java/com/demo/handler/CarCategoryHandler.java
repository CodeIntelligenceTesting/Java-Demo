package com.demo.handler;

import com.demo.controller.CarCategoryController;
import com.demo.dto.CarCategoryDTO;
import com.demo.dto.UserDTO;
import com.demo.helper.DatabaseMock;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Handler class that provides methods for controller class to utilize.
 */
public class CarCategoryHandler {

    private static final DatabaseMock db = DatabaseMock.getInstance();

    /**
     * Handler function with robustness issue, that handles querying of all vip categories
     * Called by {@link CarCategoryController#getCategories(String)}.
     * @return collection of category objects or null if no categories found
     */
    public static Collection<CarCategoryDTO> returnVIPCategories(){
        Collection<CarCategoryDTO> vipCategories = null;
        for (CarCategoryDTO category : db.getAllCategories()) {
            if (category.getVisibleTo() == UserDTO.Role.VIP_USER) {
                if (vipCategories == null) {
                    vipCategories = new ArrayList<>();
                }
                vipCategories.add(category);
            }
        }

        return vipCategories;
    }

    /**
     * Robust handler function that handles querying of all standard categories
     * Called by {@link CarCategoryController#getCategories(String)}.
     * @return collection of category objects
     */
    public static Collection<CarCategoryDTO> returnDefaultCategories(){
        Collection<CarCategoryDTO> defaultCategories = new ArrayList<>();
        for (CarCategoryDTO category : db.getAllCategories()) {
            if (category.getVisibleTo() == UserDTO.Role.DEFAULT_USER) {
                defaultCategories.add(category);
            }
        }
        return defaultCategories;
    }

    /**
     * Handler function that returns the requested category or null, if it does not exist.
     * Called by {@link CarCategoryController#getCategory(String, String)}
     * @param id category id
     * @return category dto or null
     */
    public static CarCategoryDTO returnSpecificCategory(String id) {
        return db.getCategoryWithId(id);
    }

    /**
     * Handler function that attempts to delete specified category.
     * Called by {@link CarCategoryController#deleteCategory(String, String)}
     * @param id category id to delete.
     * @return if deletion was successful. False if category did not exist.
     */
    public static boolean deleteCategory(String id) {
        return db.deleteCategory(id);
    }

    /**
     * Vulnerable handler function that attempts to check that the date time is acceptable, but not catching the hidden runtime exception.
     * Called by {@link CarCategoryController#createCategory(String, CarCategoryDTO)}.
     * Error is hidden in {@link CarCategoryHandler#verifyCreationDate(CarCategoryDTO)}.
     * @param categoryDTO dto to save
     * @return id of newly saved category.
     */
    public static String createCategory(CarCategoryDTO categoryDTO){
        //Not catching WrongDateTimeFormat exceptions
        if (CarCategoryHandler.verifyCreationDate(categoryDTO)) {
            return updateCategory(categoryDTO, db.getNextFreeCategoryId());
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Robust handler function that updates or creates a new user with a given id.
     * Called by {@link CarCategoryController#updateOrCreateCategory(String, String, CarCategoryDTO)}
     * @param categoryDTO dto object with new category infos
     * @param id id of category
     * @return the id of the changed object
     */
    public static String updateCategory(CarCategoryDTO categoryDTO, String id) {
        db.putCategory(id, categoryDTO);
        return id;
    }

    public static boolean verifyCreationDate(CarCategoryDTO dto){
        if (dto.getCreationDate() == null) {
            return false;
        } else {
            return LocalDateTime.parse(dto.getCreationDate()).isBefore(LocalDateTime.now());
        }
    }
}
