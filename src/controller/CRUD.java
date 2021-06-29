package controller;

import java.math.BigInteger;
import java.sql.Connection;
import java.util.List;

public abstract class CRUD<E>{

    public abstract BigInteger createRecord(Object ... objects);

    public abstract E getRecordById(Object id);

    public abstract List<E> getAll();

    public abstract boolean updateRecord(E instance);

    public abstract boolean deleteRecord(E instance);

}
