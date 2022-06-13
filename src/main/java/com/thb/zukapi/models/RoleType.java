package com.thb.zukapi.models;

public enum RoleType {
    MANGER, /*only CRU operations*/
    SEEKER,
    HELPER,
    ADMIN /*Super user. CRUD operations*/
}
