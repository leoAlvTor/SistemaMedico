package controller;

import java.math.BigInteger;
import java.util.List;

/**
 * The interface CRUD defines the methods for create, update, read and delete to database.
 *
 * @param <E>
 *     The class which is going to inherit it.
 *
 * @version 1.0
 *
 * @author Leo Alvarado
 */
public interface CRUD<E>{
    /**
     * Defines a method for create a new record in a database.
     *
     * @param objects objects to being inserted into table.
     * @return the number of objects created.
     */
    BigInteger createRecord(Object... objects);

    /**
     * Defines a method for getting a record by its ID.
     *
     * @param id the ID of the record.
     * @return the object which belongs to its id.
     */
    E getRecordById(Object id);

    /**
     * Defines a method for getting all records.
     *
     * @return a list of objects.
     */
    List<E> getAll();

    /**
     * Defines a method for updating a record.
     *
     * @param id represents the records id.
     * @param objects an array of objects to update the record.
     * @return the number of records updated.
     */
    int updateRecord(Object id, Object... objects);

    /**
     * Defines a method for deleting a records.
     *
     * @param id represents the id of object which is going to be deleted.
     * @return the number of records deleted.
     */
    int deleteRecord(Object id);

}
