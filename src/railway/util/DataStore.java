package railway.util;

import java.io.*;

/** Saves/loads objects to disk using Java serialization (Day 17 - File Handling). */
public final class DataStore {
    private DataStore() {}

    public static void save(Serializable obj, String path) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(path))) {
            out.writeObject(obj);
        } catch (IOException e) {
            System.err.println("Could not save data: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T load(String path) {
        File f = new File(path);
        if (!f.exists()) return null;
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(f))) {
            return (T) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Could not load data: " + e.getMessage());
            return null;
        }
    }
}
