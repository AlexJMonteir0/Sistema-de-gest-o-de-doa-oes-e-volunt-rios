package br.com.alex.gestao_de_doaoes_e_voluntarios_alex.controller;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseController {
    private FirebaseDatabase database;
    private DatabaseReference usersRef;
    private DatabaseReference donationsRef;
    private DatabaseReference missionsRef;
    private DatabaseReference tasksRef;
    private DatabaseReference reportsRef;
    private DatabaseReference notificationsRef;

    public FirebaseController() {
        database = FirebaseDatabase.getInstance();
        usersRef = database.getReference("users");
        donationsRef = database.getReference("donations");
        missionsRef = database.getReference("missions");
        tasksRef = database.getReference("tasks");
        reportsRef = database.getReference("reports");
        notificationsRef = database.getReference("notifications");
    }

    public DatabaseReference getUsersRef() {
        return usersRef;
    }

    public DatabaseReference getDonationsRef() {
        return donationsRef;
    }

    public DatabaseReference getMissionsRef() {
        return missionsRef;
    }

    public DatabaseReference getTasksRef() {
        return tasksRef;
    }

    public DatabaseReference getReportsRef() {
        return reportsRef;
    }

    public DatabaseReference getNotificationsRef() {
        return notificationsRef;
    }
}



