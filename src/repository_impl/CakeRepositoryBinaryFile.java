package repository_impl;

import domain.Identifiable;

import java.io.*;
import java.util.Map;


public class CakeRepositoryBinaryFile<T extends Identifiable> extends FileRepository<T> {

    public CakeRepositoryBinaryFile(String file) {
        super(file);
    }

    @Override
    protected void readfromfile() {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
            repository = (Map<Long, T>) ois.readObject();
            System.out.println("test");
        } catch (IOException e) {
            throw new RuntimeException("Error reading from file: " + file, e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Class not found while reading from file: " + file, e);
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error reading from file: " + file, e);
        }
    }
    @Override
    protected void writefromfile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(repository);
        } catch (IOException e) {
            throw new RuntimeException("Error writing to file: " + file, e);
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error writing to file: " + file, e);
        }
    }

}

