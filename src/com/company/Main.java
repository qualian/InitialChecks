package com.company;

public class Main {

    public static void main(String[] args) {
        java.io.File hwmonDir = new java.io.File("/sys/class/hwmon");
        java.io.File w1Dir = new java.io.File("/sys/bus/w1/devices");

        boolean foundSensor = false;

        if (hwmonDir.exists() && hwmonDir.isDirectory()) {
            java.io.File[] sensors = hwmonDir.listFiles(java.io.File::isDirectory);
            if (sensors != null) {
                for (java.io.File sensor : sensors) {
                    String name = sensor.getName();
                    java.io.File nameFile = new java.io.File(sensor, "name");
                    if (nameFile.exists()) {
                        try {
                            name = java.nio.file.Files.readString(nameFile.toPath()).trim();
                        } catch (java.io.IOException ignored) {
                        }
                    }
                    System.out.println(name + " - online");
                    foundSensor = true;
                }
            }
        }

        if (w1Dir.exists() && w1Dir.isDirectory()) {
            java.io.File[] devices = w1Dir.listFiles(file -> !file.getName().equals("w1_bus_master1"));
            if (devices != null) {
                for (java.io.File dev : devices) {
                    System.out.println(dev.getName() + " - online");
                    foundSensor = true;
                }
            }
        }

        if (!foundSensor) {
            System.out.println("No sensors detected.");
        }
    }
}
