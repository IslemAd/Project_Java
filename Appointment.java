package com.example.myapplication01;


public class Appointment {
    private int appointmentId;
    private int doctorId;
    private int patientId;
    private String date;
    private String time;
    private String patientName;


    public Appointment(int appointmentId, int doctorId, int patientId, String date, String time) {
        this.appointmentId = appointmentId;
        this.doctorId = doctorId;
        this.patientId = patientId;
        this.date = date;
        this.time = time;
        this.patientName = patientName;
    }

    // Getters et setters
    public int getAppointmentId() { return appointmentId; }
    public int getDoctorId() { return doctorId; }
    public int getPatientId() { return patientId; }
    public String getDate() { return date; }
    public String getTime() { return time; }

    public String getPatientName() { return patientName; }

    @Override
    public String toString() {
        return "Appointment on " + date + " at " + time + " with " + patientName;
    }

}

