package Network;

import Game.GameModel;

import java.io.*;

public class Blob implements Serializable{

    //converting object to bytes
    public static byte[] toStream (Object o) {
        byte[] stream = null;

        try (
            ByteArrayOutputStream BAOS = new ByteArrayOutputStream();
            ObjectOutputStream OOS = new ObjectOutputStream(BAOS);) {
            OOS.writeObject(o);
            stream = BAOS.toByteArray();
            OOS.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stream;
    }


    //converting bytes to object
    public static Object toObject (byte[] stream) {
        Object o = null;
        try (
            ByteArrayInputStream BAIS = new ByteArrayInputStream(stream);
            ObjectInputStream OIS = new ObjectInputStream(BAIS)) {
            o = OIS.readObject();

            if (o instanceof GameModel) {
                System.out.println(((GameModel) o). getCurrentPlayer().getName());
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return o;
    }

}
