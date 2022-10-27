package com.voidstudio.quickcashreg;public class FirebaseDataBase{private com.google.firebase.database.FirebaseDatabase firebaseDB;private static final java.lang.String FIREBASE_URL =
            "https://quickcash-bd58f-default-rtdb.firebaseio.com/";private com.google.firebase.database.DatabaseReference firebaseDBReference;private com.google.firebase.database.DatabaseReference userChild;private java.lang.String firebaseString;	public FirebaseDataBase()	{	}/**
     * Database initializer for log in activity
     */
    protected void initializeDatabase(){
        firebaseDB = com.google.firebase.database.FirebaseDatabase.getInstance(FIREBASE_URL);
        firebaseDBReference = firebaseDB.getReferenceFromUrl(FIREBASE_URL);
        userChild = firebaseDBReference.child("users");
    }}