package com.thb.zukapi.models;

public enum RoleType {
    MANAGER, /*only CRU operations*/
    SEEKER,
    HELPER,
    ADMIN /*Super user. CRUD operations*/
}
