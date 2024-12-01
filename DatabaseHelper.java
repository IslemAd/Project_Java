package com.example.myapplication01;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "MedicalApp.db";
    private static final int DATABASE_VERSION = 6;


    // Table des utilisateurs
    public static final String TABLE_USERS = "Users";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_ROLE = "role";
    public static final String COLUMN_SPECIALTY = "specialty";
    public static final String COLUMN_Education = "education";
    public static final String COLUMN_Sex = "sex";


    // Table des dossiers médicaux
    public static final String TABLE_MEDICAL_RECORDS = "MedicalRecords";
    public static final String COLUMN_RECORD_ID = "record_id";
    public static final String COLUMN_PATIENT_ID = "patient_id";
    public static final String COLUMN_MEDICAL_DETAILS = "medical_details";

    // Table des rendez-vous
    public static final String TABLE_APPOINTMENTS = "Appointments";
    public static final String COLUMN_APPOINTMENT_ID = "appointment_id";
    public static final String COLUMN_DOCTOR_ID = "doctor_id";

    public static final String COLUMN_APPOINTMENT_DATE = "appointment_date";
    public static final String COLUMN_APPOINTMENT_TIME = "appointment_time";

    // Table des disponibilités
    public static final String TABLE_AVAILABILITIES = "Availabilities";
    public static final String COLUMN_AVAILABILITY_ID = "availability_id";
    public static final String COLUMN_DAY_OF_WEEK = "day_of_week";
    public static final String COLUMN_START_TIME = "start_time";
    public static final String COLUMN_END_TIME = "end_time";

    // Table des rappels
    public static final String TABLE_REMINDERS = "Reminders";
    public static final String COLUMN_REMINDER_ID = "reminder_id";
    public static final String COLUMN_PATIENT_NAME = "patient_name";
    public static final String COLUMN_REMINDER_DATE = "reminder_date";
    public static final String COLUMN_REMINDER_MESSAGE = "reminder_message";




    // Requête SQL pour créer les tables

    private static final String CREATE_TABLE_USERS =
            "CREATE TABLE " + TABLE_USERS + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_USERNAME + " TEXT NOT NULL, " +
                    COLUMN_EMAIL + " TEXT NOT NULL, " +
                    COLUMN_PASSWORD + " TEXT NOT NULL, " +
                    COLUMN_ROLE + " TEXT NOT NULL, " +
                    COLUMN_SPECIALTY + " TEXT, " +
                    COLUMN_Education + " TEXT, " +
                    COLUMN_Sex + " TEXT" +
                    ");";

    private static final String CREATE_TABLE_MEDICAL_RECORDS =
            "CREATE TABLE " + TABLE_MEDICAL_RECORDS + " (" +
                    COLUMN_RECORD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_PATIENT_ID + " INTEGER NOT NULL, " +
                    COLUMN_MEDICAL_DETAILS + " TEXT NOT NULL, " +
                    "FOREIGN KEY(" + COLUMN_PATIENT_ID + ") REFERENCES " + TABLE_USERS + "(" + COLUMN_ID + ") ON DELETE CASCADE" +
                    ");";
    private static final String CREATE_TABLE_APPOINTMENTS =
            "CREATE TABLE " + TABLE_APPOINTMENTS + " (" +
                    COLUMN_APPOINTMENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_DOCTOR_ID + " INTEGER NOT NULL, " +
                    COLUMN_PATIENT_ID + " INTEGER NOT NULL, " +
                    COLUMN_APPOINTMENT_DATE + " TEXT NOT NULL, " +
                    COLUMN_APPOINTMENT_TIME + " TEXT NOT NULL, " +
                    "FOREIGN KEY(" + COLUMN_DOCTOR_ID + ") REFERENCES " + TABLE_USERS + "(" + COLUMN_ID + "), " +
                    "FOREIGN KEY(" + COLUMN_PATIENT_ID + ") REFERENCES " + TABLE_USERS + "(" + COLUMN_ID + ")" +
                    ");";

    private static final String CREATE_TABLE_AVAILABILITIES =
            "CREATE TABLE " + TABLE_AVAILABILITIES + " (" +
                    COLUMN_AVAILABILITY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_DOCTOR_ID + " INTEGER NOT NULL, " +
                    COLUMN_DAY_OF_WEEK + " TEXT NOT NULL, " +
                    COLUMN_START_TIME + " TEXT NOT NULL, " +
                    COLUMN_END_TIME + " TEXT NOT NULL, " +
                    "FOREIGN KEY(" + COLUMN_DOCTOR_ID + ") REFERENCES " + TABLE_USERS + "(" + COLUMN_ID + ")" +
                    ");";
    private static final String CREATE_TABLE_REMINDERS =
            "CREATE TABLE " + TABLE_REMINDERS + " (" +
                    COLUMN_REMINDER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_PATIENT_NAME + " TEXT NOT NULL, " +
                    COLUMN_REMINDER_DATE + " TEXT NOT NULL, " +
                    COLUMN_REMINDER_MESSAGE + " TEXT NOT NULL" +
                    ");";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_MEDICAL_RECORDS);
        db.execSQL(CREATE_TABLE_APPOINTMENTS);
        db.execSQL(CREATE_TABLE_AVAILABILITIES);
        db.execSQL(CREATE_TABLE_REMINDERS);

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE " + TABLE_USERS + " ADD COLUMN " + COLUMN_SPECIALTY + " TEXT");
        }
        if (oldVersion < 3) {
            db.execSQL("ALTER TABLE " + TABLE_USERS + " ADD COLUMN " + COLUMN_Education + " TEXT");
        }
        if (oldVersion < 4) {
            db.execSQL("ALTER TABLE " + TABLE_USERS + " ADD COLUMN " + COLUMN_Sex + " TEXT");
        }
    }

    public boolean checkUserCredentials(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            // Sélectionner l'utilisateur avec le nom d'utilisateur et mot de passe
            String query = "SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_USERNAME + " = ? AND " + COLUMN_PASSWORD + " = ?";
            cursor = db.rawQuery(query, new String[]{username, password});

            // Si un utilisateur correspondant est trouvé, retourner true, sinon false
            if (cursor != null && cursor.moveToFirst()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return false;
    }

    public String getUserRole(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        String role = null;

        try {
            // Sélectionner le rôle de l'utilisateur en fonction de son nom d'utilisateur
            String query = "SELECT " + COLUMN_ROLE + " FROM " + TABLE_USERS + " WHERE " + COLUMN_USERNAME + " = ?";
            cursor = db.rawQuery(query, new String[]{username});

            // Si un utilisateur correspondant est trouvé, récupérer son rôle
            if (cursor != null && cursor.moveToFirst()) {
                role = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ROLE));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return role;
    }


    public boolean insertUser(String username, String email, String password, String role, String specialty, String education, String sex) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        // Ajouter les valeurs à ContentValues
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_PASSWORD, password); // Vous devriez hasher le mot de passe avant de l'enregistrer
        values.put(COLUMN_ROLE, role);
        values.put(COLUMN_SPECIALTY, specialty); // Spécialité peut être null ou vide pour un patient
        values.put(COLUMN_Education, education);
        values.put(COLUMN_Sex, sex);

        long result = db.insert(TABLE_USERS, null, values); // Insérer l'utilisateur dans la table

        db.close();

        return result != -1; // Retourne true si l'insertion a réussi, false sinon
    }


    // Méthode pour ajouter un dossier médical
    public boolean addMedicalRecord(int patientId, String medicalDetails) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PATIENT_ID, patientId);
        values.put(COLUMN_MEDICAL_DETAILS, medicalDetails);

        long result = db.insert(TABLE_MEDICAL_RECORDS, null, values);
        db.close();
        return result != -1; // Retourne true si l'insertion réussit
    }

    // Méthode pour mettre à jour un dossier médical
    public boolean updateMedicalRecord(int recordId, String medicalDetails) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_MEDICAL_DETAILS, medicalDetails);

        int rowsAffected = db.update(TABLE_MEDICAL_RECORDS, values, COLUMN_RECORD_ID + " = ?", new String[]{String.valueOf(recordId)});
        db.close();
        return rowsAffected > 0;
    }
    // Méthode pour ajouter un rendez-vous
    public boolean addAppointment(int doctorId, int patientId, String appointmentDate, String appointmentTime, String patientName) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Créer un objet ContentValues pour insérer les données dans la table des rendez-vous
        ContentValues values = new ContentValues();
        values.put(COLUMN_DOCTOR_ID, doctorId);
        values.put(COLUMN_PATIENT_ID, patientId);
        values.put(COLUMN_APPOINTMENT_DATE, appointmentDate);
        values.put(COLUMN_APPOINTMENT_TIME, appointmentTime);

        // Insérer le rendez-vous dans la base de données
        long result = db.insert(TABLE_APPOINTMENTS, null, values);
        db.close();

        // Retourner true si l'insertion a réussi
        return result != -1;
    }
    // Méthode pour ajouter une disponibilité
    public boolean insertAvailability(int doctorId, String dayOfWeek, String startTime, String endTime) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_DOCTOR_ID, doctorId);
        values.put(COLUMN_DAY_OF_WEEK, dayOfWeek);
        values.put(COLUMN_START_TIME, startTime);
        values.put(COLUMN_END_TIME, endTime);

        long result = db.insert(TABLE_AVAILABILITIES, null, values);
        db.close();
        return result != -1; // Retourne true si l'insertion réussit
    }
    // Méthode pour mettre à jour un rendez-vous
    public boolean updateAppointment(int appointmentId, String newAppointmentDate, String newAppointmentTime) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_APPOINTMENT_DATE, newAppointmentDate);  // Mettre à jour la date
        values.put(COLUMN_APPOINTMENT_TIME, newAppointmentTime);  // Mettre à jour l'heure

        // Condition pour identifier le rendez-vous à mettre à jour (basé sur appointmentId)
        String whereClause = COLUMN_APPOINTMENT_ID + " = ?";
        String[] whereArgs = { String.valueOf(appointmentId) };

        // Effectuer la mise à jour
        int rowsUpdated = db.update(TABLE_APPOINTMENTS, values, whereClause, whereArgs);
        db.close();

        return rowsUpdated > 0;  // Retourner vrai si au moins une ligne a été mise à jour
    }
    // Méthode pour supprimer un rendez-vous
    public boolean deleteAppointment(int appointmentId) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Spécifiez la condition de suppression basée sur l'ID du rendez-vous
        String whereClause = COLUMN_APPOINTMENT_ID + " = ?";
        String[] whereArgs = { String.valueOf(appointmentId) };

        // Supprimer le rendez-vous
        int rowsDeleted = db.delete(TABLE_APPOINTMENTS, whereClause, whereArgs);
        db.close();

        // Retourner true si au moins une ligne a été supprimée
        return rowsDeleted > 0;
    }
    // Méthode pour insérer un rappel
    public boolean insertReminder(String patientName, String reminderDate, String reminderMessage) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PATIENT_NAME, patientName);
        values.put(COLUMN_REMINDER_DATE, reminderDate);
        values.put(COLUMN_REMINDER_MESSAGE, reminderMessage);
        // Insertion dans la table des rappels
        long result = db.insert(TABLE_REMINDERS, null, values);
        db.close();

        // Si l'insertion échoue, result vaudra -1
        return result != -1;
    }



    /**
     * Méthode pour récupérer tous les patients depuis la table des utilisateurs.
     *
     * @return Une liste contenant les noms des patients.
     */
    public List<String> getAllPatients() {
        List<String> patientsList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Requête pour sélectionner uniquement les utilisateurs avec le rôle "patient"
        String query = "SELECT " + COLUMN_USERNAME + " FROM " + TABLE_USERS + " WHERE " + COLUMN_ROLE + " = ?";
       Cursor cursor = null;

        try {
            cursor = db.rawQuery(query, new String[]{"patient"});
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    patientsList.add(cursor.getString(0)); // Récupère le nom d'utilisateur
               }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
           if (cursor != null) {
                cursor.close();
            }
           db.close();
        }

        return patientsList;
    }

    // Méthode pour rechercher les rendez-vous d'un médecin
    public List<Appointment> searchAppointmentsByDoctorId(int doctorId, String query) {
        List<Appointment> appointments = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            // Requête pour récupérer les rendez-vous du médecin, filtrée par date ou autre critère
            String selectQuery = "SELECT * FROM " + TABLE_APPOINTMENTS +
                    " WHERE " + COLUMN_DOCTOR_ID + " = ? AND " +
                    COLUMN_APPOINTMENT_DATE + " LIKE ?";
            cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(doctorId), "%" + query + "%"});

            // Ajouter chaque rendez-vous comme objet Appointment
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    int appointmentId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_APPOINTMENT_ID));
                    int patientId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PATIENT_ID));
                    String date = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_APPOINTMENT_DATE));
                    String time = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_APPOINTMENT_TIME));


                    appointments.add(new Appointment(appointmentId, doctorId, patientId, date, time));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return appointments;
    }


    // Alternative : Retourne une liste de rendez-vous avec tous les détails
    public List<Appointment> getAppointmentsByDoctorId(int doctorId) {
        List<Appointment> appointments = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            // Requête pour récupérer les détails des rendez-vous
            String query = "SELECT * FROM " + TABLE_APPOINTMENTS +
                    " WHERE " + COLUMN_DOCTOR_ID + " = ?";
            cursor = db.rawQuery(query, new String[]{String.valueOf(doctorId)});

            // Ajouter chaque rendez-vous comme objet Appointment
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    int appointmentId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_APPOINTMENT_ID));
                    int patientId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PATIENT_ID));
                    String date = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_APPOINTMENT_DATE));
                    String time = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_APPOINTMENT_TIME));
                    appointments.add(new Appointment(appointmentId, doctorId, patientId, date, time));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return appointments;
    }

}



