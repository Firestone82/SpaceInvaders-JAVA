package cz.devfire.java2.spaceInvaders.client.object.other;

import cz.devfire.java2.spaceInvaders.client.enums.Constants;
import javafx.geometry.Point2D;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.HttpURLConnection;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;

public class Utils {

    public static Point2D getPoint(double x, double y) {
        return new Point2D(x, y);
    }

    public static Point2D getPoint(Point2D point) {
        return new Point2D(point.getX(),Constants.GAME_HEIGHT - point.getY());
    }

    public static String getPads(int pos) {
        if (pos < 10) {
            return "     "+ pos;
        } else {
            return String.valueOf(pos);
        }
    }

    public static int getRandom(int min, int max) {
        return new Random().nextInt(min,max + 1);
    }

    public static String getTimerString(long millis) {
        String s = "";
        Long amount = 0L;

        amount /= 60000L;
        millis %= 60000L;
        s = s + ((amount < 10L) ? "0" : "") + amount + ":";

        amount = millis / 1000L;
        millis = millis % 1000L;
        s = s + ((amount < 10L) ? "0" : "") + amount + ":";

        s = s + ((millis < 100L) ? ("0" + ((millis < 10L) ? "0" : "")) : "") + millis;
        return s;
    }

    public static void allowMethods(String... methods) {
        try {
            Field methodsField = HttpURLConnection.class.getDeclaredField("methods");

            Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            modifiersField.setInt(methodsField, methodsField.getModifiers() & ~Modifier.FINAL);

            methodsField.setAccessible(true);

            String[] oldMethods = (String[]) methodsField.get(null);
            Set<String> methodsSet = new LinkedHashSet<>(Arrays.asList(oldMethods));
            methodsSet.addAll(Arrays.asList(methods));
            String[] newMethods = methodsSet.toArray(new String[0]);

            methodsField.set(null /*static field*/, newMethods);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
    }
}
